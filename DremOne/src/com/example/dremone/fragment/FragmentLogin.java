package com.example.dremone.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.dremone.LoginActivity;
import com.example.dremone.R;
import com.example.http.UserHttp;

public class FragmentLogin extends Fragment {
	private EditText login_user;
	private EditText login_passwd;
	private LoginActivity loginActivity;
	private ImageView image_button_left;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_login, container, false);
		// 取得控件
		login_user = (EditText) view.findViewById(R.id.login_user);
		login_passwd = (EditText) view.findViewById(R.id.login_passwd);
		loginActivity = (LoginActivity) getActivity();
		image_button_left = (ImageView) view
				.findViewById(R.id.image_button_left);
		Button login_confirm = (Button) view.findViewById(R.id.login_confirm);
		// 设置点击事件
		login_confirm.setOnClickListener(new LoginOnClickListener());
		image_button_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().finish();
			}
		});
		return view;
	}

	private class LoginOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			final String username = login_user.getText().toString().trim();
			final String password = login_passwd.getText().toString().trim();
			// -------------非空验证显示提示开始
			if (TextUtils.isEmpty(username)) {
				judgeDisplay(1, false);
				return;
			} else {
				judgeDisplay(1, true);
			}
			if (TextUtils.isEmpty(password)) {
				judgeDisplay(2, false);
				return;
			} else {
				judgeDisplay(2, false);
			}
			// ---------------非空验证显示提示结束
			// ---------------密码验证
			UserHttp.userLogin(username, password, FragmentLogin.this);
		};
	}

	/**
	 * 验证提醒
	 * 
	 * @param statue
	 *            1 用户名 2密码
	 * @param flag
	 *            true false
	 */
	public void judgeDisplay(int statue, boolean flag) {
		switch (statue) {
		case 1:
			if (flag) {
				login_user.setCompoundDrawables(null, null, null, null);
			} else {
				Drawable nav_up = getResources().getDrawable(
						R.drawable.bg_login_clear);
				nav_up.setBounds(0, 0, nav_up.getMinimumWidth(),
						nav_up.getMinimumHeight());
				login_user.setCompoundDrawables(null, null, nav_up, null);
			}
			break;
		case 2:
			if (flag) {
				login_passwd.setCompoundDrawables(null, null, null, null);
			} else {
				Drawable nav_up = getResources().getDrawable(
						R.drawable.bg_login_clear);
				nav_up.setBounds(0, 0, nav_up.getMinimumWidth(),
						nav_up.getMinimumHeight());
				login_passwd.setCompoundDrawables(null, null, nav_up, null);
			}
			break;
		}
	}

	/**
	 * 处理返回值的activoty
	 * 
	 * @param username
	 * @param id
	 */
	public void setLoginResult(String username, String id, String url) {
		Intent data = new Intent();
		data.putExtra("username", username);
		data.putExtra("id", id);
		data.putExtra("headname", url);
		data.putExtra("headurl",
				getActivity().getResources().getString(R.string.servicename)
						+ "fileImage/head/");
		loginActivity.setResult(1, data);
		loginActivity.finish();
	}
}
