package com.example.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class DownloadHttp {
	/**
	 * 获取文件长度
	 * 
	 * @return
	 * @throws ProtocolException
	 */
	public static int getFileLength(String path) {
		int length = 0;
		try {
			URL url = new URL(path);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			int code = connection.getResponseCode();
			if (code == 200) {
				// 取得文件长度
				length = connection.getContentLength();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return length;
	}
}
