package com.example.newsapp;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<NewsData> mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView TextView_title;
        public TextView TextView_Content;
        public SimpleDraweeView ImageView_title;

        public MyViewHolder(View v) {
            super(v);
            TextView_title = v.findViewById(R.id.TextView_title);
            TextView_Content = v.findViewById(R.id.TextView_Content);
            ImageView_title = (SimpleDraweeView) v.findViewById(R.id.ImageView_title);
        }
    }

    public MyAdapter(List<NewsData> myDataset, Context context) {
        mDataset = myDataset;
        Fresco.initialize(context);
    }


    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_news, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
        NewsData news = mDataset.get(position);
        String title = news.getTitle();
        String content = news.getContent();
        String image = news.getUrlToImage();


        if(title != "") {
            holder.TextView_title.setText(title);
        }
        else{
            holder.TextView_title.setText("NO DATA");
        }



        if(content != "") {
            holder.TextView_Content.setText(content);
        }
        else{
            holder.TextView_Content.setText("NO DATA");
        }



        if(image != "") {
            Uri uri = Uri.parse(image);
            holder.ImageView_title.setImageURI(uri);
        }
        else{
//            holder.ImageView_title.setText("NO DATA");
        }


    }

    @Override
    public int getItemCount() {
        
        //삼항 연산자
        return mDataset == null ? 0 : mDataset.size();
    }
}
