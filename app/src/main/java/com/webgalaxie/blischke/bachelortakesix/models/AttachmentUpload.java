package com.webgalaxie.blischke.bachelortakesix.models;

/**
 * Created by Bexx on 08.01.18.
 */

public class AttachmentUpload {

    public String name;
    public String url;
    public String id;
    public String immo_ID;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public AttachmentUpload() {
    }

    public AttachmentUpload(String name, String url, String id, String immo_ID) {
        this.name = name;
        this.url = url;
        this.id = id;
        this.immo_ID = immo_ID;
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

    public String getImmo_ID() {
        return immo_ID;
    }
}