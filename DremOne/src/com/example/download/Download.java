package com.example.download;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.example.database.DownloadDb;
import com.example.domain.Info;

public class Download {

	private int done;
	private DownloadDb dao;
	private int fileLen;
	private Handler handler;
	private boolean isPause;
	private boolean isDelete;
	public Download(Context context, Handler handler) {
		dao = new DownloadDb(context);
		this.handler = handler;
		// 创建目录
		// 初始化默认存储文件夹
		File file = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/" + "DreamDownload/");
		file.mkdir();
	}

	/**
	 * 多线程下载
	 * 
	 * @param path
	 *            下载路径
	 * @param thCount
	 *            需要开启多少个线程
	 * @throws Exception
	 */
	public void download(String path, int thCount) throws Exception {
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		// 设置超时时间
		conn.setConnectTimeout(5000);
		int code = conn.getResponseCode();
		if (code == 200) {
			fileLen = conn.getContentLength();
			String name = path.substring(path.lastIndexOf("/") + 1);
			File file = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/" + "DreamDownload/", name);
			RandomAccessFile raf = new RandomAccessFile(file, "rwd");
			raf.setLength(fileLen);
			raf.close();

			// Handler发送消息，主线程接收消息，获取数据的长度
			Message msg = new Message();
			msg.what = 0;
			msg.getData().putInt("fileLen", fileLen);
			handler.sendMessage(msg);

			// 计算每个线程下载的字节数
			int partLen = (fileLen + thCount - 1) / thCount;
			for (int i = 0; i < thCount; i++)
				new DownloadThread(url, file, partLen, i).start();
		} else {
			throw new IllegalArgumentException("404 path: " + path);
		}
	}

	private final class DownloadThread extends Thread {
		private URL url;
		private File file;
		private int partLen;
		private int id;

		public DownloadThread(URL url, File file, int partLen, int id) {
			this.url = url;
			this.file = file;
			this.partLen = partLen;
			this.id = id;
		}

		/**
		 * 写入操作
		 */
		public void run() {
			// 判断上次是否有未完成任务
			Info info = dao.query(url.toString(), id);
			if (info != null) {
				// 如果有, 读取当前线程已下载量
				done += info.getDone();
			} else {
				// 如果没有, 则创建一个新记录存入
				info = new Info(url.toString(), id, 0);
				dao.insert(info);
			}

			int start = id * partLen + info.getDone(); // 开始位置 += 已下载量
			int end = (id + 1) * partLen - 1;

			try {
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setReadTimeout(3000);
				// 获取指定位置的数据，Range范围如果超出服务器上数据范围, 会以服务器数据末尾为准
				conn.setRequestProperty("Range", "bytes=" + start + "-" + end);
				RandomAccessFile raf = new RandomAccessFile(file, "rwd");
				raf.seek(start);
				// 开始读写数据
				InputStream in = conn.getInputStream();
				byte[] buf = new byte[1024 * 10];
				int len;
				while ((len = in.read(buf)) != -1) {
					//删除
					if(isDelete)
					{
						break;
					}
					//暂停
					if (isPause) {
						// 使用线程锁锁定该线程
						synchronized (dao) {
							try {
								dao.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
					raf.write(buf, 0, len);
					done += len;
					info.setDone(info.getDone() + len);
					// 记录每个线程已下载的数据量
					dao.update(info);
					// 新线程中用Handler发送消息，主线程接收消息
					Message msg = new Message();
					msg.what = 1;
					msg.getData().putInt("done", done);
					handler.sendMessage(msg);
				}
				in.close();
				raf.close();
				// 删除下载记录
				dao.deleteAll(info.getPath(), fileLen);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 暂停下载
	public void pause() {
		isPause = true;
	}

	// 继续下载
	public void resume() {
		isPause = false;
		// 恢复所有线程
		synchronized (dao) {
			dao.notifyAll();
		}
	}
	//删除下载
	public void delete(){
		isDelete=true;
	}
}
