package com.example.dremone.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.domain.Curriculum;
import com.example.dremone.R;
import com.viewpagerindicator.TabPageIndicator;

/**
 * 课时信息
 * 
 * @author Administrator
 *
 */
public class FragmentDuration extends Fragment {
	private ViewPager pager_duration;
	private TabPageIndicator indicator_duration;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_duration_info,
				container, false);
		init(view);
		bindingDate();
		return view;
	}

	/**
	 * 初始化基础数据
	 */
	private void init(View view) {
		this.pager_duration = (ViewPager) view
				.findViewById(R.id.pager_duration);
		this.indicator_duration = (TabPageIndicator) view
				.findViewById(R.id.indicator_duration);
	}

	/**
	 * 绑定数据
	 */
	private void bindingDate() {
		this.pager_duration.setAdapter(new MyFragmentPagerAdapter(
				getFragmentManager()));
		this.indicator_duration.setViewPager(pager_duration);
	}

	private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
		private final String[] CONTENT = new String[] { "目录", "详情", "相关课程" };

		public MyFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = null;
			Intent intent = getActivity().getIntent();
			Curriculum curriculum = (Curriculum) intent
					.getSerializableExtra("curriculumInfo");
			switch (position) {
			case 0:// 目录
				fragment = new FragmentDurationItem_1(curriculum);
				break;
			case 1:// 详情
				fragment = new FragmentDurationItem_2(curriculum);
				break;
			case 2:// 相关课程
				fragment = new FragmentDurationItem_3();
				break;
			}
			return fragment;

		}

		@SuppressLint("DefaultLocale")
		@Override
		public CharSequence getPageTitle(int position) {
			return CONTENT[position % CONTENT.length].toUpperCase();
		}

		@Override
		public int getCount() {
			return CONTENT.length;
		}

	}
}
