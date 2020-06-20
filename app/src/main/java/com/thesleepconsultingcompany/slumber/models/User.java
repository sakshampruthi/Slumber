package com.thesleepconsultingcompany.slumber.models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User {
    public String Email;
    public String Name;
    public String Password;
    public String Age;
    public String Sex;

    public User() {
        Email =null;
        Name =null;
        Password =null;
        Age =null;
        Sex = null;
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User( String email, String name, String password, String age, String sex) {
        this.Email = email;
        this.Name =name;
        this.Password =password;
        this.Age = age;
        this.Sex = sex;
    }

    public static boolean addUser(String email, String name, String password, String age, String sex)
    {
        User user = new User(email,name,password,age,sex);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        String id = firebaseUser.getUid();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("Users");
        myRef.child(id).setValue(user);
        return true;
    }
}
