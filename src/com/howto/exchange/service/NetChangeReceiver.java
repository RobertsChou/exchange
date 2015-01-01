package com.howto.exchange.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetChangeReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            Log.d("mark", "网络状态已经改变");
            ConnectivityManager connectivityManager = (ConnectivityManager)
            context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info != null && info.isAvailable()) {
               /* Intent in = new Intent(context, SendCommentService.class);
                context.startService(in);
                
                in = new Intent(context, ShareService.class);
                context.startService(in);*/
            } else {
                Log.d("mark", "网络断开");
            }
        }
    }

}
