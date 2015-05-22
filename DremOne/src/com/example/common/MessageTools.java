package com.example.common;

import android.os.Handler;
import android.os.Message;

public class MessageTools {
	/**
	 * 发送消息
	 * @param handler 
	 * @param text
	 */
	public static void SendMessge(Handler handler, String text) {
		Message msg = Message.obtain();
		msg.obj = text;
		handler.sendMessage(msg);
	}
}
