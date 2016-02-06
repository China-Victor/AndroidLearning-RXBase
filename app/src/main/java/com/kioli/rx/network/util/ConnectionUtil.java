package com.kioli.rx.network.util;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Utility class to facilitate operations around connection
 */
public final class ConnectionUtil {

	private ConnectionUtil() {
	}

	/**
	 * Check if the device is connected to the internet
	 *
	 * @param context App (or activity) context
	 *
	 * @return true if connected, false otherwise
	 */
	public static boolean hasInternetConnection(final Context context) {
		final ConnectivityManager cm =
				(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		return cm.getActiveNetworkInfo() != null &&
				cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected();
	}
}
