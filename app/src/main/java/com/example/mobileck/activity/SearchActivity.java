package com.example.mobileck.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileck.R;
import com.example.mobileck.api.ApiService;
import com.example.mobileck.model.Song;
import com.example.mobileck.model.User;
import com.example.mobileck.recycler.RecyclerDataAdapter;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    List<Song> listSongs;
    ImageButton btn_back;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        btn_back = (ImageButton) findViewById(R.id.btn_back);
        searchView = (SearchView) findViewById(R.id.searchView);

        // Get User
        User user = (User) getIntent().getSerializableExtra("userSearch");

        // Listening when user submit search
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Toast.makeText(SearchActivity.this, "Success to call Api", Toast.LENGTH_LONG).show();

                // Chang to json object
                JsonObject jsonObject = JsonParser.parseString("{ \"key\": \"" + query + "\" }").getAsJsonObject();

                // POST searchSong
                ApiService.apiService.searchSong(jsonObject).enqueue(new Callback<List<Song>>() {
                    @Override
                    public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                        //Toast.makeText(PlaylistActivity.this, "Success to call Api", Toast.LENGTH_LONG).show();
                        listSongs = response.body();

                        // Setup for RecyclerView
                        RecyclerView listInt = (RecyclerView) findViewById(R.id.rv_search);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false);

                        RecyclerDataAdapter adapter = new RecyclerDataAdapter(SearchActivity.this, listSongs, user);
                        listInt.setLayoutManager(layoutManager);
                        listInt.setHasFixedSize(true);
                        listInt.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<List<Song>> call, Throwable t) {
                        Toast.makeText(SearchActivity.this, "Fail to call Api", Toast.LENGTH_LONG).show();
                        Toast.makeText(SearchActivity.this, t.toString(), Toast.LENGTH_LONG).show();
                    }
                });

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.super.onBackPressed();
            }
        });
    }
}
