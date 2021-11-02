package com.example.alpha_project01;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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


public class gallery1 extends AppCompatActivity {
    private static final String TAG = "gallery1";
    EditText name;
    String n;
    ProgressDialog progressDialog;
    private StorageReference storageRef;
    private int array_position;
    ArrayList<String>pathArray;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery1);
        name=(EditText) findViewById(R.id.name);
        pathArray=new ArrayList<>();
        progressDialog=new ProgressDialog(gallery1.this);
        storageRef= FirebaseStorage.getInstance().getReference();
        auth=FirebaseAuth.getInstance();
        checkFilePermissions();
        addFilePath();
    }

    private void addFilePath() {
        Log.d(TAG,"addFilePaths:Adding file paths.");
        String path=System.getenv("EXTERNAL STORAGE");
        LoadImageFromStorage();
    }

    private void LoadImageFromStorage() {
        try {
            String path=pathArray.get(array_position);
            File f=new File(path,"");
            Bitmap b= BitmapFactory.decodeStream(new FileInputStream(f));

        }
        catch (FileNotFoundException e){
            Log.e(TAG,"loadImageFromStorage:"+ e.getMessage());
        }

    }

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
        //else if(s.equals("TV")){
            //si=new Intent(this,update.class);
           // startActivity(si);
        //}
        return super.onOptionsItemSelected(item);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkFilePermissions(){
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck=gallery1.this.checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE");
            permissionCheck+=gallery1.this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
            if(permissionCheck!=0){
                this.requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_EXTERNAL_STORAGE});
            }
        }
        else {

            Log.d(TAG,"checkBTPermissions:No need to check permissions.SDK version>LOLIPOP.");
        }

    }

    public void upload(View view) {
        Log.d(TAG,"onClick : uploading Image.");
        progressDialog.setMessage("Uploading image...");
        progressDialog.show();
        FirebaseUser user=auth.getCurrentUser();
        String UserID=user.getUid();
        n=name.getText().toString();
        if(!n.equals("")){
            Uri uri= Uri.fromFile(new File(pathArray.get(array_position)));
            StorageReference srf=storageRef.child("images/users/"+UserID+n+".jpg");
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
        name.setText("");
        name.setHint("image name");
    }
}