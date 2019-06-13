package com.mrlonewolfer.firebasedatabseusingbean.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mrlonewolfer.firebasedatabseusingbean.Adapter.UserInfoAdapter;
import com.mrlonewolfer.firebasedatabseusingbean.Model.UserBean;
import com.mrlonewolfer.firebasedatabseusingbean.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,UserInfoAdapter.onUserClickListenr {

    private static final String TAG =MainActivity.class.getSimpleName() ;
    EditText edtFname,edtLname,edtMobile,edtEmail;
    Button btnSubmit;

    UserBean userBean;
    ArrayList<UserBean> userList;
    private DatabaseReference rootRef,childRef;
    private FirebaseDatabase mFirebaseInstance;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtFname=findViewById(R.id.edtFName);
        edtLname=findViewById(R.id.edtLName);
        edtEmail=findViewById(R.id.edtEmail);
        edtMobile=findViewById(R.id.edtMno);
        btnSubmit=findViewById(R.id.btnSubmit);
        recyclerView=findViewById(R.id.recyclerview);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        btnSubmit.setOnClickListener(this);
        userList= new ArrayList<>();

        mFirebaseInstance=FirebaseDatabase.getInstance();

        mFirebaseInstance.getReference("app_title").setValue("Firebase With UserBean Demo");

        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "App Title Updated ");

                String appTitle=dataSnapshot.getValue(String.class);
                getSupportActionBar().setTitle(appTitle);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Failed to read app title value.", databaseError.toException());
            }
        });

        rootRef =mFirebaseInstance.getReference("UserBeanDemo");

        fetchUserData();

    }


    private void fetchUserData() {
        childRef=rootRef.child("UsersInfo");
        ValueEventListener eventListener= new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    String userId=ds.child("userId").getValue(String.class);
                    String fname=ds.child("fname").getValue(String.class);
                    String lname=ds.child("lname").getValue(String.class);
                    String mobile=ds.child("mobile").getValue(String.class);
                    String email=ds.child("email").getValue(String.class);

                    userBean=new UserBean(userId,fname,lname,email,mobile);
                    userList.add(userBean);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Something Goes Wrong", Toast.LENGTH_SHORT).show();
            }
        };
        childRef.addListenerForSingleValueEvent(eventListener);
        childRef.addValueEventListener(eventListener);

        UserInfoAdapter adapter=new UserInfoAdapter(this,userList);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnSubmit){
            String btnText=btnSubmit.getText().toString();
            String fname=edtFname.getText().toString();
            String lname=edtLname.getText().toString();
            String email=edtEmail.getText().toString();
            String mobile=edtMobile.getText().toString();


                if (!fname.isEmpty() && !lname.isEmpty() && !email.isEmpty() && !mobile.isEmpty()) {

                    if(btnText.equals("Submit")) {
                        storeUserInfo(fname, lname, email, mobile);
                    }
                    if(btnText.equals("Update")){

                        childRef=rootRef.child("UsersInfo").child(userId);

                        userBean = new UserBean(userId, fname, lname, email,mobile);
                        //update  User  to firebase
                        childRef.setValue(userBean);
                        Toast.makeText(this, "Data Succesfully Updated", Toast.LENGTH_SHORT).show();
                        btnSubmit.setText("Submit");
                    }

                } else {

                    Toast.makeText(MainActivity.this, "Please Fill ALl details", Toast.LENGTH_SHORT).show();
                }

            fetchUserData();
            resetUserEntry();

    }






}

    private void storeUserInfo(String fname, String lname, String email, String mobile) {

            userId = rootRef.push().getKey();
        userBean= new UserBean(userId,fname,lname,email,mobile);
        userList.add(userBean);
        childRef.child(userId).setValue(userBean);
        Toast.makeText(MainActivity.this, "Data Successfully", Toast.LENGTH_SHORT).show();


        fetchUserData();
    }

    private void resetUserEntry() {
        edtFname.setText("");
        edtMobile.setText("");
        edtLname.setText("");
        edtEmail.setText("");
    }

    @Override
    public void onUserClick(UserBean userBean, String myaction) {
        if(myaction.equals("delete")){


            childRef=rootRef.child("UsersInfo").child(userBean.getUserId());
            //removing User
            childRef.removeValue();
            fetchUserData();
            Toast.makeText(getApplicationContext(), "User Deleted", Toast.LENGTH_LONG).show();

        }
        if(myaction.equals("edit")){
            userId=userBean.getUserId();
            edtFname.setText(userBean.getFname());
            edtLname.setText(userBean.getLname());
            edtEmail.setText(userBean.getEmail());
            edtMobile.setText(userBean.getMobile());

            btnSubmit.setText("Update");
        }
    }

}
