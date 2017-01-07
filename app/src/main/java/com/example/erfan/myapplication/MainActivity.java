package com.example.erfan.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.erfan.myapplication.Parser.Feed;
import com.example.erfan.myapplication.Parser.Item;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rec;
    RecyclerAdapter adapter;
    String[] titles;
    String[] mainStrings;
    int[] images;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rec = (RecyclerView) findViewById(R.id.rec);
        titles = getResources().getStringArray(R.array.title);
        mainStrings = getResources().getStringArray(R.array.mainStrings);

        adapter = new RecyclerAdapter();
        rec.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rec.setLayoutManager(llm);
        rec.setNestedScrollingEnabled(true);
    }

    public class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected RelativeLayout main_layout;
        protected TextView textView;
        ImageView img;

        public FeedViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.title_text);
            main_layout = (RelativeLayout) v.findViewById(R.id.rec_layout);
            img = (ImageView) v.findViewById(R.id.img);
            main_layout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, inBindActivity.class));
            Context context = itemView.getContext();
            Intent showPhotoIntent = new Intent(context, inBindActivity.class);
            showPhotoIntent.putExtra("title", textView.getText().toString());
            context.startActivity(showPhotoIntent);

        }
    }


    public class RecyclerAdapter extends RecyclerView.Adapter<FeedViewHolder> {

        Feed feed;

        public RecyclerAdapter() {

            feed=new Feed();

        }

        @Override
        public FeedViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rec_layout, viewGroup, false);
            return new FeedViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(FeedViewHolder holder, final int position) {
            String title = titles[position];
            Item item=new Item();
            item.setTitle(title);
            item.setMainString(mainStrings[position]);
            feed.addItem(item);
            holder.textView.setText(title);
            Glide.with(getApplicationContext()).load(R.mipmap.ic_launcher).into(holder.img);

            holder.main_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(MainActivity.this, inBindActivity.class).putExtra("feed",feed.getItem(position)));

                }
            });
        }

        @Override
        public int getItemCount() {
            return titles.length;
        }
    }


}


