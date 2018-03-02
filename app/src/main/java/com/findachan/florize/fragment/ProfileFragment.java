package com.findachan.florize.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.findachan.florize.R;
import com.findachan.florize.Util;
import com.findachan.florize.activity.DetailActivity;
import com.findachan.florize.activity.EditProfileActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private Toolbar toolbar;
    private static final String TAG = "profile";
    public TextView email_profile, name_profile, phone_profile, address_profile;
    private static final String LOG_TAG = DetailActivity.class.getSimpleName();

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private Util util;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_profile);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SharedPreferences userProfile = (SharedPreferences) this.getActivity().getSharedPreferences("UserProfile", Context.MODE_PRIVATE);
        Log.w(TAG,userProfile.getString("id",""));
        email_profile= (TextView) getView().findViewById(R.id.profile_email);
        email_profile.setText(userProfile.getString("email",""));
        getView().findViewById(R.id.edit_profile_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("status: ", "clicked");
                Intent myIntent = new Intent(ProfileFragment.this.getActivity(), EditProfileActivity.class);
                startActivity(myIntent);
            }
        });
        name_profile = (TextView) getView().findViewById(R.id.profile_name);
        phone_profile = (TextView) getView().findViewById(R.id.profile_phone);
        address_profile = (TextView) getView().findViewById(R.id.profile_address);
        name_profile.setText(userProfile.getString("fullname",""));
        phone_profile.setText(userProfile.getString("phone",""));
        address_profile.setText(userProfile.getString("address",""));

//        util = new Util();
//        Intent myIntent = getActivity().getIntent();
//        String id = myIntent.getStringExtra("id");
//        database = FirebaseDatabase.getInstance();
//        reference = database.getInstance().getReference("item").child(id);
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                name_profile = (TextView) getView().findViewById(R.id.profile_name);
//                phone_profile = (TextView) getView().findViewById(R.id.profile_phone);
//                email_profile = (TextView) getView().findViewById(R.id.profile_email);
//                address_profile = (TextView) getView().findViewById(R.id.profile_address);
//
//                name_profile.setText(dataSnapshot.child("fullname").getValue(String.class));
//                email_profile.setText(dataSnapshot.child("email").getValue(String.class));
//                phone_profile.setText(util.toPrettyPrice(dataSnapshot.child("phone").getValue(Long.class)));
//                address_profile.setText(dataSnapshot.child("address").getValue(String.class));
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                System.out.println("The read failed: " + databaseError.getMessage());
//            }
//        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //String strtext = getArguments().getString("edttext").toString();
        //Log.i("data :", strtext);
        return  inflater.inflate(R.layout.fragment_profile, container, false);

//        Button button = (Button) view.findViewById(R.id.edit_profile_button);
//        button.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                switch (view.getId()) {
//                    case R.id.edit_profile_button:
//                        Log.w(TAG,"edit profile clicked");
//                        startActivity(new Intent(getActivity(), EditProfileActivity.class));
//                        break;
//                }
//            }
//        });

    }

}
