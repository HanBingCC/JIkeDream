package com.example.database;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;

import com.example.domain.Curriculum;

/**
 * 课程信息
 * 
 * @author Administrator
 *
 */
public class CurriculumDb extends BaseDbAdapter {

	public CurriculumDb(Context context) {
		super(context);
	}

	/**
	 * 有网络情况下备份数据
	 * 
	 * @param lists
	 */
	public void AddDataOriginal(List<Curriculum> lists) {
		SQLiteDatabase sqLiteDatabase = getSqLiteDatabase();
		for (Curriculum curriculum : lists) {
			Cursor cusor = sqLiteDatabase.rawQuery(
					"select 1 from curriculum_tb where  id=?",
					new String[] { "" + curriculum.getId() });
			if (cusor.moveToNext()) {
				cusor.close();
				break;
			} else {
				sqLiteDatabase
						.execSQL(
								"insert into  curriculum_tb(id,title,durationCount,url,briefIntroduction,useFlag) values(?,?,?,?,?,?)",
								new String[] { curriculum.getId() + "",
										curriculum.getTitle(),
										curriculum.getDurationCount()+"",
										curriculum.getUrl(),
										curriculum.getBriefIntroduction(),
										curriculum.getUseFlag() + "" });
			}
		}
	}

	/**
	 * 无网络情况下读取数据
	 * 
	 * @param handle
	 */
	public void queryDataOriginal(Handler handle) {
		List<Curriculum> lists = new ArrayList<Curriculum>();
		SQLiteDatabase sqLiteDatabase = getSqLiteDatabase();
		Cursor cursor = sqLiteDatabase.rawQuery(
				"select * from  curriculum_tb order by _id desc limit 0,5;",
				null);
		while (cursor.moveToNext()) {
			Integer id = cursor.getInt(cursor.getColumnIndex("id"));
			String title = cursor.getString(cursor.getColumnIndex("title"));
			Integer durationCount = cursor.getInt(cursor
					.getColumnIndex("durationCount"));
			String url = cursor.getString(cursor.getColumnIndex("url"));
			String briefIntroduction = cursor.getString(cursor
					.getColumnIndex("briefIntroduction"));
			Integer useFlag = cursor.getInt(cursor.getColumnIndex("useFlag"));
			Curriculum curriculum = new Curriculum(id, title, durationCount,
					url, briefIntroduction, useFlag);
			lists.add(curriculum);
		}
		Message msg = handle.obtainMessage();
		msg.obj = lists;
		msg.what = 1;
		handle.sendMessage(msg);
	}
}
