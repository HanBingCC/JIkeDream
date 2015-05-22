package com.jike.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * 加密类
 * @author Administrator
 *
 */
public class MD5 {
	public String str;

	public String transFormMD5(String text) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(text.getBytes());
			byte b[] = md.digest();
			int x;
			StringBuffer buf = new StringBuffer("");
			for (int i = 0; i < b.length; i++) {
				x = b[i];
				if (x < 0)
					x += 256;
				if (x < 16)
					buf.append("0");
				buf.append(Integer.toHexString(x));
			}
			str = buf.toString();
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
