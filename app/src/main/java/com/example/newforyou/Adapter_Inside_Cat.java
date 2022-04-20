package com.example.newforyou;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Adapter_Inside_Cat extends RecyclerView.Adapter<Adapter_Inside_Cat.viewHolder> {
    public static FirebaseAuth authProfileLogin; // عرفته ستاتيك عشان يقدر يشوفها في الميود الي تحت
    // there declared ArrayList
    // to pass data from activity_main.xml to custom_grid_layout.xml
    public static List<String> titles;
    //public ImageButton favInsideDB;
    public static int position;
    static FirebaseAuth mAuth;
    static boolean checked = false;
    public List<Integer> images;
    public Context context;
    public LayoutInflater inflater;
    public EditText editText;
    List<String> categoryListAll;
    // create filter
    private final Filter filter = new Filter() {

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


        // run on a ui thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            titles.clear();
            //catName.addAll((Collection<? extends String>) filterResults.values);
            titles.addAll((List) filterResults.values);
            notifyDataSetChanged();

        }
    };
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    //android.widget.ImageButton ImageButton;
    DatabaseReference favoritereference;

    // this constructor for put data inside ArrayList
    public Adapter_Inside_Cat(Context ctx, List<String> titles, List<Integer> images, EditText editText) {
        this.titles = titles;
        this.images = images;
        this.context = ctx;
        this.inflater = LayoutInflater.from(ctx);
        this.editText = editText;
        this.categoryListAll = new ArrayList<>(titles);


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void favoritos(viewHolder holder, int pos, boolean isClick) {

        mAuth = FirebaseAuth.getInstance();
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Registered User").child(Objects.requireNonNull(mAuth.getUid())).child("Favoritos");
        if (isClick) {
            ref.get().addOnSuccessListener(dataSnapshot -> {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.hasChild(pos + "-" + titles.get(pos))) {
                        ref.child(pos + "-" + titles.get(pos)).removeValue();
                        holder.favInsideDB.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);

                    } else {
                        ref.child(pos + "-" + titles.get(pos)).setValue(titles.get(pos));
                        holder.favInsideDB.setBackgroundResource(R.color.purple);
                        holder.favInsideDB.setBackgroundResource(R.drawable.ic_baseline_favorite_24);

                    }
                } else {
                    ref.child(pos + "-" + titles.get(pos)).setValue(titles.get(pos));
                    holder.favInsideDB.setBackgroundResource(R.color.purple);
                    holder.favInsideDB.setBackgroundResource(R.drawable.ic_baseline_favorite_24);

                }
            });


        } else
            ref.get().addOnSuccessListener(dataSnapshot -> {
                if (dataSnapshot.exists()) {
                    Log.d("ddddddddddddddddddd ", String.valueOf(dataSnapshot.hasChild(pos + "-" + titles.get(pos))));
                    if (dataSnapshot.hasChild(pos + "-" + titles.get(pos))) {
                        holder.favInsideDB.setBackgroundResource(R.color.purple);
                        holder.favInsideDB.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                    }
                } else {
                    holder.favInsideDB.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
                }
            });

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.inside_cat, parent, false);
        return new viewHolder(view) {

        };
    }

    // Method fot filter SearchView
    //this method is that made data change in item in every-time we create new bind for ViewHolder
    //  this data come from ArrayList (declared inAdapter_Inside_Cat) inside it more than category
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
        authProfileLogin = FirebaseAuth.getInstance();
        this.position = position;
        holder.title.setText(titles.get(position));
        holder.gridIcon.setImageResource(images.get(position));
        favoritos(holder, position, false);
        if (authProfileLogin.getCurrentUser() != null) {
        }
        holder.favInsideDB.setOnClickListener(v -> {

            if (authProfileLogin.getCurrentUser() != null) {
                favoritos(holder, position, true);
            } else {
                Toast.makeText(holder.favInsideDB.getContext(), "يجب عليك إنشاء حساب حتى تسطيع إضافة العنصر الى المفضلة ", Toast.LENGTH_SHORT).show();
            }

        });
        //holder.editText.setText(editText.getText());


        // holder.setOnClickListener(viewHolder)

        // هذي الاون كليك عشان لما يضغط على اي صورة جوا الكاتيجوري ياخذ النص الي جواها ويحطه في الاديت تيكست
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.append(titles.get(position) + " ");
            }
        });

    }

    //this method return number items founded in ArrayList
    @Override
    public int getItemCount() {

        return titles.size();
    }

    public Filter getFilter() {

        return filter;
    }

    // this class we declared inside it evert variables even we can access it in constructor  for this the class
    public static class viewHolder extends RecyclerView.ViewHolder {

        Button favInsideDB;
        //        ImageButton convertToVoice ;
        TextView title;
        ImageView gridIcon;
        EditText editText;


        // View parentLayout;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView2);
            gridIcon = itemView.findViewById(R.id.imageView2);
            editText = itemView.findViewById(R.id.convertToVoice);
            favInsideDB = itemView.findViewById(R.id.favBtn2);


            // هذي الاون كليك عشلن لما يضغط على اي قلب موجود على الصور الي داخل كل كاتيجوري
            favInsideDB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    authProfileLogin = FirebaseAuth.getInstance();
//                    if (authProfileLogin.getCurrentUser() != null) {
//                        favInsideDB.setBackgroundResource(R.color.purple);
//                        favInsideDB.setBackgroundResource(R.drawable.ask);
//                        Toast.makeText(favInsideDB.getContext(), "تم إضافة العنصر الى المفضلة", Toast.LENGTH_SHORT).show();
//                        favoritos();
//                    } else {
//                        Toast.makeText(favInsideDB.getContext(), "يجب عليك إنشاء حساب حتى تسطيع إضافة العنصر الى المفضلة ", Toast.LENGTH_SHORT).show();
//
//                    }

                }
            });
        }

    }


}
