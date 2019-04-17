package ir.EasyJa;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.shawnlin.numberpicker.NumberPicker;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MapActivityView extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener,
        GoogleMap.OnMapLongClickListener {

    private static Dialog dialogVerify;
    private EditText activityRegister_name;
    private EditText activityRegister_lastName;
    private EditText activityRegister_phone;
    private Boolean fieldsAreFilled = false;
    private TextView dialogVerify_txt_0;
    private static TextView dialogVerify_txt_1;
    private TextView dialogVerify_txt_counter;
    private static EditText dialogVerify_edt_code;
    private TextView dialogVerify_btn_cancel;
    private TextView dialogVerify_btn_register;
    private TextView activityRegister_btn_register;
    private EditText activityRegister_birth;
    private RadioButton activityRegister_rb_male;
    private RadioButton activityRegister_rb_female;
    private Dialog dialogDateSelector;
    private TextView dialogDateSelector_btn_register;
    private TextView dialogDateSelector_btn_cancel;
    private TextView dialogDateSelector_txt_0;
    private NumberPicker dialogDateSelector_np_year;
    private NumberPicker dialogDateSelector_np_month;
    private NumberPicker dialogDateSelector_np_day;
    private String selectedYear = "1369";
    private String selectedMonth = "11";
    private String selectedDay = "23";
    private LinearLayout activityRegister_logo;
    private ArrayList<String> stateName = new ArrayList<>();
    private ArrayList<String> cityName = new ArrayList<>();
    private MaterialSpinner citySpinner;
    private MaterialSpinner stateSpinner;
    private Boolean isFirstTime = true;
    private String selectedCity = "8";
    private static Dialog dialogSuccess;
    private static TextView dialogSuccess_txt_memberCode;
    private TextView dialogSuccess_btn_register;
    private String oneSignalUserID;
    private String oneSignalRegistrationID;
    private static String customerId;
    private static String memberCode;
    private CountDownTimer countDownTimer;
    private EditText activityRegister_email;
    private TextView dialogVerify_btn_resend;
    private AVLoadingIndicatorView loading;
    public static MapActivityView activity;
    private static final int REQUEST_CODE_PERMISSION = 2;

    private String[] mPermission;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private LatLng LOCATION;
    private Marker positionMarker;
    private Circle accuracyCircle;
    private Marker marker;
    private LatLng mLatLng_Marker;
    private int accuracyStrokeColor = Color.argb(255, 130, 182, 228);
    private int accuracyFillColor = Color.argb(100, 130, 182, 228);
    private TextView toolbar_main_tv_header;
    private EditText activityRegister_academyName;
    private EditText activityRegister_address;
    private String lat = "0";
    private String lng = "0";
    static String from = " ";
    private LatLng LATLONG;
    private Double latitude;
    private Double longtitude;
    private MarkerOptions marker1;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);


        toolbar_main_tv_header = (TextView) findViewById(R.id.toolbar_main_tv_header);
        toolbar_main_tv_header.setText("موقیعت مکانی");
        ImageView btnNavigation = (ImageView) findViewById(R.id.btnNavigation);

        btnNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapActivityView.super.onBackPressed();
            }
        });


        mPermission = new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.ACCESS_FINE_LOCATION,};


        activity = MapActivityView.this;

        activityRegister_btn_register = (TextView) findViewById(R.id.btn_register);

        activityRegister_btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!lat.equals("0")) {
                    if (from.equals("register")) {
                        RegisterActivity.setMarker(LATLONG);
                    }else if (from.equals("AddNewAds")){
                        AddNewAds.setMarker(LATLONG);
                    }
                    finish();
                }else {
                    Toast.makeText(MapActivityView.this, "موقعیت مکانی را انتخاب کنید", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mFragmentMap = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.activity_map_fr_main);
        mFragmentMap.getMapAsync(this);

        try {
            Intent intent = getIntent();
            latitude = Double.parseDouble(intent.getStringExtra("latitude"));
            longtitude =  Double.parseDouble(intent.getStringExtra("longtitude"));



        } catch (Exception e) {

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e("Req Code", "" + requestCode);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length == 4 &&
                    grantResults[0] == MockPackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == MockPackageManager.PERMISSION_GRANTED &&
                    grantResults[2] == MockPackageManager.PERMISSION_GRANTED) {

                // Success Stuff here

            }
        }

    }

    ////////////////////////////////////////////// map methods /////////////////////////////////////////
    public void onMapSearch() {
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);
//        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        LOCATION = new LatLng(latitude,longtitude);
        marker1 = new MarkerOptions().position(new LatLng(LOCATION.latitude, LOCATION.longitude));
        marker1.icon(BitmapDescriptorFactory.fromBitmap(markerCreator(R.mipmap.ic_marker)));
        mMap.addMarker(marker1).showInfoWindow();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LOCATION, 14));

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        LOCATION = new LatLng(G.defaultGlat, G.defaultGlng);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LOCATION, 14));


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

//        double latitude = location.getLatitude();
//        double longitude = location.getLongitude();
//        float accuracy = location.getAccuracy();
//
//        if (positionMarker != null) {
//            positionMarker.remove();
//        }
////        final MarkerOptions positionMarkerOptions = new MarkerOptions()
////                .position(new LatLng(latitude, longitude))
////                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_car))
////                .anchor(0.5f, 1f);
////        positionMarker = mMap.addSelectedMarker(positionMarkerOptions);
//
//        if (accuracyCircle != null) {
//            accuracyCircle.remove();
//        }
////        final CircleOptions accuracyCircleOptions = new CircleOptions()
////                .center(new LatLng(latitude, longitude))
////                .radius(accuracy)
////                .fillColor(accuracyFillColor)
////                .strokeColor(accuracyStrokeColor)
////                .strokeWidth(2.0f);
////        accuracyCircle = mMap.addCircle(accuracyCircleOptions);
//
////        addressText = getCompleteAddressString(latitude, longitude);
////        address.setText(addressText);
//
//        LOCATION = new LatLng(latitude, longitude);
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LOCATION, 14));

    }


    @Override
    public void onMapLongClick(LatLng latLng) {
//        if (marker != null) {
//            marker.remove();
//        }
//        marker = mMap.addMarker(new MarkerOptions()
//                .position(new LatLng(latLng.latitude, latLng.longitude))
//                .draggable(true).visible(true));
//
//        mLatLng_Marker = marker.getPosition();
//        lat = String.valueOf(latLng.latitude);
//        lng = String.valueOf(latLng.longitude);
//
//        LATLONG = latLng ;
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        // Check if we were successful in obtaining the map.
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

            @Override
            public void onMyLocationChange(Location location) {
                // TODO Auto-generated method stub


                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                float accuracy = location.getAccuracy();

                if (positionMarker != null) {
                    positionMarker.remove();
                }
                final MarkerOptions positionMarkerOptions = new MarkerOptions()
                        .position(new LatLng(latitude, longitude))
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_bookmark))
                        .anchor(0.5f, 1f);
                positionMarker = mMap.addMarker(positionMarkerOptions);

                if (accuracyCircle != null) {
                    accuracyCircle.remove();
                }
                final CircleOptions accuracyCircleOptions = new CircleOptions()
                        .center(new LatLng(latitude, longitude))
                        .radius(accuracy)
                        .fillColor(accuracyFillColor)
                        .strokeColor(accuracyStrokeColor)
                        .strokeWidth(2.0f);
                accuracyCircle = mMap.addCircle(accuracyCircleOptions);

                LOCATION = new LatLng(latitude, longitude);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LOCATION, 14));


            }
        });
    }
    private static Bitmap markerCreator(int x) {
        int height = 150;
        int width = 150;
        BitmapDrawable bitmapdraw = (BitmapDrawable) G.CONTEXT.getResources().getDrawable(x);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
        return smallMarker;
    }

}
