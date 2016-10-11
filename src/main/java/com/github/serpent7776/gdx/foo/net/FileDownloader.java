package com.github.serpent7776.gdx.foo.net;

import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.utils.StreamUtils;

public class FileDownloader implements Runnable {

	/**
	 * connection timeout
	 */
	private static final int CONNECT_TIMEOUT_MS = 30000; //30 seconds

	/**
	 * timeout for read operations
	 */
	private static final int READ_TIMEOUT_MS = 30000; //30 seconds

	public interface DownloadHandler {

		/**
		 * Pass local file name and remote url of download that is being started
		 */
		void nextFile(File local, URL remote);

		/**
		 * Process response code of download file request
		 */
		void handleResponseCode(int code);

		/**
		 * Process temporary file before it's renamed to target name.
		 * If this method returns true temp file is renamed to target name overwriting if exists
		 * If this method returns false temp file is deleted
		 */
		boolean handleTempFile(File tmpFile);

		/**
		 * Process downloaded file after it was renamed to target name
		 */
		void handleFile(File file);

		/**
		 * Process last-modified header of HEAD request is checkLastModified option was set to true.
		 * If this method returns true, download will proceed and file will be downloaded using GET.
		 * If this method returns false file will not be downloaded.
		 */
		boolean handleLastModified(long timestamp);

	}

	public static class DownloadAdapter implements DownloadHandler {

		@Override
		public void nextFile(File local, URL remote) {
			//do nothing
		}

		@Override
		public void handleResponseCode(int code) {
			//do nothing
		}

		@Override
		public boolean handleTempFile(File tmpFile) {
			return true;
		}

		@Override
		public void handleFile(File file) {
			//do nothing
		}

		@Override
		public boolean handleLastModified(long timestamp) {
			return true;
		}

	}

	private final DownloadHandler handler;

	/**
	 * queue of files to be downloaded
	 * map(local_file: file_url)
	 */
	private final HashMap<File, URL> queue;

	private boolean checkLastModified = false;

	public FileDownloader(DownloadHandler handler) {
		this.handler = handler;
		this.queue = new HashMap<File, URL>();
	}

	public void checkLastModified(boolean check) {
		checkLastModified = check;
	}

	public void addFile(File local, URL remote) {
		queue.put(local, remote);
	}

	/* private methods */

	private String getTmpFileName() {
		final long timestamp = System.currentTimeMillis();
		return ".download-" + timestamp + ".part";
	}

	private boolean checkHead(URL remote) {
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) remote.openConnection();
			connection.setRequestMethod("HEAD");
			connection.setUseCaches(false);
			connection.setConnectTimeout(CONNECT_TIMEOUT_MS);
			// fix for android 4.x throwing EOFException on HEAD request
			// http://stackoverflow.com/questions/17638398/androids-httpurlconnection-throws-eofexception-on-head-requests
			connection.setRequestProperty("Accept-Encoding", "");
			connection.connect();
			final int responseCode = connection.getResponseCode();
			boolean ret = false;
			if (responseCode == HttpURLConnection.HTTP_OK) {
				final long timestamp = connection.getLastModified();
				ret = handler.handleLastModified(timestamp);
			} else {
				// TODO: handle error code
			}
			connection.disconnect();
			return ret;
		} catch (EOFException e) {
			// ignore, should not happen
		} catch (IOException e) {
			// TODO: handle cannot connect error
		} catch (IllegalStateException e) {
			// TODO: handle error
		}
		if (connection != null) {
			connection.disconnect();
		}
		return false;
	}

	private void processFile(File outputFile, URL remote) {
		try {
			final HttpURLConnection connection = (HttpURLConnection) remote.openConnection();
			connection.setUseCaches(false);
			connection.setConnectTimeout(CONNECT_TIMEOUT_MS);
			connection.setReadTimeout(READ_TIMEOUT_MS);
			connection.connect();
			final int responseCode = connection.getResponseCode();
			if (handler != null) {
				handler.handleResponseCode(responseCode);
			}
			if (responseCode != HttpURLConnection.HTTP_OK) {
				connection.disconnect();
				return;
			}
			final String tmpFileName = getTmpFileName();
			final File tmpFile = new File(outputFile.getParent(), tmpFileName);
			try {
				final InputStream input = connection.getInputStream();
				final OutputStream output = new FileOutputStream(tmpFile);
				StreamUtils.copyStream(input, output);
				StreamUtils.closeQuietly(input);
				StreamUtils.closeQuietly(output);
			} catch (IOException e) {
				// TODO: handle error
				return;
			} finally {
				connection.disconnect();
			}
			boolean isOk = true;
			if (handler != null) {
				isOk = handler.handleTempFile(tmpFile);
			}
			if (isOk) {
				final boolean r = tmpFile.renameTo(outputFile);
				if (r) {
					if (handler != null) {
						handler.handleFile(outputFile);
					}
				} else {
					// TODO: handle rename error
					isOk = false;
				}
			}
			if (!isOk) {
				tmpFile.delete();
			}
		} catch (IOException e) {
			// TODO: handle connection error
		} catch (IllegalStateException e) {
			// TODO: handle error
		}
	}

	/* Runnable overrides */

	@Override
	public void run() {
		for (Map.Entry<File, URL> entry : queue.entrySet()) {
			final File local = entry.getKey();
			final URL remote = entry.getValue();
			boolean download = true;
			if (handler != null) {
				handler.nextFile(local, remote);
			}
			if (checkLastModified) {
				download = checkHead(remote);
			}
			if (download) {
				processFile(local, remote);
			}
		}
	}

}
