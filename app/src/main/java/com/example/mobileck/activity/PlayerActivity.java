package com.example.mobileck.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileck.R;
import com.example.mobileck.api.ApiService;
import com.example.mobileck.model.ReqUserSong;
import com.example.mobileck.model.Song;
import com.example.mobileck.model.User;
import com.example.mobileck.recycler.RecyclerDataAdapter;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayerActivity extends AppCompatActivity {
    TextView txt_nameSong;
    TextView txt_timeSong;
    TextView txt_timeTotal;
    ImageButton btn_back;
    ImageButton btn_playlist;
    ImageButton btn_comment;
    ImageButton btn_playlistChange;
    ImageButton btn_shuffle;
    ImageButton btn_repeat;

    ExtendedFloatingActionButton iconPlay;
    ExtendedFloatingActionButton preBtn;
    ExtendedFloatingActionButton nextBtn;
    ShapeableImageView imageSong;
    AppCompatSeekBar sk_song;

    Song getSong;
    String getPoster;
    List<Song> getListSongs;
    MediaPlayer audio;
    User getUser;
    int pos;
    Boolean isShuffle = false;
    Boolean isRepeat = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        txt_nameSong = (TextView) findViewById(R.id.txt_nameSong);
        txt_timeSong = (TextView) findViewById(R.id.txt_timeSong);
        txt_timeTotal = (TextView) findViewById(R.id.txt_timeTotal);
        iconPlay = (ExtendedFloatingActionButton) findViewById(R.id.icon_play);
        preBtn = (ExtendedFloatingActionButton) findViewById(R.id.preBtn);
        nextBtn = (ExtendedFloatingActionButton) findViewById(R.id.nextBtn);
        imageSong = (ShapeableImageView) findViewById(R.id.imageSong);
        sk_song = (AppCompatSeekBar) findViewById(R.id.sk_song);
        btn_back = (ImageButton) findViewById(R.id.btn_back);
        btn_playlist = (ImageButton) findViewById(R.id.btn_playlist);
        btn_comment = (ImageButton) findViewById(R.id.btn_comment);
        btn_playlistChange = (ImageButton) findViewById(R.id.btn_playlistChange);
        btn_shuffle = (ImageButton) findViewById(R.id.btn_shuffle);
        btn_repeat = (ImageButton) findViewById(R.id.btn_repeat);

        takeValue();
        generateAudio();
        audio.start();

        iconPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(audio.isPlaying()) {
                    audio.pause();
                    iconPlay.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.play_icon, null));
                } else {
                    audio.start();
                    iconPlay.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.pause_icon, null));
                }
            }
        });

        preBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // OFF Repeat
                if(isRepeat) {
                    Toast.makeText(PlayerActivity.this, "Repeat: OFF", Toast.LENGTH_LONG).show();
                    isRepeat = false;
                    audio.setLooping(false);
                }

                pos--;
                if(pos < 0) {
                    pos = getListSongs.size() - 1;
                }
                if(audio.isPlaying()) {
                    audio.stop();
                }

                // Reset getSong by position in List Song
                resetValue(pos);
                generateAudio();

                audio.start();
                iconPlay.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.pause_icon, null));
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // OFF Repeat
                if(isRepeat) {
                    Toast.makeText(PlayerActivity.this, "Repeat: OFF", Toast.LENGTH_LONG).show();
                    isRepeat = false;
                    audio.setLooping(false);
                }

                pos++;
                if(pos > getListSongs.size() - 1) {
                    pos = 0;
                }
                if(audio.isPlaying()) {
                    audio.stop();
                }

                // Reset getSong by position in List Song
                resetValue(pos);
                generateAudio();

                audio.start();
                iconPlay.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.pause_icon, null));
            }
        });

        sk_song.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                audio.seekTo(sk_song.getProgress());
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audio.stop();
                PlayerActivity.super.onBackPressed();
            }
        });

        btn_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReqUserSong item = new ReqUserSong(getUser.getId(), getSong.getId());

                // POST createHistory
                ApiService.apiService.createPlaylistSong(item).enqueue(new Callback<Song>() {
                    @Override
                    public void onResponse(Call<Song> call, Response<Song> response) {
                        Toast.makeText(PlayerActivity.this, "Success add Song to Playlist", Toast.LENGTH_LONG).show();
                        //listSongs = response.body();

                    }

                    @Override
                    public void onFailure(Call<Song> call, Throwable t) {
                        Toast.makeText(PlayerActivity.this, "Fail to call Api", Toast.LENGTH_LONG).show();
                        Toast.makeText(PlayerActivity.this, t.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlayerActivity.this, CommentActivity.class);
                intent.putExtra("userComment", getUser);
                intent.putExtra("song", getSong);

                startActivity(intent);
            }
        });

        btn_playlistChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlayerActivity.this, PlaylistActivity.class);
                intent.putExtra("userPlaylist", getUser);

                startActivity(intent);
            }
        });

        btn_shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isShuffle) {
                    Toast.makeText(PlayerActivity.this, "Shuffle: OFF", Toast.LENGTH_LONG).show();
                    isShuffle = false;
                } else {
                    Toast.makeText(PlayerActivity.this, "Shuffle: ON", Toast.LENGTH_LONG).show();
                    isShuffle = true;
                }
            }
        });

        btn_repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isRepeat) {
                    Toast.makeText(PlayerActivity.this, "Repeat: OFF", Toast.LENGTH_LONG).show();
                    isRepeat = false;

                    audio.setLooping(false);
                } else {
                    Toast.makeText(PlayerActivity.this, "Repeat: ON", Toast.LENGTH_LONG).show();
                    isRepeat = true;

                    audio.setLooping(true);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        audio.stop();
    }

    private void generateAudio() {
        // Get Song Path
        String getSongPath = "audio_" + getSong.getSongPath().split("/")[1].split("\\.")[0];
//        Toast.makeText(PlayerActivity.this, getSong.toString(), Toast.LENGTH_LONG).show();

        // Set Title
        txt_nameSong.setText(getSong.getSongName());
        // Set Image Song
        imageSong.setImageResource(getResources().getIdentifier("pic_" + getPoster, "drawable", getPackageName()));
        // Set audio
        audio = MediaPlayer.create(PlayerActivity.this, getResources().getIdentifier("@raw/" + getSongPath, null, getPackageName()));
        // Set Time
        setTime();
        // Update Time
        updateTime();
        // Create History
        sendHistory(getUser);
    }

    private void takeValue() {
        Intent intent = getIntent();

        // Get Value from Intent
        getSong = (Song) intent.getSerializableExtra("data");
        getPoster = intent.getStringExtra("poster");
        getListSongs = (List<Song>) intent.getSerializableExtra("listSongs");
        getUser = (User) intent.getSerializableExtra("user");

        // Set position
        pos = getPosById(getListSongs, getSong.getId());
//        Toast.makeText(PlayerActivity.this, Integer.toString(pos), Toast.LENGTH_LONG).show();
    }

    private void resetValue(int pos) {
        Song currentSong = getListSongs.get(pos);
        getSong = currentSong;
        getPoster = currentSong.getPoster().split("/")[1].split("\\.")[0];
    }

    private void setTime() {
        SimpleDateFormat reTime = new SimpleDateFormat("mm:ss");
        txt_timeTotal.setText(reTime.format(audio.getDuration()));

        // Setup seekbar
        sk_song.setMax(audio.getDuration());
    }

    private void updateTime() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat reTime = new SimpleDateFormat("mm:ss");
                txt_timeSong.setText(reTime.format(audio.getCurrentPosition()));

                // Update seekbar
                sk_song.setProgress(audio.getCurrentPosition());

                // Next Song when finished
                audio.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        // Update view for Song
                        sendViewSong(getUser);
                        
                        // Check Shuffle
                        if(isShuffle) {
                            Random random = new Random();
                            int randomNumber = random.nextInt(getListSongs.size());

                            pos = randomNumber;
                        } else {
                            pos++;
                        }

                        if(pos > getListSongs.size() - 1) {
                            pos = 0;
                        }
                        if(audio.isPlaying()) {
                            audio.stop();
                        }

                        // Reset getSong by position in List Song
                        resetValue(pos);
                        generateAudio();

                        audio.start();
                        iconPlay.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.pause_icon, null));
                    }
                });

                // Time to make new loop
                handler.postDelayed(this, 500);
            }
            // Delay when listening Event
        }, 100);
    }

    private int getPosById(List<Song> songList, int id) {
        for (Song song : songList) {
            if (song.getId() == id) {
                return songList.indexOf(song);
            }
        }
        return 0;
    }

    private void sendHistory(User getUser) {
        ReqUserSong item = new ReqUserSong(getUser.getId(), getSong.getId());

        // POST createHistory
        ApiService.apiService.createHistorySong(item).enqueue(new Callback<Song>() {
            @Override
            public void onResponse(Call<Song> call, Response<Song> response) {
//                Toast.makeText(PlayerActivity.this, "Success to call Api", Toast.LENGTH_LONG).show();
//                listSongs = response.body();

            }

            @Override
            public void onFailure(Call<Song> call, Throwable t) {
                Toast.makeText(PlayerActivity.this, "Fail to call Api", Toast.LENGTH_LONG).show();
                Toast.makeText(PlayerActivity.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void sendViewSong(User getUser) {
        ReqUserSong item = new ReqUserSong(getUser.getId(), getSong.getId());

        // POST createHistory
        ApiService.apiService.upViewSong(item).enqueue(new Callback<Song>() {
            @Override
            public void onResponse(Call<Song> call, Response<Song> response) {
//                Toast.makeText(PlayerActivity.this, "Success to call Api", Toast.LENGTH_LONG).show();
//                listSongs = response.body();

            }

            @Override
            public void onFailure(Call<Song> call, Throwable t) {
                Toast.makeText(PlayerActivity.this, "Fail to call Api", Toast.LENGTH_LONG).show();
                Toast.makeText(PlayerActivity.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
