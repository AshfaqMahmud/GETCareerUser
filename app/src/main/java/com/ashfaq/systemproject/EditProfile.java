package com.ashfaq.systemproject;

import android.app.DatePickerDialog;

import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import com.google.firebase.storage.StorageReference;

import java.util.Calendar;


public class EditProfile extends AppCompatActivity {

    String date2 = "";
    String company = "", eid = "", ckey = "";
    Button editdon;
    LinearLayout layout;
    FirebaseUser user;
    StorageReference reference;
    DatabaseReference dbref, dbc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        reference = FirebaseStorage.getInstance().getReference("userimages");
        dbref = FirebaseDatabase.getInstance().getReference("userinfo");
        dbc = FirebaseDatabase.getInstance().getReference();
        EditText edate, ecompany, eabout, eexp1, eexp2, eescl, eesyear, eeclg, eecyear, eeid;
        TextView tname, tmail, tphone;

        editdon = findViewById(R.id.editdone);
        ecompany = findViewById(R.id.company);
        eabout = findViewById(R.id.about);
        eexp1 = findViewById(R.id.exp1);
        eexp2 = findViewById(R.id.exp2);
        eescl = findViewById(R.id.school);
        eesyear = findViewById(R.id.schoolyear);
        eeclg = findViewById(R.id.college);
        eecyear = findViewById(R.id.clgyear);
        eeid = findViewById(R.id.eid);
        tname = findViewById(R.id.username);
        tmail = findViewById(R.id.usermail);
        tphone = findViewById(R.id.userconct);


        //image = findViewById(R.id.userimage);

        layout = findViewById(R.id.emphide);

        //set date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        edate = findViewById(R.id.date);
        user = FirebaseAuth.getInstance().getCurrentUser();
        String ukey = user.getUid();
        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child(ukey).child("Name").getValue(String.class);
                String mail = snapshot.child(ukey).child("Email").getValue(String.class);
                String phone = snapshot.child(ukey).child("Phone").getValue(String.class);
                tname.setText(name);
                tmail.setText(mail);
                tphone.setText(phone);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        edate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(EditProfile.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = dayOfMonth + "-" + month + "-" + year;
                        edate.setText(date);
                        date2 = date;
                    }
                }, year, month, day);
                dialog.show();
            }
        });

        //edit button action
        editdon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String about = eabout.getText().toString();
                String exp1 = eexp1.getText().toString();
                String exp2 = eexp2.getText().toString();
                String school = eescl.getText().toString();
                String schoolyear = eesyear.getText().toString();
                String college = eeclg.getText().toString();
                String collegeyear = eecyear.getText().toString();
                eid = eeid.getText().toString();

                Query query = dbc.child("CompanyDB").child(ckey).child("Employees");
                //Toast.makeText(EditProfile.this,"Searching for company",Toast.LENGTH_SHORT).show();
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    int flag = 0;

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot snap : snapshot.getChildren()) {
                            Toast.makeText(EditProfile.this, snap.getKey(), Toast.LENGTH_SHORT).show();
                            if (snap.child("eid").getValue(String.class).equals(eid)) {
                                Toast.makeText(EditProfile.this, "Found User", Toast.LENGTH_SHORT).show();
                                String tempo = snap.getKey();

                                String uid = user.getUid();
                                Toast.makeText(EditProfile.this, uid, Toast.LENGTH_SHORT).show();
                                String umail = user.getEmail();
                                DatabaseReference dbrefer = FirebaseDatabase.getInstance().getReference("userinfo");
                                dbrefer.child(uid).child("Company").setValue(company);
                                dbrefer.child(uid).child("eid").setValue(eid);
                                dbrefer.child(uid).child("CompanyStartFrom").setValue(date2);
                                dbrefer.child(uid).child("About").setValue(about);
                                dbrefer.child(uid).child("Experience").child("Exp1").setValue(exp1);
                                dbrefer.child(uid).child("Experience").child("Exp2").setValue(exp2);
                                dbrefer.child(uid).child("Education").child("School").setValue(school);
                                dbrefer.child(uid).child("Education").child("SchoolYear").setValue(schoolyear);
                                dbrefer.child(uid).child("Education").child("College").setValue(college);
                                dbrefer.child(uid).child("Education").child("CollegeYear").setValue(collegeyear);
                                dbc.child("CompanyDB").child(ckey).child("Employees").child(tempo).child("email").setValue(umail);
                                Toast.makeText(EditProfile.this, "Profile Saved", Toast.LENGTH_SHORT).show();
                                break;
                            } else {
                                flag = 1;
                            }
                        }
                        if (flag == 1) {
                            Toast.makeText(EditProfile.this, "Wrong attempt", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }

}