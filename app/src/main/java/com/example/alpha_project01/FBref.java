package com.example.alpha_project01;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FBref {
    //public static FirebaseDatabase FBDB = FirebaseDatabase.getInstance();
    //public static DatabaseReference refUser=FBDB.getReference("Users");
    public static final FirebaseAuth AUTH = FirebaseAuth.getInstance();
    public static final StorageReference storageRef = FirebaseStorage.getInstance().getReference();

}
