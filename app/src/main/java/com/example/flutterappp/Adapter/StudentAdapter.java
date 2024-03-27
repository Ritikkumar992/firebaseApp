package com.example.flutterappp.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flutterappp.Model.StudentModel;
import com.example.flutterappp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentAdapter extends FirebaseRecyclerAdapter<StudentModel, StudentAdapter.ViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public StudentAdapter(@NonNull FirebaseRecyclerOptions<StudentModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, final int position, @NonNull StudentModel model) {
        holder.studentName.setText(model.getName());
        holder.studentEmail.setText(model.getEmail());
        holder.studentCourses.setText(model.getCourses());

        Glide.with(holder.studentImg.getContext())
                .load(model.getImgUrl())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.google.firebase.appcheck.interop.R.drawable.common_google_signin_btn_icon_dark)
                .into(holder.studentImg);

        //----------------- bind update_popup.xml  with updateBtn  -------------------------//
        holder.updateBtn.setOnClickListener(v->{
            final DialogPlus dialogPlus = DialogPlus.newDialog(holder.studentImg.getContext())
                    .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.update_popup))
                    .setExpanded(true,1800)
                    .create();

//            dialogPlus.show();

            View view = dialogPlus.getHolderView();

            //------- attaching default value------//
            EditText name = view.findViewById(R.id.editTxtName);
            EditText email = view.findViewById(R.id.editTxtEmail);
            EditText courses = view.findViewById(R.id.editTxtCourses);
            EditText imgUrl = view.findViewById(R.id.editTxtImgUrl);
            Button updateBtn = view.findViewById(R.id.popUp_updateBtn);

            name.setText(model.getName());
            email.setText(model.getEmail());
            courses.setText(model.getCourses());
            imgUrl.setText(model.getImgUrl());

            dialogPlus.show();


            // Update ------------------> main update operation ----------------------------//
            updateBtn.setOnClickListener(vv-> {
                Map<String, Object> mp = new HashMap<>();
                mp.put("name", name.getText().toString());
                mp.put("email", email.getText().toString());
                mp.put("courses", courses.getText().toString());
                mp.put("imgUrl", imgUrl.getText().toString());

                FirebaseDatabase.getInstance().getReference().child("students")
                        .child(getRef(position).getKey()).updateChildren(mp)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(holder.studentName.getContext(), "Update Successfull", Toast.LENGTH_SHORT).show();
                                dialogPlus.dismiss();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(holder.studentName.getContext(), "Update Failed", Toast.LENGTH_SHORT).show();
                                dialogPlus.dismiss();
                            }
                        });
            });
        });

        //------------------ Delete  Operation:----------------------//
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.studentName.getContext());
                builder.setTitle("Are you Sure ?" );
                builder.setMessage("Deleted data can't be recovered.");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("students")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.studentName.getContext(),"Cancelled",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item_row,parent,false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView studentImg;
        TextView studentName, studentEmail, studentCourses;
        Button updateBtn, deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            studentImg = (CircleImageView)itemView.findViewById(R.id.imgUrlId);
            studentName = (TextView) itemView.findViewById(R.id.txtName);
            studentEmail = (TextView)itemView.findViewById(R.id.txtEmail);
            studentCourses = (TextView)itemView.findViewById(R.id.txtCourse);
            updateBtn = (Button) itemView.findViewById(R.id.updateBtn);
            deleteBtn = (Button) itemView.findViewById(R.id.deleteBtn);
        }
    }
}
