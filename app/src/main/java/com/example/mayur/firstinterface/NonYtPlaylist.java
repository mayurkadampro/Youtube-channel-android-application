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
        videoUri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/mightyghosthack.appspot.com/o/wordlist%20generator%20python%20script.mp4?alt=media&token=401d5624-fa36-4454-9abd-83914cb5f88e");
        videoView.setVideoURI(videoUri);
        videoView.requestFocus();
        videoView.start();

                return v;
    }


}
