package com.bogomolov.alexander.contentprovidertest;

import java.util.Date;

/**
 * Created by admin on 27.10.2017.
 */

public class Note {
    public String title;
    public String content;
    public int priority;
    public Date createdAt;
    public byte[] image;
    public int id;

    public Note(int id, String title, String content, int priority, byte[] image, Date date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.priority = priority;
        this.image = image;
        this.createdAt = date;
    }
}
