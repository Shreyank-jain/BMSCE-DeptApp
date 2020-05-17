package com.halo.bmsce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.CollapsibleActionView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.halo.loginui2.R;

public class Activity_Contest extends AppCompatActivity {

    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference notebookRef=db.collection("contests");
    private NoteAdapterC adapter;
    String permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest);

        permission=UserAccess.getPermission();
        FloatingActionButton layers = (FloatingActionButton) findViewById(R.id.add_contest);
        if(permission.compareTo("faculty")==0){
            layers.setVisibility(View.VISIBLE);
            layers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Activity_Contest.this,Add_Contest.class));
                }
            });
        }
        if(permission.compareTo("students")==0){
            layers.setVisibility(View.GONE);
        }
        setUpRecyclerView();
    }
    private void setUpRecyclerView(){
        Query query=notebookRef.orderBy("lastdate", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Note>options=new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query,Note.class).build();
        adapter=new NoteAdapterC(options);
        RecyclerView recyclerView=findViewById(R.id.all_listc);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new NoteAdapterC.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                //Note note=documentSnapshot.toObject(Note.class);
                String id=documentSnapshot.getId();
                Intent i = new Intent(Activity_Contest.this, Contest_View.class);
                i.putExtra("uid",id);
                startActivity(i);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.startListening();
    }
}