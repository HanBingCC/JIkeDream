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
import com.example.domain.Curriculum;
import com.example.dremone.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
/**
 * 课程HTTP
 * @author Administrator
 *
 */
public class CurriculumHttp {
	/**
	 * 获得课程推荐信息
	 */
	public static void CurriculumInfo(Context context,final Handler handle) {
		final List<Curriculum> lists = new ArrayList<Curriculum>();
		AsyncHttpClient client = new AsyncHttpClient();
		client.post(context.getResources().getString(R.string.servicename)
				+ "curriculum.html?mt=findRandomCurriculum", null,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						String result = new String(responseBody);
						if (!result.equals("201")) {
							try {
								JSONArray array = new JSONArray(result);
								Curriculum curriculum;
								for (int i = 0; i < array.length(); i++) {
									JSONObject obj = array.getJSONObject(i);
									curriculum = new Curriculum(obj
											.getInt("id"), obj
											.getString("title"), obj
											.getInt("durationCount"), obj
											.getString("url"), obj
											.getString("briefIntroduction"),
											obj.getInt("useFlag"));
									lists.add(curriculum);
								}
							} catch (JSONException e) {
								System.err.println("服务端返回数据错误");
							}
							Message msg=handle.obtainMessage();
							msg.obj=lists;
							msg.what=0;
							handle.sendMessage(msg);
						} else {
							System.err.println("服务端空数据");
						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						System.out.println("最新推荐课程信息获取失败");
					}
				});
	}
}
