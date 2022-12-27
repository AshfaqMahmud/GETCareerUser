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
import com.jjoe64.graphview.series.DataPointInterface;
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
    long n = 0;
    int year, value;
    GraphView graphView;
    LineGraphSeries series;
    FirebaseUser user;
    FirebaseDatabase node = FirebaseDatabase.getInstance();
    DatabaseReference reference = node.getReference("CompanyDB");

    @Override
    public void onStart() {
        super.onStart();
        String uid = getArguments().getString("Companykey");
        reference.child(uid).child("Economy").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataPointInterface[] dp = new DataPoint[(int) snapshot.getChildrenCount()];
                int index = 0;
                for(DataSnapshot snapshot1: snapshot.getChildren())
                {
                    PointValue pointValue = snapshot1.getValue(PointValue.class);
                    dp[index]=new DataPoint(pointValue.getxYear(),pointValue.getyVal());
                    index++;
                }
                series.resetData(dp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

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
        series = new LineGraphSeries();
        graphView = view.findViewById(R.id.graph);
        graphView.addSeries(series);
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

                tname.setText(name);
                tmail.setText(mail);
                ttype.setText(cat);
                tabout.setText(about);
                tloc.setText(loc);
                tsize.setText(size);

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