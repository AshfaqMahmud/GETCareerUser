package com.ashfaq.systemproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import com.google.firebase.auth.FirebaseAuth;

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

        FirebaseDatabase node =FirebaseDatabase.getInstance();
        DatabaseReference reference= node.getReference();
        TextView tname,tmail,ttype,tabout,tloc,tsize;
        //FirebaseAuth mAuth = FirebaseAuth.getInstance();
        //FirebaseUser user = mAuth.getCurrentUser();

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

                int val18 = snapshot.child(uid).child("Economical value").child("2018").getValue(Integer.class);
                int val19 = snapshot.child(uid).child("Economical value").child("2019").getValue(Integer.class);
                int val20 = snapshot.child(uid).child("Economical value").child("2020").getValue(Integer.class);
                int val21 = snapshot.child(uid).child("Economical value").child("2021").getValue(Integer.class);
                int val22 = snapshot.child(uid).child("Economical value").child("2022").getValue(Integer.class);

                tname.setText(name);
                tmail.setText(mail);
                ttype.setText(cat);
                tabout.setText(about);
                tloc.setText(loc);
                tsize.setText(size);

                GraphView graph = (GraphView) view.findViewById(R.id.graph);
                LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                        new DataPoint(2018, val18),
                        new DataPoint(2019, val19),
                        new DataPoint(2020, val20),
                        new DataPoint(2021, val21),
                        new DataPoint(2022, val22)
                });
                graph.addSeries(series);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return view;
    }
}