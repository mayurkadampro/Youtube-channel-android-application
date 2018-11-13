package com.example.mayur.firstinterface;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VideoDetailsAdapter extends BaseAdapter {

    Activity activity;
    ArrayList<VideoDetails> videoDetailsArrayList;
    LayoutInflater layoutInflater;

    public VideoDetailsAdapter(Activity activity, ArrayList<VideoDetails> videoDetailsArrayList) {
        this.activity = activity;
        this.videoDetailsArrayList = videoDetailsArrayList;
    }

    @Override
    public int getCount() {
        return this.videoDetailsArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.videoDetailsArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return (long)i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(layoutInflater == null)
        {
            layoutInflater = this.activity.getLayoutInflater();
        }

        if(view == null)
        {
            view = layoutInflater.inflate(R.layout.youtube_video_layout,null);
        }

        ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
        TextView textView = (TextView)view.findViewById(R.id.mytitle);
        final VideoDetails videoDetails = (VideoDetails)this.videoDetailsArrayList.get(i);
        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.root);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(),Youtube_Video_Play.class);
                intent.putExtra("Video_id",videoDetails.getVideo_id());
                activity.startActivity(intent);

            }
        });



        Picasso.get().load(videoDetails.getUrl()).into(imageView);

        textView.setText(videoDetails.getTitle());

        return view;
    }
}
