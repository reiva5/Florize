package com.findachan.florize.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.findachan.florize.R;

public class DesignCardActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Intent intentPrevious;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_card);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        intentPrevious = getIntent();

        final Button button = findViewById(R.id.button_wishes);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(DesignCardActivity.this, PaymentActivity.class);
                myIntent.putExtra("id", intentPrevious.getStringExtra("id"));
                myIntent.putExtra("price", intentPrevious.getStringExtra("price"));
                myIntent.putExtra("name", intentPrevious.getStringExtra("name"));
                myIntent.putExtra("url", intentPrevious.getStringExtra("url"));
                startActivity(myIntent);
            }
        });
    }

    @Override
    public void onBackPressed(){
        intentPrevious = getIntent();
        Intent myIntent = new Intent();
        myIntent.putExtra("id", intentPrevious.getStringExtra("id"));
        myIntent.putExtra("price", intentPrevious.getStringExtra("price"));
        myIntent.putExtra("name", intentPrevious.getStringExtra("name"));
        myIntent.putExtra("url", intentPrevious.getStringExtra("url"));
        setResult(AppCompatActivity.RESULT_OK, myIntent);
        super.onBackPressed();
    }
}
