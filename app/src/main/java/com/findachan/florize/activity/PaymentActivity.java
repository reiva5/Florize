package com.findachan.florize.activity;

import android.content.Intent;
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
import com.findachan.florize.SendMailTask;
import com.findachan.florize.Util;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView itemName;
    private TextView itemPrice;
    private ImageView imageView;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private Util util;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        util = new Util();
        intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Intent myIntent = getIntent();
        String id = myIntent.getStringExtra("id");

        database = FirebaseDatabase.getInstance();
//        reference = database.getInstance().getReference("item").child("0");
        reference = database.getInstance().getReference("item").child(id);

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
                final Button send = (Button) findViewById(R.id.buttonSend);
                send.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        Log.i("SendMailActivity", "Send Button Clicked.");

                        String fromEmail = "jehiannormansaviero@gmail.com";
                        String fromPassword = "I LOVE MATEMATIKA";
                        String toEmails = "13515139@std.stei.itb.ac.id";
                        List toEmailList = Arrays.asList(toEmails
                                .split("\\s*,\\s*"));
                        Log.i("SendMailActivity", "To List: " + toEmailList);
                        String emailSubject = "Payment Pending";
                        String emailBody = "Segera bayarkan tagihanmu sebesar: " + util.toPrettyPrice(Long.parseLong(getIntent().getStringExtra("price")));
                        new SendMailTask(PaymentActivity.this).execute(fromEmail,
                                fromPassword, toEmailList, emailSubject, emailBody);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
}