package com.example.mobileck.model;

import java.io.Serializable;

public class Song implements Serializable {
    private int id;
    private String songName;
    private String songPath;
    private String poster;
    private String createdAt;
    private String updatedAt;
    private Artist artistDetail;
    private Genre genreDetail;

    public Song(int id, String songName, String songPath, String poster, String createdAt, String updatedAt, Artist artistDetail, Genre genreDetail) {
        this.id = id;
        this.songName = songName;
        this.songPath = songPath;
        this.poster = poster;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.artistDetail = artistDetail;
        this.genreDetail = genreDetail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongPath() {
        return songPath;
    }

    public void setSongPath(String songPath) {
        this.songPath = songPath;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Artist getArtistDetail() {
        return artistDetail;
    }

    public void setArtistDetail(Artist artistDetail) {
        this.artistDetail = artistDetail;
    }

    public Genre getGenreDetail() {
        return genreDetail;
    }

    public void setGenreDetail(Genre genreDetail) {
        this.genreDetail = genreDetail;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", songName='" + songName + '\'' +
                ", songPath='" + songPath + '\'' +
                ", poster='" + poster + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", artistDetail=" + artistDetail +
                ", genreDetail=" + genreDetail +
                '}';
    }
}
