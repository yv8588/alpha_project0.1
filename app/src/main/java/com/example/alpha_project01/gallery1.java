package com.example.alpha_project01;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;

import static com.example.alpha_project01.FBref.AUTH;
import static com.example.alpha_project01.FBref.storageRef;


public class gallery1 extends AppCompatActivity {
    private static final String TAG = "gallery1";
    ProgressDialog progressDialog;
    private int Read=111;
    private int File=222;
    FirebaseUser user;
    String UserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery1);
        progressDialog=new ProgressDialog(gallery1.this);
        addFilePath();
    }

    @Override
    protected void onStart() {
        super.onStart();
        user=AUTH.getCurrentUser();
        if(user!=null){
            UserID=user.getUid();
        }

    }

    private void addFilePath() {
        Log.d(TAG,"addFilePaths:Adding file paths.");
        String path=System.getenv("EXTERNAL STORAGE");
        //LoadImageFromStorage();
    }

   // private void LoadImageFromStorage() {
      //  try {
      //      String path=pathArray.get(array_position);
       //     File f=new File(path,"");
       //     Bitmap b= BitmapFactory.decodeStream(new FileInputStream(f));

       // }
       // catch (FileNotFoundException e){
       //     Log.e(TAG,"loadImageFromStorage:"+ e.getMessage());
       // }

    //}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent si;
        String s=item.getTitle().toString();
        if(s.equals("Auth")) {
            si = new Intent(this,MainActivity.class);
            startActivity(si);
        }
        return super.onOptionsItemSelected(item);
    }

    public void upload(View view) {
        if(ContextCompat.checkSelfPermission(gallery1.this,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(gallery1.this,new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},Read);
        }
        else{
            selectImage();
        }
    }

    private void selectImage() {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent,File);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==File&&resultCode==RESULT_OK){
            if(data!=null){
                Log.d(TAG,"onClick : uploading Image.");
                progressDialog.setMessage("Uploading image...");
                progressDialog.show();
                    Uri uri= data.getData();
                    StorageReference srf=storageRef.child("images/"+UserID+".jpg");
                    srf.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(gallery1.this,"image uploaded sucessfully",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(gallery1.this,"image upload failed ",Toast.LENGTH_SHORT).show();
                        }
                    });
                    progressDialog.dismiss();
                }

            }

        }
    }