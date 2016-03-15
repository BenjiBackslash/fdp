package com.fractureof.demos.location;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback, GoogleMap.OnMarkerClickListener, HangoutsAdapter.ViewHolder.IMyViewHolderClicks {
    @Override
    public void onHangoutSelected(int idx) {
        //go to the key of the marker..
        for ( Marker marker : hangoutMarkers.keySet()) {
            Integer marker_idx = hangoutMarkers.get(marker);
            if (marker_idx == idx) {
                markSelectedIfChanged(marker);
            }
        }
    }

    private static final float HUE_DIFF = 30.0f;
    private GoogleMap mMap;
    private float mCurHue;

    private Map<Marker, Integer> hangoutMarkers = new HashMap<Marker, Integer>();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    int selected_hangout_index = -1;


//    private void changeMarkers( List<LatLng> markers ) {
//        mMap.clear();
//        init();
//
//        for ( LatLng latLng : markers) {
//            if ( hangoutMarkers.containsKey(latLng) ) {
//                Marker marker =  hangoutMarkers.get(latLng);
//
//            }
//        }
//    }

    private void initMarkers() {
        initOurMarkers();

        initHangoutMarkers();
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(SplashActivity.temp_part_latLng));


    }

    private void initOurMarkers() {
        BitmapDescriptor bitmapDesc = BitmapDescriptorFactory.fromBitmap(SplashActivity.partnerMarkerBitmap);
        MarkerOptions opts = new MarkerOptions().position(SplashActivity.temp_part_latLng)
                .icon(bitmapDesc);
        mMap.addMarker(opts);
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(SplashActivity.temp_part_latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(SplashActivity.temp_part_latLng,
                12.0f));
        BitmapDescriptor bitmapDescMe = BitmapDescriptorFactory.fromBitmap(SplashActivity.meMarkerBitmap);
        MarkerOptions optsMe = new MarkerOptions().position(SplashActivity.temp_me_latLng)
                .icon(bitmapDescMe);
        mMap.addMarker(optsMe);
    }

    private void initHangoutMarkers() {
        //for each
        for (int i = 0; i < SplashActivity.hangout_arr.length(); ++i) {
            try {
                JSONObject obj = SplashActivity.hangout_arr.getJSONObject(i);
                String lat = obj.getString("lat");
                Float lat_f = Float.parseFloat(lat);
                String lon = obj.getString("lon");
                Float lon_f = Float.parseFloat(lon);
                LatLng latLng = new LatLng(lat_f,lon_f);
                MarkerOptions opts = new MarkerOptions().position(latLng);
                opts.title(obj.getString("name"));
                if ( i == selected_hangout_index) {
                    opts.icon(
                            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)
                    );
                }
                Marker marker = mMap.addMarker(opts);
                hangoutMarkers.put(marker,i);
                //new PutMarkerTask(mMap,opts,i).execute(null, null, null);

            } catch (JSONException ex) {}
        }
    }

    Marker prevMarker = null;
    @Override
    public boolean onMarkerClick(Marker marker) {
        if (hangoutMarkers.containsKey(marker)) {
            markSelectedIfChanged(marker);
        }
        return false;
    }

    private void markSelectedIfChanged(Marker marker) {
        if (marker != prevMarker) {
            int sel = hangoutMarkers.get(marker);

            mAdapter = new HangoutsAdapter(SplashActivity.hangout_arr, sel, this);
            mRecyclerView.swapAdapter(mAdapter, false);
            mRecyclerView.smoothScrollToPosition(sel);
            marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
            if (prevMarker != null) {
                prevMarker.setIcon(BitmapDescriptorFactory.defaultMarker());
            }
            prevMarker = marker;
        }
    }

//    class PutMarkerTask extends AsyncTask<Void, Void, Void> {
//        GoogleMap map;
//        MarkerOptions markerOptions;
//        int i;

//        public PutMarkerTask(GoogleMap map, MarkerOptions markerOptions, int i) {
//            this.map = map;
//            this.markerOptions= markerOptions;
//            this.i = i;
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            try {
//                Thread.sleep(i * 500);
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//        @Override
//        protected void onPostExecute(Void result) {
//            map.addMarker(markerOptions);
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.hangout_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new HangoutsAdapter(SplashActivity.hangout_arr,-1, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);


        initMarkers();
        //Add marker for partner at center of the map
//        MarkerOptions opts = new MarkerOptions().position(SplashActivity.temp_part_latLng).title("Partner's Marker");
//        mMap.addMarker(new MarkerOptions().position(SplashActivity.temp_part_latLng).title("Partner's Marker"));

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

        //// TODO: 29/02/2016  load location of me and partner
        //show on map

    }

    private void createMarker(LatLng latLng, String name, String address) {
        mCurHue += HUE_DIFF; 
        
        Marker marker = mMap.addMarker(
                new MarkerOptions()
                        .position(latLng)
                        .icon(
                                BitmapDescriptorFactory.defaultMarker(
                                        mCurHue
                                )));
    }
}

