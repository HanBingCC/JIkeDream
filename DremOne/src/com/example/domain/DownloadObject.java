package com.example.domain;

import java.io.File;
import java.io.Serializable;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
/**
 * 下载时的业务对象
 * @author Administrator
 *
 */
public class DownloadObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7188894370536988736L;
	//本地文件对象
	private File file;
	// 下载的视频对象
	private Duration duration;
	// 标题
	private TextView download_content_view_title;
	// 取消按钮
	private Button btn_cancel;
	// 进度显示
	private TextView download_content_view_pro;
	// 进度条
	private ProgressBar download_content_view_progress;
	//下载或暂停
	private Button btn_playin;
	//是否开始下载
	private boolean flag;
	//播放
	private ImageView download_content_view_image;
	


	

	public DownloadObject(File file, Duration duration,
			TextView download_content_view_title, Button btn_cancel,
			TextView download_content_view_pro,
			ProgressBar download_content_view_progress, Button btn_playin,
			boolean flag, ImageView download_content_view_image) {
		super();
		this.file = file;
		this.duration = duration;
		this.download_content_view_title = download_content_view_title;
		this.btn_cancel = btn_cancel;
		this.download_content_view_pro = download_content_view_pro;
		this.download_content_view_progress = download_content_view_progress;
		this.btn_playin = btn_playin;
		this.flag = flag;
		this.download_content_view_image = download_content_view_image;
	}

	/**
	 * @return the download_content_view_image
	 */
	public ImageView getDownload_content_view_image() {
		return download_content_view_image;
	}

	/**
	 * @param download_content_view_image the download_content_view_image to set
	 */
	public void setDownload_content_view_image(ImageView download_content_view_image) {
		this.download_content_view_image = download_content_view_image;
	}

	/**
	 * @return the flag
	 */
	public boolean isFlag() {
		return flag;
	}

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public DownloadObject() {
		super();
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * @return the duration
	 */
	public Duration getDuration() {
		return duration;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	/**
	 * @return the download_content_view_title
	 */
	public TextView getDownload_content_view_title() {
		return download_content_view_title;
	}

	/**
	 * @param download_content_view_title
	 *            the download_content_view_title to set
	 */
	public void setDownload_content_view_title(
			TextView download_content_view_title) {
		this.download_content_view_title = download_content_view_title;
	}

	/**
	 * @return the btn_cancel
	 */
	public Button getBtn_cancel() {
		return btn_cancel;
	}

	/**
	 * @param btn_cancel
	 *            the btn_cancel to set
	 */
	public void setBtn_cancel(Button btn_cancel) {
		this.btn_cancel = btn_cancel;
	}

	/**
	 * @return the download_content_view_pro
	 */
	public TextView getDownload_content_view_pro() {
		return download_content_view_pro;
	}

	/**
	 * @param download_content_view_pro
	 *            the download_content_view_pro to set
	 */
	public void setDownload_content_view_pro(TextView download_content_view_pro) {
		this.download_content_view_pro = download_content_view_pro;
	}

	/**
	 * @return the download_content_view_progress
	 */
	public ProgressBar getDownload_content_view_progress() {
		return download_content_view_progress;
	}

	/**
	 * @param download_content_view_progress
	 *            the download_content_view_progress to set
	 */
	public void setDownload_content_view_progress(
			ProgressBar download_content_view_progress) {
		this.download_content_view_progress = download_content_view_progress;
	}

	/**
	 * @return the btn_playin
	 */
	public Button getBtn_playin() {
		return btn_playin;
	}

	/**
	 * @param btn_playin
	 *            the btn_playin to set
	 */
	public void setBtn_playin(Button btn_playin) {
		this.btn_playin = btn_playin;
	}

}
