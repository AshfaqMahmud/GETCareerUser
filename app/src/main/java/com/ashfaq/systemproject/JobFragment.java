package com.ashfaq.systemproject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class JobFragment extends Fragment implements RecyclerViewInterface {
    JobAdapter myAdapter;
    RecyclerView recyclerView;
    ArrayList<Job> list;
    DatabaseReference databaseReference, dbref;
    ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job, container, false);

        recyclerView = view.findViewById(R.id.recycler2);
        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Loading available jobs\nThis may take some time");
        dialog.show();

        SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("userinfo", 0);
        String cat = sharedPreferences.getString("category", "");
        //Toast.makeText(getActivity(), cat + " fragment", Toast.LENGTH_SHORT).show();
        if (cat.equals("IT")) {
            databaseReference = FirebaseDatabase.getInstance().getReference("JobVacancy").child("Category").child("IT");

            list = new ArrayList<>();
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            myAdapter = new JobAdapter(getActivity(), list, this);
            recyclerView.setAdapter(myAdapter);

            databaseReference.addValueEventListener(new ValueEventListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String uid2 = dataSnapshot.getKey();
                        String name = dataSnapshot.child("Post").getValue(String.class);
                        String typ = dataSnapshot.child("Location").getValue(String.class);
                        String company = dataSnapshot.child("Company").getValue(String.class);
                        Job job = new Job(name, typ, company, uid2);
                        list.add(job);
                        dialog.dismiss();
                    }
                    myAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getActivity(), " " + error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } else if (cat.equals("Business & Management")) {
            databaseReference = FirebaseDatabase.getInstance().getReference("JobVacancy").child("Category").child("Business & Management");

            list = new ArrayList<>();
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            myAdapter = new JobAdapter(getActivity(), list, this);
            recyclerView.setAdapter(myAdapter);

            databaseReference.addValueEventListener(new ValueEventListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String uid2 = dataSnapshot.getKey();
                        Toast.makeText(getActivity(), uid2 + " 2nd", Toast.LENGTH_SHORT).show();
                        String name = dataSnapshot.child("Post").getValue(String.class);
                        String typ = dataSnapshot.child("Location").getValue(String.class);
                        String company = dataSnapshot.child("Company").getValue(String.class);
                        Job job = new Job(name, typ, company, uid2);
                        list.add(job);
                        dialog.dismiss();
                    }
                    myAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getActivity(), " " + error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } else if (cat.equals("Engineering")) {
            databaseReference = FirebaseDatabase.getInstance().getReference("JobVacancy").child("Category").child("Engineering");

            list = new ArrayList<>();
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            myAdapter = new JobAdapter(getActivity(), list, this);
            recyclerView.setAdapter(myAdapter);

            databaseReference.addValueEventListener(new ValueEventListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String uid2 = dataSnapshot.getKey();
                        Toast.makeText(getActivity(), uid2 + " 2nd", Toast.LENGTH_SHORT).show();
                        String name = dataSnapshot.child("Post").getValue(String.class);
                        String typ = dataSnapshot.child("Location").getValue(String.class);
                        String company = dataSnapshot.child("Company").getValue(String.class);
                        Job job = new Job(name, typ, company, uid2);
                        list.add(job);
                        dialog.dismiss();
                    }
                    myAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getActivity(), " " + error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } else if (cat.equals("Biology & Medical Science")) {
            databaseReference = FirebaseDatabase.getInstance().getReference("JobVacancy").child("Category").child("Biology & Medical Science");

            list = new ArrayList<>();
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            myAdapter = new JobAdapter(getActivity(), list, this);
            recyclerView.setAdapter(myAdapter);

            databaseReference.addValueEventListener(new ValueEventListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String uid2 = dataSnapshot.getKey();
                        Toast.makeText(getActivity(), uid2 + " 2nd", Toast.LENGTH_SHORT).show();
                        String name = dataSnapshot.child("Post").getValue(String.class);
                        String typ = dataSnapshot.child("Location").getValue(String.class);
                        String company = dataSnapshot.child("Company").getValue(String.class);
                        Job job = new Job(name, typ, company, uid2);
                        list.add(job);
                        dialog.dismiss();
                    }
                    myAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getActivity(), " " + error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            dialog.dismiss();
            Snackbar.make(getActivity().findViewById(android.R.id.content),
                    "No job found for your category", Snackbar.LENGTH_LONG).show();
        }


        return view;
    }


    @Override
    public void onItemClick(int position) {

    }
}