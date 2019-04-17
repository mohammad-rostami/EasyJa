package ir.EasyJa;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v13.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.test.mock.MockPackageManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import ir.EasyJa.Helper.DataBaseHelper;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback,
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
    public static RegisterActivity activity;
    private static final int REQUEST_CODE_PERMISSION = 2;

    private String[] mPermission;
    private static GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private static LatLng LOCATION;
    private Marker positionMarker;
    private Circle accuracyCircle;
    private static Marker marker;
    private static LatLng mLatLng_Marker;
    private int accuracyStrokeColor = Color.argb(255, 130, 182, 228);
    private int accuracyFillColor = Color.argb(100, 130, 182, 228);
    private TextView toolbar_main_tv_header;
    private EditText activityRegister_academyName;
    private EditText activityRegister_address;
    private static String lat = "0";
    private static String lng = "0";
    static String from = " ";

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        try {
            Intent intent = getIntent();
            from = intent.getStringExtra("base");
            if (from == null) {
                from = " ";
            }
        } catch (Exception e) {

        }

        toolbar_main_tv_header = (TextView) findViewById(R.id.toolbar_main_tv_header);
        toolbar_main_tv_header.setText("مشخصات کاربری");


//        mPermission = new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.ACCESS_FINE_LOCATION,};
        mPermission = new String[]{ Manifest.permission.ACCESS_FINE_LOCATION,};


        activity = RegisterActivity.this;
        // Views
        loading = (AVLoadingIndicatorView) findViewById(R.id.loading);

        activityRegister_name = (EditText) findViewById(R.id.name);
        activityRegister_academyName = (EditText) findViewById(R.id.academy_name);
        activityRegister_phone = (EditText) findViewById(R.id.phone);
        activityRegister_email = (EditText) findViewById(R.id.email);
        activityRegister_address = (EditText) findViewById(R.id.address);

        activityRegister_btn_register = (TextView) findViewById(R.id.btn_register);
        stateSpinner = (MaterialSpinner) findViewById(R.id.state_spinner);
        citySpinner = (MaterialSpinner) findViewById(R.id.city_spinner);
        stateSpinner.setVerticalScrollBarEnabled(true);
        stateSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFirstTime) {
                    stateDataBaseChecker("srProvince", "proname");
                    isFirstTime = false;
                }
            }
        });

        stateSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                String provinceID = cityDataBaseChecker("srProvince", "_id", "proname", item);
                cityDataBaseChecker("srCityBig", "citname", "citproidint", provinceID);
                citySpinner.setItems(cityName);
            }
        });
        citySpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                selectedCity = cityDataBaseCheck("srCityBig", "_id", "citname", item);
            }
        });


        // Dialog
        dialogSuccess = new Dialog(RegisterActivity.this);
        dialogSuccess.setContentView(R.layout.dialog_credit_code);
        dialogSuccess.setCancelable(false);
//        dialogSuccess.setTitle("Custom Dialog");
        dialogSuccess_txt_memberCode = (TextView) dialogSuccess.findViewById(R.id.memberCode);
        dialogSuccess_btn_register = (TextView) dialogSuccess.findViewById(R.id.register);
        dialogSuccess_btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(RegisterActivity.this, DashBoardActivity.class);
//                RegisterActivity.this.startActivity(intent);
//                finish();

            }
        });

        activityRegister_btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fieldChecker();
                if (fieldsAreFilled) {
                    try {
                        if (ActivityCompat.checkSelfPermission(RegisterActivity.this, mPermission[0]) != MockPackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(RegisterActivity.this, mPermission, REQUEST_CODE_PERMISSION);

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

//                    String firstName = activityRegister_name.getText().toString();
//                    String lastName = activityRegister_lastName.getText().toString();
//                    String phoneNumber = activityRegister_phone.getText().toString();
//                    String birthDate = activityRegister_birth.getText().toString();
//                    int gender = 1;
//                    if (activityRegister_rb_male.isChecked()) {
//                        gender = 1;
//                    } else if (activityRegister_rb_female.isChecked()) {
//                        gender = 2;
//                    }
//
//                    String sessionId = G.AUTHENTICATIONS_SESSION.getString("SESSION", "nothing");
//                    String userToken = G.AUTHENTICATIONS_TOKEN.getString("TOKEN", "nothing");
//                    new newAsync().execute("http://mcapi.ahscoltd.ir/mcapi/RegisterCustomer", sessionId, userToken,
//                            firstName, lastName, phoneNumber, birthDate, String.valueOf(gender), selectedCity);
//                    dialogVerify.show();
                }
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mFragmentMap = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.activity_map_fr_main);
        mFragmentMap.getMapAsync(this);

        ImageView fullscreen = (ImageView)findViewById(R.id.fullscreen);
        fullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,MapActivity.class);
                intent.putExtra("source","register");
                RegisterActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        RegisterActivity.this.startActivity(intent);
        finish();
    }

    public void runSessionService() {

        if (!lat.equals("0") && !lng.equals("0")) {
            String Name = activityRegister_name.getText().toString();
            String address = activityRegister_address.getText().toString();
            String academyName = activityRegister_academyName.getText().toString();
            String phoneNumber = activityRegister_phone.getText().toString();
            String email = activityRegister_email.getText().toString();
            String key = G.KEY.getString("KEY", "x");
            String cellPhone = G.CUSTOMER_PHONE.getString("CUSTOMER_PHONE", "x");

            new newAsync().execute(Urls.BASE_URL + Urls.REGISTER_BY_MOBILE, cellPhone, email, address, phoneNumber, Name, lat, lng, key);
        } else {
            Toast.makeText(activity, "موقعیت را در نقشه انتخاب کنید", Toast.LENGTH_SHORT).show();
        }
    }

    private void fieldChecker() {
        checker(activityRegister_name, 3, "نام");
        checker(activityRegister_phone, 7, "شماره تلفن");
//        checker(activityRegister_email, 6, "ایمیل");
//        spinnerChecker(stateSpinner, citySpinner);
        if (!isValidEmail(activityRegister_email.getText().toString())) {
            final String typedText = activityRegister_email.getText().toString();
            activityRegister_email.setText("");
            activityRegister_email.setHint("ایمیل وارد شده اشتباه است");
            activityRegister_email.setHintTextColor(getResources().getColor(R.color.Red));
            activityRegister_email.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.vibrate));
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    activityRegister_email.setText(typedText);
//                    activityRegister_email.setSelection(activityRegister_email.getText().length());
                }
            }, 1000);
        }

        if (!checker(activityRegister_name, 3, "نام") ||
                !checker(activityRegister_phone, 7, "شماره تلفن") ||
//                !checker(activityRegister_email, 6, "ایمیل")
                !isValidEmail(activityRegister_email.getText().toString())
//                !spinnerChecker(stateSpinner, citySpinner)
                ) {
            fieldsAreFilled = false;
        } else {
            fieldsAreFilled = true;

        }
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
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

    private class newAsync extends Webservice.registerByMobile {
        @Override
        protected void onPreExecute() {
            activityRegister_btn_register.setVisibility(View.GONE);
            loading.smoothToShow();
            loading.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String result) {
//            loading.smoothToHide();
//            String x = result;


            try {
                JSONObject mainObject = new JSONObject(result);

                try {
                    JSONObject error = mainObject.getJSONObject("Error");
                    String message = error.getString("Message");
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();

                } catch (Exception e) {

                }

                try {
                    JSONObject Value = mainObject.getJSONObject("Value");
                    String message = Value.getString("Message");
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                    G.IS_REGISTERED.edit().putBoolean("IS_REGISTERED", true).apply();

                    JSONObject x = mainObject.getJSONObject("Instance");
                    String id = x.getString("Id");
                    String name = x.getString("Name");
                    String mobile = x.getString("Mobile");
                    String phone = x.getString("PhoneNumber");
                    String Email = x.getString("Email");
                    String lat = x.getString("Lat");
                    String lng = x.getString("Lng");
                    String credit = x.getString("Credit");
                    G.CUSTOMER_CREDIT.edit().putString("CUSTOMER_CREDIT", credit).apply();
                    try {
                        String Address = x.getString("Address");
                        G.CUSTOMER_ADDRESS.edit().putString("CUSTOMER_ADDRESS", Address).apply();

                    } catch (Exception e) {

                    }
                    G.CUSTOMER_ID.edit().putString("CUSTOMER_ID", id).apply();
                    G.CUSTOMER_NAME.edit().putString("CUSTOMER_NAME", name).apply();
                    G.CUSTOMER_MOBILE.edit().putString("CUSTOMER_MOBILE", mobile).apply();
                    G.CUSTOMER_PHONE.edit().putString("CUSTOMER_PHONE", phone).apply();
                    G.CUSTOMER_EMAIL.edit().putString("CUSTOMER_EMAIL", Email).apply();
                    G.CUSTOMER_LAT.edit().putString("CUSTOMER_LAT", lat).apply();
                    G.CUSTOMER_LNG.edit().putString("CUSTOMER_LNG", lng).apply();

                    getToken();


//                    if (from.equals("Category")) {
//                        Intent intent = new Intent(RegisterActivity.this, AddNewAds.class);
//                        RegisterActivity.this.startActivity(intent);
//                    } else if (from.equals("AdsInner")) {
//                        Intent intent = new Intent(RegisterActivity.this, AdsActivity_inner.class);
//                        RegisterActivity.this.startActivity(intent);
//                    } else {
//                        Intent intent = new Intent(RegisterActivity.this, RentActivity.class);
//                        RegisterActivity.this.startActivity(intent);
//                    }
////                    if (G.fromRent) {
////                        Intent intent = new Intent(LOGIN_ACTIVITY, AddNewAds.class);
////                        LOGIN_ACTIVITY.startActivity(intent);
////                        G.fromRent = false;
////                        LOGIN_ACTIVITY.finish();
////                    } else {
//                    RegisterActivity.this.finish();
//
////                    getUserCredit();


                } catch (Exception e) {

                }

            } catch (JSONException e) {
            }
        }
    }
    public void getToken() {
        String grant_type = "password";
        String phone = G.CUSTOMER_PHONE.getString("CUSTOMER_PHONE", "");
        String code = G.CUSTOMER_ACTIVATION_CODE.getString("CUSTOMER_ACTIVATION_CODE", "");
        new tokenAsync().execute(Urls.GET_TOKEN, grant_type, phone, code);

    }


    public
    class tokenAsync extends Webservice.token {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(final String result) {
            try {
                JSONObject tokenJson = new JSONObject(result);
                String access_token = tokenJson.getString("access_token");
                String token_type = tokenJson.getString("token_type");

                G.TOKEN.edit().putString("TOKEN", access_token).apply();
                G.TOKEN_TYPE.edit().putString("TOKEN_TYPE", token_type).apply();

                if (from.equals("Category")) {
                    Intent intent = new Intent(RegisterActivity.this, AddNewAds.class);
                    RegisterActivity.this.startActivity(intent);
                } else if (from.equals("AdsInner")) {
                    Intent intent = new Intent(RegisterActivity.this, AdsActivity_inner.class);
                    RegisterActivity.this.startActivity(intent);
                } else {
                    Intent intent = new Intent(RegisterActivity.this, RentActivity.class);
                    RegisterActivity.this.startActivity(intent);
                }
//                    if (G.fromRent) {
//                        Intent intent = new Intent(LOGIN_ACTIVITY, AddNewAds.class);
//                        LOGIN_ACTIVITY.startActivity(intent);
//                        G.fromRent = false;
//                        LOGIN_ACTIVITY.finish();
//                    } else {
                RegisterActivity.this.finish();

//                    getUserCredit();


            } catch (Exception e) {

            }

        }
    }

    public void getUserCredit() {
        String phone = G.CUSTOMER_PHONE.getString("CUSTOMER_PHONE", "nothing");
        new CreditAsync().execute(Urls.BASE_URL + Urls.GET_USER_CREDIT, phone);

    }


    public void getUserDetail() {
        String phone = G.CUSTOMER_PHONE.getString("CUSTOMER_PHONE", "nothing");
        new UserDetailAsync().execute(Urls.BASE_URL + Urls.GET_USER_DETAIL, phone);

    }

    private class CreditAsync extends Webservice.sendCode {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(final String result) {


            try {
                JSONObject mainObject = new JSONObject(result);

                try {
                    JSONObject error = mainObject.getJSONObject("Error");
                    String message = error.getString("Message");
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                }

                try {
                    JSONObject Value = mainObject.getJSONObject("Value");
                    String message = Value.getString("Message");
                    String KeyStr = Value.getString("Value");
                    G.CUSTOMER_CREDIT.edit().putString("CUSTOMER_CREDIT", KeyStr).apply();
                } catch (Exception e) {

                }


                try {
                    JSONObject x = mainObject.getJSONObject("Instance");
                    String id = x.getString("Id");
                    String name = x.getString("Name");
                    String mobile = x.getString("Mobile");
                    String phone = x.getString("PhoneNumber");
                    String Email = x.getString("Email");
                    String lat = x.getString("Lat");
                    String lng = x.getString("Lng");
                    try {
                        String Address = x.getString("Address");
                        G.CUSTOMER_ADDRESS.edit().putString("CUSTOMER_ADDRESS", Address).apply();
                    } catch (Exception e) {

                    }
                    G.CUSTOMER_ID.edit().putString("CUSTOMER_ID", id).apply();
                    G.CUSTOMER_NAME.edit().putString("CUSTOMER_NAME", name).apply();
                    G.CUSTOMER_MOBILE.edit().putString("CUSTOMER_MOBILE", mobile).apply();
                    G.CUSTOMER_PHONE.edit().putString("CUSTOMER_PHONE", phone).apply();
                    G.CUSTOMER_EMAIL.edit().putString("CUSTOMER_EMAIL", Email).apply();
                    G.CUSTOMER_LAT.edit().putString("CUSTOMER_LAT", lat).apply();
                    G.CUSTOMER_LNG.edit().putString("CUSTOMER_LNG", lng).apply();


                    if (from.equals("Category")) {
                        Intent intent = new Intent(RegisterActivity.this, AddNewAds.class);
                        RegisterActivity.this.startActivity(intent);
                    } else if (from.equals("AdsInner")) {
                        Intent intent = new Intent(RegisterActivity.this, AdsActivity_inner.class);
                        RegisterActivity.this.startActivity(intent);
                    } else {
                        Intent intent = new Intent(RegisterActivity.this, RentActivity.class);
                        RegisterActivity.this.startActivity(intent);
                    }
//                    if (G.fromRent) {
//                        Intent intent = new Intent(LOGIN_ACTIVITY, AddNewAds.class);
//                        LOGIN_ACTIVITY.startActivity(intent);
//                        G.fromRent = false;
//                        LOGIN_ACTIVITY.finish();
//                    } else {
                    finish();


                    loading.smoothToHide();
                    loading.setVisibility(View.GONE);


                } catch (Exception e) {

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getUserDetail();

                        }
                    }, 1000);

                }

            } catch (JSONException e) {
            }
        }
    }

    private class UserDetailAsync extends Webservice.sendCode {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(final String result) {

            try {
                JSONObject mainObject = new JSONObject(result);

                try {
                    JSONObject error = mainObject.getJSONObject("Error");
                    String message = error.getString("Message");
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                }

                try {
                    JSONObject Value = mainObject.getJSONObject("Value");
                    String message = Value.getString("Message");
                    String KeyStr = Value.getString("Key");

                    JSONObject x = mainObject.getJSONObject("Instance");
                    String name = x.getString("Name");
                    String mobile = x.getString("Mobile");
                    String phone = x.getString("PhoneNumber");
                    String Email = x.getString("Email");
                    String lat = x.getString("Lat");
                    String lng = x.getString("Lng");
                    try {
                        String Address = x.getString("Address");
                        G.CUSTOMER_ADDRESS.edit().putString("CUSTOMER_ADDRESS", Address).apply();

                    } catch (Exception e) {

                    }
                    G.CUSTOMER_NAME.edit().putString("CUSTOMER_NAME", name).apply();
                    G.CUSTOMER_MOBILE.edit().putString("CUSTOMER_MOBILE", mobile).apply();
                    G.CUSTOMER_PHONE.edit().putString("CUSTOMER_PHONE", phone).apply();
                    G.CUSTOMER_EMAIL.edit().putString("CUSTOMER_EMAIL", Email).apply();
                    G.CUSTOMER_LAT.edit().putString("CUSTOMER_LAT", lat).apply();
                    G.CUSTOMER_LNG.edit().putString("CUSTOMER_LNG", lng).apply();


                    if (from.equals("Category")) {
                        Intent intent = new Intent(RegisterActivity.this, AddNewAds.class);
                        RegisterActivity.this.startActivity(intent);
                    } else if (from.equals("AdsInner")) {
                        Intent intent = new Intent(RegisterActivity.this, AdsActivity_inner.class);
                        RegisterActivity.this.startActivity(intent);
                    } else {
                        Intent intent = new Intent(RegisterActivity.this, RentActivity.class);
                        RegisterActivity.this.startActivity(intent);
                    }
//                    if (G.fromRent) {
//                        Intent intent = new Intent(LOGIN_ACTIVITY, AddNewAds.class);
//                        LOGIN_ACTIVITY.startActivity(intent);
//                        G.fromRent = false;
//                        LOGIN_ACTIVITY.finish();
//                    } else {
                    finish();


                } catch (Exception e) {

                }

            } catch (JSONException e) {
            }
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

    private void sendRequest() {

        String sessionId = G.AUTHENTICATIONS_SESSION.getString("SESSION", "x");
        String userToken = G.AUTHENTICATIONS_TOKEN.getString("TOKEN", "x");
        String customerId = G.CUSTOMER_ID.getString("CUSTOMER_ID", "x");
        String cellPhone = G.CUSTOMER_PHONE.getString("CUSTOMER_PHONE", "x");

        new resendAsync().execute(Urls.BASE_URL + Urls.RESEND_CODE, sessionId, userToken, customerId, cellPhone);


    }

    private class resendAsync extends Webservice.resend {
        @Override
        protected void onPreExecute() {
            activityRegister_btn_register.setVisibility(View.GONE);
            loading.smoothToShow();
            loading.setVisibility(View.VISIBLE);

            dialogVerify_txt_counter.setVisibility(View.VISIBLE);
            dialogVerify_btn_resend.setVisibility(View.GONE);
            countDownTimer.start();

        }

        @Override
        protected void onPostExecute(final String result) {
            loading.smoothToHide();
            activityRegister_btn_register.setVisibility(View.VISIBLE);

            try {
                JSONObject mainObject = new JSONObject(result);
                customerId = mainObject.getString("customerId");
                String customerName = mainObject.getString("firstName");
                String customerLastName = mainObject.getString("lastName");
                memberCode = mainObject.getString("memberCode");
                String customerPhone = mainObject.getString("cellPhone");
                String customerAddress = mainObject.getString("address");
                String customerBirth = mainObject.getString("birthDate");
                String email = mainObject.getString("email");
                Boolean activeBySms = mainObject.getBoolean("activateBySMS");
                int customerCity = mainObject.getInt("cityId");
                int customerGender = mainObject.getInt("gender");
                G.IS_REGISTERED.edit().putBoolean("IS_REGISTERED", true).apply();
                G.CUSTOMER_ID.edit().putString("CUSTOMER_ID", customerId).apply();
                G.CUSTOMER_NAME.edit().putString("CUSTOMER_NAME", customerName).apply();
                G.CUSTOMER_LAST_NAME.edit().putString("CUSTOMER_LAST_NAME", customerLastName).apply();
                G.CUSTOMER_PHONE.edit().putString("CUSTOMER_PHONE", customerPhone).apply();
                G.CUSTOMER_GENDER.edit().putInt("CUSTOMER_GENDER", customerGender).apply();
                G.CUSTOMER_ADDRESS.edit().putString("CUSTOMER_ADDRESS", customerAddress).apply();
                G.CUSTOMER_CITY.edit().putInt("CUSTOMER_CITY", customerCity).apply();
                G.CUSTOMER_EMAIL.edit().putString("CUSTOMER_EMAIL", email).apply();
                G.CUSTOMER_BIRTH.edit().putString("CUSTOMER_BIRTH", customerBirth).apply();

            } catch (JSONException e) {

                Toast.makeText(RegisterActivity.this, result, Toast.LENGTH_SHORT).show();
                // TODO : نشان دادن پیغام خطا

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
    public static void setMarker(LatLng latLng){
        if (marker != null) {
            marker.remove();
        }
        marker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latLng.latitude, latLng.longitude))
                .draggable(true).visible(true));

        mLatLng_Marker = marker.getPosition();
        lat = String.valueOf(latLng.latitude);
        lng = String.valueOf(latLng.longitude);


        LOCATION = new LatLng(latLng.latitude, latLng.longitude);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LOCATION, 14));
    }
}
