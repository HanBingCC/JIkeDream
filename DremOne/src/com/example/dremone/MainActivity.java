package com.example.dremone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.common.FileDownload;
import com.example.common.NetWorks;
import com.example.database.CurriculumDb;
import com.example.domain.Curriculum;
import com.example.download.DownloadService;
import com.example.download.DownloadServiceImpl;
import com.example.dremone.fragment.FragmentPagerAdapterItem;
import com.example.http.CurriculumHttp;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.loopj.android.image.SmartImageView;
import com.viewpagerindicator.LinePageIndicator;
import com.viewpagerindicator.PageIndicator;

public class MainActivity extends FragmentActivity {
	private SlidingMenu slidingMenu;
	private ImageView menu_login_img;// 登录图标
	private TextView menu_login_text;// 登录文本
	private FrameLayout menu_download_container;// 下载中心
	private ViewPager title_pager;//
	private PageIndicator title_indicator;// pager对象
	private GridView home_gridview;// 二级菜单
	private String id;// 用户Id
	private ListView lv_expand_body;// 课程信息
	private CurriculumDb curriculumDb = null;// 课程数据库对象
	public static DownloadServiceImpl downloadServiceImpl;// 需要初始化的下载服务接口
	private DownConnection connDownload = new DownConnection();// 下载初始化服务链接对象
	private Intent serviceIntent;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				// 有网更新课程信息
				@SuppressWarnings("unchecked")
				final List<Curriculum> lists = (List<Curriculum>) msg.obj;
				lv_expand_body.setAdapter(new MyExpandBodyAdapter(lists,
						MainActivity.this));
				// 添加原始数据检查是否需要更新
				new Thread() {
					public void run() {
						curriculumDb.AddDataOriginal(lists);
					};
				}.start();
				break;
			case 1:
				// 无网更新本地课程信息
				@SuppressWarnings("unchecked")
				final List<Curriculum> lists1 = (List<Curriculum>) msg.obj;
				lv_expand_body.setAdapter(new MyExpandBodyAdapter(lists1,
						MainActivity.this));
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		curriculumDb = new CurriculumDb(MainActivity.this);
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		// -----加载启动图
		new Thread() {
			public void run() {
				Intent intent = new Intent(MainActivity.this,
						StartActivity.class);
				MainActivity.this.startActivityForResult(intent, 0);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				finishActivity(0);
			};
		}.start();
		// -----结束加载启动图
		// ------开始初始化下载服务
		this.serviceIntent = new Intent(this, DownloadService.class);
		startService(serviceIntent);
		bindService(serviceIntent, new DownConnection(), BIND_AUTO_CREATE);
		// -------结束初始化下载服务
		// ------加载slidingMenu
		slidingMenu = new SlidingMenu(this);
		slidingMenu.setMode(SlidingMenu.LEFT);
		// 设置距离屏幕右端距离
		slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// 设置点击模式
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// 设置依附的activity
		slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		// 设置要显示的菜单布局
		slidingMenu.setMenu(R.layout.slidingmenu);
		// ------结束加载slidingMenu
		menu_login_img = (ImageView) slidingMenu
				.findViewById(R.id.menu_login_img);
		menu_login_text = (TextView) slidingMenu
				.findViewById(R.id.menu_login_text);
		menu_download_container = (FrameLayout) findViewById(R.id.menu_download_container);
		menu_login_img.setOnClickListener(new MyLoginOnClickListener());
		menu_login_text.setOnClickListener(new MyLoginOnClickListener());
		menu_download_container.setOnClickListener(new MenuItemClicks());
		// ------轮播图1
		// 设置 基础布局
		title_pager = (ViewPager) findViewById(R.id.title_pager);
		title_pager.setAdapter(new MyFragmentPager1Adapter(
				getSupportFragmentManager()));
		title_indicator = (LinePageIndicator) findViewById(R.id.title_indicator);
		title_indicator.setViewPager(title_pager);
		// -------设置轮播图2
		home_gridview = (GridView) findViewById(R.id.home_gridview);
		ArrayList<HashMap<String, Object>> al = new ArrayList<HashMap<String, Object>>();
		for (int i = 1; i <= 4; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			switch (i) {
			case 1:
				map.put("icon", R.drawable.home_item_1);
				map.put("name", "最新课程");
				break;
			case 2:
				map.put("icon", R.drawable.home_item_2);
				map.put("name", "实战课程");
				break;
			case 3:
				map.put("icon", R.drawable.home_item_3);
				map.put("name", "免费课程");
				break;
			case 4:
				map.put("icon", R.drawable.home_item_4);
				map.put("name", "全部课程");
				break;
			}
			al.add(map);
		}
		SimpleAdapter sa = new SimpleAdapter(this, al,
				R.layout.fragment_home_gridview_item, new String[] { "icon",
						"name" }, new int[] { R.id.gradview_itemimage,
						R.id.gradview_itemtext });

		home_gridview.setAdapter(sa);
		home_gridview.setOnItemClickListener(new ClickItemListener());
		// -------课程信息
		lv_expand_body = (ListView) findViewById(R.id.lv_expand_body);
		// 加载课程信息
		isNetCurriculum();
		// --------设置单一项点击事件
		lv_expand_body
				.setOnItemClickListener(new MyDurationOnItemClickListener());

	}

	/**
	 * 根据网络链接情况判断是否加载在线课程推广
	 */
	public void isNetCurriculum() {
		if (NetWorks.isNetworkAvailable(this)) {
			CurriculumHttp.CurriculumInfo(this, handler);
		} else {
			Toast.makeText(this, "亲，我们检测到当前您没有连网哦", Toast.LENGTH_SHORT).show();
			new Thread() {
				public void run() {
					curriculumDb.queryDataOriginal(handler);
				};
			}.start();
		}
	}

	/**
	 * 课程信息列表项单击时
	 * 
	 * @author Administrator
	 *
	 */
	private class MyDurationOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent intent = new Intent(MainActivity.this,
					VideoDetailActivity.class);
			Curriculum curriculum = (Curriculum) arg1.getTag();
			intent.putExtra("curriculumInfo", curriculum);
			startActivity(intent);
		}

	}

	/**
	 * 菜单项点击时
	 * 
	 * @author Administrator
	 *
	 */
	private class MenuItemClicks implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.menu_download_container:
				// 下载中心
				Intent intent = new Intent(MainActivity.this,
						DownloadActivity.class);
				startActivity(intent);
				break;
			}
		}

	}

	/**
	 * 登录关联的点击
	 * 
	 * @author Administrator
	 *
	 */
	private class MyLoginOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (menu_login_text.getText().equals("登录点我")) {
				Intent intent = new Intent(MainActivity.this,
						LoginActivity.class);
				startActivityForResult(intent, 1);
			} else {
				// 已经登录
				Intent intent = new Intent(MainActivity.this,
						CuttingHeadActivity.class);
				intent.putExtra("id", id);
				startActivityForResult(intent, 2);
			}
		}
	}

	/**
	 * 单击课程分类时
	 * 
	 * @author Administrator
	 *
	 */
	private class ClickItemListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			switch (arg2) {
			case 0:
				Toast.makeText(arg1.getContext(), "最新课程", Toast.LENGTH_SHORT)
						.show();
				break;
			case 1:
				Toast.makeText(arg1.getContext(), "实战课程", Toast.LENGTH_SHORT)
						.show();
				break;
			case 2:
				Toast.makeText(arg1.getContext(), "免费课程", Toast.LENGTH_SHORT)
						.show();
				break;
			case 3:
				Toast.makeText(arg1.getContext(), "全部课程", Toast.LENGTH_SHORT)
						.show();
				break;
			}
		}

	}

	/**
	 * 单击home按钮时
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_MENU:
			toogleMenu();
			break;

		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 切换菜单
	 */
	public void toogleMenu() {
		slidingMenu.toggle(true);
	}

	/**
	 * 回调函数
	 */
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// 用户登录
		if (arg0 == 1 && arg1 == 1) {
			String username = arg2.getStringExtra("username");
			String headname = arg2.getStringExtra("headname");
			String headurl = arg2.getStringExtra("headurl");
			this.id = arg2.getStringExtra("id");
			// 设置昵称
			menu_login_text.setText(username);
			// 设置头像
			FileDownload.downloadImage(headurl, headname, this, menu_login_img);
		}
		// 用户修改头像
		if (arg0 == 2 && arg1 == 2) {
			String filename = arg2.getStringExtra("path");
			String filepath = getCacheDir() + "/" + filename;
			menu_login_img.setImageURI(Uri.parse(filepath));
		}
		super.onActivityResult(arg0, arg1, arg2);
	}

	/**
	 * 
	 * 滚动菜单填充布局1
	 * 
	 * @author Administrator
	 *
	 */
	private class MyFragmentPager1Adapter extends FragmentPagerAdapter {
		private int[] ICONS = new int[] { R.drawable.poster_2,
				R.drawable.poster_3, R.drawable.poster_4, R.drawable.poster_1 };
		@SuppressLint("UseSparseArrays")
		private Map<Integer, Fragment> frags = new HashMap<Integer, Fragment>();

		public MyFragmentPager1Adapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = null;
			if (frags.containsKey(position) == false) {
				fragment = new FragmentPagerAdapterItem();
				Bundle args = new Bundle();
				args.putInt("img_uri", ICONS[position]);
				fragment.setArguments(args);
			} else {
				fragment = frags.get(position);
			}
			return fragment;
		}

		@Override
		public int getCount() {
			return ICONS.length;
		}

	}

	/**
	 * 课程信息填充
	 * 
	 * @author Administrator
	 *
	 */
	private class MyExpandBodyAdapter extends BaseAdapter {
		private List<Curriculum> curriculums;
		private Context context;
		private TextView list_title;// 标题
		private SmartImageView list_photo;// 图片
		private TextView item_hour;// 课时数
		private ImageView item_indicator;// 标示免费还是会员
		private TextView list_des;// 课程介绍

		public MyExpandBodyAdapter(List<Curriculum> curriculums, Context context) {
			this.curriculums = curriculums;
			this.context = context;
		}

		@Override
		public int getCount() {
			return curriculums.size();
		}

		@Override
		public Object getItem(int position) {
			return curriculums.get(position);
		}

		@Override
		public long getItemId(int position) {
			return curriculums.get(position).getId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			Curriculum curriculum;
			if (view == null) {
				view = View.inflate(context,
						R.layout.fragment_view_home_listitem, null);
				curriculum = curriculums.get(position);
				view.setTag(curriculum);
			} else {
				curriculum = (Curriculum) convertView.getTag();
			}
			list_title = (TextView) view.findViewById(R.id.list_title);// 标题
			list_photo = (SmartImageView) view.findViewById(R.id.list_photo);// 照片
			item_hour = (TextView) view.findViewById(R.id.item_hour);// 课时数
			item_indicator = (ImageView) view.findViewById(R.id.item_indicator);// 是否是会员的标识
			list_des = (TextView) view.findViewById(R.id.list_des);// 介绍
			list_title.setText(curriculum.getTitle());
			list_photo.setImageUrl(
					getResources().getString(R.string.servicename)
							+ "fileImage/curriculumImage/"
							+ curriculum.getUrl(),
					R.drawable.ic_image_default_1, R.drawable.ic_none_cat_1);
			item_hour.setText(curriculum.getDurationCount() + "课时");
			// ----0为普通用户,1为会员
			if (curriculum.getUseFlag() == 0) {
				item_indicator.setBackgroundResource(R.drawable.bg_home_free);
			} else {
				item_indicator.setBackgroundResource(R.drawable.bg_home_member);
			}
			list_des.setText(curriculum.getBriefIntroduction());
			return view;
		}

	}

	/**
	 * 初始化下载链接
	 * 
	 * @author Administrator
	 *
	 */
	public class DownConnection implements ServiceConnection {

		@Override
		public void onServiceConnected(ComponentName arg0, IBinder arg1) {
			MainActivity.downloadServiceImpl = (DownloadServiceImpl) arg1;
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		try {
			//停止服务
			stopService(serviceIntent);
			// 销毁
			unbindService(connDownload);
		} catch (Exception e) {
		}

		super.onDestroy();
	}
}
