package com.example.newforyou;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LinearFavoriteActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearFavoriteAdapter linearFavoriteAdapter;
    List<String> linearFavoriteList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_favorite);
        fetchLinearFavorite();

        recyclerView = findViewById(R.id.favorite_recyclerview);
        linearFavoriteAdapter=new LinearFavoriteAdapter(this,linearFavoriteList);
        linearFavoriteAdapter.setOnItemClickListener((position, v) -> {


            Intent intent;
            String url ="https://youtube.com/c/%D9%87%D8%AD%D8%A8%D8%A8%D9%83%D9%81%D9%89%D8%A7%D9%84%D8%A5%D8%B4%D8%A7%D8%B1%D8%A9" ;
            try {
                intent =new Intent(Intent.ACTION_VIEW);
                intent.setPackage("com.google.android.youtube");
                intent.setData(Uri.parse(url));
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
        linearFavoriteAdapter.setOnFavoriteClickListener((position, v) -> {
            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Registered User").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("linearFavorite");
            ref.get().addOnSuccessListener(dataSnapshot -> {
                if (dataSnapshot.exists()) {
                    dataSnapshot.getChildren().forEach(dataSnapshot1 -> {
                        if (Objects.requireNonNull(dataSnapshot1.getValue()).toString().equals(linearFavoriteList.get(position))) {
                            ref.child(Objects.requireNonNull(dataSnapshot1.getKey())).removeValue().addOnCompleteListener(command -> fetchLinearFavorite());
                        }

                    });
                }
            });
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    void fetchLinearFavorite() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Registered User")
                .child(Objects.requireNonNull(mAuth.getUid())).child("linearFavorite");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                               @Override
                                               public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                   linearFavoriteList.clear();
                                                   snapshot.getChildren().forEach(dataSnapshot1 -> {
                                                       linearFavoriteList.add(Objects.requireNonNull(dataSnapshot1.getValue()).toString());
                                                   });
                                                   LinearLayoutManager linearLayoutManager = new LinearLayoutManager(LinearFavoriteActivity.this, LinearLayoutManager.VERTICAL, false);
                                                   recyclerView.setLayoutManager(linearLayoutManager);
                                                   linearFavoriteAdapter = new LinearFavoriteAdapter(LinearFavoriteActivity.this, linearFavoriteList);
                                                   recyclerView.setAdapter(linearFavoriteAdapter);
                                               }

                                               @Override
                                               public void onCancelled(@NonNull DatabaseError error) {

                                               }
                                           }
        );
    }
}