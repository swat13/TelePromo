package com.alindas.rssreader;

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

import com.alindas.rssreader.myapplication.R;
import com.bumptech.glide.Glide;
import com.alindas.rssreader.Parser.Feed;
import com.alindas.rssreader.Parser.Item;

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
//37-21=16
        rec = (RecyclerView) findViewById(R.id.rec);
        titles = getResources().getStringArray(R.array.title);
        mainStrings = getResources().getStringArray(R.array.mainStrings);

        images = new int[]{R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4
                , R.drawable.image5, R.drawable.image6, R.drawable.image7, R.drawable.image8
                , R.drawable.image9, R.drawable.image10, R.drawable.image11, R.drawable.image12
                , R.drawable.image13, R.drawable.image14, R.drawable.image15, R.drawable.image16
                , R.drawable.image17, R.drawable.images18, R.drawable.image001, R.drawable.image003, R.drawable.image005
                , R.drawable.image007, R.drawable.image009, R.drawable.image011, R.drawable.image013
                , R.drawable.image015, R.drawable.image017, R.drawable.image019, R.drawable.image021
                , R.drawable.image023, R.drawable.image025, R.drawable.image027, R.drawable.image029
                , R.drawable.image031, R.drawable.image034, R.drawable.image037
                , R.drawable.image039, R.drawable.image041};

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

            feed = new Feed();

        }

        @Override
        public FeedViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rec_layout, viewGroup, false);
            return new FeedViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(FeedViewHolder holder, final int position) {

            Item item = new Item();
            String title = titles[position];
            item.setTitle(title);
            item.setMainString(mainStrings[position]);
            feed.addItem(item);
            holder.textView.setText(title);

            Log.e("Position", position + "");
            Log.e("Images", images[position] + "");
            item.setImage(images[position]);
            Glide.with(getApplicationContext()).load(images[position]).into(holder.img);


            holder.main_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(MainActivity.this, inBindActivity.class).putExtra("feed", feed.getItem(position)));

                }
            });
        }

        @Override
        public int getItemCount() {
            return titles.length;
        }
    }


}

