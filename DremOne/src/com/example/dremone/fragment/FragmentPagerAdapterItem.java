package com.example.dremone.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.dremone.R;

public class FragmentPagerAdapterItem extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_carousel_figure_item,
				container,false);
		int url = getArguments().getInt("img_uri");
		ImageView iv = (ImageView) view.findViewById(R.id.image_item_frag);
		iv.setImageResource(url);
		return view;
	}
}
