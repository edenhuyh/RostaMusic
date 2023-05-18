package com.example.mobileck.recycler;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileck.R;
import com.example.mobileck.activity.LoginActivity;
import com.example.mobileck.activity.MainActivity;
import com.example.mobileck.activity.PlayerActivity;
import com.example.mobileck.model.Song;
import com.example.mobileck.model.User;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecyclerDataAdapter extends RecyclerView.Adapter<RecyclerDataAdapter.DataViewHolder> {
    private List<Song> songs;
    private User getUser;
    private Context context;

    // Construtor
    public RecyclerDataAdapter(Context context ,List<Song> songs, User getUser) {
        this.context = context;
        this.songs = songs;
        this.getUser = getUser;
    }

    // Chọn View trong View Layout Customer cần gắn cho các phần tử
    public static class DataViewHolder extends RecyclerView.ViewHolder {
        private final ShapeableImageView iv_song;
        private final TextView txt_nameSong;
        private final TextView txt_nameArtist;
        private final MaterialCardView layout_contain;
        public DataViewHolder(View view) {
            super(view);

            iv_song = view.findViewById(R.id.iv_song);
            txt_nameSong = (TextView) view.findViewById(R.id.txt_nameSong);
            txt_nameArtist = (TextView) view.findViewById(R.id.txt_nameArtist);
            layout_contain = view.findViewById(R.id.layout_contain);
        }

        public ShapeableImageView getIv_song() {
            return iv_song;
        }

        public TextView getTxt_nameSong() {
            return txt_nameSong;
        }

        public TextView getTxt_nameArtist() {
            return txt_nameArtist;
        }

        public MaterialCardView getLayout_contain() {
            return layout_contain;
        }
    }

    // Lấy View Layout Customer
    @NonNull
    @Override
    public RecyclerDataAdapter.DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.music_view, parent, false);

        return new DataViewHolder(itemView);
    }

    // Set từng giá trị trong arr vào View Layout Customer
    @Override
    public void onBindViewHolder(RecyclerDataAdapter.DataViewHolder viewHolder, int pos) {
        String getPosterPath = "";

        if(!songs.isEmpty()) {
            getPosterPath = songs.get(pos).getPoster().split("/")[1].split("\\.")[0];
//            Log.d("hi", getPosterPath);
//            Log.d("hi", songs.get(pos).getSongName());
            // Lấy ID drawable từ chuỗi tài nguyên
            int resourceId = context.getResources().getIdentifier("pic_" + getPosterPath, "drawable", context.getPackageName());

            // Đặt drawable cho ImageView
            viewHolder.getIv_song().setImageDrawable(context.getResources().getDrawable(resourceId));
            viewHolder.getTxt_nameSong().setText(songs.get(pos).getSongName());
            viewHolder.getTxt_nameArtist().setText(songs.get(pos).getArtistDetail().getName());
        }

        String finalGetPosterPath = getPosterPath;
        viewHolder.getLayout_contain().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayerActivity.class);
                intent.putExtra("data", songs.get(pos));
                intent.putExtra("poster", finalGetPosterPath);
                intent.putExtra("listSongs", (Serializable) songs);
                intent.putExtra("user", (Serializable) getUser);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songs == null ? 0 : songs.size();
    }
}
