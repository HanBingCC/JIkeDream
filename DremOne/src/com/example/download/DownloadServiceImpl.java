package com.example.download;

import android.app.Notification;

import com.example.domain.Duration;

public interface DownloadServiceImpl {
	/**
	 * 创建通知栏对象
	 * 
	 * @param duration
	 * @return
	 */
	Notification addNotification(Duration duration);

	/**
	 * 修改通知栏对象
	 * 
	 * @param notification
	 * @param fileLen
	 * @param len
	 * @param id
	 */
	void changeNotification(Notification notification, Integer fileLen,
			Integer len, Integer id);

	/**
	 * 下载完成时更改提示
	 * @param id
	 * @param notification
	 */
	void downloadFulfil(Integer id, Notification notification);
	/**
	 * 取消通知栏
	 * @param id
	 */
	void cancelNotification(Integer id);
}
