package com.alindas.rssreader.Firebase;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Belal on 5/27/2016.
 */



//Class extending FirebaseInstanceIdService
public class InstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, "Refreshed token: " + refreshedToken);
        getSharedPreferences("YOUR_LOVE", 0).edit().putString("token", refreshedToken).apply();
        if (sendToServer(refreshedToken)) {
            getSharedPreferences("YOUR_LOVE", 0).edit().putBoolean("send_token", true).apply();
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

            TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String carrierName = manager.getNetworkOperatorName();
            Long tsLong = System.currentTimeMillis()/1000;
            String ts = tsLong.toString();
            PackageManager manager1 = getApplicationContext().getPackageManager();
            PackageInfo info = manager1.getPackageInfo(getApplicationContext().getPackageName(), 0);
            String version = info.versionName;
            String appName = getApplicationContext().getApplicationInfo().loadLabel(getApplicationContext().getPackageManager()).toString();
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
