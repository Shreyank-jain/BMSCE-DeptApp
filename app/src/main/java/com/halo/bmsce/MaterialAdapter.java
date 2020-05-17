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

public class MaterialAdapter extends FirestoreRecyclerAdapter<Material, MaterialAdapter.LinkHolder> {
    private OnItemClickListener listener;
    public MaterialAdapter(@NonNull FirestoreRecyclerOptions<Material> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull LinkHolder holder, int position, @NonNull Material material_model) {
        holder.material_link.setText(material_model.getMaterial_link());
        holder.material_title.setText(material_model.getMaterial_title());
        holder.material_date.setText(material_model.getMaterial_date());
        Picasso.get()
                .load(material_model.getIcon())
                .into(holder.material_icon);

    }

    @NonNull
    @Override
    public LinkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.material_item,parent,false);
        return new LinkHolder(v);

    }

    class LinkHolder extends RecyclerView.ViewHolder{
        TextView material_title,material_link,material_date;
        ImageView material_icon;

        public LinkHolder(@NonNull View itemView) {
            super(itemView);
            material_title=itemView.findViewById(R.id.material_name);
            material_date=itemView.findViewById(R.id.material_date);
            material_link=itemView.findViewById(R.id.material_link);
            material_icon=itemView.findViewById(R.id.material_icon);

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
