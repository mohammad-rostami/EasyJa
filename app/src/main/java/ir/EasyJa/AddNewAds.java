package ir.EasyJa;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.alirezaafkar.sundatepicker.DatePicker;
import com.alirezaafkar.sundatepicker.components.DateItem;
import com.alirezaafkar.sundatepicker.interfaces.DateSetListener;
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
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

import cz.msebera.android.httpclient.Header;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AddNewAds extends AppCompatActivity implements FragmentNavigation.OnFragmentInteractionListener, View.OnClickListener, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, DateSetListener,
        GoogleMap.OnMapLongClickListener {

    private DrawerLayout drawer;
    private TextView time;
    private TextView date;
    private EditText cancel_time;
    private EditText cancel_decreas;
    private EditText capacity;
    private EditText size;
    private TextView time1;
    private TextView time1_1;
    private EditText price1;
    private EditText off1;
    private EditText final_price1;
    private View add1;
    private CardView options2;
    private TextView time2;
    private EditText final_price3;
    private EditText off3;
    private EditText price3;
    private TextView time3_1;
    private TextView time3;
    private CardView options3;
    private View add2;
    private View remove2;
    private View remove3;
    private EditText final_price2;
    private EditText off2;
    private EditText price2;
    private TextView time2_1;
//    private Switch switch_language;
//    private Switch switch_voice;
//    private Switch switch_board;
//    private Switch switch_projector;


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
    private EditText address;
    private EditText location;
    private ImageView img1;
    public ArrayList<Uri> uriArray = new ArrayList();

    final int KeyGallery = 100, ReadExternalRequestCode = 200; // کلید ها برای باز کردن گالری و دریافت دسترسی ها
    String url = Urls.BASE_URL + "Ads/PostImage?AdsId=";
    boolean choseImage = false;
    public static Bitmap mImage;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;
    private int AdsId;
    static ArrayList<Struct> option_arrayList = new ArrayList<>();
    private Adapter_Recycler option_adapter_recycler;
    private AVLoadingIndicatorView options_loading;
    private ArrayList<String> optionsArray = new ArrayList<>();
    private TextView adDate;
    private DatePicker datePicker;
    private DatePicker.Builder builder;
    private Date mDate;
    private java.util.Date selectedDate;
    private TimePickerDialog timePickerDialog;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_ads);

        options_loading = (AVLoadingIndicatorView) findViewById(R.id.options_loading);
        getOptions();

        loading = (AVLoadingIndicatorView) findViewById(R.id.loading);


        // Navigation Drawer
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Fragment squadFragment = new FragmentNavigation();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_view, squadFragment, null);
        fragmentTransaction.commit();


        //Toolbar
        ImageView btnNavigation = (ImageView) findViewById(R.id.btnNavigation);

        btnNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.RIGHT);
            }
        });
        toolbar_main_tv_header = (TextView) findViewById(R.id.toolbar_main_tv_header);
        toolbar_main_tv_header.setText("مشخصات محل مورد اجاره");

        adDate = (TextView) findViewById(R.id.date);

        adDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePicker.Builder builder = new DatePicker
                        .Builder()
                        .theme(R.style.DialogTheme)
                        .future(true);
                mDate = new Date();
                builder.date(mDate.getDay(), mDate.getMonth(), mDate.getYear());
                builder.build(new DateSetListener() {
                    @Override
                    public void onDateSet(int id, @Nullable Calendar calendar, int day, int month, int year) {
                        mDate.setDate(day, month, year);
                        //textView
                        adDate.setText(mDate.getDate());

                        selectedDate = JalaliCalendar.getGregorianDate(adDate.getText().toString());
//                        String jDate = JalaliCalendar.getJalaliDate(dt);

                    }
                }).show(getSupportFragmentManager(), "");
            }
        });

        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                G.selectedImag = 1;
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(AddNewAds.this);
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                G.selectedImag = 2;
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(AddNewAds.this);
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                G.selectedImag = 3;
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(AddNewAds.this);
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                G.selectedImag = 4;
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(AddNewAds.this);
            }
        });

        Intent intent = getIntent();
        choseImage = intent.getBooleanExtra("chose_image", false);
        if (choseImage) {
            if (mImage != null) {
                switch (G.selectedImag) {
                    case 1:
                        img1.setImageBitmap(mImage);
                        break;
                    case 2:
                        img2.setImageBitmap(mImage);
                        break;
                    case 3:
                        img3.setImageBitmap(mImage);
                        break;
                    case 4:
                        img4.setImageBitmap(mImage);
                        break;
                }
                Random r = new Random();
                int i1 = r.nextInt(10000 - 100) + 100;
                File file = persistImage(mImage, String.valueOf(i1));
                uploadImage(file);
//                convertImageToBas64(mImage);
            } else {

                Uri imageUri = intent.getParcelableExtra("URI");
                if (imageUri != null) {
                    switch (G.selectedImag) {
                        case 1:
                            img1.setImageURI(imageUri);
                            break;
                        case 2:
                            img2.setImageURI(imageUri);
                            break;
                        case 3:
                            img3.setImageURI(imageUri);
                            break;
                        case 4:
                            img4.setImageURI(imageUri);
                            break;
                    }

                    if (imageUri != null) {
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (!filePathColumn.equals(null)) {
                            Cursor cursor = getContentResolver().query(imageUri,
                                    filePathColumn, null, null, null);
                            cursor.moveToFirst();
                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            String picturePath = cursor.getString(columnIndex);
                            cursor.close();
                            if (!picturePath.equals(null)) {
                                uploadImage(new File(picturePath));
                            }
                        }
                    }
                } else {
                    Toast.makeText(this, "تصویری برای نمایش ثبت نشده است.", Toast.LENGTH_LONG).show();
                }
            }
        }


        cancel_time = findViewById(R.id.cancel_time);
        cancel_decreas = findViewById(R.id.cancel_decreas);
        capacity = findViewById(R.id.capacity);
        size = findViewById(R.id.size);
        address = findViewById(R.id.address);
        location = findViewById(R.id.location);

        time1 = findViewById(R.id.time1);
        time1_1 = findViewById(R.id.time1_1);
        price1 = findViewById(R.id.price1);
        off1 = findViewById(R.id.off1);
        final_price1 = findViewById(R.id.final_price1);
        add1 = findViewById(R.id.add1);

        options2 = findViewById(R.id.options2);
        time2 = findViewById(R.id.time2);
        time2_1 = findViewById(R.id.time2_1);
        price2 = findViewById(R.id.price2);
        off2 = findViewById(R.id.off2);
        final_price2 = findViewById(R.id.final_price2);
        add2 = findViewById(R.id.add2);
        remove2 = findViewById(R.id.remove2);
        remove3 = findViewById(R.id.remove3);
        add2 = findViewById(R.id.add2);

        options3 = findViewById(R.id.options3);
        time3 = findViewById(R.id.time3);
        time3_1 = findViewById(R.id.time3_3);
        price3 = findViewById(R.id.price3);
        off3 = findViewById(R.id.off3);
        final_price3 = findViewById(R.id.final_price3);

        off1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    int price = Integer.parseInt(price1.getText().toString());
                    int off = Integer.parseInt(off1.getText().toString());

                    formatNumber(final_price1, price, off);
                } catch (Exception e) {
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        off2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    int price = Integer.parseInt(price2.getText().toString());
                    int off = Integer.parseInt(off2.getText().toString());

                    formatNumber(final_price2, price, off);
                } catch (Exception e) {
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        off3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                try {
                    int price = Integer.parseInt(price3.getText().toString());
                    int off = Integer.parseInt(off3.getText().toString());

                    formatNumber(final_price3, price, off);
                } catch (Exception e) {
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        price1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    int price = Integer.parseInt(price1.getText().toString());
                    int off;
                    if (off1.getText().toString().equals("")) {
                        off = 0;
                    } else {
                        off = Integer.parseInt(off1.getText().toString());
                    }

                    formatNumber(final_price1, price, off);

                } catch (Exception e) {
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        price2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    int price = Integer.parseInt(price2.getText().toString());
                    int off;
                    if (off2.getText().toString().equals("")) {
                        off = 0;
                    } else {
                        off = Integer.parseInt(off2.getText().toString());
                    }
                    formatNumber(final_price2, price, off);
                } catch (Exception e) {
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        price3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                try {
                    int price = Integer.parseInt(price3.getText().toString());
                    int off;
                    if (off3.getText().toString().equals("")) {
                        off = 0;
                    } else {
                        off = Integer.parseInt(off3.getText().toString());
                    }

                    formatNumber(final_price3, price, off);
                } catch (Exception e) {
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                options2.setVisibility(View.VISIBLE);
                add1.setVisibility(View.INVISIBLE);
            }
        });
        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                options3.setVisibility(View.VISIBLE);
                add2.setVisibility(View.INVISIBLE);
            }
        });
        remove2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                options2.setVisibility(View.GONE);
                add1.setVisibility(View.VISIBLE);
            }
        });
        remove3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                options3.setVisibility(View.GONE);
                add2.setVisibility(View.VISIBLE);
            }
        });

        time1_1.setEnabled(false);
        time2_1.setEnabled(false);
        time3_1.setEnabled(false);

        time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHourPicker(1);

            }
        });
        time1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHourPicker(2);

            }
        });
        time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHourPicker(3);

            }
        });
        time2_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHourPicker(4);

            }
        });
        time3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHourPicker(5);

            }
        });
        time3_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHourPicker(6);

            }
        });

        activityRegister_btn_register = (TextView) findViewById(R.id.btn_register);


        activityRegister_btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fieldChecker();
                if (fieldsAreFilled) {
//                    try {
//                        if (ActivityCompat.checkSelfPermission(AddNewAds.this, mPermission[0]) != MockPackageManager.PERMISSION_GRANTED ||
//                                ActivityCompat.checkSelfPermission(AddNewAds.this, mPermission[1]) != MockPackageManager.PERMISSION_GRANTED ||
//                                ActivityCompat.checkSelfPermission(AddNewAds.this, mPermission[2]) != MockPackageManager.PERMISSION_GRANTED) {
//                            ActivityCompat.requestPermissions(AddNewAds.this, mPermission, REQUEST_CODE_PERMISSION);
//
//                            // If any permission aboe not allowed by user, this condition will execute every tim, else your else part will work
//                        } else {


//                            if (G.isNetworkAvailable()) {
                    runSessionService();

//                            } else {
//                                Toast.makeText(G.CONTEXT, "اتصال با اینترنت قطع شده است", Toast.LENGTH_SHORT).show();
//                            }

//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                }
            }
        });

        RecyclerView option_list = (RecyclerView) findViewById(R.id.option_list);
        LinearLayoutManager option_manager = new LinearLayoutManager(G.CONTEXT, LinearLayoutManager.VERTICAL, false);
        option_adapter_recycler = new Adapter_Recycler(getApplicationContext(), option_arrayList, new OnItemListener() {
            @Override
            public void onItemSelect(int position) {

                try {
                    optionsArray.add(option_arrayList.get(position).strItemId);

                    for (int i = 0; i < optionsArray.size(); i++) {
                        Log.d("options item", optionsArray.get(i));
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onItemDelete(int position) {
                try {
                    for (int i = 0; i < optionsArray.size(); i++) {
                        String current = optionsArray.get(i);
                        String selected = option_arrayList.get(position).strItemId;
                        if (optionsArray.get(i).equals(option_arrayList.get(position).strItemId)) {
                            optionsArray.remove(i);
                        }
                    }
                    for (int i = 0; i < optionsArray.size(); i++) {
                        Log.d("options item", optionsArray.get(i));
                    }
                } catch (Exception e) {
                }

            }

            @Override
            public void onCardClick(int position) {

            }

            @Override
            public void onEdit(int position) {

            }
        }, 32, false);
        option_list.setAdapter(option_adapter_recycler);
        option_list.setLayoutManager(option_manager);


        SupportMapFragment mFragmentMap = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.activity_map_fr_main);
        mFragmentMap.getMapAsync(this);

        ImageView fullscreen = (ImageView)findViewById(R.id.fullscreen);
        fullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddNewAds.this,MapActivity.class);
                intent.putExtra("source","AddNewAds");
                AddNewAds.this.startActivity(intent);
            }
        });
    }

    public void formatNumber(EditText view, int price, int off) {
        if (String.valueOf(price).length() < 3) {
            view.setText(String.valueOf(((float) price / 100) * (100 - off)));
        } else {
            String s = null;
            try {
                s = String.format("%,d", (price / 100) * (100 - off));
            } catch (NumberFormatException e) {
            }
            view.setText(s);
        }
    }

    private void fieldChecker() {
        checker(cancel_time, 1, "زمان لفو");
        checker(cancel_decreas, 1, "کاهش قیمت");
        checker(capacity, 2, "ظرفیت");
        checker(size, 2, "اندازه");
        checker(address, 5, "آدرس");
        checker(location, 2, "نام محله");

        checker3(adDate, 4, "تاریخ");
        checker2(time1, 2, "زمان شروع");
        checker2(time1_1, 2, "زمان پایان");
        checker(price1, 2, "قیمت");
        checker(off1, 1, "میزان تخفیف");
        checker(final_price1, 2, "قیمت نهایی");

        if (options2.getVisibility() == View.VISIBLE) {
            checker2(time2, 2, "زمان شروع");
            checker2(time2_1, 2, "زمان پایان");
            checker(price2, 2, "قیمت");
            checker(off2, 1, "میزان تخفیف");
            checker(final_price2, 2, "قیمت نهایی");
        }
        if (options3.getVisibility() == View.VISIBLE) {
            checker2(time3, 2, "زمان شروع");
            checker2(time3_1, 2, "زمان پایان");
            checker(price3, 2, "قیمت");
            checker(off3, 1, "میزان تخفیف");
            checker(final_price3, 2, "قیمت نهایی");
        }


        if (!checker(cancel_time, 1, "زمان لفو") ||
                !checker(cancel_decreas, 1, "کاهش قیمت") ||
                !checker(capacity, 2, "ظرفیت") ||
                !checker(size, 2, "اندازه") ||
                !checker(address, 2, "آدرس") ||
                !checker(location, 2, "نام محله") ||
                !checker2(time1, 2, "زمان") ||
                !checker2(time1_1, 2, "تاریح") ||
                !checker(price1, 2, "قیمت") ||
                !checker(off1, 1, "میزان تخفیف") ||
                !checker(final_price1, 2, "قیمت نهایی")
                ) {
            fieldsAreFilled = false;
        } else {
            fieldsAreFilled = true;

        }
    }


    private boolean checker2(final TextView editText, int limit, String text) {
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
//                    editText.setSelection(editText.getText().length());
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
//                    editText.setSelection(editText.getText().length());
                }
            }, 1000);
            result = false;
        }
        return result;
    }

    private boolean checker3(final TextView editText, int limit, String text) {
        Boolean result = true;
        final String typedText = editText.getText().toString();
        if (editText.getText().toString().length() == 0) {
            editText.setText("");
            editText.setHint(text + " را وارد کنید");
            editText.setHintTextColor(getResources().getColor(R.color.white));
            editText.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.vibrate));
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    editText.setText(typedText);
//                    editText.setSelection(editText.getText().length());
                }
            }, 1000);
            result = false;
        } else if (editText.getText().toString().length() < limit) {
            editText.setHint(text + " وارد شده اشتباه است");
            editText.setText("");
            editText.setHintTextColor(getResources().getColor(R.color.white));
            editText.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.vibrate));
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    editText.setText(typedText);
//                    editText.setSelection(editText.getText().length());
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void showHourPicker(final int no) {
        final Calendar myCalender = Calendar.getInstance();
        int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        int minute = myCalender.get(Calendar.MINUTE);


        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time = hourOfDay + " : " + minute;
                switch (no) {
                    case 1:
                        time1.setText(time);
                        time1_1.setEnabled(true);


                        break;
                    case 2:
                        String currentString = time1.getText().toString();
                        String[] separated = currentString.split(" : ");
                        String startHour = separated[0]; // this will contain "Fruit"
//                        separated[1]; // this will contain " they taste good"
                        if (hourOfDay < Integer.parseInt(startHour) + 1) {
                            Toast.makeText(AddNewAds.this, "انتخاب نباید قبل از زمان شروع باشد", Toast.LENGTH_SHORT).show();
                            timePickerDialog.show();
                        } else {
                            time1_1.setText(time);
                        }
                        break;
                    case 3:
                        time2.setText(time);
                        time2_1.setEnabled(true);

                        break;
                    case 4:
                        String currentString2 = time2.getText().toString();
                        String[] separated2 = currentString2.split(" : ");
                        String startHour2 = separated2[0]; // this will contain "Fruit"
//                        separated[1]; // this will contain " they taste good"
                        if (hourOfDay < Integer.parseInt(startHour2) + 1) {
                            Toast.makeText(AddNewAds.this, "انتخاب نباید قبل از زمان شروع باشد", Toast.LENGTH_SHORT).show();
                            timePickerDialog.show();
                        } else {
                            time2_1.setText(time);
                        }

                        break;
                    case 5:
                        time3.setText(time);
                        time3_1.setEnabled(true);

                        break;
                    case 6:
                        String currentString3 = time3.getText().toString();
                        String[] separated3 = currentString3.split(" : ");
                        String startHour3 = separated3[0]; // this will contain "Fruit"
//                        separated[1]; // this will contain " they taste good"
                        if (hourOfDay < Integer.parseInt(startHour3) + 1) {
                            Toast.makeText(AddNewAds.this, "انتخاب نباید قبل از زمان شروع باشد", Toast.LENGTH_SHORT).show();
                            timePickerDialog.show();
                        } else {
                            time3_1.setText(time);
                        }

                        break;
                }
            }
        };
        timePickerDialog = new TimePickerDialog(AddNewAds.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, true);
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
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

        if (accuracyCircle != null) {
            accuracyCircle.remove();
        }
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

    @Override
    public void onClick(View view) {
    }

    /////////////////////////////////////////// End Of Map Methods /////////////////////////////////////


    public void runSessionService() {


        if (!lat.equals("0") && !lng.equals("0")) {
            String cancelTimeStr = cancel_time.getText().toString();
            String cancelDecreaseStr = cancel_decreas.getText().toString();

            int Id = 1;
            String addressStr = address.getText().toString();
            String name = G.CUSTOMER_NAME.getString("CUSTOMER_NAME", "x");
            String latitude = lat;
            String longtitude = lng;
            String capacityStr = capacity.getText().toString();
            String locationStr = location.getText().toString();
            Utilities utilities = new Utilities();
            String createTimeStr = utilities.getCurrentShamsidate();
            String sizeStr = size.getText().toString();
            String mailStr = G.CUSTOMER_EMAIL.getString("CUSTOMER_EMAIL", "x");
            String phoneStr = G.CUSTOMER_MOBILE.getString("CUSTOMER_MOBILE", "x");
            int typeId = G.TYPE_ID;
            int userID = 1;

            JSONArray adsOption = new JSONArray();

            try {

                for (int i = 0; i < option_arrayList.size(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("Id", option_arrayList.get(i).strItemId);
                    jsonObject.put("Name", "option");
                    int state = 1;
                    for (int j = 0; j < optionsArray.size(); j++) {
                        if (optionsArray.get(j).equals(option_arrayList.get(i).strItemId)) {
                            state = 0;
                        }
                    }
                    jsonObject.put("HasOption", state);

                    adsOption.put(jsonObject);
                }
            } catch (Exception e) {
            }

            String x = String.valueOf(adsOption);


            JSONArray adsShift = new JSONArray();
            String timeStr = time1.getText().toString();
            String timeStr_end = time1_1.getText().toString();
            String priceStr = price1.getText().toString();
            String offStr = off1.getText().toString();
            String finalPriceStr = final_price1.getText().toString();

            String timeStr2 = time2.getText().toString();
            String timeStr2_end = time2_1.getText().toString();
            String priceStr2 = price2.getText().toString();
            String offStr2 = off2.getText().toString();
            String finalPriceStr2 = final_price2.getText().toString();


            String timeStr3 = time3.getText().toString();
            String timeStr3_end = time3_1.getText().toString();
            String priceStr3 = price3.getText().toString();
            String offStr3 = off3.getText().toString();
            String finalPriceStr3 = final_price3.getText().toString();

            try {
                JSONObject firstTime = new JSONObject();
                firstTime.put("ID", 1);
                firstTime.put("FromTime", timeStr);
                firstTime.put("ToTime", timeStr_end);
                firstTime.put("Date", adDate.getText().toString());
                firstTime.put("Fee", priceStr);
                firstTime.put("FeeAfterOff", finalPriceStr);
                firstTime.put("Off", offStr);
                firstTime.put("IsReserve", 0);
                firstTime.put("AdsId", 1);
                firstTime.put("UnReservedDeadline", cancelTimeStr);
                firstTime.put("UnReservedPenalty", cancelDecreaseStr);
                adsShift.put(firstTime);

                if (options2.getVisibility() == View.VISIBLE) {
                    JSONObject secondTime = new JSONObject();
                    secondTime.put("ID", 1);
                    secondTime.put("FromTime", timeStr2);
                    secondTime.put("ToTime", timeStr2_end);
                    secondTime.put("Date", adDate.getText().toString());
                    secondTime.put("Fee", priceStr2);
                    secondTime.put("FeeAfterOff", finalPriceStr2);
                    secondTime.put("Off", offStr2);
                    secondTime.put("IsReserve", 0);
                    secondTime.put("AdsId", 1);
                    firstTime.put("UnReservedDeadline", cancelTimeStr);
                    firstTime.put("UnReservedPenalty", cancelDecreaseStr);
                    adsShift.put(secondTime);
                }

                if (options3.getVisibility() == View.VISIBLE) {
                    JSONObject thirdTime = new JSONObject();
                    thirdTime.put("ID", 1);
                    thirdTime.put("FromTime", timeStr3);
                    thirdTime.put("ToTime", timeStr3_end);
                    thirdTime.put("Date", adDate.getText().toString());
                    thirdTime.put("Fee", priceStr3);
                    thirdTime.put("FeeAfterOff", finalPriceStr3);
                    thirdTime.put("Off", offStr3);
                    thirdTime.put("IsReserve", 0);
                    thirdTime.put("AdsId", 1);
                    firstTime.put("UnReservedDeadline", cancelTimeStr);
                    firstTime.put("UnReservedPenalty", cancelDecreaseStr);
                    adsShift.put(thirdTime);
                }
            } catch (JSONException e) {
            }

            new newAsync().execute(Urls.BASE_URL + Urls.INSERT_NEW_ADS,
                    String.valueOf(Id), addressStr, name, latitude, longtitude, capacityStr,
                    locationStr, createTimeStr, sizeStr, mailStr, phoneStr, String.valueOf(typeId),
                    String.valueOf(userID), String.valueOf(adsOption), String.valueOf(adsShift));

        } else {
            Toast.makeText(G.CONTEXT, "موقعیت را در نقشه انتخاب کنید", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDateSet(int i, @Nullable Calendar calendar, int i1, int i2, int i3) {

    }

    private class newAsync extends Webservice.registerNewClass {
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
                JSONObject jsonObject = new JSONObject(result);
                String Message = jsonObject.getString("Message");
                AdsId = jsonObject.getInt("AdsId");
                Toast.makeText(AddNewAds.this, Message, Toast.LENGTH_SHORT).show();

                if (jsonObject.getString("Code").equals("0")) {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }
                    }, 1000);
                }

                for (int i = 0; i < uriArray.size(); i++) {
                    String filePath = null;
                    try {
                        filePath = PathUtil.getPath(G.CONTEXT, uriArray.get(i));
                        uploadImage(new File(filePath));
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    public boolean checkPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) { // ابتدا کنترل می کند تا اندروید بالاتر M باشد
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);//یک دیالوگ ایجاد می شود.
                    alertBuilder.setCancelable(true);// بصورت قابل کنسل شدنی
                    alertBuilder.setTitle("Permission necessary");// با عنوان نیازمند دسترسی
                    alertBuilder.setMessage("External storage permission is necessary"); // متن دسترسی
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() { // دکمه مثبت
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, ReadExternalRequestCode);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, ReadExternalRequestCode);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case ReadExternalRequestCode:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    galleryIntent();
                } else {
                    Toast.makeText(getBaseContext(), "دسترسی داده نشد!", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void galleryIntent() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, KeyGallery);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                switch (G.selectedImag) {
                    case 1:
                        img1.setImageURI(resultUri);
                        break;
                    case 2:
                        img2.setImageURI(resultUri);
                        break;
                    case 3:
                        img3.setImageURI(resultUri);
                        break;
                    case 4:
                        img4.setImageURI(resultUri);
                        break;
                }
                uriArray.add(resultUri);
//                String filePath = null;
//                try {
//                    filePath = PathUtil.getPath(G.CONTEXT, resultUri);
//                    uploadImage(new File(filePath));
//                } catch (URISyntaxException e) {
//                    e.printStackTrace();
//                }


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


    public void uploadImage(File file) {
        AsyncHttpClient myClient = new AsyncHttpClient();

        RequestParams params = new RequestParams();
        try {
            params.put("file", file);
        } catch (FileNotFoundException e) {
        }
        myClient.post(url + String.valueOf(AdsId), params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                try {
                    JSONObject jsonObject = new JSONObject(new String(bytes));
                    if (jsonObject.getString("Code").equals("0")) {
                        Toast.makeText(AddNewAds.this, "آپلود با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
//                        Picasso.with(G.CONTEXT)
//                                .load(url + jsonObject.getString("filename"))
//                                .into(img1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                Toast.makeText(getBaseContext(), new String(responseBody), Toast.LENGTH_LONG).show();
                try {
                    Toast.makeText(G.CONTEXT, new String(responseBody), Toast.LENGTH_LONG).show();

                }catch (Exception e){}
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(Gravity.RIGHT)){
            drawer.closeDrawer(Gravity.RIGHT);
        }else {
            releaseBitmap();
            super.onBackPressed();        }
    }


    public void onImageViewClicked(View view) {
        releaseBitmap();
        finish();
    }

    private void releaseBitmap() {
        if (mImage != null) {
            mImage.recycle();
            mImage = null;
        }
    }

    private File persistImage(Bitmap bitmap, String name) {
        File filesDir = getApplicationContext().getFilesDir();
        File imageFile = new File(filesDir, name + ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
        }
        return imageFile;
    }

    private void getOptions() {
        new OptionsAsync().execute(Urls.BASE_URL + Urls.GET_ESTATE_TYPES_OPTIONS, String.valueOf(G.LATITUDE), String.valueOf(G.LONGTITUDE), String.valueOf(1));

    }

    private class OptionsAsync extends Webservice.getOptions {
        @Override
        protected void onPreExecute() {
            options_loading.smoothToShow();
            options_loading.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(final String result) {
            options_loading.setVisibility(View.GONE);
            option_arrayList.clear();

            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    Struct struct = new Struct();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    struct.strItemId = String.valueOf(jsonObject.getInt("Id"));
                    struct.strItemTitle = jsonObject.getString("Name");
                    struct.strItemText = jsonObject.getString("PersianName");
                    option_arrayList.add(struct);
                }
                option_adapter_recycler.notifyDataSetChanged();

                resizeView(jsonArray.length());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void resizeView(int cardNo) {
        FrameLayout layout = findViewById(R.id.options_layout);
        ViewGroup.LayoutParams params = layout.getLayoutParams();

        int size = cardNo * 40;
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, getResources().getDisplayMetrics());

        params.height = height;
//        params.width = 100;
        layout.setLayoutParams(params);
    }

    class Date extends DateItem {
        String getDate() {
            Calendar calendar = getCalendar();
            return String.format(Locale.US,
                    "%d/%d/%d",
                    getYear(), getMonth(), getDay(),
                    calendar.get(Calendar.YEAR),
                    +calendar.get(Calendar.MONTH) + 1,
                    +calendar.get(Calendar.DAY_OF_MONTH));
        }
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
