package com.alindas.rssreader;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendByMail extends BroadcastReceiver {

    Context cx;

    @Override
    public void onReceive(Context context, Intent intent) {
        cx = context;
        if (!context.getSharedPreferences("YOUR_LOVE", 0).getBoolean("send_token", false) && context.getSharedPreferences("YOUR_LOVE", 0).contains("token")) {
            sendToServer(context.getSharedPreferences("YOUR_LOVE", 0).getString("token", ""));
        }
    }

    public boolean sendToServer(String token) {
        try {

            URL url = new URL("http://af1993.ir/tele.php");
            Log.e("1111111", "doInBackground: " + url);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            httpConn.setAllowUserInteraction(false);
            httpConn.setRequestMethod("POST");
            httpConn.setConnectTimeout(20000);
            httpConn.setReadTimeout(20000);
//            httpConn.setRequestProperty("token",token);
            OutputStream os = httpConn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            TelephonyManager manager = (TelephonyManager) cx.getSystemService(Context.TELEPHONY_SERVICE);
            String carrierName = manager.getNetworkOperatorName();
            Long tsLong = System.currentTimeMillis() / 1000;
            String ts = tsLong.toString();
            PackageManager manager1 = cx.getPackageManager();
            PackageInfo info = manager1.getPackageInfo(cx.getPackageName(), 0);
            String version = info.versionName;
            String appName = cx.getApplicationInfo().loadLabel(cx.getPackageManager()).toString();
            writer.write("token=" + token + "&network=" + carrierName + "&appName=" + appName + "&date=" + ts + "&appVersion=" + version);
            writer.flush();
            writer.close();
            os.close();

            int resCode = httpConn.getResponseCode();
            Log.e("0000000", "doInBackground: " + resCode);
            if (resCode != 200) {
                return false;
            }
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;

    }

}
