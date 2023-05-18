package com.example.mobileck.model;

public class ReqUserSong {
    private int idUser;
    private int idSong;

    public ReqUserSong(int idUser, int idSong) {
        this.idUser = idUser;
        this.idSong = idSong;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdSong() {
        return idSong;
    }

    public void setIdSong(int idSong) {
        this.idSong = idSong;
    }
}
