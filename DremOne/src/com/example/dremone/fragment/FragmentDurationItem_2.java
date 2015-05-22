package com.example.dremone.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.domain.Curriculum;
import com.example.dremone.R;

public class FragmentDurationItem_2 extends Fragment {
	@SuppressWarnings("unused")
	private TextView duration_detail;// 课程背景
	private Curriculum curriculum;// 课程

	public FragmentDurationItem_2(Curriculum curriculum) {
		this.curriculum = curriculum;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_duration_item_2,
				container, false);
		TextView duration_detail = (TextView) view
				.findViewById(R.id.duration_detail);
		duration_detail.setText(curriculum.getBriefIntroduction());
		return view;
	}
}
