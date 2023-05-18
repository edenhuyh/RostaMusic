package com.example.mobileck.recycler;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileck.R;
import com.example.mobileck.activity.PlayerActivity;
import com.example.mobileck.model.Comment;
import com.example.mobileck.model.Song;
import com.example.mobileck.model.User;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RecyclerCommentAdapter extends RecyclerView.Adapter<RecyclerCommentAdapter.DataViewHolder> {
    private List<Comment> comments;
    private Context context;

    // Construtor
    public RecyclerCommentAdapter(Context context , List<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    // Chọn View trong View Layout Customer cần gắn cho các phần tử
    public static class DataViewHolder extends RecyclerView.ViewHolder {
        private final ImageView iv_song;
        private final TextView txt_name;
        private final TextView txt_comment;
        private final TextView txt_date;
        public DataViewHolder(View view) {
            super(view);

            iv_song = view.findViewById(R.id.iv_song);
            txt_name = (TextView) view.findViewById(R.id.txt_name);
            txt_comment = (TextView) view.findViewById(R.id.txt_comment);
            txt_date = (TextView) view.findViewById(R.id.txt_date);
        }

        public ImageView getIv_song() {
            return iv_song;
        }

        public TextView getTxt_name() {
            return txt_name;
        }

        public TextView getTxt_comment() {
            return txt_comment;
        }

        public TextView getTxt_date() {
            return txt_date;
        }
    }

    // Lấy View Layout Customer
    @NonNull
    @Override
    public RecyclerCommentAdapter.DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.music_comment, parent, false);

        return new DataViewHolder(itemView);
    }

    // Set từng giá trị trong arr vào View Layout Customer
    @Override
    public void onBindViewHolder(RecyclerCommentAdapter.DataViewHolder viewHolder, int pos) {
        if(!comments.isEmpty()) {
            // Re-format Time
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date date = null;
            try {
                date = inputFormat.parse(comments.get(pos).getCreatedAt());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy");
            String output = outputFormat.format(date);

            viewHolder.getTxt_name().setText(comments.get(pos).getUserDetail().getUsername());
            viewHolder.getTxt_comment().setText(comments.get(pos).getComment());
            viewHolder.getTxt_date().setText(output);
        }
    }

    @Override
    public int getItemCount() {
        return comments == null ? 0 : comments.size();
    }
}
