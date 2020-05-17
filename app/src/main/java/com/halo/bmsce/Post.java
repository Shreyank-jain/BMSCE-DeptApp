package com.halo.bmsce;

public class Post {
    private String post_user,post_message,post_date;

    public Post(){}

    public Post(String post_user,String post_message,String post_date){
        this.post_user=post_user;
        this.post_message=post_message;
        this.post_date=post_date;
    }

    public String getPost_user() {
        return post_user;
    }

    public String getPost_message() {
        return post_message;
    }

    public String getPost_date() {
        return post_date;
    }
}
