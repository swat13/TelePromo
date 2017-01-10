package com.alindas.rssreader;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.alindas.rssreader.Parser.Item;
import com.alindas.rssreader.myapplication.R;

/**
 * Created by erfan on 1/5/2017.
 */

public class inBindActivity extends Activity {

    Item item;
    TextView title;
    ImageView img;

    TextView cmText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_layout);
        Bundle bundle = getIntent().getExtras();

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
}
