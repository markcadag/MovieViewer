package com.markcadag.movieviewer.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager


/**
 * Created by markcadag on 4/8/18.
 */
class SyncService : BroadcastReceiver() {

    object NetworkUtil {
        fun isNetworkConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }

    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ConnectivityManager.CONNECTIVITY_ACTION && NetworkUtil.isNetworkConnected(context)) {
//            context.startService(getStartIntent(context))
        }
    }
}