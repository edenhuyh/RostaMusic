package com.example.mobileck.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mobileck.R;
import com.example.mobileck.api.ApiService;
import com.example.mobileck.model.Song;
import com.example.mobileck.model.User;
import com.example.mobileck.recycler.RecyclerDataAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    List<Song> listSongs;
    Button btn_history;
    Button btn_playlist;
    Button btn_search;
    Button btn_charts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get User
        User getUser = (User) getIntent().getSerializableExtra("user");

        // GET showAllSong
        ApiService.apiService.showAllSong().enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
//                Toast.makeText(MainActivity.this, "Success to call Api", Toast.LENGTH_LONG).show();
                listSongs = response.body();

                // Setup for RecyclerView
                RecyclerView listInt = (RecyclerView) findViewById(R.id.rv_song);
                LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);

                RecyclerDataAdapter adapter = new RecyclerDataAdapter(MainActivity.this, listSongs, getUser);
                listInt.setLayoutManager(layoutManager);
                listInt.setHasFixedSize(true);
                listInt.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fail to call Api", Toast.LENGTH_LONG).show();
                Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });

        btn_history = (Button) findViewById(R.id.btn_history);
        btn_playlist = (Button) findViewById(R.id.btn_playlist);
        btn_search = (Button) findViewById(R.id.btn_search);
        btn_charts = (Button) findViewById(R.id.btn_charts);

        // Change to History
        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                intent.putExtra("userHistory", getUser);

                startActivity(intent);
            }
        });

        // Change to Playlist
        btn_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlaylistActivity.class);
                intent.putExtra("userPlaylist", getUser);

                startActivity(intent);
            }
        });

        // Change to Playlist
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("userSearch", getUser);

                startActivity(intent);
            }
        });

        // Change to Top
        btn_charts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TopActivity.class);
                intent.putExtra("userTop", getUser);

                startActivity(intent);
            }
        });
    }
}