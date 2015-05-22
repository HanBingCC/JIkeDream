package com.example.dremone;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.dremone.fragment.FragmentDuration;
import com.example.dremone.fragment.FragmentVideoVertical;

/**
 * 进入视频播放的详情页面
 * 
 * @author Administrator
 *
 */
public class VideoDetailActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_videodetail);
		bundleFragment();
	}
	//处理播放逻辑
	private FragmentManager fm;
	private FragmentTransaction ft;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			fm = getSupportFragmentManager();
			ft = fm.beginTransaction();
			if (msg.what == 0) {
				String[] msgs=(String[]) msg.obj;
				String path = getResources().getString(R.string.servicename)
						+ "fileImage/curriculumVideo/"
						+ msgs[0];
				String title =msgs[1];
				ft.replace(R.id.detail_video, new FragmentVideoVertical(path,
						title));
			}
			ft.commit();
		};
	};

	/**
	 * @return the handler
	 */
	public Handler getHandler() {
		return handler;
	}

	/**
	 * 绑定frgment信息
	 */
	private void bundleFragment() {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction fs = fm.beginTransaction();
		FragmentDuration fragmentDuration = new FragmentDuration();
		fs.replace(R.id.detail_tab, fragmentDuration);
		fs.commit();
	}
}
