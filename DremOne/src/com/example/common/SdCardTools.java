package com.example.common;

import android.os.Environment;

public class SdCardTools {
	/**
	 * 判断sdcard是否可读可写
	 * 
	 * @return
	 */
	public static boolean isExtenceSdcard() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}
}
