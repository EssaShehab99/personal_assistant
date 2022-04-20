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

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder> {
    List<String> favoriteList = new ArrayList<>();
    private Context context;
    private static ClickListener clickListener;
    private static ClickListener favoriteClickListener;

    List<String> titles=new ArrayList<>();
    List<Integer> images=new ArrayList<>();
    public FavoriteAdapter(Context context, List<String> favoriteList) {
        initialImageAndText();
        this.context = context;
        this.favoriteList = favoriteList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView favoriteTV;
        public Button favoriteBTN;
        public ImageView cardIMG;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            favoriteTV = itemView.findViewById(R.id.favorite_tv);
            favoriteBTN = itemView.findViewById(R.id.favorite_btn);
            cardIMG = itemView.findViewById(R.id.card_img);
        }

        public void setFavoriteTV(String value) {
            favoriteTV.setText(value);
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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listViewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_card, null);
        return new MyViewHolder(listViewItem);

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setFavoriteTV(favoriteList.get(position));
        holder.setFavoriteImage(determineImage(favoriteList.get(position)));
        holder.favoriteBTN.setOnClickListener(v1 -> {
            try {
                favoriteClickListener.onItemClick(position, v1);
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

    int determineImage(String text) {

        if (titles.contains(text)) {
            return  images.get(titles.indexOf(text));
        }
        return R.drawable.ask;
    }

    void initialImageAndText(){
        titles.add("أستفسر");
        titles.add("أنا");
        titles.add("أين");
        titles.add("قسم");
        titles.add("دكتور العيون");
        titles.add("دكتور الباطنية");
        titles.add("دكتور العظام");
        titles.add("دكتور نفسي");
        titles.add("صيدلية");
        titles.add("الطوارئ");

        images.add(R.drawable.ask);
        images.add(R.drawable.me);
        images.add(R.drawable.where);
        images.add(R.drawable.secton);
        images.add(R.drawable.eye);
        images.add(R.drawable.stomch);
        images.add(R.drawable.bone);
        images.add(R.drawable.psychologist);
        images.add(R.drawable.pharmcy);
        images.add(R.drawable.emergency1);

        titles.add("أين");
        titles.add("متى");
        titles.add("طائرة");
        titles.add("تذكرة");
        titles.add("مقعدي");
        titles.add("بوابة");
        titles.add("بطاقة صعود");
        titles.add("موعد الإنطلاق");
        titles.add("موعد الهبوط");
        titles.add("صالة الإنتظار");


        images.add(R.drawable.where);
        images.add(R.drawable.whene);
        images.add(R.drawable.airplane);
        images.add(R.drawable.plane_ticket);
        images.add(R.drawable.seat);
        images.add(R.drawable.gate);
        images.add(R.drawable.boarding_pass);
        images.add(R.drawable.arrival);
        images.add(R.drawable.deoarture_time);
        images.add(R.drawable.lounge);
        titles.add("أنا");
        titles.add("أريد");
        titles.add("الدفع");
        titles.add("كريب");
        titles.add("بان كيك");
        titles.add("تشيز كيك");
        titles.add("مشروب بارد");
        titles.add("مشروب ساخن");
        titles.add("شاي");
        titles.add("كروسان");

        images.add(R.drawable.me);
        images.add(R.drawable.iwants);
        images.add(R.drawable.pay);
        images.add(R.drawable.crepe);
        images.add(R.drawable.pancakes);
        images.add(R.drawable.cake);
        images.add(R.drawable.could_coffee);
        images.add(R.drawable.hot_cup);
        images.add(R.drawable.tea);
        images.add(R.drawable.croissant);

        titles.add("أنا");
        titles.add("أريد");
        titles.add("قائمة الطعام");
        titles.add("برجر دجاج");
        titles.add("برجر لحم");
        titles.add("سَلطةٌ");
        titles.add("سوشي");
        titles.add("بيتزا");
        titles.add("كَبسة");
        titles.add("باستا ");
        titles.add("مشروب غازي");
        titles.add("عصير");


        images.add(R.drawable.me);
        images.add(R.drawable.iwants);
        images.add(R.drawable.menu1);
        images.add(R.drawable.burger);
        images.add(R.drawable.burger_m);
        images.add(R.drawable.salad);
        images.add(R.drawable.sushi);
        images.add(R.drawable.pizza);
        images.add(R.drawable.kabsa);
        images.add(R.drawable.pasta);
        images.add(R.drawable.softdrinks);
        images.add(R.drawable.juice);

        titles.add("أنا");
        titles.add("أريد");
        titles.add("أين");
        titles.add("قسم");
        titles.add("المشروبات");
        titles.add("فواكه");
        titles.add("المخبوزات");
        titles.add("خضروات");
        titles.add("اللحوم");
        titles.add("المنظفات");

        images.add(R.drawable.me);
        images.add(R.drawable.iwants);
        images.add(R.drawable.where);
        images.add(R.drawable.secton);
        images.add(R.drawable.milk);
        images.add(R.drawable.fruit);
        images.add(R.drawable.bread);
        images.add(R.drawable.vegetables);
        images.add(R.drawable.proteins);
        images.add(R.drawable.cleaning);

    }


}