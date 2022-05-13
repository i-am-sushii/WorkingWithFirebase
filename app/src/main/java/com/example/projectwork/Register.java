package com.example.projectwork;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    EditText name, email, password;
    Button register;
    TextView signin;
    String Name, Email, Password;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        signin = findViewById(R.id.signin);
        auth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, com.example.projectwork.Login.class));
            }
        });


    }

    private void register() {
        Name=name.getText().toString().trim();
        Email = email.getText().toString().trim();
        Password = password.getText().toString().trim();
        if (Name.isEmpty()) {
            name.setError("Please Enter Your Name");
        }
        if (Email.isEmpty()) {
            email.setError("Please Enter Your Email");
        }
        if (Password.isEmpty()) {
            password.setError("Please Enter Your Password");
        } else {
            auth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        UserDetails();
                        Toast.makeText(Register.this, "Registeration Succcessfull", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Register.this, com.example.projectwork.Login.class));
                    } else {
                        Toast.makeText(Register.this, "Registeration failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }

    public void UserDetails(){
        holder data=new holder(Name,Email);
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        DatabaseReference node=db.getReference("Users");
        node.child(Name).setValue(data);

    }
}