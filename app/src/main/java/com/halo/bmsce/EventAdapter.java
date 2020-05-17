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

public class EventAdapter extends FirestoreRecyclerAdapter<Event,EventAdapter.LinkHolder> {
    private OnItemClickListener listener;
    private String type;
    public EventAdapter(@NonNull FirestoreRecyclerOptions<Event> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull LinkHolder holder, int position, @NonNull Event class_model) {
        holder.event_title.setText(class_model.getTitle());
        holder.event_date.setText(class_model.getDate());
        holder.event_month.setText(class_model.getMonth());
        holder.event_year.setText(class_model.getYear());
        holder.event_type.setText(class_model.getType());
    }

    @NonNull
    @Override
    public LinkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item,parent,false);

        return new LinkHolder(v);

    }

    class LinkHolder extends RecyclerView.ViewHolder{
        TextView event_title,event_date,event_month,event_year,event_type;


        public LinkHolder(@NonNull View itemView) {
            super(itemView);
            event_title=itemView.findViewById(R.id.event_title);
            event_date=itemView.findViewById(R.id.event_date);
            event_month=itemView.findViewById(R.id.event_month);
            event_year=itemView.findViewById(R.id.event_year);
            event_type=itemView.findViewById(R.id.event_type);

        }
    }

    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }
}
