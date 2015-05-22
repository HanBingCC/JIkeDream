package com.example.dremone.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.dremone.MainActivity;
import com.example.dremone.R;
/**
 * 固定标题
 * @author Administrator
 *
 */
public class FragmentTitleBar extends Fragment {
	private ImageView home_title_left;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_titlebar, null);
		home_title_left = (ImageView) view.findViewById(R.id.home_title_left);
		home_title_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MainActivity activity = (MainActivity) getActivity();
				activity.toogleMenu();
			}
		});
		return view;
	}
}
