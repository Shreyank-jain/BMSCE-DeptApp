package com.halo.bmsce;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.halo.loginui2.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostFragment extends Fragment {
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    String tablename=pass_classname.getClassname();
    String doc=pass_classname.getClass_id();
    private EditText message;
    private FirebaseAuth fStore;
    private String user,username;
    private CollectionReference notebookRef;
    FloatingActionButton post;
    private String usertype=UserAccess.getPermission();
    private PostAdapter adapter;
    private RecyclerView recyclerView;
    View view;

    public PostFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fStore=FirebaseAuth.getInstance();
        user=fStore.getCurrentUser().getUid();

        DocumentReference documentReference=db.collection(usertype).document(user);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                username=documentSnapshot.getString("name");
            }

        });

        notebookRef=db.collection(tablename).document(doc).collection("posts");
        view= inflater.inflate(R.layout.fragment_post, container, false);
        recyclerView = view.findViewById(R.id.all_posts);
        message=view.findViewById(R.id.post_message);
        post=view.findViewById(R.id.post_button);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_message();
            }
        });

        setUpRecyclerView();


        return view;

    }



    private void setUpRecyclerView(){

        Query query=notebookRef.orderBy("post_date", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Post>options=new FirestoreRecyclerOptions.Builder<Post>()
                .setQuery(query, Post.class).build();
        adapter=new PostAdapter(options);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.startListening();
    }

    private void send_message() {
        final String mssg = message.getText().toString();
        final String timeStamp = new SimpleDateFormat("dd-MM-yyyy   HH:mm:ss").format(Calendar.getInstance().getTime());
        final String uname ;

        uname=username;
        if (!TextUtils.isEmpty(mssg)) {
            DocumentReference documentReference1 = db.collection(tablename).document(doc).collection("posts").document();
            Map<String, Object> post = new HashMap<>();
            post.put("post_date", timeStamp);
            post.put("post_message", mssg);
            post.put("post_user", uname);

            documentReference1.set(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "successfully signed up");
                    message.setText("");
                    recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());

                }
            });
        }
    }
    }
