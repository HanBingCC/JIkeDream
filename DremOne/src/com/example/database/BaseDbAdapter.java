package com.example.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class BaseDbAdapter {
	private DreamOneDbHelp dreamOneDbHelp;

	private static SQLiteDatabase sqLiteDatabase;

	/**
	 * @return the dreamOneDbHelp
	 */
	public DreamOneDbHelp getDreamOneDbHelp() {
		return dreamOneDbHelp;
	}

	/**
	 * @return the sqLiteDatabase
	 */
	public SQLiteDatabase getSqLiteDatabase() {
		return sqLiteDatabase;
	}

	public BaseDbAdapter(Context context) {
		this.dreamOneDbHelp = new DreamOneDbHelp(context);
		if (sqLiteDatabase == null) {
			sqLiteDatabase = dreamOneDbHelp.getReadableDatabase();
		}
	}
}
