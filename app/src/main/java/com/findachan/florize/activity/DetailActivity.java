package com.findachan.florize.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
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

public class DetailActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private static final String LOG_TAG = DetailActivity.class.getSimpleName();
    private ImageView imageBuy;
    private TextView namePrice;
    private TextView nameBuy;
    private TextView nameDesc;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private Util util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        util = new Util();
        Intent myIntent = getIntent();
        String id = myIntent.getStringExtra("id");
        database = FirebaseDatabase.getInstance();
        reference = database.getInstance().getReference("item").child(id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                imageBuy = (ImageView) findViewById(R.id.imageBuy);
                nameBuy = (TextView) findViewById(R.id.nameBuy);
                nameDesc = (TextView) findViewById(R.id.nameDesc);
                namePrice = (TextView) findViewById(R.id.namePrice);

                nameBuy.setText(dataSnapshot.child("nama").getValue(String.class));
                namePrice.setText(util.toPrettyPrice(dataSnapshot.child("harga").getValue(Long.class)));
                nameDesc.setText(dataSnapshot.child("deskripsi").getValue(String.class));
                String imgURL  = dataSnapshot.child("url").getValue(String.class);
                new DownloadImageTask(imageBuy).execute(imgURL);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Button button = findViewById(R.id.button_buy);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(DetailActivity.this, DesignCardActivity.class);
                startActivity(myIntent);
            }
        });
    }

//    public void launchDesignCard(View view) {
//        Log.d(LOG_TAG, "Button clicked!");
//        Intent intent = new Intent(this, DesignCardActivity.class);
//
//        startActivity(intent);
//    }
}
