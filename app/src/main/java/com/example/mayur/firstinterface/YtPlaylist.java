package com.example.mayur.firstinterface;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class YtPlaylist extends Fragment {

    private ListView mListView;
    private int[] Image = {
            R.mipmap.ic_logo_python,
            R.mipmap.ic_java,
            R.mipmap.ic_tor_browser,
            R.mipmap.ic_anonymous_mask_png21,
            R.mipmap.ic_image7 ,
            R.mipmap.ic_image8 ,
            R.mipmap.ic_image9,

            };

    private String[] name = {
                    "Python Coding",
                    "Java Coding",
                    "Deep Web",
                    "Hacking",
                    "Gaming",
                    "Android",
                    "Linux",
                    };



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Youtube Videos");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.ytplaylist,container,false);
        mListView = (ListView) v.findViewById(R.id.playlistlistview);
        CustomAdapter customAdapter = new CustomAdapter();
        mListView.setAdapter(customAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if(position == 0) {
                    Intent myIntent = new Intent(view.getContext(),PythonCodingYTList.class);
                    startActivityForResult(myIntent,0);
                }
                if(position == 1) {
                    Intent myIntent = new Intent(view.getContext(),JavaCodingYTList.class);
                    startActivityForResult(myIntent,0);
                }
            }
        });


        return v;
    }


    class CustomAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return Image.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = getLayoutInflater().inflate(R.layout.customytplaylist,null);
            ImageView mImageView = view1.findViewById(R.id.imageView4);
            TextView mTextView = view1.findViewById(R.id.textView3);

            mImageView.setImageResource(Image[i]);
            mTextView.setText(name[i]);
            return view1;
        }
    }




}
