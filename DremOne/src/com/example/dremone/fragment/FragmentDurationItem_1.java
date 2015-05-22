package com.example.dremone.fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.common.NetWorks;
import com.example.common.SdCardTools;
import com.example.database.DurationDb;
import com.example.domain.Curriculum;
import com.example.domain.Duration;
import com.example.download.DownloadCase;
import com.example.dremone.R;
import com.example.dremone.VideoDetailActivity;
import com.example.http.DurationHttp;

public class FragmentDurationItem_1 extends Fragment {
	// 播放路径处理
	private VideoDetailActivity detailActivity;
	private String path;

	private void managePath(String title, String path, Integer state) {
		String[] msgs = new String[2];
		Handler handler = detailActivity.getHandler();
		if (state == 0 && this.path == null) {
			this.path = path;
			// 基础数据转换
			msgs[0] = path;
			msgs[1] = title;
			Message msg = handler.obtainMessage(0, msgs);
			handler.sendMessage(msg);
		} else if (state == 1) {
			this.path = path;
			// 基础数据转换
			msgs[0] = path;
			msgs[1] = title;
			Message msg = handler.obtainMessage(0, msgs);
			handler.sendMessage(msg);
		}
	}

	private Curriculum curriculumInfo;
	private ListView lv_durationItem;
	private TextView durationcount_1;// 总课时信息
	private DurationDb durationDb ;
	@SuppressLint("SimpleDateFormat")
	private SimpleDateFormat simpleDateFormatCount = new SimpleDateFormat("mm");
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				// 有网更新课程信息
				@SuppressWarnings("unchecked")
				final List<Duration> lists = (List<Duration>) msg.obj;
				lv_durationItem.setAdapter(new MyExpandBodyAdapter(lists,
						getActivity()));
				// 添加原始数据检查是否需要更新
				new Thread() {
					public void run() {
						durationDb.AddDataOriginal(lists);
					};
				}.start();
				break;
			case 1:
				// 无网更新本地课程信息
				@SuppressWarnings("unchecked")
				final List<Duration> lists1 = (List<Duration>) msg.obj;
				lv_durationItem.setAdapter(new MyExpandBodyAdapter(lists1,
						getActivity()));
				break;
			case 2:
				Toast.makeText(getActivity(), "开始下载", Toast.LENGTH_SHORT)
						.show();
				break;
			case 3:
				Toast.makeText(getActivity(), "下载完成", Toast.LENGTH_SHORT)
						.show();
				break;
			}
		}
	};

	public FragmentDurationItem_1(Curriculum curriculum) {
		this.curriculumInfo = curriculum;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_duration_item_1,
				container, false);
		init(view);
		isNetDuration();
		return view;
	}

	/**
	 * 初始化基础数据
	 */
	private void init(View view) {
		// 初始化数据库配置
		durationDb = new DurationDb(getActivity());
		lv_durationItem = (ListView) view.findViewById(R.id.lv_durationItem);
		durationcount_1 = (TextView) view.findViewById(R.id.durationcount_1);
		// 初始化activity
		detailActivity = (VideoDetailActivity) getActivity();
		// 课时单击时
		lv_durationItem.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Duration duration = ((Duration) arg1.getTag());
				String path = duration.getUrl();
				String title = duration.getName();
				managePath(title, path, 1);
			}
		});

		// 初始化总课时数
		durationcount_1.setText("共" + curriculumInfo.getDurationCount() + "课时");
	}

	/**
	 * 根据网络链接情况判断是否重新获取视频信息
	 */
	public void isNetDuration() {
		if (NetWorks.isNetworkAvailable(getActivity())) {
			DurationHttp.getDurationInfo(curriculumInfo.getId().toString(),
					handler, getActivity());
		} else {
			Toast.makeText(getActivity(), "无网络读取本地数据", Toast.LENGTH_SHORT)
					.show();
			new Thread() {
				public void run() {
					durationDb.queryDataOriginal(handler, curriculumInfo
							.getId().toString());
				};
			}.start();
		}
	}

	private class MyExpandBodyAdapter extends BaseAdapter {
		private List<Duration> lists;
		private TextView durationnumber;// 课时序号
		private TextView durationname;// 课时名称
		private TextView durationtime;// 时长
		private ImageView durationdownload;// 下载地址对象
		@SuppressLint("SimpleDateFormat")
		private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"mm:ss");
		// 时间格式化工具
		private Context context;

		public MyExpandBodyAdapter(List<Duration> lists, Context context) {
			this.lists = lists;
			this.context = context;
		}

		@Override
		public int getCount() {
			return lists.size();
		}

		@Override
		public Object getItem(int position) {
			return lists.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Duration duration = lists.get(position);
			if (convertView == null) {
				convertView = View.inflate(context,
						R.layout.fragment_duration_item_1_item, null);
				convertView.setTag(duration);
			} else {
				duration = (Duration) convertView.getTag();
			}
			// ----开始处理视频播放
			if (position == 0)
				managePath(duration.getName(), duration.getUrl(), 0);
			// ----结束处理视频播放
			durationnumber = (TextView) convertView
					.findViewById(R.id.durationnumber);
			durationname = (TextView) convertView
					.findViewById(R.id.durationname);
			durationtime = (TextView) convertView
					.findViewById(R.id.durationtime);
			durationdownload = (ImageView) convertView
					.findViewById(R.id.durationdownload);
			// 单击课时下载时
			durationdownload.setOnClickListener(new DownloadOnClickListener(
					context));
			// Integer id = duration.getId();// Id
			String name = duration.getName();// name
			Long timeSpan = duration.getTimeSpan();// 时间
			// Integer useFlag = duration.getUseFlag();// 是否是vip
			// String briefIntroduction = duration.getBriefIntroduction();// 简介
			durationnumber.setText("课时:" + (position + 1) + " ");
			durationname.setText(name);
			durationtime.setText(simpleDateFormat.format(new Date(timeSpan)));
			durationdownload.setTag(duration);
			return convertView;
		}

		/*
		 * 当课时下载被单击时
		 */
		private class DownloadOnClickListener implements OnClickListener {
			private Context context;

			public DownloadOnClickListener(Context context) {
				this.context = context;
			}

			@Override
			public void onClick(View v) {
				Duration duration = (Duration) v.getTag();
				String url = duration.getUrl();
				if (SdCardTools.isExtenceSdcard()) {
					url = getResources().getString(R.string.servicename)
							+ "fileImage/curriculumVideo/" + url;
					new DownloadCase(context).downloadVideo(url, duration);
				} else {
					Toast.makeText(context, "不能下载sdCard不存在", Toast.LENGTH_SHORT)
							.show();
				}
			}

		}
	}
}
