package dku.mse.TestApp.System;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

public class Accelerometer {
	
	SensorManager sm;
	float x;
	float y;
	float z;
	
	public Accelerometer(Context context){
		sm = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
		sm.registerListener(mSensorListener,sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	SensorEventListener mSensorListener = new SensorEventListener(){
		public void onAccuracyChanged(Sensor sensor, int accuracy){
		}

		@SuppressWarnings("deprecation")
		@Override
		public void onSensorChanged(SensorEvent event) {
			// TODO Auto-generated method stub
			if(event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE){}
			float[] value = event.values;
		
			x = value[0];
			y = value[1];
			z = value[2];
				
		}
	};
	
	public float returnValue()
	{
		return x;
	}
}
