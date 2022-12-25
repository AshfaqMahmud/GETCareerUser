package com.ashfaq.systemproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
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
    float number= 0;
    int val18=10,val19=20,val20=24,val21=5,val22=1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_company_details, container, false);


        FirebaseDatabase node =FirebaseDatabase.getInstance();
        DatabaseReference reference= node.getReference();
        TextView tname,tmail,ttype,tabout,tloc,tsize,tstar;
        EditText feedback;
        RatingBar ratingBar;
        Button postReview,showReview,showrvw2;
        LinearLayout layout;

        layout = view.findViewById(R.id.ratelayout);
        postReview =view.findViewById(R.id.reviewb);
        showReview = view.findViewById(R.id.show);
        showrvw2 = view.findViewById(R.id.show2);
        feedback = view.findViewById(R.id.feedET);
        ratingBar = view.findViewById(R.id.ratebar);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        tstar= view.findViewById(R.id.star);
        tname = view.findViewById(R.id.username);
        tmail = view.findViewById(R.id.usermail);
        ttype = view.findViewById(R.id.type);
        tabout= view.findViewById(R.id.about);
        tloc= view.findViewById(R.id.companyloc);
        tsize= view.findViewById(R.id.companysize);
        String uid = getArguments().getString("Companykey");
        SharedPreferences preferences = getContext().getSharedPreferences("userinfo", 0);
        String cname = preferences.getString("usercompany","def");
        Log.i("company value",cname);
        //Toast.makeText(getActivity(),cname,Toast.LENGTH_SHORT).show();


        reference.child("CompanyDB").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child(uid).child("name").getValue(String.class);
                String mail = snapshot.child(uid).child("email").getValue(String.class);
                String size = snapshot.child(uid).child("Size").getValue(String.class);
                String cat = snapshot.child(uid).child("category").getValue(String.class);
                String loc = snapshot.child(uid).child("Location").getValue(String.class);
                String about = snapshot.child(uid).child("about").getValue(String.class);

//                val18 = snapshot.child(uid).child("Economical value").child("2018").getValue(Integer.class);
//                val19 = snapshot.child(uid).child("Economical value").child("2019").getValue(Integer.class);
//                val20 = snapshot.child(uid).child("Economical value").child("2020").getValue(Integer.class);
//                val21 = snapshot.child(uid).child("Economical value").child("2021").getValue(Integer.class);
//                val22 = snapshot.child(uid).child("Economical value").child("2022").getValue(Integer.class);

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
                //Toast.makeText(getActivity(),name+" and "+cname,Toast.LENGTH_SHORT).show();
                if(cname.equals(name))
                {
                    showrvw2.setVisibility(View.GONE);
                    layout.setVisibility(View.VISIBLE);
                    ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                            if( rating >= 0 && rating <= 1)
                            {
                                tstar.setText("Worst");
                                number = rating;
                            }
                            else if( rating > 1 && rating <= 2)
                            {
                                tstar.setText("Bad");
                                number = rating;
                            }
                            else if( rating > 2 && rating <= 2.5)
                            {
                                tstar.setText("Neutral");
                                number = rating;

                            }
                            else if( rating > 2.5 && rating <= 4)
                            {
                                tstar.setText("Good");
                                number = rating;

                            }
                            else
                            {
                                tstar.setText("Best");
                                number = rating;

                            }
                        }
                    });

                    postReview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String feedb= feedback.getText().toString();
                            long count = snapshot.child(uid).child("Reviews").getChildrenCount();
                            if(count==0)
                            {
                                reference.child("CompanyDB").child(uid).child("Reviews").child("1").child("Feedback").setValue(feedb);
                                reference.child("CompanyDB").child(uid).child("Reviews").child("1").child("Rating").setValue(number);
                            }
                            else
                            {
                                reference.child("CompanyDB").child(uid).child("Reviews").child(String.valueOf(count+1)).child("Feedback").setValue(feedb);
                                reference.child("CompanyDB").child(uid).child("Reviews").child(String.valueOf(count+1)).child("Rating").setValue(number);
                                //reference.child("CompanyDB").child(uid).child("Reviews").child(String.valueOf(count+1)).child("Given By").setValue(user.getUid());
                            }
                            Toast.makeText(getActivity(),"Review submitted",Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        showrvw2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowReviews showReviews = new ShowReviews();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.container, showReviews ,"null");
                Bundle args = new Bundle();
                args.putString("Companykey", uid);
                showReviews.setArguments(args);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        showReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowReviews showReviews = new ShowReviews();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.container, showReviews ,"null");
                Bundle args = new Bundle();
                args.putString("Companykey", uid);
                showReviews.setArguments(args);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
}