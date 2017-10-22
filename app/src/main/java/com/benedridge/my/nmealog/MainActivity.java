package com.benedridge.my.nmealog;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener, GpsStatus.NmeaListener {

    private LocationManager locationManager;
    private String locationProvider = LocationManager.GPS_PROVIDER;

    TextView textViewHeading;
    TextView textViewLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        setContentView(R.layout.activity_main);
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
            startActivity(new Intent(this,SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();

        textViewHeading = (TextView) findViewById(R.id.textViewHeading);
        textViewLog = (TextView) findViewById(R.id.textViewLog);

        locationManager.requestLocationUpdates(locationProvider, 0, 0, this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(locationProvider, 0, 0, this);
        locationManager.addNmeaListener(this);
    }

    @Override
    public void onPause(){
        super.onPause();
        //locationManager.removeNmeaListener(this);
        //locationManager.removeGpsStatusListener(locationListener);
    }

    @Override
    public void onProviderEnabled(String s){

        if(s!=null){
            textViewHeading.setText("Location Provider Enabled " + s +"\n");
        }
        else{
            textViewHeading.setText("Location Provider Disabled "+"\n");
        }
        //locationManager.addGpsStatusListener(locationListener);
        //locationManager.addNmeaListener(this);
    }

    @Override
    public void onLocationChanged(Location location){
        textViewLog.append("Location changed: " + location.getLatitude() + " " + location.getLongitude()+"\n");
        textViewLog.append(location.getLatitude() + " " + location.getLongitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle){
        System.out.println("Status Changed: " + "String: " + s + "Int: " + i);
    }

    @Override
    public void onProviderDisabled(String s){
        if(s!=null){
            textViewHeading.setText("Location Provider disabled " + s +"\n");
        }
    }

    @Override
    public void onNmeaReceived(long l, String s){
        if(s!=null){
            textViewLog.append("NMEA: + " + l + " : " + s+"\n");
        }
    }



    //Buttons
    public void buttonStreamOnclick (View view){
        Toast.makeText(this, "Streaming to HTTP and USB", Toast.LENGTH_SHORT).show();
    }

    public void buttonCopyOnclick (View view){
        ClipboardManager clip = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipping = ClipData.newPlainText("Nmea Data",textViewLog.getText());
        clip.setPrimaryClip(clipping);
        Toast.makeText(this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
    }
}
