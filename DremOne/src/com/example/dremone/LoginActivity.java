package com.example.dremone;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.dremone.fragment.FragmentLogin;

public class LoginActivity extends FragmentActivity {
	private FragmentManager fm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		
		Fragment fragment=new FragmentLogin();
		ft.replace(android.R.id.content, fragment);
		ft.commit();
		
	}	
}
