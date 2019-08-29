package com.makaroni.usersphoto.data;

public class PhotoImage {
    private String title;
    private String url;

    public PhotoImage(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
