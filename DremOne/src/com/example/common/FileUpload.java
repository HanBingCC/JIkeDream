package com.example.common;

import java.io.File;

import org.apache.http.Header;

import android.content.Context;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 文件上传
 * 
 * @author Administrator
 *
 */
public class FileUpload {
	/**
	 * 文件上传
	 * 
	 * @param path
	 *            路径
	 * @param file
	 *            文件
	 * @return
	 */
	public static boolean fileupLoad(String url, File file, Context context) {
		if (file.exists() && file.length() > 0) {
			AsyncHttpClient client = new AsyncHttpClient();
			RequestParams params = new RequestParams();
			try {
				params.put("file", file);
			} catch (Exception e) {
				Toast.makeText(context, "上传文件时发生错误", Toast.LENGTH_SHORT).show();
			}
			client.post(url, params, new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] responseBody) {
					System.out.println("上传文件成功");
				}

				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					System.out.println("上传文件失败");
				}

			});
		} else {
			Toast.makeText(context, "文件不存在", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
}
