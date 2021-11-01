package com.example.alpha_project01;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FBref {
    public static FirebaseDatabase FBDB = FirebaseDatabase.getInstance();
    public static DatabaseReference refUser=FBDB.getReference("Users");
    public static final FirebaseAuth AUTH = FirebaseAuth.getInstance();

}
