package com.example.nouran.playground.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nouran.playground.Adapters.PlaceAutocompleteAdapter;
import com.example.nouran.playground.Models.Places;
import com.example.nouran.playground.Models.PlacesInfo;
import com.example.nouran.playground.R;
import com.example.nouran.playground.Services.Services;
import com.example.nouran.playground.Services.playgroundAPI;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.PolyUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(70, 166));
    private static final int PLACE_PICKER_REQUEST = 1;
    private final int Perm_REQ = 123123;
    protected GeoDataClient mGeoDataClient;
    AutoCompleteTextView ETsearch;
    Double latitude, longitude;
    ArrayList<LatLng> Listpoints;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private String fineLoc = Manifest.permission.ACCESS_FINE_LOCATION;
    private String coarseLoc = Manifest.permission.ACCESS_COARSE_LOCATION;
    private boolean isGranted = false;
    private LatLng myLatLng;
    private PlaceAutocompleteAdapter mplaceAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient;
    private String TAG = "";
    private Marker mmarker;
    private PlacesInfo mplace;
    private ImageView mInfo, mpicker;
    private ResultCallback<PlaceBuffer> mUpdateplaceDetailscallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {

            if (!places.getStatus().isSuccess()) {
                Log.d(TAG, "place query is not completed" + places.getStatus().toString());
                places.release();
                return;

            }
            try {

                final Place place = places.get(0);


                mplace = new PlacesInfo();
                mplace.setAddress(place.getAddress().toString());
                mplace.setAttributions(place.getAddress().toString());
                mplace.setName(place.getName().toString());
                mplace.setId(place.getId().toString());
                mplace.setWebsite(place.getWebsiteUri());
                mplace.setRating(place.getRating());
                mplace.setPhonenumper(place.getPhoneNumber().toString());
                mplace.setLatLng(place.getLatLng());

                Log.d(TAG, "Place Details : " + mplace.toString());


            } catch (NullPointerException e) {

                Log.d(TAG, "NullPointerException : " + e.toString());
            }

            moveCamera(mplace.getLatLng(), 7f, mplace);

            places.release();

        }
    };
    // Retrieving the google place object
    private AdapterView.OnItemClickListener mAutocompleteClickListner = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            hideSoftkeyboard();
            final AutocompletePrediction item = mplaceAutocompleteAdapter.getItem(i);
            final String placeId = item.getPlaceId();
            PendingResult<PlaceBuffer> placeResult = com.google.android.gms.location.places.Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);

            placeResult.setResultCallback(mUpdateplaceDetailscallback);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ETsearch = findViewById(R.id.input_search);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        checkPermission();
        init();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        //get data from detail
        Intent i = getIntent();
        latitude = i.getDoubleExtra("latitude", 0);
        longitude = i.getDoubleExtra("longitude", 0);
        Listpoints = new ArrayList<>();
    }

    private void init() {

        //Location Auto_suggestion textview
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(com.google.android.gms.location.places.Places.GEO_DATA_API)
                .addApi(com.google.android.gms.location.places.Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();
        mGeoDataClient = com.google.android.gms.location.places.Places.getGeoDataClient(this, null);

        mplaceAutocompleteAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient, LAT_LNG_BOUNDS, null);

        ETsearch.setOnItemClickListener(mAutocompleteClickListner);
        ETsearch.setAdapter(mplaceAutocompleteAdapter);

        ETsearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionid, KeyEvent keyEvent) {
                if (actionid == EditorInfo.IME_ACTION_SEARCH || actionid == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {

                    geolocate();
                    hideSoftkeyboard();
                }

                return false;
            }
        });
        mInfo = findViewById(R.id.place_info);
        mInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (mmarker.isInfoWindowShown()) {
                        mmarker.hideInfoWindow();

                    } else {

                        mmarker.showInfoWindow();
                    }

                } catch (NullPointerException e) {
                    Log.e(TAG, "Click mInfo NullPointerException" + e.getMessage());
                }
            }
        });
        mpicker = findViewById(R.id.place_picker);
        mpicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(MapsActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);

                PendingResult<PlaceBuffer> placeResult = com.google.android.gms.location.places.Places.GeoDataApi
                        .getPlaceById(mGoogleApiClient, place.getId());

                placeResult.setResultCallback(mUpdateplaceDetailscallback);
            }
        }
    }

    private void moveCamera(LatLng latLng, float zoom, PlacesInfo placesInfo) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        mMap.clear();
        if (placesInfo != null) {
            try {
                String snnipit = "Address :" + placesInfo.getAddress() + "\n" +
                        "Website :" + placesInfo.getWebsite() + "\n" +
                        "Rating :" + placesInfo.getRating() + "\n" +
                        "PhoneNumper :" + placesInfo.getPhonenumper() + "\n";
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(latLng)
                        .title(placesInfo.getName())
                        .snippet(snnipit);

                mmarker = mMap.addMarker(markerOptions);


            } catch (NullPointerException e) {
                Log.e(TAG, "movecamera : NullPointerException" + e.getMessage());
            }

        } else {
            mMap.addMarker(new MarkerOptions().position(latLng));
        }
        hideSoftkeyboard();
    }

    private void moveCamera(LatLng latLng, float zoom, String title) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title(title);
        mMap.addMarker(markerOptions);
        hideSoftkeyboard();
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void checkPermission() {
        String[] permissions = new String[]{fineLoc, coarseLoc};
        if (ContextCompat.checkSelfPermission(this, fineLoc) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, coarseLoc) == PackageManager.PERMISSION_GRANTED) {
                isGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(MapsActivity.this, permissions, Perm_REQ);
            }
        } else {
            ActivityCompat.requestPermissions(MapsActivity.this, permissions, Perm_REQ);

        }
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

        if (googleMap != null) {
            mMap = googleMap;
            try {
                mMap.setMyLocationEnabled(true);
                if (isGranted) {
                    getDeviceLoc();
                    mMap.getUiSettings().setZoomControlsEnabled(true);

                    mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                        @Override
                        public void onMapLongClick(LatLng latLng) {
                            //Reset marker when already 2

                            Listpoints.add(latLng);
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(latLng);

                            mMap.addMarker(markerOptions);

                            if (Listpoints.size() > 2) {

                                Listpoints.clear();
                                mMap.clear();

                            } else if (Listpoints.size() == 2) {
                                getRequestURL(Listpoints.get(0), Listpoints.get(1));
                            }
                            //TODO: request get direction code bellow

                            /*if(Listpoints.size()==2){

                    *//*String url = getRequestURL(Listpoints.get(0), Listpoints.get(1));
                    Taskrequestdirection taskRequestDirections = new Taskrequestdirection();
                    taskRequestDirections.execute(url);*//*
                            }*/
                        }
                    });

                    // Add a marker in Sydney and move the camera
                    //LatLng playground = new LatLng(latitude, longitude);

                }

            } catch (SecurityException e) {

            }
        }

//        mMap.addMarker(new MarkerOptions().position(playground).title("Marker in playground"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(playground.latitude, playground.longitude), 5));
    }

    private void getRequestURL(LatLng origin, LatLng dest) {

        String str_org = origin.latitude + "," + origin.longitude;
        String str_des = dest.longitude + "," + dest.longitude;
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + str_org + "&destination=" + str_des + "&key=" + getString(R.string.google_API_KEy);

        Retrofit retrofit = playgroundAPI.getclient();
        Services services = retrofit.create(Services.class);
        Call<Places> call = services.get_Direction(url);
        call.enqueue(new Callback<Places>() {
            @Override
            public void onResponse(Call<Places> call, Response<Places> response) {
                if (response.isSuccessful()) {
                    Log.e("p1", response.body().getRoutes().get(0).getLegs().get(0).getDistance().getText());
                    Log.e("p1", response.body().getRoutes().get(0).getLegs().get(0).getDuration().getText());
                    Log.e("p1", response.body().getRoutes().get(0).getLegs().get(0).getSteps().get(0).getPolyline().getPoints());


                }
                int size = response.body().getRoutes().get(0).getLegs().get(0).getSteps().size();

                List<String> points = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    String poin = response.body().getRoutes().get(0).getLegs().get(0).getSteps().get(i).getPolyline().getPoints();
                    points.add(poin);
                }


                for (int i = 0; i < points.size(); i++) {
                    PolylineOptions polylineOptions = new PolylineOptions();

                    polylineOptions.color(Color.BLUE);
                    polylineOptions.width(12);
                    polylineOptions.addAll(PolyUtil.decode(points.get(i)));
                    polylineOptions.geodesic(true);
                    mMap.addPolyline(polylineOptions);
                }
            }

            @Override
            public void onFailure(Call<Places> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        isGranted = false;
        if (requestCode == Perm_REQ) {
            if (grantResults.length > 0) {
                for (int index = 0; index < grantResults.length; index++) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                        isGranted = false;
                        return;
                    }
                }
                isGranted = true;
                initMap();
            }
        }
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location ", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    private void getDeviceLoc() {
        try {

            Task<Location> lastLocation = fusedLocationProviderClient.getLastLocation();
            lastLocation.addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if (task.isSuccessful()) {
                        Location location = task.getResult();
                        if (location != null) {
                            myLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                            Listpoints.add(myLatLng);
                            addMarker(myLatLng, "");
                        }
                    }
                }
            });

        } catch (SecurityException e) {

        } catch (NullPointerException e) {

        }
    }

    //method for searching
    private void geolocate() {
        String searchstring = ETsearch.getText().toString();

        Geocoder geocoder = new Geocoder(MapsActivity.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchstring, 1);

        } catch (IOException e) {
            Log.e(TAG, "geolocate IOException" + e.getMessage());

        }
        if (list.size() > 0) {
            Address address = list.get(0);
            Log.d(TAG, "Information for Address :" + address.toString());
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), 9f, address.getAddressLine(0));

        }
        hideSoftkeyboard();


    }

    private void addMarker(LatLng latLng, String title) {
        mMap.addMarker(new MarkerOptions().position(latLng).title(title));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f));
    }

    private void hideSoftkeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
