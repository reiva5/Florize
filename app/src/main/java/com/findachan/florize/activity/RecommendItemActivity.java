package com.findachan.florize.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.findachan.florize.GpsTracker;
import com.findachan.florize.R;
import com.findachan.florize.adapter.BouquetAdapter;
import com.findachan.florize.models.Bouquet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecommendItemActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BouquetAdapter adapter;
    private List<Bouquet> albumList;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Activity activity;

    private SensorManager mSensorManager;
    private Sensor sensor;
    TextView textLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hand_bouquet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initCollapsingToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        albumList = new ArrayList<>();
        adapter = new BouquetAdapter(this, albumList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new RecommendItemActivity.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getInstance().getReference("item");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ActivityCompat.requestPermissions(RecommendItemActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 123);
                GpsTracker gt = new GpsTracker(getApplicationContext());
                Location l = gt.getLocation();
                if( l == null){
                    Toast.makeText(getApplicationContext(),"GPS unable to get Value",Toast.LENGTH_SHORT).show();
                }else {
                    double lat = l.getLatitude();
                    double lon = l.getLongitude();
                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    try {
                        addresses = geocoder.getFromLocation(lat, lon, 1);
                        String location = "Bandung";
                        Toast.makeText(getApplicationContext(),addresses.get(0).getSubAdminArea(),Toast.LENGTH_SHORT).show();
                        for (DataSnapshot bq : dataSnapshot.getChildren()){
                            if (addresses.get(0).getSubAdminArea().toLowerCase().contains(bq.child("lokasi").getValue(String.class).toLowerCase())) {
                                albumList.add(new Bouquet(bq.child("nama").getValue(String.class),
                                        bq.child("kategori").getValue(Integer.class),
                                        bq.child("harga").getValue(Integer.class),
                                        bq.child("deskripsi").getValue(String.class),
                                        bq.child("url").getValue(String.class),
                                        bq.getKey())
                                );
                            }
                        }
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(),"GPS unable to get city",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
                try {
                    Glide.with(getApplicationContext()).load(R.drawable.handbouquet).into((ImageView) findViewById(R.id.backdrop));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
                findViewById(R.id.main_content).setBackgroundColor(Color.BLACK);
                findViewById(R.id.appbar).setBackgroundColor(Color.BLACK);
                findViewById(R.id.collapsing_toolbar).setBackgroundColor(Color.BLACK);
                findViewById(R.id.layout_daftar).setBackgroundColor(Color.parseColor("#FFC1C0C0"));
//                getWindow().getDecorView().setBackgroundColor(Color.parseColor("#EFEFEF"));
            } else if (event.values[0] > 30) {
                Log.i("Sensor Changed", "onSensor Change :" + event.values[0]);
                Log.i("warna", ">>> tetap");
                findViewById(R.id.appbar).setBackgroundColor(Color.parseColor("#FF4081"));
                findViewById(R.id.collapsing_toolbar).setBackgroundColor(Color.parseColor("#FF4081"));
                findViewById(R.id.main_content).setBackgroundColor(Color.parseColor("#FFFFFF"));
                findViewById(R.id.layout_daftar).setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        }
    };

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}