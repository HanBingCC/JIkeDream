package com.example.download;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.example.domain.Duration;
import com.example.dremone.DownloadActivity;
import com.example.dremone.R;

public class DownloadService extends Service {
	@SuppressLint("UseSparseArrays")
	private Map<Integer, Notification> maps = new HashMap<Integer, Notification>();// 通知对象管理;
	private NotificationManager nm;// 通知栏管理

	@SuppressLint("UseSparseArrays")
	@Override
	public IBinder onBind(Intent arg0) {
		// 得到服务对象
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		return new DownloadBinder();
	}

	private class DownloadBinder extends Binder implements DownloadServiceImpl {

		@Override
		public Notification addNotification(Duration duration) {
			return DownloadService.this.addNotification(duration);
		}

		@Override
		public void changeNotification(Notification notification,
				Integer fileLen, Integer len, Integer id) {
			DownloadService.this.changeNotification(notification, fileLen, len,
					id);
		}

		@Override
		public void downloadFulfil(Integer id, Notification notification) {
			DownloadService.this.downloadFulfil(id, notification);
		}

		@Override
		public void cancelNotification(Integer id) {
			DownloadService.this.cancelNotification(id);
		}
	}

	/**
	 * 添加通知栏对象
	 * 
	 * @param fileLen
	 *            文件长度
	 * @param duration
	 *            课程对象
	 * @return
	 */
	private Notification addNotification(Duration duration) {
		// 创建新的通知栏对象
		Notification notification = new Notification(
				R.drawable.ic_launcher_download, duration.getName() + "开始下载", 0);
		// 下载中心对象
		Intent intent1 = new Intent(this, DownloadActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 10,
				intent1, 0);
		// 下载提醒设置
		notification.setLatestEventInfo(this, duration.getName(), "下载中",
				contentIntent);
		// 设置点击取消模式
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		// 设置布局文件
		notification.contentView = new RemoteViews(this.getPackageName(),
				R.layout.download_view);
		// 设置标题
		notification.contentView.setTextViewText(
				R.id.download_content_view_title, duration.getName());
		// 设置提示文字颜色
		nm.notify(duration.getId(), notification);
		// 放入集合
		maps.put(duration.getId(), notification);
		return notification;
	}

	/**
	 * 修改通知栏对象
	 * 
	 * @param notification
	 *            需要修改的对象
	 * @param fileLen
	 *            文件长度
	 * @param len
	 *            当前进度
	 * @param id
	 *            消息框ID
	 */
	private void changeNotification(Notification notification, Integer fileLen,
			Integer len, Integer id) {
		notification.contentView.setTextViewText(
				R.id.download_content_view_pro, len * 100 / fileLen + "%");
		notification.contentView.setProgressBar(
				R.id.download_content_view_progress, fileLen, len, false);
		nm.notify(id, notification);
	}

	/**
	 * 取消通知栏
	 * 
	 * @param id
	 */
	private void cancelNotification(Integer id) {
		nm.cancel(id);
	}

	/**
	 * 下载完成时更改提示
	 * 
	 * @param id
	 * @param notification
	 */
	private void downloadFulfil(Integer id, Notification notification) {
		notification.contentView.setTextViewText(
				R.id.download_content_view_pro, "下载完成");
		notification.contentView.setProgressBar(
				R.id.download_content_view_progress, 100, 100, false);
		nm.notify(id, notification);
	}
}
