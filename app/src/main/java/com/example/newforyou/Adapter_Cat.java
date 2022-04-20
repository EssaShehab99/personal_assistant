package com.example.newforyou;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Cat extends RecyclerView.Adapter<Adapter_Cat.viewHolder> implements Filterable {
    public static FirebaseAuth authProfileLogin; // عرفته ستاتيك عشان يقدر يشوفها في الميود الي تحت
    //    private  static  final String TAG = "Adapter_Cat";
    // to pass data from activity_main.xml to custom_grid_layout.xml
    public List<String> catName;
    public List<Integer> catImage;
    //public List<String> favBtn;
    public Context context;
    public LayoutInflater inflater;
    //All category before Filter
    List<String> categoryListAll;
    // create filter
    public final Filter filter = new Filter() {

        // run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {


            List<String> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(categoryListAll);

            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (String category : categoryListAll) {
                    if (category.toString().toLowerCase().contains(filterPattern)) {
                        filteredList.add(category);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }


//            if (CharSequence.toString().isEmpty()){
//                filteredList.addAll(categoryListAll);
//            }else {
//
//                for (String category: categoryListAll){
//
//
//                    if (category.toLowerCase().contains(CharSequence.toString().toLowerCase())){
//                        filteredList.add(category);
//                    }
//                }
//
//            }
//
//            FilterResults filterResults = new FilterResults();
//            filterResults.values = filteredList;
//
//            return filterResults;
        //}

        // run on a ui thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            catName.clear();
            //catName.addAll((Collection<? extends String>) filterResults.values);
            catName.addAll((List) filterResults.values);
            notifyDataSetChanged();

        }
    };


    // عند إنشاء كائنات من هذا الكلاس سنستخدم هذا الكونستركتور لإضافةعناوين وصور الكتيجوري مباشرة عند تعريفهم
// عشان نحد داتا جوا المصفوفة ونقدر نستخدمها علطول
    public Adapter_Cat(Context ctx, List<String> catName, List<Integer> catImage) {
        this.catName = catName;
        this.catImage = catImage;
        //this.favBtn = favBtn;
        this.context = ctx;
        this.inflater = LayoutInflater.from(ctx);
        //Initialize All Category even become all category equal a new ArrayList
        // in parameter will pass All Category that will be obtained from itself constructor , so contained catName on All this Items
        // so , we will change catName list by Filtering even don't keep track all the category(catName )
        // but categoryListAll will keep on track All the category that found inside recycleView
        this.categoryListAll = new ArrayList<>(catName);


    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.main_cat, parent, false);
        return new viewHolder(view) {

        };
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        //create obj from variables catName and catImage that found in constructor
        // put catName and catImage for every category
        // بياخذ النص للكاتيجوري وصورة الكاتيوجوري بناء على البوزيشيين بتاعها الي عليه الدور
        holder.catName.setText(catName.get(position));
        holder.catImage.setImageResource((catImage.get(position)));
        //holder.favBtn.setBackgroundColor(favBtn);

        // holder.setOnClickListener(viewHolder)
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.getAdapterPosition() == 0) {
                    Intent i = new Intent(context, Hospital_Cat.class);
                    context.startActivity(i);
//                i.addCategory(String.valueOf(titles.add("مستشفى")));
//                    i.addCategory(String.valueOf(titles.add("مستشفى")));
//                    i.addCategory(String.valueOf(images.add(R.drawable.hospital)));

//


                } else if (holder.getAdapterPosition() == 1) {
                    Intent i = new Intent(context, Restraint_Cat.class);
                    context.startActivity(i);

                } else if (holder.getAdapterPosition() == 2) {
                    Intent i = new Intent(context, Coffee_Cat.class);
                    context.startActivity(i);

                } else if (holder.getAdapterPosition() == 3) {
                    Intent i = new Intent(context, Airport_Cat.class);
                    context.startActivity(i);

                } else {
                    Intent i = new Intent(context, SuperMarket_Cat.class);
                    context.startActivity(i);

                }

            }
        });

    }

//    public String toString(){
//        return List<String>;
//    }
    // Method fot filter SearchView

    @Override
    public int getItemCount() {

        return catName.size();
    }

    @Override
    public Filter getFilter() {

        return filter;
    }

    // end filter

    public static class viewHolder extends RecyclerView.ViewHolder {

        public static Button favBtn;
        TextView catName;
        ImageView catImage;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            catName = itemView.findViewById(R.id.catName);
            catImage = itemView.findViewById(R.id.catImage);
            favBtn = itemView.findViewById(R.id.favBtn);

            itemView.findViewById(R.id.favBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    authProfileLogin = FirebaseAuth.getInstance();

                    if (authProfileLogin.getCurrentUser() != null) {

//                        favoritos();
                        //favBtn.setBackgroundResource(R.color.purple);
                        favBtn.setBackgroundResource(R.drawable.ic_baseline_favorite_24);


                        Toast.makeText(favBtn.getContext(), "تم إضافة القائمة الى المفضلة", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(favBtn.getContext(), "يجب عليك إنشاء حساب حتى تسطيع إضافة القائمة الى المفضلة ", Toast.LENGTH_SHORT).show();

                    }


                }
            });
        }


    }
}
