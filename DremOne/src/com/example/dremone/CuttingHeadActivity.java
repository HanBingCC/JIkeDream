package com.example.dremone;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.example.dremone.fragment.FragmentCuttingHead;

public class CuttingHeadActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cutting);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		// -------添加fragment
		FragmentCuttingHead fct = new FragmentCuttingHead();
		ft.replace(android.R.id.content, fct);
		// -------结束添加fragment
		ft.commit();
	}
}
