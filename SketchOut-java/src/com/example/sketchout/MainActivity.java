package com.example.sketchout;

import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.sketchout.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

public class MainActivity extends Activity {

	//Map object
	private double latitude;
	private double longitude;
	private GoogleMap googleMap;
	private GeoFire geoFire;
	private GeoQuery geoQuery;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        try {
            // Loading map
            initilizeMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
        Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER); //GPS PROVIDER HAS ISSUES

     	longitude = location.getLongitude();
     	latitude = location.getLatitude();       
     	LatLng position = new LatLng(latitude, longitude);
     	
        // create marker
        MarkerOptions marker = new MarkerOptions().position(position).title("You");
         
        // adding marker
        googleMap.addMarker(marker);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(position));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }
    
    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
 
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
            }
        }
    }
 
    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }
    
    //This heatmap area needs JSON and needs work
    /*private void addHeatMap() {
        List<LatLng> list = null;

        Firebase myFirebaseRef = new Firebase("https://sketchout.firebaseio.com/");
        
        myFirebaseRef.child("sketchout").addValueEventListener(new ValueEventListener() {	
        	@Override
        	public void onDataChange(DataSnapshot snapshot) {
        		snapshot.getValue();
        	}
        	
        	@Override public void onCancelled(FirebaseError error) {}
        });
        
        // Get the data: latitude/longitude positions of police stations.
        try {
            list = readItems();
        } catch (JSONException e) {
            Toast.makeText(this, "Problem reading list of locations.", Toast.LENGTH_LONG).show();
        }

        // Create a heat map tile provider, passing it the latlngs of the police stations.
        HeatmapTileProvider mProvider = new HeatmapTileProvider.Builder().data(list).build();
        // Add a tile overlay to the map, using the heat map tile provider.
        TileOverlay mOverlay = googleMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
    } */
    
}