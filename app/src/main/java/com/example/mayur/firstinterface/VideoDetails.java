package com.example.mayur.firstinterface;

public class VideoDetails {

    private String video_id,title,description,url;

    public VideoDetails(String video_id, String title, String description, String url) {
        this.video_id = video_id;
        this.title = title;
        this.description = description;
        this.url = url;
    }

    public VideoDetails(){}


    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
