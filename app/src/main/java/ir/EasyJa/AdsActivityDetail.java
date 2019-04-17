package ir.EasyJa;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.StringTokenizer;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AdsActivityDetail extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener,
        GoogleMap.OnMapLongClickListener, FragmentNavigation.OnFragmentInteractionListener {

    private Boolean fieldsAreFilled = false;
    private ArrayList<String> stateName = new ArrayList<>();
    private ArrayList<String> cityName = new ArrayList<>();
    public static AdsActivityDetail activity;
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
    private String lat = "0";
    private String lng = "0";
    private DrawerLayout drawer;
    private static Adapter_Recycler_Ads adapter_recycler;
    static ArrayList<AdsModel> arrayList = new ArrayList<>();
    static ArrayList<Struct> option_arrayList = new ArrayList<>();
    private TextView btn_reserve;
    private TextView btn_cancel_reserve;
    private FrameLayout fl_reserved;
    private FrameLayout fl_reserve;
    private Dialog dialogCancelConfirm;
    private TextView dialogCancelConfirm_cancel;
    private TextView dialogCancelConfirm_return;
    private ImageView call;
    private ImageView message;
    private TextView date;
    private TextView capacity;
    private TextView name;
    private TextView location;
    private TextView create_date;
    private TextView class_size;
    private double Lat;
    private double Lng;
    private int AdsShift1_ID = 0;
    private boolean IsReserved = false;
    private FrameLayout demo;
    private int userID;
    private int rentID;
    private AVLoadingIndicatorView loading;
    JSONObject Object;
    private String AdID;
    private int EstateTypeID;
    private int SenderID;
    private TextView ad_state;
    private Adapter_Recycler option_adapter_recycler;
    private String PhoneNumber;
    private FrameLayout option_layout;
    private FrameLayout shift_layout;
    private ImageView item_image;
    private WebView web;
    private Dialog dialogReserveConfirm;
    private TextView dialogReserveConfirm_cancel;
    private TextView dialogReserveConfirm_return;
    private ProgressDialog progDailog;
    private boolean needRefresh = false;
    private ImageView btnLike;
    private MarkerOptions marker1;
    private String latitude;
    private String longtitude;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
//        Intent intent = new Intent(AdsActivity.this, LoginActivity.class);
//        AdsActivity.this.startActivity(intent);

        if (web.getVisibility() == View.GONE) {
            finish();

        } else if (web.getVisibility() == View.INVISIBLE) {
            finish();

        } else {
            web.setVisibility(View.GONE);


        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (needRefresh) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);

            needRefresh = false;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_inner);

        // Navigation Drawer
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Fragment squadFragment = new FragmentNavigation();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_view, squadFragment, null);
        fragmentTransaction.commit();

        // Dialog Reserve Confirm
        dialogReserveConfirm = new Dialog(AdsActivityDetail.this);
        dialogReserveConfirm.setContentView(R.layout.dialog_reserve_confirmation);
        dialogReserveConfirm.setCancelable(false);
        dialogReserveConfirm_cancel = (TextView) dialogReserveConfirm.findViewById(R.id.cancel);
        dialogReserveConfirm_return = (TextView) dialogReserveConfirm.findViewById(R.id.go_back);
        dialogReserveConfirm_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogReserveConfirm.dismiss();
                payRequest();

            }
        });

        dialogReserveConfirm_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogReserveConfirm.dismiss();
            }
        });


        // Dialog Reserve Cancel
        dialogCancelConfirm = new Dialog(AdsActivityDetail.this);
        dialogCancelConfirm.setContentView(R.layout.dialog_cancel_confirm);
        dialogCancelConfirm.setCancelable(false);
        dialogCancelConfirm_cancel = (TextView) dialogCancelConfirm.findViewById(R.id.cancel);
        dialogCancelConfirm_return = (TextView) dialogCancelConfirm.findViewById(R.id.go_back);
        dialogCancelConfirm_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCancelConfirm.dismiss();
//                fl_reserve.setVisibility(View.VISIBLE);
//                fl_reserved.setVisibility(View.GONE);
                runUnRentService();

            }
        });

        dialogCancelConfirm_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCancelConfirm.dismiss();
            }
        });

        btn_reserve = (TextView) findViewById(R.id.btn_reserve);
        btn_cancel_reserve = (TextView) findViewById(R.id.btn_cancel_reserve);
        fl_reserved = (FrameLayout) findViewById(R.id.fl_reserved);
        fl_reserve = (FrameLayout) findViewById(R.id.fl_reserve);
        demo = (FrameLayout) findViewById(R.id.demo);
        ad_state = (TextView) findViewById(R.id.ad_state);
        item_image = (ImageView) findViewById(R.id.item_image);
        web = (WebView) findViewById(R.id.web);

        btn_reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean is_registered = G.IS_REGISTERED.getBoolean("IS_REGISTERED", false);
                if (is_registered) {
                    dialogReserveConfirm.show();
                } else {
                    Intent intent = new Intent(AdsActivityDetail.this, LoginActivity.class);
                    intent.putExtra("base", "Ads");
                    AdsActivityDetail.this.startActivity(intent);
                    needRefresh = true;
                    G.mainNeedRefresh = true;
                }
//                cancelReserveView();
//                runRentService();

            }
        });

        btn_cancel_reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                reserveView();
                dialogCancelConfirm.show();

            }
        });


        call = (ImageView) findViewById(R.id.call);
        message = (ImageView) findViewById(R.id.message);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", PhoneNumber, null));
                startActivity(intent);
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdsActivityDetail.this, MessageActivity.class);
                AdsActivityDetail.this.startActivity(intent);
            }
        });

        //Toolbar
        ImageView btnNavigation = (ImageView) findViewById(R.id.btnNavigation);

        btnNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.RIGHT);
            }
        });
        btnLike = (ImageView) findViewById(R.id.btnlike);
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favoriteThisAd();
            }
        });
        toolbar_main_tv_header = (TextView) findViewById(R.id.toolbar_main_tv_header);
        toolbar_main_tv_header.setText("انتخاب شما");


        mPermission = new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.ACCESS_FINE_LOCATION,};


        activity = AdsActivityDetail.this;
        // Views
        loading = (AVLoadingIndicatorView) findViewById(R.id.loading);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mFragmentMap = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.activity_map_fr_main);
        mFragmentMap.getMapAsync(this);

        shift_layout = findViewById(R.id.shift_layout);
        RecyclerView list = (RecyclerView) findViewById(R.id.shift_list);
        LinearLayoutManager manager = new LinearLayoutManager(G.CONTEXT, LinearLayoutManager.VERTICAL, false);
        adapter_recycler = new Adapter_Recycler_Ads(getApplicationContext(), arrayList, new OnItemListener() {
            @Override
            public void onItemSelect(int position) {

            }

            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onItemDelete(int position) {

            }

            @Override
            public void onCardClick(int position) {
                AdsShift1_ID = arrayList.get(position).AdsShift1_ID;
                IsReserved = arrayList.get(position).AdsShift1_IsReserve;
                demo.setVisibility(View.GONE);
                if (isMyAd(SenderID)) {

                } else {
                    if (IsReserved) {
                        userID = arrayList.get(position).AdsShift1_UserId;
                        String myID = G.CUSTOMER_ID.getString("CUSTOMER_ID", "x");
                        if (String.valueOf(userID).equals(myID)) {
                            rentID = arrayList.get(position).AdsShift1_RentId;
                            cancelReserveView();
                        } else {
                            demo.setVisibility(View.VISIBLE);
                        }
                    } else {
                        reserveView();
                    }
                }
            }

            @Override
            public void onEdit(int position) {

            }
        }, 3, false);
        list.setAdapter(adapter_recycler);
        list.setLayoutManager(manager);

        option_layout = findViewById(R.id.options_layout);
        RecyclerView option_list = (RecyclerView) findViewById(R.id.option_list);
        LinearLayoutManager option_manager = new LinearLayoutManager(G.CONTEXT, LinearLayoutManager.VERTICAL, false);
        option_adapter_recycler = new Adapter_Recycler(getApplicationContext(), option_arrayList, new OnItemListener() {
            @Override
            public void onItemSelect(int position) {

            }

            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onItemDelete(int position) {

            }

            @Override
            public void onCardClick(int position) {

            }

            @Override
            public void onEdit(int position) {

            }
        }, 31, false);
        option_list.setAdapter(option_adapter_recycler);
        option_list.setLayoutManager(option_manager);

        date = (TextView) findViewById(R.id.date);
        capacity = (TextView) findViewById(R.id.capacity);
        name = (TextView) findViewById(R.id.name);
        location = (TextView) findViewById(R.id.location);
        create_date = (TextView) findViewById(R.id.create_date);
        class_size = (TextView) findViewById(R.id.class_size);

        try {
            Intent intent = getIntent();
            AdID = intent.getStringExtra("adID");
            sendRequest(AdID);
        } catch (Exception e) {
        }

        getFavoriteState();

        ImageView fullscreen = (ImageView)findViewById(R.id.fullscreen);
        fullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdsActivityDetail.this,MapActivityView.class);
                intent.putExtra("latitude",String.valueOf(Lat));
                intent.putExtra("longtitude",String.valueOf(Lng));
                AdsActivityDetail.this.startActivity(intent);
            }
        });
    }

    private void favoriteThisAd() {
        String customerId = G.CUSTOMER_ID.getString("CUSTOMER_ID", "x");

        new favoriteAsync().execute(Urls.BASE_URL + Urls.SetUserFavoriteAds, customerId, AdID);

    }

    private class favoriteAsync extends Webservice.setAsFavorite {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String result) {
            String x = result ;
            try {
                JSONObject jsonObject = new JSONObject(result);
                Boolean like = jsonObject.getBoolean("Like");
                if (like){
                    isFavorite();
                    Toast.makeText(AdsActivityDetail.this, "در لیست مورد علاقه ها ثبت شد", Toast.LENGTH_SHORT).show();
                }else {
                    isNotFavorite();
                    Toast.makeText(AdsActivityDetail.this, "از لیست مورد علاقه ها حذف شد", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){}

        }
    }
    private void getFavoriteState() {
        String customerId = G.CUSTOMER_ID.getString("CUSTOMER_ID", "x");
        new getFavoriteAsync().execute(Urls.BASE_URL + Urls.GetUserFavoriteAds, customerId, AdID);

    }
    private class getFavoriteAsync extends Webservice.setAsFavorite {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String result) {
            String x = result ;
            try {
                JSONObject jsonObject = new JSONObject(result);
                Boolean like = jsonObject.getBoolean("Like");
                if (like){
                    isFavorite();
                }else {
                    isNotFavorite();
                }
            }catch (Exception e){}

        }
    }
    private void isFavorite(){
        btnLike.setImageResource(R.mipmap.ic_like);
    }
    private void isNotFavorite(){
        btnLike.setImageResource(R.mipmap.ic_dislike);

    }


    private Boolean isMyAd(int senderID) {
        boolean isMine = false;
        String myId = G.CUSTOMER_ID.getString("CUSTOMER_ID", "");
        if (String.valueOf(senderID).equals(myId)) {
            demoView();
            isMine = true;
        }
        return isMine;
    }

    private void cancelReserveView() {
        fl_reserve.setVisibility(View.GONE);
        fl_reserved.setVisibility(View.VISIBLE);

    }

    private void reserveView() {
        fl_reserve.setVisibility(View.VISIBLE);
        fl_reserved.setVisibility(View.GONE);
    }

    private void demoView() {
        fl_reserve.setVisibility(View.GONE);
        fl_reserved.setVisibility(View.GONE);
        demo.setVisibility(View.VISIBLE);
        ad_state.setVisibility(View.VISIBLE);
        ad_state.setText("این آگهی توسط شما ثبت شده است");
    }

    public void runRentService() {
        Boolean isRegistered = G.IS_REGISTERED.getBoolean("IS_REGISTERED", false);
        if (isRegistered) {
            if (AdsShift1_ID != 0) {
                String cellPhone = G.CUSTOMER_PHONE.getString("CUSTOMER_PHONE", "x");
                new newRentAsync().execute(Urls.BASE_URL + Urls.RENT_ADS_SHIFT, cellPhone, String.valueOf(AdsShift1_ID));
            } else {
                Toast.makeText(G.CONTEXT, "یک زمان را انتخاب کنید", Toast.LENGTH_SHORT).show();
            }
        } else {
            Intent intent = new Intent(AdsActivityDetail.this, LoginActivity.class);
            intent.putExtra("base", "AdsInner");
            AdsActivityDetail.this.startActivity(intent);
        }

    }

    public void runUnRentService() {
        Boolean isRegistered = G.IS_REGISTERED.getBoolean("IS_REGISTERED", false);
        if (isRegistered) {
            if (AdsShift1_ID != 0) {
                String cellPhone = G.CUSTOMER_PHONE.getString("CUSTOMER_PHONE", "x");
                new newUnRentAsync().execute(Urls.BASE_URL + Urls.UNRENT_ADS_SHIFT, cellPhone, String.valueOf(rentID));
            } else {
                Toast.makeText(G.CONTEXT, "یک زمان را انتخاب کنید", Toast.LENGTH_SHORT).show();
            }
        } else {
            Intent intent = new Intent(AdsActivityDetail.this, LoginActivity.class);
            intent.putExtra("base", "AdsInner");
            AdsActivityDetail.this.startActivity(intent);
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private class newRentAsync extends Webservice.rentAds {
        @Override
        protected void onPreExecute() {
//            activityRegister_btn_register.setVisibility(View.GONE);
//            loading.smoothToShow();
//            loading.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String result) {
//            loading.smoothToHide();
            String x = result;

            try {
                JSONObject jsonObject = new JSONObject(result);
                String Message = jsonObject.getString("Message");
                Toast.makeText(AdsActivityDetail.this, Message, Toast.LENGTH_SHORT).show();

                Intent intent = getIntent();
                finish();
                startActivity(intent);

            } catch (Exception e) {
            }
        }
    }

    private class newUnRentAsync extends Webservice.unrentAds {
        @Override
        protected void onPreExecute() {
//            activityRegister_btn_register.setVisibility(View.GONE);
//            loading.smoothToShow();
//            loading.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String result) {
//            loading.smoothToHide();
            String x = result;

            try {
                JSONObject jsonObject = new JSONObject(result);
                String Message = jsonObject.getString("Message");
                Toast.makeText(AdsActivityDetail.this, Message, Toast.LENGTH_SHORT).show();

                Intent intent = getIntent();
                finish();
                startActivity(intent);

            } catch (Exception e) {
            }

        }
    }

    public static void showToast(final String message) {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                JSONObject object = null;
                try {
                    object = new JSONObject(message);
                    String result = object.getString("Message");
                    Toast.makeText(G.CONTEXT, result, Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {


                }

            }
        });

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
//    String location = mEditText_Search.getText().toString();
//    List<Address> mList_address = null;
//
//    if (location != null || !location.equals("")) {
//        Geocoder geocoder = new Geocoder(this);
//        try {
//            mList_address = geocoder.getFromLocationName(location, 1);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            android.location.Address address = mList_address.get(0);
//            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
//            if (marker != null) {
//                marker.remove();
//            }
//
//            marker = mMap.addSelectedMarker(new MarkerOptions().position(latLng).title("Marker"));
//            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//
//            mLatLng_Marker = marker.getPosition();
//        } catch (Exception e) {
//            Toast.makeText(Activity_Map.this, "منطقه مورد نظر یافت نشد!", Toast.LENGTH_SHORT).show();
//        }
//
//    }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
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


//        LOCATION = new LatLng(35.6892, 51.3890);
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LOCATION, 14));
//
//        MarkerOptions marker = new MarkerOptions().position(new LatLng(LOCATION.latitude, LOCATION.longitude));
//        marker.icon(BitmapDescriptorFactory.fromBitmap(markerCreator(R.mipmap.ic_marker)));
//        googleMap.addSelectedMarker(marker).showInfoWindow();


        LOCATION = new LatLng(Lat, Lng);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LOCATION, 14));

        MarkerOptions marker = new MarkerOptions().position(new LatLng(LOCATION.latitude, LOCATION.longitude));
        marker.icon(BitmapDescriptorFactory.fromBitmap(markerCreator(R.mipmap.ic_marker)));

//        MarkerOptions marker1 = new MarkerOptions().position(new LatLng(LOCATION.latitude + 00.002200, LOCATION.longitude + 00.002200));
//        marker1.icon(BitmapDescriptorFactory.fromBitmap(markerCreator(R.mipmap.ic_marker)));
//        googleMap.addSelectedMarker(marker1).showInfoWindow();
//
//        MarkerOptions marker2 = new MarkerOptions().position(new LatLng(LOCATION.latitude - 00.002200, LOCATION.longitude - 00.001150));
//        marker2.icon(BitmapDescriptorFactory.fromBitmap(markerCreator(R.mipmap.ic_marker)));
//        googleMap.addSelectedMarker(marker2).showInfoWindow();
//
//        MarkerOptions marker3 = new MarkerOptions().position(new LatLng(LOCATION.latitude - 00.001150, LOCATION.longitude - 00.002200));
//        marker3.icon(BitmapDescriptorFactory.fromBitmap(markerCreator(R.mipmap.ic_marker)));
//        googleMap.addSelectedMarker(marker3).showInfoWindow();
//
//        MarkerOptions marker4 = new MarkerOptions().position(new LatLng(LOCATION.latitude + 00.001180, LOCATION.longitude - 00.002200));
//        marker4.icon(BitmapDescriptorFactory.fromBitmap(markerCreator(R.mipmap.ic_marker)));
//        googleMap.addSelectedMarker(marker4).showInfoWindow();
//
//        MarkerOptions marker5 = new MarkerOptions().position(new LatLng(LOCATION.latitude + 00.001100, LOCATION.longitude + 00.003300));
//        marker5.icon(BitmapDescriptorFactory.fromBitmap(markerCreator(R.mipmap.ic_marker)));
//        googleMap.addSelectedMarker(marker5).showInfoWindow();
//
//        MarkerOptions marker6 = new MarkerOptions().position(new LatLng(LOCATION.latitude - 00.000880, LOCATION.longitude - 00.005580));
//        marker6.icon(BitmapDescriptorFactory.fromBitmap(markerCreator(R.mipmap.ic_marker)));
//        googleMap.addSelectedMarker(marker6).showInfoWindow();

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
//        LOCATION = new LatLng(G.defaultGlat, G.defaultGlng);
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LOCATION, 14));


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
//
//        double latitude = location.getLatitude();
//        double longitude = location.getLongitude();
//        float accuracy = location.getAccuracy();
//
//        if (positionMarker != null) {
//            positionMarker.remove();
//        }
//
//        if (accuracyCircle != null) {
//            accuracyCircle.remove();
//        }
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

    @Override
    public void onClick(View view) {

    }

    private void sendRequest(String EstateTypeID) {

        String sessionId = G.AUTHENTICATIONS_SESSION.getString("SESSION", "x");
        String userToken = G.AUTHENTICATIONS_TOKEN.getString("TOKEN", "x");
        String customerId = G.CUSTOMER_ID.getString("CUSTOMER_ID", "x");
        String cellPhone = G.CUSTOMER_PHONE.getString("CUSTOMER_PHONE", "x");
        String typeId = String.valueOf(EstateTypeID);

//        new resendAsync().execute(Urls.BASE_URL + Urls.GET_NEAR_STATES, String.valueOf(G.defaultGlat), String.valueOf(G.defaultGlng), typeId);
        new resendAsync().execute(Urls.BASE_URL + Urls.GET_ADS_DETAILS, EstateTypeID);


    }

    private class resendAsync extends Webservice.getAdsDetails {
        @Override
        protected void onPreExecute() {
            arrayList.clear();
        }

        @Override
        protected void onPostExecute(final String result) {
            try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject mainObject = jsonObject.getJSONObject("Ads");

                    String ID = mainObject.getString("ID");
                try {
                    AdID = mainObject.getString("ID");

                    String Name = mainObject.getString("Name");
                    int Capacity = mainObject.getInt("Capacity");
                    String Address = mainObject.getString("Address");
                    String Location = mainObject.getString("Location");
                    String CreateTime = mainObject.getString("CreateTime");
                    int ClassSize = mainObject.getInt("ClassSize");
                    String Email = mainObject.getString("Email");
                    PhoneNumber = mainObject.getString("PhoneNumber");
                    Lat = mainObject.getDouble("Lat");
                    Lng = mainObject.getDouble("Lng");
                    int EstateTypeID = mainObject.getInt("EstateTypeID");


                    latitude = String.valueOf(Lat) ;
                    longtitude = String.valueOf(Lng) ;
                    LOCATION = new LatLng(Lat, Lng);
                    marker1 = new MarkerOptions().position(new LatLng(LOCATION.latitude, LOCATION.longitude));
                    marker1.icon(BitmapDescriptorFactory.fromBitmap(markerCreator(R.mipmap.ic_marker)));
                    mMap.addMarker(marker1).showInfoWindow();
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LOCATION, 14));

                    SenderID = mainObject.getInt("UserID");
                    isMyAd(SenderID);

                    name.setText(Name);
                    capacity.setText(String.valueOf(Capacity) + " نفر ");

                    String[] separated = CreateTime.split("T");
                    StringTokenizer st = new StringTokenizer(separated[0], "-");
                    String year = st.nextToken();
                    String month = st.nextToken();
                    String day = st.nextToken();

                    String date = Utilities1.getCurrentShamsidate(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
                    create_date.setText(date);
                    location.setText(Address);
                    class_size.setText(String.valueOf(ClassSize) + " متر مربع ");

                    option_arrayList.clear();
                    JSONArray AdsOption = jsonObject.getJSONArray("AdsOptions");
                    for (int i = 0; i < AdsOption.length(); i++) {
                        JSONObject AdsOptions1 = AdsOption.getJSONObject(i);
                        int AdsOption1_id = AdsOptions1.getInt("Id");
                        String AdsOption1_name = AdsOptions1.getString("Name");
                        String AdsOption1_Pname = AdsOptions1.getString("PersianName");
                        boolean AdsOption1_hasOption = AdsOptions1.getBoolean("HasOption");

                        Struct struct = new Struct();
                        struct.strItemId = String.valueOf(AdsOption1_id);
                        struct.strItemText = String.valueOf(AdsOption1_name);
                        struct.strItemTitle = String.valueOf(AdsOption1_Pname);
                        if (AdsOption1_hasOption) {
                            struct.isEnabled = true;
                            option_arrayList.add(struct);
                        } else {
                            struct.isEnabled = false;
                        }
                    }
                    option_adapter_recycler.notifyDataSetChanged();


//////////////////////////////////////////////////////

                    JSONArray AdsShift = jsonObject.getJSONArray("AdsShift");
                    arrayList.clear();

                    for (int i = 0; i < AdsShift.length(); i++) {
                        AdsModel struct = new AdsModel();

                        JSONObject AdsShift1 = AdsShift.getJSONObject(i);
                        int AdsShift1_ID = AdsShift1.getInt("ID");
                        String AdsShift1_FromTime = AdsShift1.getString("FromTime");
                        String AdsShift1_ToTime = AdsShift1.getString("ToTime");
                        String AdsShift1_Date = AdsShift1.getString("Date");
                        int AdsShift1_Fee = AdsShift1.getInt("Fee");
                        int AdsShift1_FeeAfterOff = AdsShift1.getInt("FeeAfterOff");
                        int AdsShift1_Off = AdsShift1.getInt("Off");
                        boolean AdsShift1_IsReserve = AdsShift1.getBoolean("IsReserve");
                        int AdsShift1_AdsId = AdsShift1.getInt("AdsId");
                        try {
                            int AdsShift1_UserID = AdsShift1.getInt("UserID");
                            struct.AdsShift1_UserId = AdsShift1_UserID;
                        } catch (Exception e) {
                        }

                        try {
                            int AdsShift1_RentID = AdsShift1.getInt("RentId");
                            struct.AdsShift1_RentId = AdsShift1_RentID;
                        } catch (Exception e) {
                        }

                        struct.AdsShift1_ID = AdsShift1_ID;
                        struct.AdsShift1_FromTime = AdsShift1_FromTime;
                        struct.AdsShift1_ToTime = AdsShift1_ToTime;
                        struct.AdsShift1_Date = AdsShift1_Date;
                        struct.AdsShift1_Fee = AdsShift1_Fee;
                        struct.AdsShift1_FeeAfterOff = AdsShift1_FeeAfterOff;
                        struct.AdsShift1_Off = AdsShift1_Off;
                        struct.AdsShift1_IsReserve = AdsShift1_IsReserve;
                        struct.AdsShift1_AdsId = AdsShift1_AdsId;

                        arrayList.add(struct);
                    }

                    adapter_recycler.notifyDataSetChanged();


                } catch (Exception e) {
                }
            } catch (JSONException e) {
            }
        }
    }

    private void resizeView(FrameLayout layout, int cardNo, int defaultHeight) {
        ViewGroup.LayoutParams params = layout.getLayoutParams();

        int size = cardNo * defaultHeight;
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, getResources().getDisplayMetrics());

        params.height = height;
//        params.width = 100;
        layout.setLayoutParams(params);
    }

    private void payRequest() {

        String sessionId = G.AUTHENTICATIONS_SESSION.getString("SESSION", "x");
        String userToken = G.AUTHENTICATIONS_TOKEN.getString("TOKEN", "x");
        String customerId = G.CUSTOMER_ID.getString("CUSTOMER_ID", "x");
        String cellPhone = G.CUSTOMER_PHONE.getString("CUSTOMER_PHONE", "x");
        String email = G.CUSTOMER_EMAIL.getString("CUSTOMER_EMAIL", "x");
        String typeId = String.valueOf(EstateTypeID);


        new payAsync().execute(Urls.BASE_URL2 + Urls.PAY, "100", "test", cellPhone, email);


    }

    private class payAsync extends Webservice.payRequest {
        @Override
        protected void onPreExecute() {
            arrayList.clear();
        }

        @Override
        protected void onPostExecute(final String result) {
            String x = result;

            web.setVisibility(View.VISIBLE);

            progDailog = ProgressDialog.show(activity, "در حال بارگذاری...", "لطفا صبور باشید!", true);
            progDailog.setCancelable(false);


            web.setInitialScale(1);
            web.getSettings().setLoadWithOverviewMode(true);
            web.getSettings().setUseWideViewPort(true);
            web.getSettings().setJavaScriptEnabled(true);
            web.getSettings().setAllowFileAccess(true);
            web.getSettings().setAllowContentAccess(true);
            web.getSettings().setPluginState(WebSettings.PluginState.ON);
            web.setScrollbarFadingEnabled(false);
            web.addJavascriptInterface(new MyJavaScriptInterface(G.CONTEXT), "HtmlViewer");
            web.setWebViewClient(new WebViewClient() {

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    progDailog.show();
                    view.loadUrl(url);

                    return false;
                }

                @Override
                public void onPageFinished(WebView view, final String url) {
                    progDailog.dismiss();
                }
            });
            String url = result.replaceAll("^\"|\"$", "");
            web.loadUrl(url);


        }
    }

    class MyJavaScriptInterface {

        private Context ctx;

        MyJavaScriptInterface(Context ctx) {
            this.ctx = ctx;
        }

        @JavascriptInterface
        public void showHTML(String html) {
            System.out.println(html);
            Toast.makeText(G.CONTEXT, html, Toast.LENGTH_SHORT).show();
        }

    }

}
