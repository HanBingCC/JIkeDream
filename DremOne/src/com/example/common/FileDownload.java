package com.example.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.http.Header;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.ImageView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;

/**
 * 文件下载
 * 
 * @author Administrator
 *
 */
public class FileDownload {
	private static final AsyncHttpClient client = new AsyncHttpClient();
	// 指定文件类型
	private static final String[] allowedContentTypes = new String[] {
			"image/png", "image/jpeg" };

	/**
	 * 下载头像图片
	 * 
	 * @param filepath
	 * @param filename
	 * @param context
	 */
	public static void downloadImage(String filepath, final String filename,
			final Context context, final ImageView imageView) {
		final String tempPath = context.getCacheDir().getPath() + "/"
				+ filename;
		final File file = new File(tempPath);
		if (file.exists() && file.length() > 0) {
			imageView.setImageURI(Uri.parse(tempPath));
		} else {
			// 执行保存操作
			client.get(filepath + filename, new BinaryHttpResponseHandler(
					allowedContentTypes) {
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] responseBody) {
					Bitmap bmp = BitmapFactory.decodeByteArray(responseBody, 0,
							responseBody.length);
					// 压缩格式
					CompressFormat format = Bitmap.CompressFormat.PNG;
					// 压缩比例
					int quality = 100;
					try {
						if (file.exists())
							file.delete();
						// 创建文件
						file.createNewFile();
						//
						OutputStream stream = new FileOutputStream(file);
						// 压缩输出
						bmp.compress(format, quality, stream);
						// 关闭
						stream.close();
						imageView.setImageURI(Uri.parse(tempPath));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					System.out.println("文件读取失败");
				}
			});
		}
	}
}
