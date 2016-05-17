package com.tman.ivntel.popularmovies.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Ivan Telisman on 9/1/15.
 */
public class Review implements Serializable {

    public static final String AUTHOR = "author";
    public static final String CONTENT = "content";
    public static final String URL = "url";
    public static final String ID = "id";

    @SerializedName(AUTHOR)
    private String author;

    @SerializedName(CONTENT)
    private String content;

    @SerializedName(URL)
    private String url;

    @SerializedName(ID)
    private String iD;

    public Review() {
    }

    public String getUrl() {
        return url;
    }

    public String getiD() {
        return iD;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setiD(String iD) {
        this.iD = iD;
    }
}

