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

public class LinkAdapter extends FirestoreRecyclerAdapter<Link,LinkAdapter.LinkHolder> {
    private OnItemClickListener listener;
    public LinkAdapter(@NonNull FirestoreRecyclerOptions<Link> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull LinkHolder holder, int position, @NonNull Link link_model) {
        holder.text_attachment.setText(link_model.getA1());
        holder.text_link.setText(link_model.getLink());
        Picasso.get()
                .load(link_model.getThumbnail())
                .into(holder.image_attachment);

    }

    @NonNull
    @Override
    public LinkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.workshop_details_item,parent,false);
        return new LinkHolder(v);

    }

    class LinkHolder extends RecyclerView.ViewHolder{
        TextView text_attachment,text_link;
        ImageView image_attachment;

        public LinkHolder(@NonNull View itemView) {
            super(itemView);
            text_attachment=itemView.findViewById(R.id.text_attachment);
            text_link=itemView.findViewById(R.id.text_link);
            image_attachment=itemView.findViewById(R.id.image_attachment);

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
