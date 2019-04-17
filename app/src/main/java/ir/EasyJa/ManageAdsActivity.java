package ir.EasyJa;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.test.mock.MockPackageManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.shawnlin.numberpicker.NumberPicker;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.StringTokenizer;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ManageAdsActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener,
        GoogleMap.OnMapLongClickListener, FragmentNavigation.OnFragmentInteractionListener {

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
    public static ManageAdsActivity activity;
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
    private DrawerLayout drawer;
    private static Adapter_Recycler_Ads adapter_recycler;
    static ArrayList<AdsModel> arrayList = new ArrayList<>();
    private TabLayout tabLayout;
    private FloatingActionButton list_mode;
    private boolean isGrid = true;
    private LinearLayoutManager manager;
    private int EstateTypeID;
    private TextView list_state;
    private ArrayList<String> imageArray = new ArrayList<>();

    @Override
    protected void onResume() {
        super.onResume();
        if (G.ManageAdsNeedToRefresh) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);

            G.ManageAdsNeedToRefresh = false;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_ads);

//        // Navigation Drawer
//        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        Fragment squadFragment = new FragmentNavigation();
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.nav_view, squadFragment, null);
//        fragmentTransaction.commit();

        //Toolbar
        ImageView btnNavigation = (ImageView) findViewById(R.id.btnNavigation);
        btnNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.RIGHT);
            }
        });
        btnNavigation.setVisibility(View.GONE);


        toolbar_main_tv_header = (TextView) findViewById(R.id.toolbar_main_tv_header);
        toolbar_main_tv_header.setText("مدیریت کلاسها");


        mPermission = new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.ACCESS_FINE_LOCATION,};
        activity = ManageAdsActivity.this;

        // Views
        loading = (AVLoadingIndicatorView) findViewById(R.id.loading);
        list_state = (TextView) findViewById(R.id.list_state);

        sendRequest();



        final LinearLayout list_layout = (LinearLayout) findViewById(R.id.list_layout);
        final RecyclerView list = (RecyclerView) findViewById(R.id.list);
        manager = new LinearLayoutManager(G.CONTEXT, LinearLayoutManager.VERTICAL, false);
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
                Intent intent = new Intent(ManageAdsActivity.this, ManageAds.class);
                intent.putExtra("ID", String.valueOf(arrayList.get(position).ID));
                ManageAdsActivity.this.startActivity(intent);
            }

            @Override
            public void onEdit(int position) {

            }
        }, 1, isGrid);
        list.setAdapter(adapter_recycler);
        list.setLayoutManager(manager);

    }

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void listStateCheck() {
        if (arrayList.size() < 1) {
            list_state.setVisibility(View.VISIBLE);
        } else {
            list_state.setVisibility(View.GONE);

        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public static class verifyAsync extends Webservice.verifyClass {
        @Override
        protected void onPreExecute() {
            dialogVerify.dismiss();
        }

        @Override
        protected void onPostExecute(final String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                Boolean verifyResult = jsonObject.getBoolean("isActivationCodeValid");
                if (verifyResult) {
                    dialogSuccess_txt_memberCode.setText(memberCode);
                    dialogSuccess.show();
                } else {
                    Toast.makeText(G.CONTEXT, "خطا در روند ثبت نام", Toast.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendRequest() {

        String cellphone = G.CUSTOMER_MOBILE.getString("CUSTOMER_MOBILE", "0");
        new resendAsync().execute(Urls.BASE_URL + Urls.GET_USER_ADS, cellphone);


    }

    private class resendAsync extends Webservice.getAds {
        @Override
        protected void onPreExecute() {
            arrayList.clear();
            loading.setVisibility(View.VISIBLE);
            loading.smoothToShow();
        }

        @Override
        protected void onPostExecute(final String result) {
            loading.smoothToHide();
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    AdsModel struct = new AdsModel();
                    JSONObject Object = jsonArray.getJSONObject(i);
                    JSONObject mainObject = Object.getJSONObject("Ads");

                    struct.object = Object;

                    String ID = mainObject.getString("ID");
                    String Name = mainObject.getString("Name");
                    int Capacity = mainObject.getInt("Capacity");
                    String Address = mainObject.getString("Address");
                    String Location = mainObject.getString("Location");
                    String CreateTime = mainObject.getString("CreateTime");

                    int ClassSize = mainObject.getInt("ClassSize");
                    String Email = mainObject.getString("Email");
                    String PhoneNumber = mainObject.getString("PhoneNumber");
                    double Lat = mainObject.getDouble("Lat");
                    double Lng = mainObject.getDouble("Lng");
                    int EstateTypeID = mainObject.getInt("EstateTypeID");
//                    int UserID = mainObject.getInt("UserID");

//                    LOCATION = new LatLng(Lat, Lng);
//                    MarkerOptions marker1 = new MarkerOptions().position(new LatLng(LOCATION.latitude, LOCATION.longitude));
//                    marker1.icon(BitmapDescriptorFactory.fromBitmap(markerCreator(R.mipmap.ic_marker)));
//                    mMap.addSelectedMarker(marker1).showInfoWindow();
//
//                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LOCATION, 16));


                    JSONArray AdsOption = Object.getJSONArray("AdsOptions");

                    JSONObject AdsOptions1 = AdsOption.getJSONObject(0);
                    int AdsOption1_id = AdsOptions1.getInt("Id");
                    String AdsOption1_name = AdsOptions1.getString("Name");
                    String AdsOption1_Pname = AdsOptions1.getString("PersianName");
                    boolean AdsOption1_hasOption = AdsOptions1.getBoolean("HasOption");

                    JSONObject AdsOptions2 = AdsOption.getJSONObject(1);
                    int AdsOption2_id = AdsOptions2.getInt("Id");
                    String AdsOption2_name = AdsOptions2.getString("Name");
                    String AdsOption2_Pname = AdsOptions2.getString("PersianName");
                    boolean AdsOption2_hasOption = AdsOptions2.getBoolean("HasOption");

                    JSONObject AdsOptions3 = AdsOption.getJSONObject(2);
                    int AdsOption3_id = AdsOptions3.getInt("Id");
                    String AdsOption3_name = AdsOptions3.getString("Name");
                    String AdsOption3_Pname = AdsOptions3.getString("PersianName");
                    boolean AdsOption3_hasOption = AdsOptions3.getBoolean("HasOption");


                    struct.ID = ID;
                    struct.Address = Address;
                    struct.Name = Name;
                    struct.Lat = Lat;
                    struct.Lng = Lng;
                    struct.Capacity = Capacity;
                    struct.Location = Location;

                    String[] separated = CreateTime.split("T");
                    StringTokenizer st = new StringTokenizer(separated[0], "-");
                    String year = st.nextToken();
                    String month = st.nextToken();
                    String day = st.nextToken();

                    struct.CreateTime = Utilities1.getCurrentShamsidate(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
                    struct.ClassSize = ClassSize;
                    struct.Email = Email;
                    struct.PhoneNumber = PhoneNumber;
                    struct.EstateTypeID = EstateTypeID;
//                    struct.UserID = UserID;

                    struct.AdsOption1_id = AdsOption1_id;
                    struct.AdsOption1_name = AdsOption1_name;
                    struct.AdsOption1_hasOption = AdsOption1_hasOption;

                    struct.AdsOption2_id = AdsOption2_id;
                    struct.AdsOption2_name = AdsOption2_name;
                    struct.AdsOption2_hasOption = AdsOption2_hasOption;

                    struct.AdsOption3_id = AdsOption3_id;
                    struct.AdsOption3_name = AdsOption3_name;
                    struct.AdsOption3_hasOption = AdsOption3_hasOption;


//////////////////////////////////////////////////////

                    JSONArray AdsShift = Object.getJSONArray("AdsShift");
                    try {
                        JSONObject AdsShift1 = AdsShift.getJSONObject(0);
                        int AdsShift1_ID = AdsShift1.getInt("ID");
                        String AdsShift1_FromTime = AdsShift1.getString("FromTime");
                        String AdsShift1_ToTime = AdsShift1.getString("ToTime");
                        String AdsShift1_Date = AdsShift1.getString("Date");
                        int AdsShift1_Fee = AdsShift1.getInt("Fee");
                        int AdsShift1_FeeAfterOff = AdsShift1.getInt("FeeAfterOff");
                        int AdsShift1_Off = AdsShift1.getInt("Off");
                        boolean AdsShift1_IsReserve = AdsShift1.getBoolean("IsReserve");
                        int AdsShift1_AdsId = AdsShift1.getInt("AdsId");

                        struct.AdsShift1_ID = AdsShift1_ID;
                        String[] startTime = AdsShift1_FromTime.split(":");
                        String[] endTime = AdsShift1_ToTime.split(":");
                        struct.AdsShift1_FromTime = startTime[0];
                        struct.AdsShift1_ToTime = endTime[0];
                        struct.AdsShift1_Date = AdsShift1_Date;
                        struct.AdsShift1_Fee = AdsShift1_Fee;
                        struct.AdsShift1_FeeAfterOff = AdsShift1_FeeAfterOff;
                        struct.AdsShift1_Off = AdsShift1_Off;
                        struct.AdsShift1_IsReserve = AdsShift1_IsReserve;
                        struct.AdsShift1_AdsId = AdsShift1_AdsId;
                    } catch (Exception e) {
                        struct.AdsShift1_ID = 0;
                        struct.AdsShift1_FromTime = "";
                        struct.AdsShift1_ToTime = "";
                        struct.AdsShift1_Date = "";
                        struct.AdsShift1_Fee = 0;
                        struct.AdsShift1_FeeAfterOff = 0;
                        struct.AdsShift1_Off = 0;
//                        struct.AdsShift2_IsReserve = AdsShift2_IsReserve;
//                        struct.AdsShift2_AdsId = AdsShift2_AdsId;
                    }

                    try {
                        JSONObject AdsShift2 = AdsShift.getJSONObject(1);
                        int AdsShift2_ID = AdsShift2.getInt("ID");
                        String AdsShift2_FromTime = AdsShift2.getString("FromTime");
                        String AdsShift2_ToTime = AdsShift2.getString("ToTime");
                        String AdsShift2_Date = AdsShift2.getString("Date");
                        int AdsShift2_Fee = AdsShift2.getInt("Fee");
                        int AdsShift2_FeeAfterOff = AdsShift2.getInt("FeeAfterOff");
                        int AdsShift2_Off = AdsShift2.getInt("Off");
                        boolean AdsShift2_IsReserve = AdsShift2.getBoolean("IsReserve");
                        int AdsShift2_AdsId = AdsShift2.getInt("AdsId");

                        struct.AdsShift2_ID = AdsShift2_ID;
                        String[] startTime2 = AdsShift2_FromTime.split(":");
                        String[] endTime2 = AdsShift2_ToTime.split(":");
                        struct.AdsShift2_FromTime = startTime2[0];
                        struct.AdsShift2_ToTime = endTime2[0];
                        struct.AdsShift2_Date = AdsShift2_Date;
                        struct.AdsShift2_Fee = AdsShift2_Fee;
                        struct.AdsShift2_FeeAfterOff = AdsShift2_FeeAfterOff;
                        struct.AdsShift2_Off = AdsShift2_Off;
                        struct.AdsShift2_IsReserve = AdsShift2_IsReserve;
                        struct.AdsShift2_AdsId = AdsShift2_AdsId;
                    } catch (Exception e) {
                        struct.AdsShift2_ID = 0;
                        struct.AdsShift2_FromTime = "";
                        struct.AdsShift2_ToTime = "";
                        struct.AdsShift2_Date = "";
                        struct.AdsShift2_Fee = 0;
                        struct.AdsShift2_FeeAfterOff = 0;
                        struct.AdsShift2_Off = 0;
//                        struct.AdsShift2_IsReserve = AdsShift2_IsReserve;
//                        struct.AdsShift2_AdsId = AdsShift2_AdsId;
                    }


                    try {
                        JSONObject AdsShift3 = AdsShift.getJSONObject(2);
                        int AdsShift3_ID = AdsShift3.getInt("ID");
                        String AdsShift3_FromTime = AdsShift3.getString("FromTime");
                        String AdsShift3_ToTime = AdsShift3.getString("ToTime");
                        String AdsShift3_Date = AdsShift3.getString("Date");
                        int AdsShift3_Fee = AdsShift3.getInt("Fee");
                        int AdsShift3_FeeAfterOff = AdsShift3.getInt("FeeAfterOff");
                        int AdsShift3_Off = AdsShift3.getInt("Off");
                        boolean AdsShift3_IsReserve = AdsShift3.getBoolean("IsReserve");
                        int AdsShift3_AdsId = AdsShift3.getInt("AdsId");

                        struct.AdsShift3_ID = AdsShift3_ID;
                        String[] startTime3 = AdsShift3_FromTime.split(":");
                        String[] endTime3 = AdsShift3_ToTime.split(":");
                        struct.AdsShift3_FromTime = startTime3[0];
                        struct.AdsShift3_ToTime = endTime3[0];
                        struct.AdsShift3_Date = AdsShift3_Date;
                        struct.AdsShift3_Fee = AdsShift3_Fee;
                        struct.AdsShift3_FeeAfterOff = AdsShift3_FeeAfterOff;
                        struct.AdsShift3_Off = AdsShift3_Off;
                        struct.AdsShift3_IsReserve = AdsShift3_IsReserve;
                        struct.AdsShift3_AdsId = AdsShift3_AdsId;
                    } catch (Exception e) {
                        struct.AdsShift3_ID = 0;
                        struct.AdsShift3_FromTime = "";
                        struct.AdsShift3_ToTime = "";
                        struct.AdsShift3_Date = "";
                        struct.AdsShift3_Fee = 0;
                        struct.AdsShift3_FeeAfterOff = 0;
                        struct.AdsShift3_Off = 0;
//                        struct.AdsShift2_IsReserve = AdsShift2_IsReserve;
//                        struct.AdsShift2_AdsId = AdsShift2_AdsId;
                    }

                    try {
                        JSONArray adsImage = Object.getJSONArray("AdsImage");
                        for (int j = 0; j < adsImage.length(); j++) {
                            JSONObject imageObject = adsImage.getJSONObject(j);
                            struct.image = imageObject.getString("ImageUrl") + imageObject.getString("ImageName");
                        }

                    } catch (Exception e) {

                    }

                    arrayList.add(struct);
                }
                adapter_recycler.notifyDataSetChanged();
                listStateCheck();
            } catch (JSONException e) {
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

    public static void verificationCodeSetter(String string) {
        dialogVerify_edt_code.setText("");
        dialogVerify_edt_code.setText(string);

        if (dialogVerify_edt_code.getText().toString().length() < 4) {
            dialogVerify_txt_1.setVisibility(View.VISIBLE);
        } else {

            String sessionId = G.AUTHENTICATIONS_SESSION.getString("SESSION", "nothing");
            String userToken = G.AUTHENTICATIONS_TOKEN.getString("TOKEN", "nothing");
            String phone = G.CUSTOMER_PHONE.getString("CUSTOMER_PHONE", "empty");
            String code = dialogVerify_edt_code.getText().toString();


            new verifyAsync().execute(Urls.BASE_URL + Urls.VERIFY_CUSTOMER, sessionId, userToken, customerId, phone, code);
//                    Intent intent = new Intent(RegisterActivity.this, ProjectsActivity.class);
//                    intent.putExtra("name", activityRegister_name.getText().toString() + " " + activityRegister_lastName.getText().toString());
//                    RegisterActivity.this.startActivity(intent);
//                    finish();
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


        LOCATION = new LatLng(35.6892, 51.3890);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LOCATION, 14));

//        MarkerOptions marker = new MarkerOptions().position(new LatLng(LOCATION.latitude, LOCATION.longitude));
//        marker.icon(BitmapDescriptorFactory.fromBitmap(markerCreator(R.mipmap.ic_marker)));
//        googleMap.addSelectedMarker(marker).showInfoWindow();
//
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

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        float accuracy = location.getAccuracy();

        if (positionMarker != null) {
            positionMarker.remove();
        }
//        final MarkerOptions positionMarkerOptions = new MarkerOptions()
//                .position(new LatLng(latitude, longitude))
//                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_car))
//                .anchor(0.5f, 1f);
//        positionMarker = mMap.addSelectedMarker(positionMarkerOptions);

        if (accuracyCircle != null) {
            accuracyCircle.remove();
        }
//        final CircleOptions accuracyCircleOptions = new CircleOptions()
//                .center(new LatLng(latitude, longitude))
//                .radius(accuracy)
//                .fillColor(accuracyFillColor)
//                .strokeColor(accuracyStrokeColor)
//                .strokeWidth(2.0f);
//        accuracyCircle = mMap.addCircle(accuracyCircleOptions);

//        addressText = getCompleteAddressString(latitude, longitude);
//        address.setText(addressText);

        LOCATION = new LatLng(latitude, longitude);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LOCATION, 14));


    }


    @Override
    public void onMapLongClick(LatLng latLng) {
        if (marker != null) {
            marker.remove();
        }
        marker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latLng.latitude, latLng.longitude))
                .draggable(true).visible(true));

        mLatLng_Marker = marker.getPosition();
        lat = String.valueOf(latLng.latitude);
        lng = String.valueOf(latLng.longitude);

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
