package com.findachan.florize.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.findachan.florize.R;
import com.findachan.florize.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "signup";
    private EditText inputEmail, inputPassword, inputUsername, inputPhone, inputFullname, inputAddress;
    private Button btnSignUp;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        auth = FirebaseAuth.getInstance();
//        if (auth.getCurrentUser() != null) {
//            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
//            finish();
//        }
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email_signup);
        inputPassword = (EditText) findViewById(R.id.password_signup);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
//        inputFullname = (EditText) findViewById(R.id.fullname_signup);
//        inputAddress = (EditText) findViewById(R.id.address_signup);
//        inputPhone = (EditText) findViewById(R.id.phone_signup);
        findViewById(R.id.sign_in_click).setOnClickListener(this);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();
//                String fullname = inputFullname.getText().toString().trim();
//                String address = inputAddress.getText().toString().trim();
//                String phone = inputPhone.getText().toString().trim();

//                if (TextUtils.isEmpty(fullname)) {
//                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
//                    return;
//                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignUpActivity.this, "Success" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignUpActivity.this,""+task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    FirebaseUser newuser = FirebaseAuth.getInstance().getCurrentUser();
                                    String email = newuser.getEmail();
                                    String id = newuser.getUid();
                                    User user = new User("-",email,"-","-",id);
                                    DatabaseReference myRef = database.getReference("user");
                                    myRef.child(id).setValue(user);
                                    auth.signInWithEmailAndPassword(email, password)
                                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    // If sign in fails, display a message to the user. If sign in succeeds
                                                    // the auth state listener will be notified and logic to handle the
                                                    // signed in user can be handled in the listener.
                                                    //progressBar.setVisibility(View.GONE);
                                                    if (!task.isSuccessful()) {
                                                        // there was an error
                                                        if (password.length() < 6) {
                                                            inputPassword.setError(getString(R.string.minimum_password));
                                                        } else {
                                                            Toast.makeText(SignUpActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                }
                                            });
                                    SharedPreferences userProfile = getSharedPreferences("UserProfile", Context.MODE_PRIVATE);

                                    // We need an editor object to make changes
                                    SharedPreferences.Editor edit = userProfile.edit();

                                    // Set/Store data
                                    edit.putString("email", email);
                                    edit.putString("id",id);
                                    edit.putString("fullname","-");
                                    edit.putString("phone","-");
                                    edit.putString("address","-");
                                    edit.putBoolean("logged_in", true);

                                    // Commit the changes
                                    edit.commit();
                                    startActivity(new Intent(SignUpActivity.this, EditProfileActivity.class));
                                    finish();
                                }
                            }
                        });

            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_click:
                Log.w(TAG,"sign in with password");
                startActivity(new Intent(this, LoginActivity.class));
                break;
            // ...
        }
    }
}

