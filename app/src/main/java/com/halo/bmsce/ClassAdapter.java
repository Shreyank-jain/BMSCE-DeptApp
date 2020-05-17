package com.halo.bmsce;

import android.net.Uri;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.halo.loginui2.R;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class ClassAdapter extends FirestoreRecyclerAdapter<ClassA,ClassAdapter.LinkHolder> {
    private OnItemClickListener listener;
    public ClassAdapter(@NonNull FirestoreRecyclerOptions<ClassA> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull LinkHolder holder, int position, @NonNull ClassA class_model) {
        holder.text_class.setText(class_model.getClassname());
        holder.text_faculty.setText(class_model.getFacultyname());


    }

    @NonNull
    @Override
    public LinkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.class_item,parent,false);
        return new LinkHolder(v);

    }

    class LinkHolder extends RecyclerView.ViewHolder{
        TextView text_class,text_faculty;


        public LinkHolder(@NonNull View itemView) {
            super(itemView);
            text_class=itemView.findViewById(R.id.class_title);
            text_faculty=itemView.findViewById(R.id.facultyname);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(position!=-1 && listener!=null){
                        listener.onItemClick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }
}
