package com.ashfaq.systemproject;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CompanyRegDemo extends AppCompatActivity {
    String multinational="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();
        setContentView(R.layout.activity_company_reg_demo);
        //Declare variables
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        Button register = findViewById(R.id.rgstrb);
        Spinner spinner = (Spinner) findViewById(R.id.companytyp);
        EditText tname, tmail, tpass, tid, tloc, tsize, tabout;

        tname = findViewById(R.id.tname);
        tid = findViewById(R.id.tid);
        tmail = findViewById(R.id.tmail);
        tloc = findViewById(R.id.tloc);
        tsize = findViewById(R.id.tsize);
        tabout = findViewById(R.id.tabout);
        tpass = findViewById(R.id.tpass);

        //arraylist for spinner
        List<String> status = new ArrayList<>();
        status.add(0,"Choose Category");
        status.add("IT");
        status.add("Business & Management");
        status.add("Engineering");
        status.add("Biology & Medical Science");
        status.add("Others");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, status);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Choose current status")) {
                } else {
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });

        //register button clicl action
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CompanyRegDemo.this, "Register Button CLicked",Toast.LENGTH_SHORT).show();
                String name = tname.getText().toString();
                String id = tid.getText().toString();
                String cat = spinner.getSelectedItem().toString();
                String email = tmail.getText().toString();
                String pass = tpass.getText().toString();
                String location = tloc.getText().toString();
                String size = tsize.getText().toString();
                String about = tabout.getText().toString();

                if(email!=null && pass!=null)
                {
                    mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(CompanyRegDemo.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                FirebaseUser user = mAuth.getCurrentUser();
                                String uid = user.getUid();
                                reference.child("CompanyDB").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        reference.child("CompanyDB").child(uid).child("name").setValue(name);
                                        reference.child("CompanyDB").child(uid).child("id").setValue(id);
                                        reference.child("CompanyDB").child(uid).child("category").setValue(cat);
                                        reference.child("CompanyDB").child(uid).child("email").setValue(email);
                                        reference.child("CompanyDB").child(uid).child("Location").setValue(location);
                                        reference.child("CompanyDB").child(uid).child("Size").setValue(size);
                                        reference.child("CompanyDB").child(uid).child("about").setValue(about);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                            else{
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(CompanyRegDemo.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rbutton1:
                if (checked) {
                    multinational="Yes";
                }
                break;
            case R.id.rbutton2:
                if (checked) {
                    multinational="No";
                }
                break;
        }
    }
}