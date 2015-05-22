package com.example.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 客户端数据库
 * 
 * @author Administrator
 *
 */
public class DreamOneDbHelp extends SQLiteOpenHelper {
	private final static String dbName = "dream.db";

	public DreamOneDbHelp(Context context) {
		super(context, dbName, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 创建课程表
		db.execSQL("CREATE TABLE `curriculum_tb` (`_id` integer PRIMARY KEY autoincrement ,`id` integer NOT NULL,`title` varchar(50) DEFAULT NULL ,`durationCount` integer DEFAULT '0' , `url` varchar(100) DEFAULT NULL ,`briefIntroduction` varchar(500) DEFAULT NULL ,`useFlag` integer DEFAULT NULL ,`isValid` integer DEFAULT '1' ); ");
		// 创建课时表
		db.execSQL("CREATE TABLE `duration_tb` (`_id` integer PRIMARY KEY autoincrement ,`id` integer NOT NULL ,`curriculum_id` integer DEFAULT NULL ,`name` varchar(50) DEFAULT NULL ,`url` varchar(200) DEFAULT NULL ,`timeSpan` mediumtext ,`briefIntroduction` varchar(500) ,`useFlag` integer DEFAULT NULL ,`isValid` integer DEFAULT '1' );");
		// 创建下载记录表
		db.execSQL("CREATE TABLE info(path VARCHAR(1024), thid INTEGER, done INTEGER, PRIMARY KEY(path, thid));");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
