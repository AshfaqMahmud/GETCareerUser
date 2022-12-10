package com.ashfaq.systemproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CompanyDetails extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_company_details, container, false);

        ImageView back = view.findViewById(R.id.back);
        FirebaseDatabase node =FirebaseDatabase.getInstance();
        DatabaseReference reference= node.getReference();
        TextView tname,tmail,ttype,tabout,tloc,tsize;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        tname = view.findViewById(R.id.username);
        tmail = view.findViewById(R.id.usermail);
        ttype = view.findViewById(R.id.type);
        tabout= view.findViewById(R.id.about);
        tloc= view.findViewById(R.id.companyloc);
        tsize= view.findViewById(R.id.companysize);
        String uid = getArguments().getString("Companykey");

        reference.child("CompanyDB").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child(uid).child("name").getValue(String.class);
                String mail = snapshot.child(uid).child("email").getValue(String.class);
                String size = snapshot.child(uid).child("Size").getValue(String.class);
                String cat = snapshot.child(uid).child("category").getValue(String.class);
                String loc = snapshot.child(uid).child("Location").getValue(String.class);
                String about = snapshot.child(uid).child("about").getValue(String.class);

                tname.setText(name);
                tmail.setText(mail);
                ttype.setText(cat);
                tabout.setText(about);
                tloc.setText(loc);
                tsize.setText(size);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });


        return view;
    }
}