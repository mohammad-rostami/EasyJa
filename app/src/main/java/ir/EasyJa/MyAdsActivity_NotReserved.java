package ir.EasyJa;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v13.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import ir.EasyJa.Helper.DataBaseHelper;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MyAdsActivity_NotReserved extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback,
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
    public static MyAdsActivity_NotReserved activity;
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
    private TextView list_state;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserved_ads);

        // Navigation Drawer
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Fragment squadFragment = new FragmentNavigation();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_view, squadFragment, null);
        fragmentTransaction.commit();


        list_state = (TextView) findViewById(R.id.list_state);

        //Toolbar
        ImageView btnNavigation = (ImageView) findViewById(R.id.btnNavigation);
        btnNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar_main_tv_header = (TextView) findViewById(R.id.toolbar_main_tv_header);
        toolbar_main_tv_header.setText("در انتظار اجاره");


        mPermission = new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.ACCESS_FINE_LOCATION,};
        activity = MyAdsActivity_NotReserved.this;

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("فنی"));
        tabLayout.addTab(tabLayout.newTab().setText("کامپیوتر"));
        tabLayout.addTab(tabLayout.newTab().setText("زبان های خارجه"));

//        View root = tabLayout.getChildAt(0);
//        if (root instanceof LinearLayout) {
//            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
//            GradientDrawable drawable = new GradientDrawable();
//            drawable.setColor(getResources().getColor(R.color.lightGray));
//            drawable.setSize(2, 1);
//            ((LinearLayout) root).setDividerPadding(10);
//            ((LinearLayout) root).setDividerDrawable(drawable);
//        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tabLayout.getSelectedTabPosition() == 0) {
//                    Toast.makeText(MainActivity.this, "Tab " + tabLayout.getSelectedTabPosition(), Toast.LENGTH_LONG).show();
                } else if (tabLayout.getSelectedTabPosition() == 1) {
//                    Toast.makeText(MainActivity.this, "Tab " + tabLayout.getSelectedTabPosition(), Toast.LENGTH_LONG).show();
                } else if (tabLayout.getSelectedTabPosition() == 2) {
//                    Toast.makeText(MainActivity.this, "Tab " + tabLayout.getSelectedTabPosition(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // Views
        loading = (AVLoadingIndicatorView) findViewById(R.id.loading);

        list_mode = (FloatingActionButton) findViewById(R.id.list_mode);


        activityRegister_btn_register = (TextView) findViewById(R.id.btn_register);
        activityRegister_btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fieldChecker();
                if (fieldsAreFilled) {
                    try {
                        if (ActivityCompat.checkSelfPermission(MyAdsActivity_NotReserved.this, mPermission[0]) != MockPackageManager.PERMISSION_GRANTED ||
                                ActivityCompat.checkSelfPermission(MyAdsActivity_NotReserved.this, mPermission[1]) != MockPackageManager.PERMISSION_GRANTED ||
                                ActivityCompat.checkSelfPermission(MyAdsActivity_NotReserved.this, mPermission[2]) != MockPackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(MyAdsActivity_NotReserved.this, mPermission, REQUEST_CODE_PERMISSION);

                            // If any permission aboe not allowed by user, this condition will execute every tim, else your else part will work
                        } else {


                            if (G.isNetworkAvailable()) {
                                runSessionService();

                            } else {
                                Toast.makeText(G.CONTEXT, "اتصال با اینترنت قطع شده است", Toast.LENGTH_SHORT).show();
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        sendRequest();

//        adapter_recycler.notifyDataSetChanged();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mFragmentMap = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.activity_map_fr_main);
        mFragmentMap.getMapAsync(this);


        final LinearLayout list_layout = (LinearLayout) findViewById(R.id.list_layout);
        final RecyclerView list = (RecyclerView) findViewById(R.id.list);
        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
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
                Intent intent = new Intent(MyAdsActivity_NotReserved.this, AdsActivity_inner.class);
                intent.putExtra("object", String.valueOf(arrayList.get(position).object));
                MyAdsActivity_NotReserved.this.startActivity(intent);
            }

            @Override
            public void onEdit(int position) {

            }
        }, 5, isGrid);
        list.setAdapter(adapter_recycler);
        list.setLayoutManager(manager);

        list_layout.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;

        list_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isGrid) {
                    isGrid = true;
                    manager = new LinearLayoutManager(G.CONTEXT, LinearLayoutManager.VERTICAL, false);
                    list_layout.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                    list_mode.setImageResource(R.mipmap.ic_location);

                } else {
                    isGrid = false;
                    manager = new LinearLayoutManager(G.CONTEXT, LinearLayoutManager.HORIZONTAL, false);
                    list_layout.getLayoutParams().height = (int) convertDpToPixel(400, G.CONTEXT);
                    list_layout.requestLayout();
                    list_mode.setImageResource(R.mipmap.ic_list);

                }

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
                        Intent intent = new Intent(MyAdsActivity_NotReserved.this, AdsActivity_inner.class);
                        intent.putExtra("object", String.valueOf(arrayList.get(position).object));
                        MyAdsActivity_NotReserved.this.startActivity(intent);
                    }

                    @Override
                    public void onEdit(int position) {

                    }
                }, 1, isGrid);
                list.setLayoutManager(manager);
                list.setAdapter(adapter_recycler);
                adapter_recycler.notifyDataSetChanged();
            }
        });

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
//        Intent intent = new Intent(AdsActivity.this, LoginActivity.class);
//        AdsActivity.this.startActivity(intent);
        finish();
    }

    public void runSessionService() {
//        boolean firstTimeConnection = G.FIRST_TIME_REQUEST.getBoolean("FIRST_TIME_CONNECTION", false);
//        if (!firstTimeConnection) {
//            OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
//
//                @Override
//                public void idsAvailable(String userId, String registrationId) {
//                    oneSignalUserID = userId;
//                    if (registrationId != null)
//                        oneSignalRegistrationID = registrationId;
//
//                    Log.d("userId", oneSignalUserID);
//                    Log.d("registerId", oneSignalRegistrationID);
//                    String uniqueId = G.ANDROID_ID;
//                    String androidVersion = G.ANDROID_VERSION;
//                    String appVersion = G.APP_VERSION;
//                    String deviceName = G.DEVICE_NAME;
//
//                    new AsyncSesssion().execute(Urls.BASE_URL + Urls.REGISTER_SESSION, oneSignalUserID, oneSignalRegistrationID, uniqueId, androidVersion, appVersion, deviceName);
//
//                    G.FIRST_TIME_REQUEST.edit().putBoolean("FIRST_TIME_CONNECTION", true).apply();
//                }
//            });
//        } else {

        if (!lat.equals("0") && !lng.equals("0")) {
            String Name = activityRegister_name.getText().toString();
            String address = activityRegister_address.getText().toString();
            String academyName = activityRegister_academyName.getText().toString();
            String phoneNumber = activityRegister_phone.getText().toString();
            String email = activityRegister_email.getText().toString();
            String key = G.KEY.getString("KEY", "x");
            String cellPhone = G.CUSTOMER_PHONE.getString("CUSTOMER_PHONE", "x");

            new newAsync().execute(Urls.BASE_URL + Urls.REGISTER_BY_MOBILE, cellPhone, email, address, phoneNumber, academyName, lat, lng, key);
        } else {
            Toast.makeText(activity, "موقعیت را در نقشه انتخاب کنید", Toast.LENGTH_SHORT).show();
        }
//        }
    }

    private void fieldChecker() {
        checker(activityRegister_name, 3, "نام");
        checker(activityRegister_academyName, 3, "نام آموزشگاه");
        checker(activityRegister_phone, 7, "شماره تلفن");
        checker(activityRegister_email, 6, "ایمیل");
//        spinnerChecker(stateSpinner, citySpinner);


        if (!checker(activityRegister_name, 3, "نام") ||
                !checker(activityRegister_academyName, 3, "نام آموزشگاه") ||
                !checker(activityRegister_phone, 7, "شماره تلفن") ||
                !checker(activityRegister_email, 6, "ایمیل")
//                ||
//                !spinnerChecker(stateSpinner, citySpinner)
                ) {
            fieldsAreFilled = false;
        } else {
            fieldsAreFilled = true;

        }
    }

    private boolean mailChecker(final String typedText) {
        Boolean result = true;

        if (activityRegister_email.getText().toString().length() < 10) {
            activityRegister_email.setText("");
            activityRegister_email.setHint("ایمیل" + " خود را وارد کنید");
            activityRegister_email.setHintTextColor(getResources().getColor(R.color.white));
            activityRegister_email.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.vibrate));
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    activityRegister_email.setText(typedText);
                    activityRegister_email.setSelection(activityRegister_email.getText().length());
                }
            }, 1000);
            result = false;
        } else if (!activityRegister_email.getText().toString().contains("@")) {
            activityRegister_email.setText("");
            activityRegister_email.setHint("ایمیل وارد شده اشتباه است");
            activityRegister_email.setHintTextColor(getResources().getColor(R.color.white));
            activityRegister_email.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.vibrate));
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    activityRegister_email.setText(typedText);
                    activityRegister_email.setSelection(activityRegister_email.getText().length());
                }
            }, 1000);
            result = false;
        }
        return result;
    }

    private boolean checker(final EditText editText, int limit, String text) {
        Boolean result = true;
        final String typedText = editText.getText().toString();
        if (editText.getText().toString().length() == 0) {
            editText.setText("");
            editText.setHint(text + " خود را وارد کنید");
            editText.setHintTextColor(getResources().getColor(R.color.Red));
            editText.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.vibrate));
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    editText.setText(typedText);
                    editText.setSelection(editText.getText().length());
                }
            }, 1000);
            result = false;
        } else if (editText.getText().toString().length() < limit) {
            editText.setHint(text + " وارد شده اشتباه است");
            editText.setText("");
            editText.setHintTextColor(getResources().getColor(R.color.Red));
            editText.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.vibrate));
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    editText.setText(typedText);
                    editText.setSelection(editText.getText().length());
                }
            }, 1000);
            result = false;
        }
        return result;
    }

    private boolean spinnerChecker(final MaterialSpinner spinner, final MaterialSpinner citySpinner) {
        Boolean result = true;
        try {
            if (spinner.getItems().size() == 0) {
                spinner.setHint("استان را وارد کنید");
                spinner.setHintTextColor(getResources().getColor(R.color.Red));
                spinner.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.vibrate));
                citySpinner.setHint("شهر را وارد کنید");
                citySpinner.setHintTextColor(getResources().getColor(R.color.Red));
                citySpinner.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.vibrate));
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        spinner.setHint("استان");
                        citySpinner.setHint("شهر");
                        spinner.setHintTextColor(getResources().getColor(R.color.lightGray));
                        citySpinner.setHintTextColor(getResources().getColor(R.color.lightGray));

                    }
                }, 1000);
                result = false;
            }
        } catch (Exception e) {
            spinner.setHint("استان را وارد کنید");
            spinner.setHintTextColor(getResources().getColor(R.color.Red));
            spinner.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.vibrate));
            citySpinner.setHint("شهر را وارد کنید");
            citySpinner.setHintTextColor(getResources().getColor(R.color.Red));
            citySpinner.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.vibrate));
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    spinner.setHint("استان");
                    citySpinner.setHint("شهر");
                    spinner.setHintTextColor(getResources().getColor(R.color.lightGray));
                    citySpinner.setHintTextColor(getResources().getColor(R.color.lightGray));

                }
            }, 1000);
            result = false;

        }

        return result;
    }

    private String stateDataBaseChecker(String TableName, String column) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getBaseContext());
        try {
            dataBaseHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        SQLiteDatabase sqld = dataBaseHelper.getWritableDatabase();

        Cursor cursor = sqld.rawQuery("select * from " + TableName + " order by " + column, null);
        String title = null;
        if (cursor.moveToFirst()) {
            do {
                title = cursor.getString(cursor.getColumnIndex(column));
                stateName.add(title);
            } while (cursor.moveToNext());
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stateSpinner.setItems(stateName);
                cityDataBaseChecker("srCityBig", "citname", "citproidint", String.valueOf(8));
                citySpinner.setItems(cityName);

            }
        }, 500);
        sqld.close();
        return title;
    }

    private String cityDataBaseChecker(String TableName, String column, String columnName, String amount) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getBaseContext());
        try {
            dataBaseHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        SQLiteDatabase sqld = dataBaseHelper.getWritableDatabase();

        Cursor cursor = sqld.rawQuery("select * from " + TableName + " where " + columnName + "=\"" + amount + "\" order by " + column, null);
        String title = null;
        cityName.clear();
        if (cursor.moveToFirst()) {
            do {
                title = cursor.getString(cursor.getColumnIndex(column));
                cityName.add(title);
            } while (cursor.moveToNext());
        }

        sqld.close();
        return title;
    }

    private String cityDataBaseCheck(String TableName, String column, String columnName, String amount) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getBaseContext());
        try {
            dataBaseHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        SQLiteDatabase sqld = dataBaseHelper.getWritableDatabase();

        Cursor cursor = sqld.rawQuery("select * from " + TableName + " where " + columnName + "=\"" + amount + "\" order by " + column, null);
        String title = null;
//        cityName.clear();
        if (cursor.moveToFirst()) {
            do {
                title = cursor.getString(cursor.getColumnIndex(column));
//                cityName.add(title);
            } while (cursor.moveToNext());
        }

        sqld.close();
        return title;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    private class newAsync extends Webservice.registerByMobile {
        @Override
        protected void onPreExecute() {
            activityRegister_btn_register.setVisibility(View.GONE);
            loading.smoothToShow();
            loading.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String result) {
            loading.smoothToHide();
            String x = result;


            try {
                JSONObject mainObject = new JSONObject(result);

                try {
                    JSONObject error = mainObject.getJSONObject("Error");
                    String message = error.getString("Message");
                    Toast.makeText(MyAdsActivity_NotReserved.this, message, Toast.LENGTH_SHORT).show();

                } catch (Exception e) {

                }

                try {
                    JSONObject Value = mainObject.getJSONObject("Value");
                    String message = Value.getString("Message");
                    Toast.makeText(MyAdsActivity_NotReserved.this, message, Toast.LENGTH_SHORT).show();
                    G.IS_REGISTERED.edit().putBoolean("IS_REGISTERED", true).apply();

//                    String KeyStr = Value.getString("Key");
//                    G.KEY.edit().putString("KEY", KeyStr).apply();
//                    String phone = dialog_phone_edt_txt.getText().toString();
//                    G.CUSTOMER_PHONE.edit().putString("CUSTOMER_PHONE", phone).apply();

//                    boolean IsRegisterd = Value.getBoolean("IsRegisterd");
//                    if (!IsRegisterd) {
//                        finish();
//                        Intent intent = new Intent(RegisterActivity.this, RegisterActivity.class);
//                        RegisterActivity.this.startActivity(intent);
//                    }else {
//                        G.IS_REGISTERED.edit().putBoolean("IS_REGISTERED", true).apply();
//                    }
//                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {

                }

            } catch (JSONException e) {
            }


//            activityRegister_btn_register.setVisibility(View.VISIBLE);

//            try {
//                JSONObject mainObject = new JSONObject(result);
//                customerId = mainObject.getString("customerId");
//                String customerName = mainObject.getString("firstName") + " " + mainObject.getString("lastName");
//                memberCode = mainObject.getString("memberCode");
//                String customerPhone = mainObject.getString("cellPhone");
//                String customerAddress = mainObject.getString("address");
//                String customerBirth = mainObject.getString("birthDate");
//                String email = mainObject.getString("email");
//                Boolean activeBySms = mainObject.getBoolean("activateBySMS");
//                int customerCity = mainObject.getInt("cityId");
//                int customerGender = mainObject.getInt("gender");
//                G.IS_REGISTERED.edit().putBoolean("IS_REGISTERED", true).apply();
//                G.CUSTOMER_ID.edit().putString("CUSTOMER_ID", customerId).apply();
//                G.CUSTOMER_NAME.edit().putString("CUSTOMER_NAME", customerName).apply();
//                G.CUSTOMER_PHONE.edit().putString("CUSTOMER_PHONE", customerPhone).apply();
//                G.CUSTOMER_GENDER.edit().putInt("CUSTOMER_GENDER", customerGender).apply();
//                G.CUSTOMER_ADDRESS.edit().putString("CUSTOMER_ADDRESS", customerAddress).apply();
//                G.CUSTOMER_CITY.edit().putInt("CUSTOMER_CITY", customerCity).apply();
//                G.CUSTOMER_EMAIL.edit().putString("CUSTOMER_EMAIL", email).apply();
//                G.CUSTOMER_BIRTH.edit().putString("CUSTOMER_BIRTH", customerBirth).apply();
//                G.MEMBER_CODE.edit().putString("MEMBER_CODE", memberCode).apply();
//
//                if (activeBySms) {
//                    dialogVerify_txt_counter.setVisibility(View.VISIBLE);
//                    dialogVerify_btn_resend.setVisibility(View.GONE);
//
//                    countDownTimer.start();
//                    dialogVerify.show();
//                } else {
//                    dialogSuccess_txt_memberCode.setText(memberCode);
//                    dialogSuccess.show();
//                }
//                Log.d("request result", sessionId);
//                Log.d("request result", userToken);

//            } catch (JSONException e) {
//                Log.d("error", "Response: " + result);
//                Log.d("error", "Response: " + e);
//
//
//                try {
//                    JSONObject object = new JSONObject(result);
//                    Toast.makeText(RegisterActivity.this, object.getString("Message"), Toast.LENGTH_SHORT).show();
//
//                } catch (JSONException e1) {
//
//                    Toast.makeText(RegisterActivity.this,"خطا در روند ثبت نام.", Toast.LENGTH_SHORT).show();
//
//                }
            // TODO : نشان دادن پیغام خطا

//            }

        }
    }

    private class AsyncSesssion extends Webservice.sessionConnection {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject sessionData = new JSONObject(result);
                String sessionId = sessionData.getString("SessionId");
                String userToken = sessionData.getString("UserToken");

                G.AUTHENTICATIONS_SESSION.edit().putString("SESSION", sessionId).apply();
                G.AUTHENTICATIONS_TOKEN.edit().putString("TOKEN", userToken).apply();

                String firstName = activityRegister_name.getText().toString();
                String lastName = activityRegister_lastName.getText().toString();
                String phoneNumber = activityRegister_phone.getText().toString();
                String birthDate = activityRegister_birth.getText().toString();
                String mail = activityRegister_email.getText().toString();
                if (activityRegister_email.getText().toString().length() < 2) {
                    mail = "placeHolderMail";
                }
                int gender = 1;
                if (activityRegister_rb_male.isChecked()) {
                    gender = 1;
                } else if (activityRegister_rb_female.isChecked()) {
                    gender = 2;
                }

                new newAsync().execute(Urls.BASE_URL + Urls.REGISTER_CUSTOMER, sessionId, userToken, firstName, lastName, phoneNumber, birthDate, String.valueOf(gender), selectedCity, mail);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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

        MarkerOptions marker = new MarkerOptions().position(new LatLng(LOCATION.latitude, LOCATION.longitude));
        marker.icon(BitmapDescriptorFactory.fromBitmap(markerCreator(R.mipmap.ic_marker)));
        googleMap.addMarker(marker).showInfoWindow();

        MarkerOptions marker1 = new MarkerOptions().position(new LatLng(LOCATION.latitude + 00.002200, LOCATION.longitude + 00.002200));
        marker1.icon(BitmapDescriptorFactory.fromBitmap(markerCreator(R.mipmap.ic_marker)));
        googleMap.addMarker(marker1).showInfoWindow();

        MarkerOptions marker2 = new MarkerOptions().position(new LatLng(LOCATION.latitude - 00.002200, LOCATION.longitude - 00.001150));
        marker2.icon(BitmapDescriptorFactory.fromBitmap(markerCreator(R.mipmap.ic_marker)));
        googleMap.addMarker(marker2).showInfoWindow();

        MarkerOptions marker3 = new MarkerOptions().position(new LatLng(LOCATION.latitude - 00.001150, LOCATION.longitude - 00.002200));
        marker3.icon(BitmapDescriptorFactory.fromBitmap(markerCreator(R.mipmap.ic_marker)));
        googleMap.addMarker(marker3).showInfoWindow();

        MarkerOptions marker4 = new MarkerOptions().position(new LatLng(LOCATION.latitude + 00.001180, LOCATION.longitude - 00.002200));
        marker4.icon(BitmapDescriptorFactory.fromBitmap(markerCreator(R.mipmap.ic_marker)));
        googleMap.addMarker(marker4).showInfoWindow();

        MarkerOptions marker5 = new MarkerOptions().position(new LatLng(LOCATION.latitude + 00.001100, LOCATION.longitude + 00.003300));
        marker5.icon(BitmapDescriptorFactory.fromBitmap(markerCreator(R.mipmap.ic_marker)));
        googleMap.addMarker(marker5).showInfoWindow();

        MarkerOptions marker6 = new MarkerOptions().position(new LatLng(LOCATION.latitude - 00.000880, LOCATION.longitude - 00.005580));
        marker6.icon(BitmapDescriptorFactory.fromBitmap(markerCreator(R.mipmap.ic_marker)));
        googleMap.addMarker(marker6).showInfoWindow();

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

    private void sendRequest() {

        String sessionId = G.AUTHENTICATIONS_SESSION.getString("SESSION", "x");
        String userToken = G.AUTHENTICATIONS_TOKEN.getString("TOKEN", "x");
        String customerId = G.CUSTOMER_ID.getString("CUSTOMER_ID", "x");
        String cellPhone = G.CUSTOMER_PHONE.getString("CUSTOMER_PHONE", "x");
//        String typeId = String.valueOf(EstateTypeID);

//        new resendAsync().execute(Urls.BASE_URL + Urls.GET_NEAR_STATES, String.valueOf(G.defaultGlat), String.valueOf(G.defaultGlng), typeId);
        new resendAsync().execute(Urls.BASE_URL + Urls.GET_USER_ADS_SHIFT_STATUS, cellPhone);


    }

    private class resendAsync extends Webservice.getMyAds {
        @Override
        protected void onPreExecute() {
            arrayList.clear();
            loading.setVisibility(View.VISIBLE);
            loading.smoothToShow();
        }

        @Override
        protected void onPostExecute(final String result) {
            loading.smoothToHide();
            String x = result;

            try {
                JSONObject jsonObject = new JSONObject(result);
//                String imageUrl = jsonObject.getString("ImageAddress");
                JSONArray jsonArray = jsonObject.getJSONArray("NotReserved");
                for (int i = 0; i < jsonArray.length(); i++) {
                    AdsModel struct = new AdsModel();
                    JSONObject AdsShift1 = jsonArray.getJSONObject(i);
                    int AdsShift1_ID = AdsShift1.getInt("ID");
                    String AdsShift1_FromTime = AdsShift1.getString("FromTime");
                    String AdsShift1_ToTime = AdsShift1.getString("ToTime");
                    String AdsShift1_Date = AdsShift1.getString("Date");
                    int AdsShift1_Fee = AdsShift1.getInt("Fee");
                    int AdsShift1_FeeAfterOff = AdsShift1.getInt("FeeAfterOff");
                    int AdsShift1_Off = AdsShift1.getInt("Off");
                    int AdsShift1_AdsId = AdsShift1.getInt("AdsId");
                    String location = AdsShift1.getString("Address");
                    String name = AdsShift1.getString("Name");
//                    int AdsShift1_RentID = AdsShift1.getInt("RentID");

//                    try {
//                        String image = AdsShift1.getString("ImageUrl");
//                        struct.image = imageUrl + image;
//                    } catch (Exception e) {
//                    }

                    String[] separated = AdsShift1_Date.split("T");
                    StringTokenizer st = new StringTokenizer(separated[0], "-");
                    String year = st.nextToken();
                    String month = st.nextToken();
                    String day = st.nextToken();

                    struct.AdsShift1_Date = Utilities1.getCurrentShamsidate(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));

                    struct.AdsShift1_ID = AdsShift1_ID;
                    struct.AdsShift1_FromTime = AdsShift1_FromTime;
                    struct.AdsShift1_ToTime = AdsShift1_ToTime;
//                    struct.AdsShift1_Date = AdsShift1_Date;
                    struct.AdsShift1_Fee = AdsShift1_Fee;
                    struct.AdsShift1_FeeAfterOff = AdsShift1_FeeAfterOff;
                    struct.AdsShift1_Off = AdsShift1_Off;
                    struct.AdsShift1_AdsId = AdsShift1_AdsId;
                    struct.address = location;
                    struct.name = name;
//                    struct.AdsShift1_RentId = AdsShift1_RentID;

                    arrayList.add(struct);
                }
                adapter_recycler.notifyDataSetChanged();
                listStateCheck();

            } catch (JSONException e) {
            }
        }
    }

    public void listStateCheck() {
        if (arrayList.size() < 1) {
            list_state.setVisibility(View.VISIBLE);
        } else {
            list_state.setVisibility(View.GONE);

        }
    }

}
