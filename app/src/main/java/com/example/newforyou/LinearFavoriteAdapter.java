package com.example.newforyou;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LinearFavoriteAdapter extends RecyclerView.Adapter<LinearFavoriteAdapter.MyViewHolder>{
    List<String> linearFavoriteList = new ArrayList<>();
    private Context context;
    private static LinearFavoriteAdapter.ClickListener clickListener;
    private static LinearFavoriteAdapter.ClickListener linearFavoriteClickListener;
    List<String> titles=new ArrayList<>();
    List<Integer> images=new ArrayList<>();

    public LinearFavoriteAdapter(Context context, List<String> linearFavoriteList) {
        initialImageAndText();
        this.context = context;
        this.linearFavoriteList = linearFavoriteList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView titleTV;
        public Button favoriteBTN;
        public ImageView cardIMG;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            titleTV = itemView.findViewById(R.id.textViewl2);
            favoriteBTN = itemView.findViewById(R.id.favBtnl2);
            cardIMG = itemView.findViewById(R.id.imageViewl2);
        }

        public void setTitleTV(String value) {
            titleTV.setText(value);
        }

        public void setFavoriteImage(int value) {
            cardIMG.setBackgroundResource(value);
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
    public LinearFavoriteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listViewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.learrn_sign_language, null);
        return new LinearFavoriteAdapter.MyViewHolder(listViewItem);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onBindViewHolder(@NonNull LinearFavoriteAdapter.MyViewHolder holder, int position) {
        holder.setTitleTV(linearFavoriteList.get(position));
        holder.setFavoriteImage(determineImage(linearFavoriteList.get(position)));
        holder.favoriteBTN.setOnClickListener(v1 -> {
            try {
                linearFavoriteClickListener.onItemClick(position, v1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void setOnItemClickListener(LinearFavoriteAdapter.ClickListener clickListener) {
        LinearFavoriteAdapter.clickListener = clickListener;
    }
    public void setOnFavoriteClickListener(LinearFavoriteAdapter.ClickListener clickListener) {
        LinearFavoriteAdapter.linearFavoriteClickListener = clickListener;
    }
    @Override
    public int getItemCount() {
        return linearFavoriteList.size();
    }

    int determineImage(String text) {

        if (titles.contains(text)) {
            return  images.get(titles.indexOf(text));
        }
        return R.drawable.ask;
    }

    void initialImageAndText(){
        titles.add("الحروف العربية");
        titles.add("الحروف الإنجليزية");
        titles.add("الأرقام ");
        titles.add("كلمات");
        titles.add("كلمات");
        titles.add("قناة يوتيوب لتعلم لغة الإشارة");
        titles.add("تعلم بعض المصلطحات بلغة الإشارة !");
        titles.add("موقع لتعلم لغة الإشارة");
        titles.add("كتاب قاموس تعلم لغة الإشارة");

        images.add(R.drawable.character);
        images.add(R.drawable.character_eng);
        images.add(R.drawable.number);
        images.add(R.drawable.words);
        images.add(R.drawable.wd);
        images.add(R.drawable.ytube);
        images.add(R.drawable.terms);
        images.add(R.drawable.cource);
        images.add(R.drawable.dictionary_learner);
    }


    public interface ClickListener {
        void onItemClick(int position, View v) throws Exception;
    }
}
