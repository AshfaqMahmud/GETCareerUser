package com.ashfaq.systemproject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ShowReviews extends Fragment implements RecyclerViewInterface {
    String uid;
    ReviewAdapter myAdapter;
    RecyclerView recyclerView;
    ArrayList<Review> list;
    DatabaseReference databaseReference;
    ProgressDialog dialog;
    TextView tstar;
    float avg = 0;
    long n = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_reviews, container, false);
        recyclerView = view.findViewById(R.id.recyclerreview);
        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Loading reviews\nThis may take some time");
        dialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference("CompanyDB");
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myAdapter = new ReviewAdapter(getActivity(), list, this);
        recyclerView.setAdapter(myAdapter);

        tstar = view.findViewById(R.id.star);

        String args = getArguments().getString("Companykey");
        //Toast.makeText(getActivity(), args, Toast.LENGTH_SHORT).show();

        databaseReference.child(args).child("Reviews").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    dialog.dismiss();
                    tstar.setText("No reviews found");
                } else {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        String no = snapshot1.getKey();
                        //Toast.makeText(getActivity(), no, Toast.LENGTH_SHORT).show();
                        float temp = snapshot1.child("Rating").getValue(Float.class);
                        avg = avg + temp;
                        n++;
                        String star = String.valueOf(temp);
                        String fed = snapshot1.child("Feedback").getValue(String.class);
                        //Toast.makeText(getActivity(), star + " " + fed, Toast.LENGTH_SHORT).show();
                        star = "Rating: " + star;
                        Review review = new Review(no, star, fed);
                        list.add(review);
                        dialog.dismiss();
                    }
                    myAdapter.notifyDataSetChanged();
                    avg = avg / n;
                    tstar.setText("Current Rating : " + String.valueOf(avg) + " of " + String.valueOf(n) + " feedbacks");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), " " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getActivity(), "Item clicked", Toast.LENGTH_SHORT).show();
    }
}