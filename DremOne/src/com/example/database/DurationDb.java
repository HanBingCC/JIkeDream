package com.example.database;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;

import com.example.domain.Duration;

/**
 * 课时信息
 * 
 * @author Administrator
 *
 */
public class DurationDb extends BaseDbAdapter {

	public DurationDb(Context context) {
		super(context);
	}

	/**
	 * 又有网络情况下备份数据
	 * 
	 * @param lists
	 */
	public void AddDataOriginal(List<Duration> lists) {
		SQLiteDatabase sqLiteDatabase = getSqLiteDatabase();
		for (Duration duration : lists) {
			Cursor cusor = sqLiteDatabase.rawQuery(
					"select 1 from duration_tb where id=?", new String[] { ""
							+ duration.getId() });
			if (cusor.moveToNext()) {
				cusor.close();
				break;
			} else {
				sqLiteDatabase
						.execSQL(
								"insert into  duration_tb(id,curriculum_id,name,url,timespan,briefIntroduction,useFlag)values(?,?,?,?,?,?,?)",
								new String[] { duration.getId() + "",
										duration.getCurriculum_id() + "",
										duration.getName(), duration.getUrl(),
										duration.getTimeSpan() + "",
										duration.getBriefIntroduction(),
										duration.getUseFlag() + "" });
			}
		}
	}

	/**
	 * 无网络情况下读取数据
	 * 
	 * @param handle
	 */
	public void queryDataOriginal(Handler handle, String curriculumid) {
		List<Duration> lists = new ArrayList<Duration>();
		SQLiteDatabase sqLiteDatabase = getSqLiteDatabase();
		Cursor cursor = sqLiteDatabase.rawQuery(
				"select * from  duration_tb where curriculum_id=? order by id",
				new String[] { curriculumid });
		while (cursor.moveToNext()) {
			Integer id = cursor.getInt(cursor.getColumnIndex("id"));
			Integer curriculum_id = cursor.getInt(cursor
					.getColumnIndex("curriculum_id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String url = cursor.getString(cursor.getColumnIndex("url"));
			Long timeSpan = cursor.getLong(cursor.getColumnIndex("timeSpan"));
			Integer useFlag = cursor.getInt(cursor.getColumnIndex("useFlag"));
			String briefIntroduction = cursor.getString(cursor
					.getColumnIndex("briefIntroduction"));
			Duration duration = new Duration(id, curriculum_id, name, url,
					timeSpan, useFlag, briefIntroduction);
			lists.add(duration);
		}
		Message msg = handle.obtainMessage();
		msg.obj = lists;
		msg.what = 1;
		handle.sendMessage(msg);
	}

	/**
	 * 根据服务器的课时ID获取课时信息
	 * 
	 * @param index
	 * @return
	 */
	public Duration getDurationInfo(String index) {
		SQLiteDatabase sqLiteDatabase = getSqLiteDatabase();
		Cursor cursor = sqLiteDatabase.rawQuery(
				"select * from duration_tb where id=?", new String[] { index });
		Duration duration = null;
		if (cursor.moveToNext()) {
			Integer id = cursor.getInt(cursor.getColumnIndex("id"));
			Integer curriculum_id = cursor.getInt(cursor
					.getColumnIndex("curriculum_id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String url = cursor.getString(cursor.getColumnIndex("url"));
			Long timeSpan = cursor.getLong(cursor.getColumnIndex("timeSpan"));
			Integer useFlag = cursor.getInt(cursor.getColumnIndex("useFlag"));
			String briefIntroduction = cursor.getString(cursor
					.getColumnIndex("briefIntroduction"));
			duration = new Duration(id, curriculum_id, name, url, timeSpan,
					useFlag, briefIntroduction);
		}
		return duration;
	}

	public List<Duration> queryScanUrl(String... args) {
		List<Duration> lists = new ArrayList<Duration>();
		SQLiteDatabase sqLiteDatabase = getSqLiteDatabase();
		StringBuilder sb=new StringBuilder();
		sb.append("select * from duration_tb where url in(");
		for(int i=1;i<=args.length;i++)
		{
			if(i!=args.length)
			{
				sb.append("?,");
			}else{
				sb.append("?");
			}
		}
		sb.append(")");
		Cursor cursor = sqLiteDatabase.rawQuery(sb.toString(), args);
		while (cursor.moveToNext()) {
			Integer id = cursor.getInt(cursor.getColumnIndex("id"));
			Integer curriculum_id = cursor.getInt(cursor
					.getColumnIndex("curriculum_id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String url = cursor.getString(cursor.getColumnIndex("url"));
			Long timeSpan = cursor.getLong(cursor.getColumnIndex("timeSpan"));
			Integer useFlag = cursor.getInt(cursor.getColumnIndex("useFlag"));
			String briefIntroduction = cursor.getString(cursor
					.getColumnIndex("briefIntroduction"));
			Duration duration = new Duration(id, curriculum_id, name, url,
					timeSpan, useFlag, briefIntroduction);
			lists.add(duration);
		}
		return lists;
	}
}
