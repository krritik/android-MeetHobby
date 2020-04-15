package com.example.meethobby;

public class Post_product {
    int postID;
    String description, user, posttime;

    public Post_product(int postID, String description, String user, String posttime) {
        this.postID = postID;
        this.description = description;
        this.user = user;
        this.posttime = posttime;
    }

    public int getPostID() { return postID; }
    public String getDescription() { return  description; }
    public String getUser() { return  user; }
    public String getPostTime() { return  posttime; }

}
