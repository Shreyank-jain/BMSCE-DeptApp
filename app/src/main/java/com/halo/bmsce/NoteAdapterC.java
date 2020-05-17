package com.halo.bmsce;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.halo.loginui2.R;

import java.util.Calendar;

public class NoteAdapterC extends FirestoreRecyclerAdapter<Note,NoteAdapterC.NoteHolder> {
    private OnItemClickListener listener;
    public NoteAdapterC(@NonNull FirestoreRecyclerOptions<Note> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull Note model) {
        holder.text_title.setText(model.getTitle());
        holder.text_description.setText(model.getDescription());
        holder.text_lastdate.setText(model.getLastdate());
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.contest_item,parent,false);
        return new NoteHolder(v);

    }

    class NoteHolder extends RecyclerView.ViewHolder{
        TextView text_title,text_description,text_lastdate;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            text_title=itemView.findViewById(R.id.text_titlec);
            text_description=itemView.findViewById(R.id.text_descriptionc);
            text_lastdate=itemView.findViewById(R.id.text_lastdatec);

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
        void onItemClick(DocumentSnapshot documentSnapshot,int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }
}
