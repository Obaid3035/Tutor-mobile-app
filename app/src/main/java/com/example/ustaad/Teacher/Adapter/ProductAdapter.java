package com.example.ustaad.Teacher.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ustaad.R;
import com.example.ustaad.Teacher.Model.Product;
import com.example.ustaad.Teacher.Fragment.View_Profile_Fragment;
import com.example.ustaad.Teacher.Model.Teacher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductviewHolder> {


    private Context mctx;
    private List<Product> productList;
    FirebaseUser firebaseUser;


    public ProductAdapter(Context mctx, List<Product> productList) {
        this.mctx = mctx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mctx);
        View view = inflater.inflate(R.layout.post_item, null );
        ProductviewHolder holder = new ProductviewHolder(view);
        return holder;


    }

    @Override
    public void onBindViewHolder(@NonNull ProductviewHolder holder, int position) {



         firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
      final Product product = productList.get(position);

      //holder.username.setText(product.getName());
      holder.textView_description.setText(product.getText());
      holder.subject.setText(product.getSubject());
      holder.timing.setText(product.getTiming());
      //holder.address.setText(product.getAddress());
     // Glide.with(mctx).load(product.getImage()).into(holder.image_profile);
     // holder.ratingBar.setRating(product.getRatings());
      //holder.imageView.setImageDrawable(mctx.getResources().getDrawable(product.getImage()));
        PublishInfo(holder.image_profile,holder.username,holder.address,product.getId());




        holder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SharedPreferences.Editor editor = mctx.getSharedPreferences("PREFS",Context.MODE_PRIVATE) .edit();
//                editor.putString("profileId1",product.getId());
//                editor.apply();
                Bundle bundle = new Bundle();
                bundle.putString("profileId",product.getId());
                View_Profile_Fragment fragment = new View_Profile_Fragment();
                fragment.setArguments(bundle);
                ((FragmentActivity)mctx).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container,fragment)
                        .commit();


//                Intent intent = new Intent(mctx , HomeActivity.class);
//                intent.putExtra("PublishId",product.getId());
//                //Log.e("Studentvalue", ""+product.getId());
//                mctx.startActivity(intent);
            }
        });




    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductviewHolder extends RecyclerView.ViewHolder{

        ImageView image_profile;
        TextView username, textView_description, show_more,show_less,subject, address,timing;
        //RatingBar ratingBar;

        public ProductviewHolder(@NonNull View itemView) {
            super(itemView);
            image_profile = itemView.findViewById(R.id.Post_image);
            username = itemView.findViewById(R.id.textView_name);
            textView_description = itemView.findViewById(R.id.Description);
            show_more = itemView.findViewById(R.id.showmore);
            show_less = itemView.findViewById(R.id.showless);
            address = itemView.findViewById(R.id.address_post);
            timing = itemView.findViewById(R.id.timing_post);
            subject = itemView.findViewById(R.id.subject_post);


            show_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_more.setVisibility(View.GONE);
                    show_less.setVisibility(View.VISIBLE);
                    textView_description.setMaxLines(Integer.MAX_VALUE);
                }
            });

            show_less.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_less.setVisibility(View.GONE);
                    show_more.setVisibility(View.VISIBLE);
                    textView_description.setMaxLines(0);

                }
            });

            //ratingBar = itemView.findViewById(R.id.ratingbar);
        }

    }

    private void PublishInfo(final ImageView image_profile, final TextView username, final TextView address, String userId){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Teacher").child(userId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Teacher teacher = dataSnapshot.getValue(Teacher.class);
                username.setText(teacher.getTeachername());
                address.setText(teacher.getTeacheraddress());
                Glide.with(mctx).load(teacher.getTeacherimage()).into(image_profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }





}
