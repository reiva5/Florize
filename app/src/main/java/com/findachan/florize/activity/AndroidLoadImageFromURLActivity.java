package com.findachan.florize.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.findachan.florize.DownloadImageTask;
import com.findachan.florize.R;
import com.findachan.florize.Util;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AndroidLoadImageFromURLActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView itemName;
    private TextView itemPrice;
    private ImageView imageView;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private Util util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        util = new Util();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Intent myIntent = getIntent();
        String id = myIntent.getStringExtra("id");

        database = FirebaseDatabase.getInstance();
        reference = database.getInstance().getReference("item").child("0");
        //reference = database.getInstance().getReference("item").child(id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                imageView = (ImageView) findViewById(R.id.itemImageCart);
                itemName = (TextView) findViewById(R.id.itemNameCart);
                itemPrice = (TextView) findViewById(R.id.itemPriceCart);
                itemName.setText(dataSnapshot.child("nama").getValue(String.class));
                itemPrice.setText(util.toPrettyPrice(dataSnapshot.child("harga").getValue(Long.class)));
                String imgURL  = dataSnapshot.child("url").getValue(String.class);
                new DownloadImageTask(imageView).execute(imgURL);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

    }
}