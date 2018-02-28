package com.findachan.florize.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

    private SensorManager mSensorManager;
    private Sensor sensor;

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

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(lightListener, sensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(lightListener);
    }

    public SensorEventListener lightListener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            if(sensor.getType() == Sensor.TYPE_LIGHT){
                Log.i("Sensor Changed", "Accuracy :" + accuracy);
            }
        }

        public void onSensorChanged(SensorEvent event) {
//            if( event.sensor.getType() == Sensor.TYPE_LIGHT){
//                Log.i("Sensor Changed", "onSensor Change :" + event.values[0]);
//            }

            if (event.values[0] <= 30) {
                Log.i("Sensor Changed", "onSensor Change :" + event.values[0]);
                Log.i("warna", ">>> hijau");
                findViewById(R.id.toolbar).setBackgroundColor(Color.BLACK);
                findViewById(R.id.tab_layout).setBackgroundColor(Color.BLACK);
                findViewById(R.id.activity_main).setBackgroundColor(Color.parseColor("#FFC1C0C0"));
//                getWindow().getDecorView().setBackgroundColor(Color.parseColor("#EFEFEF"));
            } else if (event.values[0] > 30) {
                Log.i("Sensor Changed", "onSensor Change :" + event.values[0]);
                Log.i("warna", ">>> tetap");
                findViewById(R.id.toolbar).setBackgroundColor(Color.parseColor("#FF4081"));
                findViewById(R.id.tab_layout).setBackgroundColor(Color.parseColor("#FF4081"));
                findViewById(R.id.activity_main).setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        }
    };

//    public void launchDesignCard(View view) {
//        Log.d(LOG_TAG, "Button clicked!");
//        Intent intent = new Intent(this, DesignCardActivity.class);
//
//        startActivity(intent);
//    }
}
