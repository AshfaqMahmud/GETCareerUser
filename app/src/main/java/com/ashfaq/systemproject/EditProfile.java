package com.ashfaq.systemproject;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class EditProfile extends AppCompatActivity {
    final int PICK_IMG_REQ = 1;
    String date2= "";
    Uri mImageUri;
    Button btnselect, btnupload, editdon;
    ShapeableImageView image;
    FirebaseUser user;
    StorageReference reference;
    DatabaseReference dbref;
    StorageTask uploadtask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        reference = FirebaseStorage.getInstance().getReference("userimages");
        dbref = FirebaseDatabase.getInstance().getReference("userinfo");
        EditText edate, ecompany, eabout, eexp1, eexp2, eescl, eesyear, eeclg, eecyear;
        btnselect = findViewById(R.id.choosebtn);
        btnupload = findViewById(R.id.uploadbtn);
        editdon= findViewById(R.id.editdone);
        ecompany= findViewById(R.id.company);
        eabout= findViewById(R.id.about);
        eexp1= findViewById(R.id.exp1);
        eexp2= findViewById(R.id.exp2);
        eescl= findViewById(R.id.school);
        eesyear= findViewById(R.id.schoolyear);
        eeclg= findViewById(R.id.college);
        eecyear= findViewById(R.id.clgyear);


        image = findViewById(R.id.userimage);


        //set date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        edate = findViewById(R.id.date);


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



        btnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uploadtask != null && uploadtask.isInProgress())
                {
                    Toast.makeText(EditProfile.this,"Upload in progress",Toast.LENGTH_LONG).show();
                }
                else
                {
                    uploadFile();
                }

            }
        });


        //edit button action
        editdon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String company = ecompany.getText().toString();
                String about = eabout.getText().toString();
                String exp1 = eexp1.getText().toString();
                String exp2 = eexp2.getText().toString();
                String school = eescl.getText().toString();
                String schoolyear = eesyear.getText().toString();
                String college = eeclg.getText().toString();
                String collegeyear = eecyear.getText().toString();

                //get current user
                user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                DatabaseReference dbrefer = FirebaseDatabase.getInstance().getReference("userinfo");
                dbrefer.child(uid).child("Company").setValue(company);
                dbrefer.child(uid).child("CompanyStartFrom").setValue(date2);
                dbrefer.child(uid).child("About").setValue(about);
                dbrefer.child(uid).child("Experience").child("Exp1").setValue(exp1);
                dbrefer.child(uid).child("Experience").child("Exp2").setValue(exp2);
                dbrefer.child(uid).child("Education").child("School").setValue(school);
                dbrefer.child(uid).child("Education").child("SchoolYear").setValue(schoolyear);
                dbrefer.child(uid).child("Education").child("College").setValue(college);
                dbrefer.child(uid).child("Education").child("CollegeYear").setValue(collegeyear);
                Toast.makeText(EditProfile.this,"Profile Saved",Toast.LENGTH_SHORT).show();

            }
        });

    }
    private String FileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadFile() {
        if (mImageUri != null){
            StorageReference fileref = reference.child(System.currentTimeMillis()+"."+FileExtension(mImageUri));



            uploadtask = fileref.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(EditProfile.this,"Upload Successful",Toast.LENGTH_LONG).show();
                    String url = fileref.toString();
                    Upload upload = new Upload("pp",url);
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    String uploadid = user.getUid();
                    dbref.child(uploadid).child("imageurl").setValue(url);

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditProfile.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                        }
                    });
        }
        else {
            Toast.makeText(this,"No file Selected",Toast.LENGTH_SHORT).show();
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMG_REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMG_REQ && resultCode == RESULT_OK && data != null && data.getData()!=null)
        {
            mImageUri = data.getData();
            Picasso.with(this).load(mImageUri).into(image);
        }
    }
}