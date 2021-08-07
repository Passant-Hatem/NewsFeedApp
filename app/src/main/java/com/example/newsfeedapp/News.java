package com.example.newsfeedapp;

public class News {
    String title; //webTitle
    String webUrl; //webUrl
    String type; //sectionName
    String date; //webPublicationDate
    String author;

    public News(String title, String webUrl, String type, String date, String author) {
        this.title = title;
        this.webUrl = webUrl;
        this.type = type;
        this.date = date;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }
}
