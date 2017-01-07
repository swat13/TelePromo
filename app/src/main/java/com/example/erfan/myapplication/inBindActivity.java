package com.example.erfan.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.erfan.myapplication.Parser.Item;
import com.uncopt.android.widget.text.justify.JustifiedTextView;

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
