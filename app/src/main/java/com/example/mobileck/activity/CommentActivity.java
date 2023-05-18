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
import com.example.mobileck.model.Comment;
import com.example.mobileck.model.Song;
import com.example.mobileck.model.User;
import com.example.mobileck.recycler.RecyclerCommentAdapter;
import com.example.mobileck.recycler.RecyclerDataAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentActivity extends AppCompatActivity {
    List<Comment> listComments;
    ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        btn_back = (ImageButton) findViewById(R.id.btn_back);

        // Get User
        User user = (User) getIntent().getSerializableExtra("userComment");
        // Get Song
        Song song = (Song) getIntent().getSerializableExtra("song");

        // POST showAllPlaylistSong
        ApiService.apiService.showAllComment().enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                //Toast.makeText(PlaylistActivity.this, "Success to call Api", Toast.LENGTH_LONG).show();
                listComments = response.body();

                List<Comment> filterComments = new ArrayList<>();

                // Filter Comment By Song Id
                for (Comment item : listComments) {
                    if (item.getSong_id() == song.getId()) {
                        filterComments.add(item);
                    }
                }

                // Setup for RecyclerView
                RecyclerView listInt = (RecyclerView) findViewById(R.id.rv_commentSong);
                LinearLayoutManager layoutManager = new LinearLayoutManager(CommentActivity.this, LinearLayoutManager.VERTICAL, false);

                RecyclerCommentAdapter adapter = new RecyclerCommentAdapter(CommentActivity.this, filterComments);
                listInt.setLayoutManager(layoutManager);
                listInt.setHasFixedSize(true);
                listInt.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Toast.makeText(CommentActivity.this, "Fail to call Api", Toast.LENGTH_LONG).show();
                Toast.makeText(CommentActivity.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentActivity.super.onBackPressed();
            }
        });
    }
}
