package com.example.database;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.domain.Info;

public class DownloadDb extends BaseDbAdapter {

	public DownloadDb(Context context) {
		super(context);
	}

	public void insert(Info info) {
		SQLiteDatabase db =  getSqLiteDatabase();
		db.execSQL("INSERT INTO info(path, thid, done) VALUES(?, ?, ?)",
				new Object[] { info.getPath(), info.getThid(), info.getDone() });
	}

	public void delete(String path, int thid) {
		SQLiteDatabase db =  getSqLiteDatabase();
		db.execSQL("DELETE FROM info WHERE path=? AND thid=?", new Object[] {
				path, thid });
	}

	public void update(Info info) {
		SQLiteDatabase db =  getSqLiteDatabase();
		db.execSQL("UPDATE info SET done=? WHERE path=? AND thid=?",
				new Object[] { info.getDone(), info.getPath(), info.getThid() });
	}

	public Info query(String path, int thid) {
		SQLiteDatabase db =  getSqLiteDatabase();
		Cursor c = db.rawQuery(
				"SELECT path, thid, done FROM info WHERE path=? AND thid=?",
				new String[] { path, String.valueOf(thid) });
		Info info = null;
		if (c.moveToNext())
			info = new Info(c.getString(0), c.getInt(1), c.getInt(2));
		c.close();

		return info;
	}

	public void deleteAll(String path, int len) {
		SQLiteDatabase db = getSqLiteDatabase();
		Cursor c = db.rawQuery("SELECT SUM(done) FROM info WHERE path=?",
				new String[] { path });
		if (c.moveToNext()) {
			int result = c.getInt(0);
			if (result == len)
				db.execSQL("DELETE FROM info WHERE path=? ",
						new Object[] { path });
		}
	}

	public List<String> queryUndone() {
		SQLiteDatabase db =  getSqLiteDatabase();
		Cursor c = db.rawQuery("SELECT DISTINCT path FROM info", null);
		List<String> pathList = new ArrayList<String>();
		while (c.moveToNext())
			pathList.add(c.getString(0));
		c.close();
		return pathList;
	}
}
