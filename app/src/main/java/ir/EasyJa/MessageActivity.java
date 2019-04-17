package ir.EasyJa;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.shawnlin.numberpicker.NumberPicker;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MessageActivity extends AppCompatActivity {

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
    public static MessageActivity activity;
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
    private TextView btn_reserve;
    private TextView btn_cancel_reserve;
    private FrameLayout fl_reserved;
    private FrameLayout fl_reserve;
    private Dialog dialogCancelConfirm;
    private TextView dialogCancelConfirm_cancel;
    private TextView dialogCancelConfirm_return;
    private ImageView call;
    private TextView message;
    private TextView name;
    private TextView mail;
    private EditText phone;
    private EditText description;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);


        name = (TextView) findViewById(R.id.name);
        mail = (TextView) findViewById(R.id.mail);
        phone = (EditText) findViewById(R.id.phone);
        description = (EditText) findViewById(R.id.description);



        message = (TextView) findViewById(R.id.message);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        String nameStr = G.CUSTOMER_NAME.getString("CUSTOMER_NAME", "");
        String mobileStr = G.CUSTOMER_MOBILE.getString("CUSTOMER_MOBILE", "");
        String emailStr = G.CUSTOMER_EMAIL.getString("CUSTOMER_EMAIL", "");

        name.setText(nameStr);
        mail.setText(emailStr);
        phone.setText(mobileStr);



        //Toolbar
        ImageView btnNavigation = (ImageView) findViewById(R.id.btnNavigation);

        btnNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar_main_tv_header = (TextView) findViewById(R.id.toolbar_main_tv_header);
        toolbar_main_tv_header.setText("پست الکترونیک");


        mPermission = new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.ACCESS_FINE_LOCATION,};


        activity = MessageActivity.this;
        // Views
        loading = (AVLoadingIndicatorView) findViewById(R.id.loading);

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
//
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

//            new newAsync().execute(Urls.BASE_URL + Urls.REGISTER_BY_MOBILE, cellPhone, email, address, phoneNumber, academyName, lat, lng, key);
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
            activityRegister_email.setHint("ایمیل" + " را وارد کنید");
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
            editText.setHint(text + " را وارد کنید");
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


}
