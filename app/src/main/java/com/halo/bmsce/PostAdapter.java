package com.halo.bmsce;

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

public class PostAdapter extends FirestoreRecyclerAdapter<Post, PostAdapter.LinkHolder> {
    private OnItemClickListener listener;
    public PostAdapter(@NonNull FirestoreRecyclerOptions<Post> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull LinkHolder holder, int position, @NonNull Post post_model) {
        holder.post_user.setText(post_model.getPost_user());
        holder.post_message.setText(post_model.getPost_message());
        holder.post_date.setText(post_model.getPost_date());

    }

    @NonNull
    @Override
    public LinkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item,parent,false);
        return new LinkHolder(v);

    }

    class LinkHolder extends RecyclerView.ViewHolder{
        TextView post_user,post_message,post_date;

        public LinkHolder(@NonNull View itemView) {
            super(itemView);
            post_user=itemView.findViewById(R.id.post_user);
            post_message=itemView.findViewById(R.id.post_message);
            post_date=itemView.findViewById(R.id.post_date);


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
