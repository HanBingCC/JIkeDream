package com.example.dremone;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.common.SdCardTools;
import com.example.database.DownloadDb;
import com.example.database.DurationDb;
import com.example.domain.DownloadObject;
import com.example.domain.Duration;
import com.example.download.DownloadCase;

public class DownloadActivity extends FragmentActivity implements
		OnClickListener {

	private ImageView image_backbutton_left;
	@SuppressWarnings("unused")
	private LayoutInflater inflater;// 布局填充器
	public static LinearLayout rootLinearLayout;// 主布局对象
	@SuppressLint("UseSparseArrays")
	public static Map<Integer, DownloadObject> mapObjs = new HashMap<Integer, DownloadObject>();// 下载对象集合

	@SuppressLint("UseSparseArrays")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download);
		init();
	}

	private void init() {
		image_backbutton_left = (ImageView) findViewById(R.id.image_backbutton_left);
		image_backbutton_left.setOnClickListener(this);
		// 动态生成新View，获取系统服务LayoutInflater，用来生成新的View
		inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		rootLinearLayout = (LinearLayout) findViewById(R.id.down_root);
		// 是否有未下载完成的任务<原始数据>
		List<String> listinitial = new DownloadDb(this).queryUndone();
		// 是否有未下载完成的任务<转换后的数据>
		List<String> listnew = new ArrayList<String>(listinitial.size());
		// 取得本地的所有下载url列表
		List<String> junior = getDownloadName();
		// 去除重复项
		for (String local : listinitial) {
			String temp = local.substring(local.lastIndexOf("/") + 1);
			// 移除本地中的未下载完成的项
			junior.remove(temp);
			//
			listnew.add(temp);
		}
		// 课时对象
		DurationDb durationDb = new DurationDb(this);
		// 所有下载完成的课时集合
		List<Duration> downloadComplete = new ArrayList<Duration>(junior.size());
		// 所有未下载完成的课时集合
		List<Duration> noComplete = new ArrayList<Duration>(listinitial.size());

		if (junior.size() > 0) {
			String[] temp = new String[junior.size()];
			junior.toArray(temp);
			downloadComplete = durationDb.queryScanUrl(temp);
		}
		if (listnew.size() > 0) {
			String[] temp = new String[listnew.size()];
			listnew.toArray(temp);
			noComplete = durationDb.queryScanUrl(temp);
		}
		// 初始化显示
		for (Duration duration : downloadComplete) {
			// 已经下载完成的
			createNativeDownload(duration);
		}
		for (Duration duration : noComplete) {
			// 下载过程中的
			createBreakPointDownload(duration);
		}
	}

	private List<String> getDownloadName() {
		List<String> lists = new ArrayList<String>();
		if (SdCardTools.isExtenceSdcard()) {
			File filelist = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/DreamDownload/");
			if (filelist.isDirectory()) {
				File[] files = filelist.listFiles();
				for (File file : files) {
					lists.add(file.getName());
				}
			}
		}
		return lists;
	}

	/**
	 * 点击事件的处理
	 */
	@Override
	public void onClick(View v) {
		// 当返回按钮被点击时
		switch (v.getId()) {
		case R.id.image_backbutton_left:
			finish();
			break;
		}
	}

	/**
	 * 创建本地线程版本
	 * 
	 * @param duration
	 */
	@SuppressLint("InflateParams")
	private void createNativeDownload(final Duration duration) {
		// 本地视频文件对象
		final File file = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/DreamDownload/" + duration.getUrl());
		// 获取系统服务LayoutInflater，用来生成新的View
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(
				R.layout.download, null);
		// 标题
		TextView download_content_view_title = (TextView) relativeLayout
				.findViewById(R.id.download_content_view_title);
		download_content_view_title.setText(duration.getName());
		// 播放
		ImageView download_content_view_image = (ImageView) relativeLayout
				.findViewById(R.id.download_content_view_image);
		download_content_view_image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DownloadActivity.this,
						VideoHorizontalActivity.class);
				intent.putExtra("url", Uri.fromFile(file).toString());
				intent.putExtra("title", duration.getName());
				startActivity(intent);
			}
		});
		final Button btn_cancel = (Button) relativeLayout
				.findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(new OnClickListener() {
			/**
			 * 单击删除时
			 */
			@Override
			public void onClick(View v) {
				MainActivity.downloadServiceImpl.cancelNotification(duration
						.getId());
				// 删除本地文件
				file.delete();
				// 移除布局
				rootLinearLayout.removeView((View) btn_cancel.getParent());
			}
		});
		// 调用当前页面中某个容器的addView，将新创建的View添加进来
		rootLinearLayout.addView(relativeLayout);

	}

	/**
	 * 创建在线下载版本
	 * 
	 * @param duration
	 */
	@SuppressLint("InflateParams")
	private void createBreakPointDownload(final Duration duration) {
		// 本地视频文件对象
		final File file = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/DreamDownload/" + duration.getUrl());
		// 获取系统服务LayoutInflater，用来生成新的View
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(
				R.layout.download, null);
		// 标题
		TextView download_content_view_title = (TextView) relativeLayout
				.findViewById(R.id.download_content_view_title);
		download_content_view_title.setText(duration.getName());
		// 进度条文字显示
		TextView download_content_view_pro = (TextView) relativeLayout
				.findViewById(R.id.download_content_view_pro);
		download_content_view_pro.setText("继续下载");
		// 进度条
		ProgressBar download_content_view_progress = (ProgressBar) relativeLayout
				.findViewById(R.id.download_content_view_progress);
		download_content_view_progress.setProgress(0);
		// 播放控制
		Button btn_playin = (Button) relativeLayout
				.findViewById(R.id.btn_playin);
		btn_playin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 标志是否已经下载过了
				if (mapObjs.get(duration.getId()).isFlag() == false) {
					// 启动下载
					String url = duration.getUrl();
					if (SdCardTools.isExtenceSdcard()) {
						url = getResources().getString(R.string.servicename)
								+ "fileImage/curriculumVideo/" + url;
						new DownloadCase(getApplicationContext())
								.downloadVideo(url, duration);
					} else {
						Toast.makeText(getApplicationContext(),
								"不能下载sdCard不存在", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		// 删除按钮比
		Button btn_cancel = (Button) relativeLayout
				.findViewById(R.id.btn_cancel);
		// 播放
		ImageView download_content_view_image = (ImageView) relativeLayout
				.findViewById(R.id.download_content_view_image);
		// 调用当前页面中某个容器的addView，将新创建的View添加进来
		rootLinearLayout.addView(relativeLayout);
		// -----------加入集合开始
		DownloadObject downloadObj = new DownloadObject(file, duration,
				download_content_view_title, btn_cancel,
				download_content_view_pro, download_content_view_progress,
				btn_playin, false, download_content_view_image);
		mapObjs.put(duration.getId(), downloadObj);
		// -----------加入集合结束
	}

	@Override
	protected void onDestroy() {
		rootLinearLayout = null;
		super.onDestroy();
	}
}
