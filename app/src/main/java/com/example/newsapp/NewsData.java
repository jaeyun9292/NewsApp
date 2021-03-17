package com.example.newsapp;

import java.io.Serializable;

public class NewsData implements Serializable {         //Serializable = 직렬화 - 어떠한 데이터가 많고 형태가 이상할때 그것을 하나의 데이터 구조로 바꿔서 넘겨주고 받기위함.
    private String title;
    private String urlToImage;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
