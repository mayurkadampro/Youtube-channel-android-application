package com.example.mayur.firstinterface;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

public class NonYtPlaylist extends Fragment {

    private VideoView videoView;
    private Uri videoUri;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Non Youtube Videos");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.nonytplaylist,container,false);
        videoView = (VideoView)v.findViewById(R.id.videoView);
        videoUri = Uri.parse("https://drive.google.com/open?id=1M7lu7wiMV2Jei5962kcVTGCH6mHuTSlT");
        videoView.setVideoURI(videoUri);
        videoView.requestFocus();
        videoView.start();

                return v;
    }


}
