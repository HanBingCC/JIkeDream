package com.example.http;

import java.io.File;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

import com.example.common.FileUpload;
import com.example.dremone.R;
import com.example.dremone.fragment.FragmentLogin;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
/**
 * 用户HTTP
 * @author Administrator
 *
 */
public class UserHttp {
	/**
	 * 修改用户头像
	 * 
	 * @param id 用户Id
	 * @param filename 头像名称
	 * @param context 上下文
	 * 
	 */
	public static void updateHeadImageId(String id, String filename,
			final Context context) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("id", id);
		params.put("filename", filename);
		client.post(context.getResources().getString(R.string.servicename)
				+ "index.html?mt=updatehead", params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						Toast.makeText(context, "头像保存成功", Toast.LENGTH_SHORT)
								.show();
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						Toast.makeText(context, "头像保存失败", Toast.LENGTH_SHORT)
								.show();
					}
				});
	}

	/**
	 * 上传用户头像
	 * 
	 * @param id Id
	 * @param filename 文件名
	 * @param context 上下文
	 */
	public static void uploadHeadImage(String filename, final Context context) {
		FileUpload.fileupLoad(
				context.getResources().getString(R.string.servicename)
						+ "index.html?mt=uploadhead",
				new File(context.getCacheDir() + "/", filename), context);
	}
	/**
	 * 用户登录
	 * @param username 用户名
	 * @param password 密码
	 * @param fragmentLogin 显示提示的对象
	 * @return
	 */
	public static boolean userLogin(String username, String password,
			final FragmentLogin fragmentLogin) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("phone", username);
		params.put("password", password);
		client.post(
				fragmentLogin.getActivity().getResources()
						.getString(R.string.servicename)
						+ "index.html?mt=login", params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						String result = new String(responseBody);
						if (result.equals("201")) {
							fragmentLogin.judgeDisplay(2, false);
						} else {
							try {
								JSONObject jsonObject = new JSONObject(result);
								// 提醒
								fragmentLogin.judgeDisplay(2, true);
								// 回调
								fragmentLogin.setLoginResult(
										jsonObject.getString("nick_name"),
										jsonObject.getString("id"),
										jsonObject.getString("image_url"));
							} catch (JSONException e) {
								System.out.println("登录的返回值异常");
							}

						}

					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						Toast.makeText(fragmentLogin.getActivity(), "请求失败",
								Toast.LENGTH_SHORT).show();
					}
				});
		return false;
	}
}
