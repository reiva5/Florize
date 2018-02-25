package com.findachan.florize.activity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;


/**
 * Created by Finda on 23/02/2018.
 */

public class SensorActivity implements SensorEventListener {

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        if(sensor.getType() == Sensor.TYPE_LIGHT){
            Log.i("Sensor Changed", "Accuracy :" + accuracy);
        }
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
//        float x = event.values[0];
//        textLight.setText((int)x + " lux");
        if( event.sensor.getType() == Sensor.TYPE_LIGHT){
            Log.i("Sensor Changed", "onSensor Change :" + event.values[0]);
        }
    }

}
