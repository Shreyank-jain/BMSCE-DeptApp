package com.halo.bmsce;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.halo.loginui2.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Activity_FileUpload extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button btnbrowse, btnupload;
    EditText txtdata ;
    ImageView imgview;
    Uri FilePathUri;
    StorageReference ImageFolder;
    private CollectionReference collectionReference;

    int Image_Request_Code = 1;
    int Pdf_Request_code=2342 ;
    int Docx_Request_Code=1;
    ProgressDialog progressDialog ;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String uid;
    private String key ;
    StorageReference storageRef ;
    DocumentReference documentReference;
    private static final String TAG = Activity_Register.class.getSimpleName();
    Spinner ext ;

    private String tablename,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__file_upload);
        ext=findViewById(R.id.selext);
        ext.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.extensions, R.layout.spiner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        ext.setAdapter(adapter);
        ext.setOnItemSelectedListener(this);

        tablename=getIntent().getStringExtra("classname");
        id=getIntent().getStringExtra("classcode");

        ext.setOnItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();

       /* storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("MAD19-20/mad19-20/materials");
      */  btnbrowse = (Button)findViewById(R.id.btnbrowse);
        btnupload= (Button)findViewById(R.id.btnupload);
        txtdata = (EditText)findViewById(R.id.txtdata);
        progressDialog = new ProgressDialog(Activity_FileUpload.this);// context name as per your project name



        btnbrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                String extName ;
                switch (ext.getSelectedItemPosition()){


                    case 1 :
                        intent.setType("application/pdf");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        extName = ext.getSelectedItem().toString();
                        startActivityForResult(Intent.createChooser(intent, "Select "+ extName), Pdf_Request_code);
                        break;
                    case 2 :
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        extName = ext.getSelectedItem().toString();
                        startActivityForResult(Intent.createChooser(intent, "Select "+ extName), Image_Request_Code);
                        break;
                    case 3 :
                        intent.setType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");//.docx
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        extName = ext.getSelectedItem().toString();
                        startActivityForResult(Intent.createChooser(intent, "Select "+ extName), Docx_Request_Code);
                        break;
                    case 4 :
                        intent.setType("application/vnd.ms-powerpoint"); //.ppt
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        extName = ext.getSelectedItem().toString();
                        startActivityForResult(Intent.createChooser(intent, "Select "+ extName), Docx_Request_Code);
                        break;
                    case 5 :
                        intent.setType("application/msword"); //.doc
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        extName = ext.getSelectedItem().toString();
                        startActivityForResult(Intent.createChooser(intent, "Select "+ extName), Docx_Request_Code);
                        break;
                    case 6 :
                        intent.setType("application/zip"); // .zip
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        extName = ext.getSelectedItem().toString();
                        startActivityForResult(Intent.createChooser(intent, "Select "+ extName), Docx_Request_Code);
                        break;
                    case 7 :
                        intent.setType("text/plain"); // .txt
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        extName = ext.getSelectedItem().toString();
                        startActivityForResult(Intent.createChooser(intent, "Select "+ extName), Docx_Request_Code);
                        break;
                    case 8 :
                        intent.setType("application/vnd.ms-excel"); // .xls
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        extName = ext.getSelectedItem().toString();
                        startActivityForResult(Intent.createChooser(intent, "Select "+ extName), Docx_Request_Code);
                        break;
                    case 9 :
                        intent.setType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); // .xlsx
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        extName = ext.getSelectedItem().toString();
                        startActivityForResult(Intent.createChooser(intent, "Select "+ extName), Docx_Request_Code);
                        break;

                }
/*                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                String extName = ext.getSelectedItem().toString();
                startActivityForResult(Intent.createChooser(intent, "Select "+ extName), Image_Request_Code);*/

            }
        });
        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UploadImage();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if ( resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();
        }
    }


    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }


    public void UploadImage() {

        if (FilePathUri != null) {
            String filename=System.currentTimeMillis()+"";
            progressDialog.setTitle("File is Uploading...");
            progressDialog.show();

            ImageFolder = FirebaseStorage.getInstance().getReference().child(tablename);
            final StorageReference ImageName = ImageFolder.child(filename);

            ImageName.putFile(FilePathUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = String.valueOf(uri);
                            StoreLink(url);
                            progressDialog.dismiss();
                            startActivity(new Intent(Activity_FileUpload.this,Activity_Classes.class));
                            Toast.makeText(Activity_FileUpload.this, "File Uploaded Successfully", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }
        else {

            Toast.makeText(Activity_FileUpload.this, "Please Select File", Toast.LENGTH_LONG).show();

        }
    }

    private void StoreLink(String url) {
        db = FirebaseFirestore.getInstance();
        Uri link = Uri.parse(url);
        db = FirebaseFirestore.getInstance();
        final String title = txtdata.getText().toString();
        final String upload_date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());


        final String material_link = url;
        if (title != null && material_link != null) {
            collectionReference = db.collection(tablename).document(id).collection("materials");
            Map<String, Object> material = new HashMap<>();
            material.put("material_title", title);
            material.put("material_link", material_link);
            material.put("material_date",upload_date);

            if(ext.getSelectedItemPosition()==1) //.pdf
                material.put("icon","https://firebasestorage.googleapis.com/v0/b/bmsce-6b242.appspot.com/o/pdf.jpg?alt=media&token=eb44a598-0d05-4ed8-9dce-d1466fecd68b");

            if(ext.getSelectedItemPosition()==2) //image
                material.put("icon","https://firebasestorage.googleapis.com/v0/b/bmsce-6b242.appspot.com/o/img.png?alt=media&token=bc106005-5e6b-4215-9dca-b8aeb945792c");

            if(ext.getSelectedItemPosition()==3) //docx
                material.put("icon","https://firebasestorage.googleapis.com/v0/b/bmsce-6b242.appspot.com/o/docx.jpg?alt=media&token=74f55e2e-d04a-4a89-b430-25fd33a5d9c8");

            if(ext.getSelectedItemPosition()==4) //.ppt
                material.put("icon","https://firebasestorage.googleapis.com/v0/b/bmsce-6b242.appspot.com/o/ppt.png?alt=media&token=d71899d9-c69f-4b38-ba65-9ced90354786");

            if(ext.getSelectedItemPosition()==5) //.doc
                material.put("icon","https://firebasestorage.googleapis.com/v0/b/bmsce-6b242.appspot.com/o/doc.png?alt=media&token=a32700f3-6961-4180-8f23-9b04294bf7e2");

            if(ext.getSelectedItemPosition()==6) //.zip
                material.put("icon","https://firebasestorage.googleapis.com/v0/b/bmsce-6b242.appspot.com/o/zip.jpg?alt=media&token=3cc6fc68-ed7e-46e0-9c2a-2c199ff80e8e");

            if(ext.getSelectedItemPosition()==7) //.txt
                material.put("icon","https://firebasestorage.googleapis.com/v0/b/bmsce-6b242.appspot.com/o/txt.png?alt=media&token=ceff6608-7dce-41fe-a790-b4ca749d5b7e");

            if(ext.getSelectedItemPosition()==8) //.xls
                material.put("icon","https://firebasestorage.googleapis.com/v0/b/bmsce-6b242.appspot.com/o/xls.png?alt=media&token=5a03ed0a-67ad-4c82-b032-2f8edefe1d9a");

            if(ext.getSelectedItemPosition()==9) //.xlsx
                material.put("icon","https://firebasestorage.googleapis.com/v0/b/bmsce-6b242.appspot.com/o/xlsx.png?alt=media&token=38b76236-82e6-4810-b46e-29d7b46f5a92");

            collectionReference
                    .add(material)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });


        } else {
            Toast.makeText(this, "fill in all fields", Toast.LENGTH_LONG);
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
