package com.example.newforyou;

import static android.content.Context.DOWNLOAD_SERVICE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.MimeTypeMap;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import android.view.KeyEvent;


public class Adapter_Learrn_Sign_Language extends RecyclerView.Adapter<Adapter_Learrn_Sign_Language.viewHolder>{
    // there declared ArrayList
    // to pass data from activity_main.xml to custom_grid_layout.xml
    public List<String> titles;
    public List<Integer> images;
    public Context context;
    public LayoutInflater inflater;
    List<String> categoryListAll;
    //    public static ScaleGestureDetector scaleGestureDetector;
//    public static float mScaleFactor = 1.0f;
//    String[] titles;
    public static FirebaseAuth authProfileLogin; // عرفته ستاتيك عشان يقدر يشوفها في الميود الي تحت
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference favoritereference;
//    public Adapter_Learrn_Sign_Language.RecyclerViewOnTouchListener touchListener;
//Adapter_Learrn_Sign_Language touchListener;



//    WebView webView;

//    WebView webView;

//    titles = new String[]{"","" }



    //public ImageButton favInsideDB;
    public int position;
    //android.widget.ImageButton ImageButton;


    // this constructor for put data inside ArrayList
    public Adapter_Learrn_Sign_Language(Context ctx, List<String> titles, List<Integer> images){
        this.titles = titles;
        this.images = images;
        this.context = ctx;
        this.inflater = LayoutInflater .from(ctx);
        this.categoryListAll = new ArrayList<>(titles);

        // this.favInsideDB =favInsideDB;
//        scaleGestureDetector = new ScaleGestureDetector(context.getApplicationContext(), new viewHolder.ScaleListener());



    }








    @NonNull
    @Override
    public Adapter_Learrn_Sign_Language.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.learrn_sign_language,parent, false);
        return new Adapter_Learrn_Sign_Language.viewHolder(view) {

        };
    }

    //this method is that made data change in item in every-time we create new bind for ViewHolder
    //  this data come from ArrayList (declared inAdapter_Inside_Cat) inside it more than category
    @Override
    public void onBindViewHolder(@NonNull Adapter_Learrn_Sign_Language.viewHolder holder, @SuppressLint("RecyclerView") int position) {

        this.position = position;
        holder.title.setText(titles.get(position) );
        holder.gridIcon.setImageResource(images.get(position));
        // To prevent RecyclerView from recycling some items
        holder.setIsRecyclable(false);





        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent shareIntent;
//                Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher);
//                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/Share.png";
//                OutputStream out = null;
//                File file=new File(path);
//                try {
//                    out = new FileOutputStream(file);
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//                    out.flush();
//                    out.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                path=file.getPath();
//                Uri bmpUri = Uri.parse("file://"+path);
//                shareIntent = new Intent(android.content.Intent.ACTION_SEND);
//                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
//                shareIntent.putExtra(Intent.EXTRA_TEXT,"Hey please check this application " + "https://play.google.com/store/apps/details?id=" +context.getPackageName());
//                shareIntent.setType("image/png");
//                context.startActivity(Intent.createChooser(shareIntent,"Share with"));





                Uri imageUri;
                Intent intent;

                imageUri = Uri.parse("android.resource://" + context.getPackageName()
                        + "/drawable/" + "ic_launcher");

                intent = new Intent(Intent.ACTION_SEND);
                //text
                intent.putExtra(Intent.EXTRA_TEXT, "Hello");
                //image
                intent.putExtra(Intent.EXTRA_STREAM, imageUri);
                //type of things
                intent.setType("/");
                //sending
                context.startActivity(intent);

//                Uri imageUri = Uri.parse("android.resource://" + context.getPackageName()
//                        + "/drawable/" + "ic_launcher");
//                Intent shareIntent = new Intent();
//                shareIntent.setAction(Intent.ACTION_SEND);
//                shareIntent.putExtra(Intent.EXTRA_TEXT, "Hello");
//                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
//                shareIntent.setType("image/jpeg");
//                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                context.startActivity(Intent.createChooser(shareIntent, "send"));

                //Share RecycleView
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                    intent.putExtra(Intent.EXTRA_TEXT, "Let's go for a trip to "
//                            + titles.get(holder.getAdapterPosition()) +
//                            "\nHere is the link to the full review: " + images.get(holder.
//                            getAdapterPosition()));
//                    intent.setType("text/plain");
//                    context.startActivity(Intent.createChooser(intent, "Send To"));


            }
        });



        //holder.editText.setText(editText.getText());


        // holder.setOnClickListener(viewHolder)

        // هذي الاون كليك عشان لما يضغط على اي صورة جوا الكاتيجوري ياخذ النص الي جواها ويحطه في الاديت تيكست
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            //            @SuppressLint("SetJavaScriptEnabled")
            @Override
            public  void onClick(View view) {



                if (holder.getAdapterPosition() == 0){



                }else if (holder.getAdapterPosition() == 1){


                } else if (holder.getAdapterPosition() == 2){


                } else if (holder.getAdapterPosition() == 3){


                } else if (holder.getAdapterPosition() == 4){
                }
                else if (holder.getAdapterPosition() == 5) {

                    Intent intent=null;
//                    String url ="http://www.youtube.com/channel/UCnq80gyMXXtOb74tB7iY0ww" ;
                    String url ="https://youtube.com/c/%D9%87%D8%AD%D8%A8%D8%A8%D9%83%D9%81%D9%89%D8%A7%D9%84%D8%A5%D8%B4%D8%A7%D8%B1%D8%A9" ;

                    try {
                        intent =new Intent(Intent.ACTION_VIEW);
                        intent.setPackage("com.google.android.youtube");
                        intent.setData(Uri.parse(url));
                        context.startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        context.startActivity(intent);
//                        https://www.youtube.com/channel/UCnq80gyMXXtOb74tB7iY0ww
                    }

//                    Intent i = new Intent(context.getApplicationContext(), DeafMute_Edit_Profile.class);
//                    context.startActivity(i);
                }
                else if (holder.getAdapterPosition() == 6){


                    Intent intent;
                    String url ="https://youtu.be/DWIOMA79fvU" ;
                    try {
                        intent =new Intent(Intent.ACTION_VIEW);
                        intent.setPackage("com.google.android.youtube");
                        intent.setData(Uri.parse(url));
                        context.startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        context.startActivity(intent);
//                        https://www.youtube.com/channel/UCnq80gyMXXtOb74tB7iY0ww
                    }



                }
                else if (holder.getAdapterPosition() == 7){

                    Intent i = new Intent(Intent.ACTION_VIEW , Uri.parse("https://www.specialegypt.com/2021/01/free-arabic-sign-language-learning-course.html"));
                    context.startActivity(i);


                }


                else if (holder.getAdapterPosition() == 8){
                    Intent i = new Intent(Intent.ACTION_VIEW , Uri.parse("https://books-library.net/free-195386349-download"));
                    context.startActivity(i);

                }
                else if (holder.getAdapterPosition() == 9){
                }
                else if (holder.getAdapterPosition() == 10){
                }
                else if (holder.getAdapterPosition() == 11){
                }
                else if (holder.getAdapterPosition() == 12){
                }

            }
        });



    }



//
//    public void setTextFromImg(){
//        editText.setText(titles.get(position));
//    }
//
//

    //this method return number items founded in ArrayList
    @Override
    public int getItemCount() {

        return titles.size();
    }

    // Method fot filter SearchView

    public Filter getFilter() {

        return filter;
    }
    // create filter
    private final Filter filter = new Filter() {

        // run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {


            List<String> filteredList = new ArrayList<>();

            if(charSequence ==null || charSequence.length()==0){
                filteredList.addAll(categoryListAll);

            }else{
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(String category : categoryListAll){
                    if(category.toString().toLowerCase().contains(filterPattern)){
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
            titles.clear();
            //catName.addAll((Collection<? extends String>) filterResults.values);
            titles.addAll((List)filterResults.values);
            notifyDataSetChanged();

        }
    };
//
//    @Override
//    public boolean onTouch(View view, MotionEvent motionEvent) {
//        if (touchListener != null) {
//            touchListener.onTouch((ImageView) view, motionEvent);
//        }
//        return false;    }



    // this class we declared inside it evert variables even we can access it in constructor  for this the class
    public  static class  viewHolder extends RecyclerView.ViewHolder  {

        TextView title;
        static ImageView gridIcon;
        Button favInsideDB , btnShare;


        // View parentLayout;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewl2);
            gridIcon = itemView.findViewById(R.id.imageViewl2);
            favInsideDB = itemView.findViewById(R.id.favBtnl2);
            btnShare = itemView.findViewById(R.id.btnShare);
//            gridIcon.setOnTouchListener((View.OnTouchListener) this);




//            itemView.findViewById(R.id.btnShare).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    Intent intent = new Intent(Intent.ACTION_SEND);
//                    intent.putExtra(Intent.EXTRA_TEXT, "Let's go for a trip to "
//                            + title[itemView.getAdapterPosition()] +
//                            "\nHere is the link to the full review: " + gridIcon[holder.
//                            getAdapterPosition()]);
//                    intent.setType("text/plain");
//                    .startActivity(Intent.createChooser(intent, "Send To"));
//
//                }
//            });


            // هذي الاون كليك عشلن لما يضغط على اي قلب موجود على الصور الي داخل كل كاتيجوري
            itemView.findViewById(R.id.favBtnl2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    authProfileLogin = FirebaseAuth.getInstance();

                    if (authProfileLogin.getCurrentUser() != null) {

                        //favBtn.setBackgroundResource(R.color.purple);
                        favInsideDB.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                        Toast.makeText(favInsideDB.getContext(), "تم إضافة العنصر الى المفضلة", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(favInsideDB.getContext(), "يجب عليك إنشاء حساب حتى تسطيع إضافة العنصر الى المفضلة ", Toast.LENGTH_SHORT).show();

                    }

                }
            });
        }



    }



}