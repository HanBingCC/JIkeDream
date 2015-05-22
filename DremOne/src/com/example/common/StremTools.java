package com.example.common;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StremTools {
	public static String readInputStrem(InputStream is) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		@SuppressWarnings("unused")
		int len = 0;
		byte[] buffer = new byte[1024];
		try {
			while ((len = is.read(buffer)) != -1) {
				baos.write(buffer);
			}
			is.close();
			baos.close();
			return new String(baos.toByteArray(),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return "获取数据失败";
		}
	}
}
