package com.webgalaxie.blischke.bachelortakesix.models;

/**
 * Created by Bexx on 08.01.18.
 */

public class PictureUpload {

    public String name;
    public String url;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public PictureUpload() {
    }

    public PictureUpload(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}