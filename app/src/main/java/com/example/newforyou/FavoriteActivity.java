package com.example.newforyou;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FavoriteActivity extends AppCompatActivity{
    RecyclerView recyclerView;
    FavoriteAdapter favoriteAdapter;
    List<String> favoriteList = new ArrayList<>();
    ArabicTTS  tts ;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        tts = new ArabicTTS();
        tts.prepare(this);
        fetchFavorite();
        favoriteAdapter=new FavoriteAdapter(this,favoriteList);
        recyclerView = findViewById(R.id.favorite_recyclerview);
        favoriteAdapter.setOnItemClickListener((position, v) -> {
            if(!favoriteList.get(position).equals("")){
                tts.talk(favoriteList.get(position));
            }
        });
        favoriteAdapter.setOnFavoriteClickListener((position, v) -> {
            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Registered User").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("Favoritos");
            ref.get().addOnSuccessListener(dataSnapshot -> {
                if (dataSnapshot.exists()) {
                    dataSnapshot.getChildren().forEach(dataSnapshot1 -> {
                        if (Objects.requireNonNull(dataSnapshot1.getValue()).toString().equals(favoriteList.get(position))) {
                            ref.child(Objects.requireNonNull(dataSnapshot1.getKey())).removeValue().addOnCompleteListener(command -> fetchFavorite());
                        }

                    });
                }
            });
//            favoriteList.remove(position);
//            fetchFavorite();
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void fetchFavorite() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Registered User")
                .child(Objects.requireNonNull(mAuth.getUid())).child("Favoritos");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                               @Override
                                               public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                   favoriteList.clear();
                                                   snapshot.getChildren().forEach(dataSnapshot1 -> {
                                                       favoriteList.add(Objects.requireNonNull(dataSnapshot1.getValue()).toString());
                                                   });
                                                   LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FavoriteActivity.this, LinearLayoutManager.VERTICAL, false);
                                                   recyclerView.setLayoutManager(linearLayoutManager);
                                                   favoriteAdapter = new FavoriteAdapter(FavoriteActivity.this, favoriteList);
                                                   recyclerView.setAdapter(favoriteAdapter);
                                               }

                                               @Override
                                               public void onCancelled(@NonNull DatabaseError error) {

                                               }
                                           }
        );
     /*   ref.get().addOnSuccessListener(dataSnapshot -> {
            if (dataSnapshot.exists()) {
                favoriteList.clear();
                dataSnapshot.getChildren().forEach(dataSnapshot1 -> {
                    favoriteList.add(Objects.requireNonNull(dataSnapshot1.getValue()).toString());
                });
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FavoriteActivity.this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
                favoriteAdapter = new FavoriteAdapter(FavoriteActivity.this, favoriteList);
                recyclerView.setAdapter(favoriteAdapter);

            }
        });*/
    }



}