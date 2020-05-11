package com.example.ustaad.Students;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.ustaad.Students.Model.Product_Student;
import com.example.ustaad.Students.Model.Student;
import com.example.ustaad.Teacher.Adapter.ProductAdapter;
import com.example.ustaad.Teacher.Fragment.View_Profile_Fragment;
import com.example.ustaad.Teacher.Model.Product;
import com.example.ustaad.Teacher.Model.Teacher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ProductAdapter_Student extends RecyclerView.Adapter<ProductAdapter_Student.StudentviewHolder> {

    private Context mctx;
    private List<Product_Student> productList;
    FirebaseUser firebaseUser;


    public ProductAdapter_Student(Context mctx, List<Product_Student> productList) {
        this.mctx = mctx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductAdapter_Student.StudentviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mctx);
        View view = inflater.inflate(R.layout.post_item_student, null );
        ProductAdapter_Student.StudentviewHolder holder = new ProductAdapter_Student.StudentviewHolder(view);
        return holder;


    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter_Student.StudentviewHolder holder, int position) {



        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final Product_Student product_student = productList.get(position);

        holder.textView_description.setText(product_student.getStudenttext());
        holder.subject.setText(product_student.getStudentsubject());
        holder.timing.setText(product_student.getStudenttiming());

        PublishInfo(holder.image_profile,holder.username,product_student.getStudentid());

        holder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mctx.getSharedPreferences("PREFS",Context.MODE_PRIVATE) .edit();
                editor.putString("profileId",product_student.getStudentid());
                editor.apply();
                ((FragmentActivity)mctx).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container1,new View_Profile_Fragment_Student())
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

    class StudentviewHolder extends RecyclerView.ViewHolder{

        ImageView image_profile;
        TextView username, textView_description, show_more,show_less,subject, address,timing;
        //RatingBar ratingBar;

        public StudentviewHolder(@NonNull View itemView) {
            super(itemView);
            image_profile = itemView.findViewById(R.id.Post_image_student);
            username = itemView.findViewById(R.id.textView_name_student);
            textView_description = itemView.findViewById(R.id.Description_student);
            show_more = itemView.findViewById(R.id.showmore_student);
            show_less = itemView.findViewById(R.id.showless_student);
            timing = itemView.findViewById(R.id.timing_post_student);
            subject = itemView.findViewById(R.id.subject_post_student);


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


    private void PublishInfo(final ImageView image_profile, final TextView username, String userId){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(userId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Student student = dataSnapshot.getValue(Student.class);
                username.setText(student.getStudentname());
                Glide.with(mctx).load(student.getStudentimage()).into(image_profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



}
