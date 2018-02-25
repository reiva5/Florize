package com.findachan.florize.activity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.findachan.florize.R;
import com.findachan.florize.fragment.HomeFragment;
import com.findachan.florize.fragment.ProfileFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_person_white_24dp,
            R.drawable.ic_store_white_24dp,
    };

    private SensorManager mSensorManager;
    private Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

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

    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("SHOP");
        tabOne.setGravity(Gravity.CENTER);
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_store_white_24dp, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("PROFILE");
        tabTwo.setGravity(Gravity.CENTER);
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_person_white_24dp, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new HomeFragment(), "ONE");
        adapter.addFrag(new ProfileFragment(), "TWO");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
