package com.findachan.florize.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.findachan.florize.R;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "profile";
    public TextView email_profile, name_profile, phone_profile, address_profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        SharedPreferences userProfile = getSharedPreferences("UserProfile", Context.MODE_PRIVATE);
        Log.w(TAG,userProfile.getString("id",""));
        email_profile= (TextView) findViewById(R.id.profile_email);
        email_profile.setText(userProfile.getString("email",""));
        findViewById(R.id.edit_profile_button).setOnClickListener(this);
        name_profile = (TextView) findViewById(R.id.profile_name);
        phone_profile = (TextView) findViewById(R.id.profile_phone);
        address_profile = (TextView) findViewById(R.id.profile_address);
        name_profile.setText(userProfile.getString("fullname",""));
        phone_profile.setText(userProfile.getString("phone",""));
        address_profile.setText(userProfile.getString("address",""));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit_profile_button:
                Log.w(TAG,"edit profile clicked");
                startActivity(new Intent(this, EditProfileActivity.class));
                break;
        }
    }
}
