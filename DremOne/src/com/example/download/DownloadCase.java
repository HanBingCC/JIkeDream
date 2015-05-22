package com.example.download;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.domain.DownloadObject;
import com.example.domain.Duration;
import com.example.dremone.DownloadActivity;
import com.example.dremone.MainActivity;
import com.example.dremone.VideoHorizontalActivity;

public class DownloadCase {
	private final Context context;
	private final Download downloader;
	private int fileLen;// 文件总长度
	private Duration duration;// 课时对象
	private DownloadServiceImpl service = MainActivity.downloadServiceImpl;// 调用服务方法的对象
	private Notification notification;// 通知栏消息对象
	private Integer nowProgres = 0;// 上次进度
	private DownloadObject downloadObject;
	private static final List<Integer> lists = new ArrayList<Integer>();// 正在下载的对象集合
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// 初始数据
			if (setUpdateProgress(duration.getId())) {
				// 重新设置最大值
				downloadObject.getDownload_content_view_progress().setMax(
						fileLen);
			}
			switch (msg.what) {
			case 0:
				Toast.makeText(context, duration.getName() + " 开始下载",
						Toast.LENGTH_SHORT).show();
				// 获取文件的大小
				fileLen = msg.getData().getInt("fileLen");
				// 列表设置进度条最大刻度：setMax()
				if (DownloadCase.this.downloadObject != null) {
					downloadObject.getDownload_content_view_progress().setMax(
							fileLen);
				}
				// 添加通栏消息
				notification = service.addNotification(duration);
				break;
			case 1:
				// 获取当前下载的总量
				int done = msg.getData().getInt("done");
				// 存当前进度
				Integer temp = done * 100 / fileLen;
				if (temp > (nowProgres + 5)) {
					// 列表当前进度的百分比
					if (DownloadCase.this.downloadObject != null) {
						downloadObject.getDownload_content_view_pro().setText(
								done * 100 / fileLen + "%");
					}
					// 列表进度条设置当前进度：setProgress()
					if (DownloadCase.this.downloadObject != null) {
						downloadObject.getDownload_content_view_progress()
								.setProgress(done);
					}
					// 通知栏更改进度
					service.changeNotification(notification, fileLen, done,
							duration.getId());
					nowProgres = temp;
				}
				// 判断是否结束
				if (done == fileLen) {
					// 列表进度设置下载成功
					if (DownloadCase.this.downloadObject != null) {
						downloadObject.getDownload_content_view_pro().setText(
								"下载完成");
						downloadObject.getDownload_content_view_progress()
								.setProgress(done);
					}
					// 更改进度
					service.downloadFulfil(duration.getId(), notification);
					// 移除下载成功对象
					lists.remove(duration.getId());

					Toast.makeText(context, duration.getName() + " 下载完成",
							Toast.LENGTH_SHORT).show();
				}
				break;
			}
		}
	};

	public DownloadCase(Context context) {
		this.context = context;
		downloader = new Download(context, handler);
	}

	public void downloadVideo(final String path, Duration duration) {
		// 判断下载总数是否超限
		if (lists.size() <= 3) {
			// 判断该下载课程是否已经存在
			if (!lists.contains(duration.getId())) {
				this.duration = duration;
				setUpdateProgress(duration.getId());
				// 添加进正在下载的课程数组
				lists.add(duration.getId());
				new Thread() {
					public void run() {
						try {
							downloader.download(path, 3);
						} catch (Exception e) {
							e.printStackTrace();
							Looper.prepare();
							Looper.loop();
							Toast.makeText(context, "下载过程中出现异常",
									Toast.LENGTH_SHORT).show();
							throw new RuntimeException(e);
						}
					};
				}.start();
			} else {
				Toast.makeText(context, "该课程已经在下载了", Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(context, "亲！当前下载的课程够多", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 判断是否更改成功
	 * 
	 * @param id
	 * @return
	 */
	public boolean setUpdateProgress(Integer id) {
		if (DownloadActivity.mapObjs != null) {
			if (DownloadActivity.mapObjs.get(id) != null) {
				this.downloadObject = DownloadActivity.mapObjs.get(id);
				this.downloadObject.setFlag(true);
				// 取消
				downloadObject.getBtn_cancel().setOnClickListener(
						new OnClickListener() {
							@Override
							public void onClick(View v) {
								// 停止进度
								downloader.delete();
								// 移除列表
								DownloadActivity.rootLinearLayout
										.removeView((View) downloadObject
												.getBtn_cancel().getParent());
								// 删除文件
								downloadObject.getFile().delete();
								// 移除正在下载对象
								lists.remove(duration.getId());
								// 移除通知栏对象
								service.cancelNotification(duration.getId());
								// 移除列表对象
								DownloadActivity.mapObjs.remove(duration
										.getId());
							}
						});
				downloadObject.getBtn_playin().setOnClickListener(
						new OnClickListener() {

							@Override
							public void onClick(View v) {
								Button pauseButton = (Button) v;
								if ("||".equals(pauseButton.getText())) {
									// 下载
									downloader.pause();
									pauseButton.setText("▶");
								} else {
									// 暂停
									downloader.resume();
									pauseButton.setText("||");
								}
							}
						});
				downloadObject.getDownload_content_view_image()
						.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								if (downloadObject
										.getDownload_content_view_pro()
										.getText().toString().equals("下载完成")) {
									// 移除通知栏对象
									service.cancelNotification(duration.getId());
									// 完成之后跳转
									Intent intent = new Intent(context,
											VideoHorizontalActivity.class);
									intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
											| Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
									intent.putExtra(
											"url",
											Uri.fromFile(
													downloadObject.getFile())
													.toString());
									intent.putExtra("title", duration.getName());
									context.startActivity(intent);
								}
							}
						});
				return true;
			}
		}
		return false;
	}

}
