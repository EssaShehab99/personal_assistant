package com.example.newforyou;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder> {
    List<String> favoriteList = new ArrayList<>();
    private Context context;
    private static ClickListener clickListener;
    private static ClickListener favoriteClickListener;

    public FavoriteAdapter(Context context, List<String> favoriteList) {
        this.context = context;
        this.favoriteList = favoriteList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView favoriteTV;
        public Button favoriteBTN;
        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            favoriteTV = itemView.findViewById(R.id.favorite_tv);
            favoriteBTN = itemView.findViewById(R.id.favorite_btn);
        }

        public void setFavoriteTV(String value) {
            favoriteTV.setText(value);
        }

        @Override
        public void onClick(View view) {
            try {
                clickListener.onItemClick(getAdapterPosition(), view);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listViewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_card, null);
        return new MyViewHolder(listViewItem);

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setFavoriteTV(favoriteList.get(position));
        holder.favoriteBTN.setOnClickListener(v1 -> {
            try {
                favoriteClickListener.onItemClick(position,v1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        FavoriteAdapter.clickListener = clickListener;
    }
    public void setOnFavoriteClickListener(ClickListener clickListener) {
        FavoriteAdapter.favoriteClickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    public interface ClickListener {
        void onItemClick(int position, View v) throws Exception;
    }
}
