package com.example.flutterappp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flutterappp.Model.StudentModel;
import com.example.flutterappp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

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
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull StudentModel model) {
        holder.studentName.setText(model.getName());
        holder.studentEmail.setText(model.getEmail());
        holder.studentCourse.setText(model.getCourse());

        Glide.with(holder.studentImg.getContext())
                .load(model.getImgUrl())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.google.firebase.appcheck.interop.R.drawable.common_google_signin_btn_icon_dark)
                .into(holder.studentImg);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item_row,parent,false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView studentImg;
        TextView studentName, studentEmail, studentCourse;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            studentImg = (CircleImageView)itemView.findViewById(R.id.imgUrlId);
            studentName = (TextView) itemView.findViewById(R.id.txtName);
            studentEmail = (TextView)itemView.findViewById(R.id.txtEmail);
            studentCourse = (TextView)itemView.findViewById(R.id.txtCourse);
        }
    }
}
