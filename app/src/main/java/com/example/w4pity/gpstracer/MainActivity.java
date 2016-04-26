package com.example.w4pity.gpstracer;

import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements LocationListener {


    LocationManager lm;
    Location oldLoc;
    static int time = 0;
    static float avSpeed = 0;
    static float dist = 0;

    public float distance (float lat_a, float lng_a, float lat_b, float lng_b )
    {
        double earthRadius = 3958.75;
        double latDiff = Math.toRadians(lat_b-lat_a);
        double lngDiff = Math.toRadians(lng_b-lng_a);
        double a = Math.sin(latDiff /2) * Math.sin(latDiff /2) +
                Math.cos(Math.toRadians(lat_a)) * Math.cos(Math.toRadians(lat_b)) *
                        Math.sin(lngDiff /2) * Math.sin(lngDiff /2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = earthRadius * c;

        int meterConversion = 1609;

        return new Float(distance * meterConversion).floatValue();
    }

        @Override
        public void onLocationChanged(Location location) {
            DecimalFormat f = new DecimalFormat();
            f.setMaximumFractionDigits(2);
            CustomView cv = (CustomView) findViewById(R.id.Cv);
            cv.tabSpeed[cv.currentIndex++] = (float)(location.getSpeed()*3.6);
            if(cv.currentIndex == 99)
                cv.currentIndex = 0;
            //Toast.makeText(getApplicationContext(), "latitude: "+ location.getSpeed()*3.6, Toast.LENGTH_SHORT).show();
            TextView t = (TextView) findViewById(R.id.time);
            TextView s = (TextView) findViewById(R.id.speed);
            TextView av = (TextView) findViewById(R.id.average);
            TextView d = (TextView) findViewById(R.id.dist);
            s.setText("Current speed: "+f.format(location.getSpeed()*3.6 )+" km/h");
            avSpeed+=location.getSpeed()*3.6;
            av.setText("Average speed :"+ f.format(avSpeed / time) + "km/h");
            time++;
            t.setText("Time: "+time+" s");
            if(time>1)
            {
                //dist += (float) Math.sqrt(((location.getLatitude()-oldLoc.getLatitude())*(location.getLatitude()-oldLoc.getLatitude())+(location.getLongitude()-oldLoc.getLongitude())*(location.getLongitude()-oldLoc.getLongitude())));
                //dist += 6371*Math.acos(Math.sin((location.getLatitude()/**Math.PI/180*/)*Math.sin(oldLoc.getLatitude()/**Math.PI/180*/)+Math.cos(location.getLatitude()/**Math.PI/180*/)*Math.cos((oldLoc.getLatitude()/* * Math.PI / 180*/) * Math.cos((location.getLongitude()/* * Math.PI / 180*/) -(oldLoc.getLongitude()/* * Math.PI / 180*/)))));
                dist += distance((float)location.getLatitude(),(float) location.getLongitude(), (float)oldLoc.getLatitude(), (float)oldLoc.getLongitude())*0.001;
                d.setText("Distance: "+f.format(dist)+" km");
            }
            oldLoc = location;
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CustomView cv = (CustomView) findViewById(R.id.Cv);
        Display d = getWindowManager().getDefaultDisplay();
        int width = d.getWidth();
        cv.setLayoutParams(new LinearLayout.LayoutParams(width, width));
        final Button BStart = (Button) findViewById(R.id.start);
        BStart.setBackgroundColor(Color.RED);
        BStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cv.started = !cv.started;
                if (cv.started) {
                    BStart.setText("Stop Tracking");
                    onResume();
                    BStart.setBackgroundColor(Color.RED);
                } else {
                    BStart.setText("Start Tracking");
                    onPause();
                    BStart.setBackgroundColor(Color.GREEN);
                }

            }
        });
        final Button valid = (Button) findViewById(R.id.valid);
        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText e = (EditText) findViewById(R.id.editText);
                //e.setText("60");
                cv.maxSpeedView = Integer.parseInt(e.getText().toString())/10;
                Toast.makeText(getApplicationContext(),"Set to "+ cv.maxSpeedView*10+" km/h view", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        try {
            lm.removeUpdates(this);
        }
        catch(Exception e){}
    }
    @Override
    protected void onResume() {

        super.onResume();

        lm = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        TextView t = (TextView) findViewById(R.id.activation);


        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER))
            try {
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
                t.setText("Microchip: GPS activated");
            }
            catch(Exception e){}
        else
        try {
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
            t.setText("Microchip: Internet activated");
        }
        catch(Exception e){}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
