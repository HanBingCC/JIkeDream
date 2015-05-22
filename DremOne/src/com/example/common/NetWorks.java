package com.example.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

public class NetWorks {
	/**
	 * 判断网络连接状况
	 * 
	 * @param ctx
	 * @return
	 */
	public static boolean isNetworkAvailable(Context ctx) {
		boolean bFlag = false;
		if (ctx != null) {
			ConnectivityManager conMan = (ConnectivityManager) ctx
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (conMan != null) {
				// wifi Network
				NetworkInfo nInfoW = conMan
						.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
				if (nInfoW != null) {
					State wifi = nInfoW.getState();
					if (State.CONNECTED == wifi) {
						bFlag = true;
					}
				}
				if (!bFlag) {
					// mobile Network
					NetworkInfo nInfoM = conMan
							.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
					if (nInfoM != null) {
						State mobile = nInfoM.getState();
						if (State.CONNECTED == mobile) {
							bFlag = true;
						}
					}
				}
			}
		}
		return bFlag;
	}
}
