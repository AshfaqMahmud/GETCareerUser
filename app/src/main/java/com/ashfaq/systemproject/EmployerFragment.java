package com.ashfaq.systemproject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class EmployerFragment extends Fragment implements RecyclerViewInterface {
    String uid;
    MyAdapter myAdapter;
    RecyclerView recyclerView;
    ArrayList<Company> list;
    DatabaseReference databaseReference;
    ProgressDialog dialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_employer, container, false);
        recyclerView=view.findViewById(R.id.recycler);
        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Loading Employers\nThis may take some time");
        dialog.show();
        databaseReference= FirebaseDatabase.getInstance().getReference("CompanyDB");
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myAdapter=new MyAdapter(getActivity(),list,this);
        recyclerView.setAdapter(myAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    uid =dataSnapshot.getKey();
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String loc = dataSnapshot.child("Location").getValue(String.class);
                    String cat = dataSnapshot.child("category").getValue(String.class);
                    Company company = new Company(name,loc,cat,uid);
                    list.add(company);
                    dialog.dismiss();
                }
                myAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity()," "+error.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onItemClick(int position) {
        CompanyDetails companyDetails = new CompanyDetails();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, companyDetails ,"null");
        Bundle args = new Bundle();
        args.putString("Companykey", list.get(position).getCompanykey());
        companyDetails.setArguments(args);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}