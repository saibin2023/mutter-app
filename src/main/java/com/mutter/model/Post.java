package com.mutter.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Post {
    private int id;
    private String title;
    private String content;
    private String author;
    private String imagePath;
    private LocalDateTime createTime;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    public Post() {}

    public Post(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.createTime = LocalDateTime.now();
    }

    public Post(String title, String content, String author, String imagePath) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.imagePath = imagePath;
        this.createTime = LocalDateTime.now();
    }

    public Post(int id, String title, String content, String author, String imagePath, LocalDateTime createTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.imagePath = imagePath;
        this.createTime = createTime;
    }

    public Post(int id, String title, String content, String author, LocalDateTime createTime) {
        this(id, title, content, author, null, createTime);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getCreateTime() {
        return createTime.format(formatter);
    }

    public LocalDateTime getCreateTimeRaw() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
} 