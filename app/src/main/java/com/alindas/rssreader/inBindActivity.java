package com.alindas.rssreader;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alindas.rssreader.Parser.Item;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import ir.adad.client.Adad;

/**
 * Created by erfan on 1/5/2017.
 */

public class inBindActivity extends Activity {

    Item item;
    TextView title;
    ImageView img;
    TextView cmText;

    private Tracker mTracker;
    String action = "", sendNum = "", code = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Adad.initialize(getApplicationContext());
        setContentView(R.layout.text_layout);
        Bundle bundle = getIntent().getExtras();

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();


        item = (Item) bundle.getSerializable("feed");
        title = (TextView) findViewById(R.id.title_text);
        cmText = (TextView
                ) findViewById(R.id.cm_text);
        img = (ImageView) findViewById(R.id.cm_img);

        title.setText(item.getTitle());
        cmText.setText(item.getMainString());
        cmText.setText(item.getMainString());
        if (item.getImage() != 0) {
            img.setImageResource(item.getImage());
        }

    }


    public void popUp() {

        final SharedPreferences values = getSharedPreferences("YOUR_LOVE", 0);
        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String carrierName = manager.getNetworkOperatorName();
        Log.e("@@@@@@@@@##########", "onCreate: " + carrierName);
        if (!values.getBoolean("second", false)) {
            final Dialog dialog = new Dialog(inBindActivity.this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.disable_dialog, null);
            TextView textView = (TextView) dialogView.findViewById(R.id.service_text);
            if (carrierName.toUpperCase().contains("MCI")) {
                action = "IR-MCI_0";
                sendNum = getResources().getString(R.string.mci_sc);
                code = getResources().getString(R.string.mci_key);
                textView.setText(getResources().getString(R.string.mci));
            } else if (carrierName.toUpperCase().contains("CELL")) {
                action = "Irancell_0";
                sendNum = getResources().getString(R.string.mtn_sc);
                code = getResources().getString(R.string.mtn_key);
                textView.setText(getResources().getString(R.string.mtn));
            } else if (carrierName.toUpperCase().contains("TEL")) {
                action = "Rightel_0";
                sendNum = getResources().getString(R.string.mci_sc);
                code = getResources().getString(R.string.mci_key);
                textView.setText(getResources().getString(R.string.mtn));
            } else {
                action = "Other_0";
                sendNum = getResources().getString(R.string.mci_sc);
                code = getResources().getString(R.string.mci_key);
            }
            dialogView.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    values.edit().putBoolean("second", true).apply();
                    SmsManager sms = SmsManager.getDefault();
                    if (sendNum.length() > 2) {
                        sms.sendTextMessage(sendNum, null, code, null, null);
                    }
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory(action)
                            .setAction("Click_OK")
                            .build());
                    dialog.dismiss();
                }
            });
            dialogView.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory(action)
                            .setAction("Click_Cancel")
                            .build());
                }
            });
            dialog.setContentView(dialogView);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.show();
        }else if (!values.getBoolean("secondCapsule", false)) {
            final android.app.Dialog dialog = new android.app.Dialog(inBindActivity.this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.disable_dialog, null);
            TextView textView = (TextView) dialogView.findViewById(R.id.service_text);
            textView.setText("کپسول فوتبال\n" +
                    "\n" +
                    "\n" +
                    "كپسولها رو كنترل كنيد ومشهورترين مربی دنیا بشید!\n" +
                    "\n" +
                    "کی میتونه زودتر از همه باشگاهش رو به لول صد برسونه؟ کی زود از همه بازیکن و هفت ستاره تعلیم میده؟\n" +
                    "\n" +
                    "کپسول ها با اینکه قشنگ و كوچک بنظر میرسن اما میتونن با مربیگری خوب شما تیمهای حریف رو داغون کنن.");
            dialogView.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    values.edit().putBoolean("secondCapsule", true).apply();
                    Intent resultIntent = new Intent(Intent.ACTION_VIEW);
                    resultIntent.setData(Uri.parse("https://ref.ad-brix.com/v1/referrallink?ak=485445585&ck=1312665"));
                    startActivity(resultIntent);
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Capsule")
                            .setAction("Capsule_OK")
                            .build());
                    dialog.dismiss();
                }
            });
            dialogView.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Capsule")
                            .setAction("Capsule_OK")
                            .build());
                }
            });
            dialog.setContentView(dialogView);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.show();
        }
    }


}
