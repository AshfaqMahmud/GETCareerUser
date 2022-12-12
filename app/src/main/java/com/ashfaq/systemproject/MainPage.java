package com.ashfaq.systemproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainPage extends AppCompatActivity {
    BottomNavigationView bnView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_page);
        bnView = findViewById(R.id.bottomNavigation);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference dbref= FirebaseDatabase.getInstance().getReference();
        dbref.child("userinfo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String uid = user.getUid();
                Toast.makeText(MainPage.this,uid+" uid",Toast.LENGTH_SHORT).show();
                String cat =snapshot.child(uid).child("Category").getValue(String.class);
                Toast.makeText(MainPage.this, cat +" 1st",Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences = getSharedPreferences("userinfo",0);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString("category",cat);
                myEdit.apply();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        HomeFragment fragment=new HomeFragment();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container,fragment,"");
        fragmentTransaction.commit();
        bnView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.home:

                        HomeFragment fragment = new HomeFragment();
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container, fragment, "");
                        fragmentTransaction.commit();
                        return true;

                    case R.id.profile:

                        ProfileFragment fragment1 = new ProfileFragment();
                        FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction1.replace(R.id.container, fragment1);
                        fragmentTransaction1.commit();
                        return true;

                    case R.id.employer:

                        FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction2.replace(R.id.container, new EmployerFragment());
                        fragmentTransaction2.commit();
                        return true;

                    case R.id.job:

                        FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction3.replace(R.id.container, new JobFragment());
                        fragmentTransaction3.commit();
                        return true;
                }
                return false;
            }
        });
    }
}