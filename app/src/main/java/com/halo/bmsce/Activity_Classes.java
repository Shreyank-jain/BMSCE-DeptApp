package com.halo.bmsce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.halo.loginui2.R;

public class Activity_Classes extends AppCompatActivity {
    FirebaseAuth fAuth=FirebaseAuth.getInstance();
    private String userId=fAuth.getCurrentUser().getUid();
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private String usertype=UserAccess.getPermission();
    private CollectionReference notebookRef=db.collection(usertype).document(userId).collection("classes");
    private DocumentReference documentReference=db.collection(usertype).document(userId);
    private String student_name;
    ProgressDialog progressDialog;
    private ClassAdapter adapter;
    String permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);
        progressDialog=new ProgressDialog(Activity_Classes.this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();
        permission=UserAccess.getPermission();
        FloatingActionButton layers = (FloatingActionButton) findViewById(R.id.add_class);
        FloatingActionButton layers1 = (FloatingActionButton) findViewById(R.id.join_class);
        if(permission.compareTo("faculty")==0){
            layers.show();
            layers1.hide();
            layers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Activity_Classes.this,Create_Class.class));
                }
            });
        }
        if(permission.compareTo("students")==0){
            layers1.show();
            layers.hide();
            layers1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Activity_Classes.this,Join_Class.class));
                }
            });
        }

        setUpRecyclerView();
    }
    private void setUpRecyclerView(){
        Query query=notebookRef;
        FirestoreRecyclerOptions<ClassA>options=new FirestoreRecyclerOptions.Builder<ClassA>()
                .setQuery(query,ClassA.class).build();
        adapter=new ClassAdapter(options);
        RecyclerView recyclerView=findViewById(R.id.all_classes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        progressDialog.dismiss();

        adapter.setOnItemClickListener(new ClassAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                //Note note=documentSnapshot.toObject(Note.class);
                String classname=documentSnapshot.getString("classname");
                String classcode=documentSnapshot.getString("classcode");
                Intent i = new Intent(Activity_Classes.this,activity_inside_class.class);

                i.putExtra("class_name",classname);
                i.putExtra("class_code",classcode);

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