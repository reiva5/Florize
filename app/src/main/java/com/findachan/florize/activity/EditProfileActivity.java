package com.findachan.florize.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.findachan.florize.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "editprofile";
    public TextView email_edit_profile;
    public EditText edit_profile_name,edit_profile_phone,edit_profile_address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        SharedPreferences userProfile = getSharedPreferences("UserProfile", Context.MODE_PRIVATE);
        Log.w(TAG,userProfile.getString("id",""));
        email_edit_profile= (TextView) findViewById(R.id.edit_profile_email);
        email_edit_profile.setText(userProfile.getString("email",""));
        findViewById(R.id.save_profile_button).setOnClickListener(this);
        edit_profile_name = (EditText) findViewById(R.id.edit_profile_name);
        edit_profile_phone = (EditText) findViewById(R.id.edit_profile_phone);
        edit_profile_address = (EditText) findViewById(R.id.edit_profile_address);
        if(!userProfile.getString("fullname","").contentEquals("-")){
            edit_profile_name.setText(userProfile.getString("fullname",""));
        }
        if(!userProfile.getString("phone","").contentEquals("-")){
            edit_profile_phone.setText(userProfile.getString("phone",""));
        }
        if(!userProfile.getString("address","").contentEquals("-")){
            edit_profile_address.setText(userProfile.getString("address",""));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save_profile_button:
                Log.w(TAG,"save profile clicked");
                saveProfile();
                break;
        }
    }

    private void saveProfile() {
        final String name = edit_profile_name.getText().toString();
        final String phone = edit_profile_phone.getText().toString();
        final String address = edit_profile_address.getText().toString();
        SharedPreferences userProfile = getSharedPreferences("UserProfile", Context.MODE_PRIVATE);
        String id = userProfile.getString("id","");
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(), "Enter your name!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(getApplicationContext(), "Enter your phone number!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (phone.matches("[a-zA-Z ]+")) {
            Toast.makeText(getApplicationContext(), "Phone number should contain number only", Toast.LENGTH_SHORT).show();
            return;
        }
        if (phone.length() < 9) {
            Toast.makeText(getApplicationContext(), "Phone number is too short", Toast.LENGTH_SHORT).show();
            return;
        }
        if (phone.length() > 13) {
            Toast.makeText(getApplicationContext(), "Phone number is too long", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(address)) {
            Toast.makeText(getApplicationContext(), "Enter your address!", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("user").child(id);
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Map<String,Object> value = (Map<String, Object>) dataSnapshot.getValue();
                value.put("email",name);
                value.put("phone",phone);
                value.put("address",address);
                value.put("emailSet",true);
                value.put("phoneSet",true);
                value.put("addressSet",true);
                Log.w(TAG, "Value is: " + value);
                myRef.setValue(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read or update value.", error.toException());
            }
        });

        // We need an editor object to make changes
        SharedPreferences.Editor edit = userProfile.edit();

        // Set/Store data
        edit.putString("fullname",name);
        edit.putString("phone",phone);
        edit.putString("address",address);

        // Commit the changes
        edit.commit();
        Log.w(TAG,userProfile.getString("fullname",""));
        startActivity(new Intent(this, MainActivity.class));
    }
}
