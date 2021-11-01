package com.example.alpha_project01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class gallery1 extends AppCompatActivity {
    private static final int PICK_FROM_GALLERY = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery1);
    }
ChoosePhoto.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick (View v){
        try {
            if (ActivityCompat.checkSelfPermission(gallery1.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(gallery1.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
            } else {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    });
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
}