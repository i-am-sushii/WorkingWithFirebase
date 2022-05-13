package com.example.projectwork;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MainActivity extends AppCompatActivity {
    private static  final int GALLERY_CODE=1;
    Uri imageUrl=null;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseStorage mStorage;
    ImageButton demoimg;
    EditText Name,Description;
    ProgressDialog progressDialog;
    Button btninsert;
    TextView Show;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();
        Show=findViewById(R.id.show);

        demoimg=findViewById(R.id.demoimg);
        Name=findViewById(R.id.name);
        Description=findViewById(R.id.desc);
        btninsert=findViewById(R.id.insert);
        progressDialog=new ProgressDialog(this);

        mDatabase=FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference().child("Information");
        mStorage=FirebaseStorage.getInstance();

        demoimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_CODE);

            }
        });
        Show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,InfoActivity.class));
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK)
        {
            imageUrl = data.getData();
            demoimg.setImageURI(imageUrl);
        }
        btninsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fn=Name.getText().toString().trim();
                String ds=Description.getText().toString().trim();

                if (!(fn.isEmpty() && ds.isEmpty() && imageUrl!=null)){
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();

                    StorageReference filepath=mStorage.getReference().child("ImagePost").child(imageUrl.getLastPathSegment());
                    filepath.putFile(imageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> downloadUrl=taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    String t=task.getResult().toString();
                                    DatabaseReference newPost=mRef.push();
                                    newPost.child("Description").setValue(ds);
                                    newPost.child("Name").setValue(fn);
                                    newPost.child("image").setValue(task.getResult().toString());
                                    progressDialog.dismiss();

                                    startActivity(new Intent(MainActivity.this,InfoActivity.class));
                                }
                            });
                        }
                    });

                }
            }
        });
    }











    @Override
    protected void onStart() {
        FirebaseUser user=mAuth.getCurrentUser();
        if (user==null)
        {
            startActivity(new Intent(MainActivity.this,Login.class));
        }

        super.onStart();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home) {
            Toast.makeText(this, "You clicked Home", Toast.LENGTH_SHORT).show();
        }
        if (item.getItemId() == R.id.logout) {
            Toast.makeText(this, "Logout Successfull", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, Login.class));
        }
        if (item.getItemId() == R.id.settings) {
            Toast.makeText(this, "You clicked Settings", Toast.LENGTH_SHORT).show();
        }
        if (item.getItemId() == R.id.about) {
            Toast.makeText(this, "You clicked About", Toast.LENGTH_SHORT).show();
        }

        return true;
    }
}