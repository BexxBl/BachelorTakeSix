package com.webgalaxie.blischke.bachelortakesix.models;

/**
 * Created by Bexx on 08.01.18.
 */

public class AttachmentUpload {

    public String name;
    public String url;
    public String id;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public AttachmentUpload() {
    }

    public AttachmentUpload(String name, String url, String id) {
        this.name = name;
        this.url = url;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }
}