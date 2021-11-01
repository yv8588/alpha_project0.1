package com.example.alpha_project01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.alpha_project01.FBref.AUTH;
import static com.example.alpha_project01.FBref.refUser;

public class MainActivity extends AppCompatActivity {
   EditText password,mail;
   String p,m;
   User user;
   boolean prove;
       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        password=(EditText)findViewById(R.id.password);
        mail=(EditText)findViewById(R.id.mail);
    }
    protected final void createUserAuthWithEmailAndPassword(String emailId, String password) {

        AUTH.createUserWithEmailAndPassword(emailId, password).addOnCompleteListener((@NonNull Task<AuthResult> task) -> {

            if (task.isSuccessful()) {
                prove = true;
            } else {
                prove = false;
            }

        });
    }

    public void register(View view) {
        p=password.getText().toString();
        m=mail.getText().toString();
        if(m==null){
            Toast.makeText(MainActivity.this, "enter mail", Toast.LENGTH_SHORT).show();
        }
        else if(p==null){
            Toast.makeText(MainActivity.this, "enter password", Toast.LENGTH_SHORT).show();
        }
        else{
            createUserAuthWithEmailAndPassword(m,p);
            Toast.makeText(MainActivity.this,"user registered",Toast.LENGTH_SHORT).show();
          //  user=new User(m,p);
          //  refUser.child(p).setValue(user);
            password.setText("");
            password.setHint("password");
            mail.setText("");
            mail.setHint("mail");

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
        else if (s.equals("Gallery")){
            si=new Intent(this,gallery1.class);
            startActivity(si);
        }
       // else if(s.equals("TV")){
         //   si=new Intent(this,update.class);
           // startActivity(si);
        //}
        return super.onOptionsItemSelected(item);
    }
}