package com.example.http;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import com.example.domain.Duration;
import com.example.dremone.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 课时HTTP
 * 
 * @author Administrator
 *
 */
public class DurationHttp {
	/**
	 * 获得某一课程的课时信息
	 * 
	 * @param id
	 * @param handler
	 */
	public static void getDurationInfo(String id, final Handler handle,
			Context context) {
		final List<Duration> lists = new ArrayList<Duration>();
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("id", id);
		client.post(context.getResources().getString(R.string.servicename)
				+ "duration.html?mt=findCurriculumId", params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						String result = new String(responseBody);
						if (!result.equals("201")) {
							try {
								JSONArray array = new JSONArray(result);
								Duration duration;
								for (int i = 0; i < array.length(); i++) {
									JSONObject obj = array.getJSONObject(i);
									duration = new Duration(obj.getInt("id"),
											obj.getInt("curriculum_id"), obj
													.getString("name"), obj
													.getString("url"), obj
													.getLong("timeSpan"), obj
													.getInt("useFlag"),
											obj.getString("briefIntroduction"));
									lists.add(duration);
								}
							} catch (JSONException e) {
								System.err.println("服务端返回数据错误");
							}
							Message msg = handle.obtainMessage();
							msg.obj = lists;
							msg.what = 0;
							handle.sendMessage(msg);
						} else {
							System.err.println("服务端空数据");
						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {

					}

				});
	}
}
