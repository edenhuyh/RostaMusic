package com.example.mobileck.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileck.R;
import com.example.mobileck.api.ApiService;
import com.example.mobileck.model.Song;
import com.example.mobileck.model.User;
import com.example.mobileck.recycler.RecyclerDataAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaylistActivity extends AppCompatActivity {
    List<Song> listSongs;
    ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        btn_back = (ImageButton) findViewById(R.id.btn_back);

        // Get User
        User user = (User) getIntent().getSerializableExtra("userPlaylist");

        // POST showAllPlaylistSong
        ApiService.apiService.showAllPlaylistSong(user).enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                //Toast.makeText(PlaylistActivity.this, "Success to call Api", Toast.LENGTH_LONG).show();
                listSongs = response.body();

                // Setup for RecyclerView
                RecyclerView listInt = (RecyclerView) findViewById(R.id.rv_playlistSong);
                LinearLayoutManager layoutManager = new LinearLayoutManager(PlaylistActivity.this, LinearLayoutManager.VERTICAL, false);

                RecyclerDataAdapter adapter = new RecyclerDataAdapter(PlaylistActivity.this, listSongs, user);
                listInt.setLayoutManager(layoutManager);
                listInt.setHasFixedSize(true);
                listInt.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                Toast.makeText(PlaylistActivity.this, "Fail to call Api", Toast.LENGTH_LONG).show();
                Toast.makeText(PlaylistActivity.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaylistActivity.super.onBackPressed();
            }
        });
    }
}
