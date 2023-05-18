package com.example.mobileck.api;

import com.example.mobileck.model.Comment;
import com.example.mobileck.model.ReqUserSong;
import com.example.mobileck.model.Song;
import com.example.mobileck.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    //192.168.1.5
    String ipAddress = "192.168.1.3";

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://" + ipAddress +":3000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("getAllSong")
    Call<List<Song>> showAllSong();

    @POST("loginMobile")
    Call<User> sendLogin(@Body User user);

    @POST("registerMobile")
    Call<User> sendRegister(@Body User user);

    @POST("historyMobile")
    Call<List<Song>> showAllHistorySong(@Body User user);

    @POST("createHistory")
    Call<Song> createHistorySong(@Body ReqUserSong item);

    @POST("playlistMobile")
    Call<List<Song>> showAllPlaylistSong(@Body User user);

    @POST("createPlaylist")
    Call<Song> createPlaylistSong(@Body ReqUserSong item);

    @GET("getAllComment")
    Call<List<Comment>> showAllComment();

    @POST("searchMobile")
    Call<List<Song>> searchSong(@Body JsonObject key);

    @GET("topMobile")
    Call<List<Song>> showTopSong();

    @POST("musicviewMobile")
    Call<Song> upViewSong(@Body ReqUserSong item);
}
