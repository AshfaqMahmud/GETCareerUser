package com.ashfaq.systemproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //declare variables
        ImageView logout = view.findViewById(R.id.logout);
        ShapeableImageView userimage =view.findViewById(R.id.userimage);
        FirebaseDatabase rootnode =FirebaseDatabase.getInstance();
        DatabaseReference reference= rootnode.getReference();
        TextView tname,tmail,tphone,tstatus,tabout,texp1,texp2,tscl,tclg,tsyear,tcyear;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        Button edit;

        //get id of textviews
        tname = view.findViewById(R.id.username);
        tmail = view.findViewById(R.id.usermail);
        tphone = view.findViewById(R.id.userconct);
        tstatus = view.findViewById(R.id.status);
        tabout= view.findViewById(R.id.about);
        texp1= view.findViewById(R.id.exp1);
        texp2= view.findViewById(R.id.exp2);
        tscl= view.findViewById(R.id.education1);
        tclg= view.findViewById(R.id.education2);
        //tsyear= view.findViewById(R.id.s);
        //tcyear= view.findViewById(R.id.about);

        edit = view.findViewById(R.id.editprofile);

        //set value to TV using firebase
        reference.child("userinfo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String uid = user.getUid();
                String name = snapshot.child(uid).child("Name").getValue(String.class);
                String mail = snapshot.child(uid).child("Email").getValue(String.class);
                String phone = snapshot.child(uid).child("Phone").getValue(String.class);
                String status = snapshot.child(uid).child("Status").getValue(String.class);
                String about = snapshot.child(uid).child("About").getValue(String.class);
                String school = snapshot.child(uid).child("Education").child("School").getValue(String.class);
                String syear = snapshot.child(uid).child("Education").child("SchoolYear").getValue(String.class);
                String college = snapshot.child(uid).child("Education").child("College").getValue(String.class);
                String cyear = snapshot.child(uid).child("Education").child("CollegeYear").getValue(String.class);
                String exp1 = snapshot.child(uid).child("Experience").child("Exp1").getValue(String.class);
                String exp2 = snapshot.child(uid).child("Experience").child("Exp2").getValue(String.class);
                String image = snapshot.child(uid).child("imageurl").getValue(String.class);
                Picasso.with(userimage.getContext()).load(image).into(userimage);


                tname.setText(name);
                tmail.setText(mail);
                tphone.setText(phone);
                tstatus.setText(status);
                tabout.setText(about);
                texp1.setText(exp1);
                texp2.setText(exp2);
                tscl.setText("School : "+school+" from "+syear);
                tclg.setText("College : "+college+" from "+cyear);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

            //logout button
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "ImageView clicked", Toast.LENGTH_LONG).show();
                FirebaseAuth.getInstance().signOut();
                SharedPreferences preferences = getActivity().getSharedPreferences("login", 0);
                SharedPreferences.Editor myEdit = preferences.edit();
                myEdit.putBoolean("status",false);
                myEdit.apply();
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }
        });

        //profile edit
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfile.class);
                startActivity(intent);
            }
        });
        return view;
    }
}