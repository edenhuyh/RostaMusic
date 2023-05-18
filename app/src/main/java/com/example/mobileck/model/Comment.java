package com.example.mobileck.model;

public class Comment {
    private int id;
    private String comment;
    private int song_id;
    private User userDetail;
    private String createdAt;

    public Comment(int id, String comment, int song_id, User userDetail, String createdAt) {
        this.id = id;
        this.comment = comment;
        this.song_id = song_id;
        this.userDetail = userDetail;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getSong_id() {
        return song_id;
    }

    public void setSong_id(int song_id) {
        this.song_id = song_id;
    }

    public User getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(User userDetail) {
        this.userDetail = userDetail;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", song_id=" + song_id +
                ", userDetail=" + userDetail +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
