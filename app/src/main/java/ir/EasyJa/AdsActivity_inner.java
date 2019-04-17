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
import android.os.Handler;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AdsActivity_inner extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener,
        GoogleMap.OnMapLongClickListener, FragmentNavigation.OnFragmentInteractionListener {

    private Boolean fieldsAreFilled = false;
    private ArrayList<String> stateName = new ArrayList<>();
    private ArrayList<String> cityName = new ArrayList<>();
    public static AdsActivity_inner activity;
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
    private Double Lat;
    private Double Lng;
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
    //    private ImageView item_image;
    private WebView web;
    private Dialog dialogReserveConfirm;
    private TextView dialogReserveConfirm_cancel;
    private TextView dialogReserveConfirm_return;
    private ProgressDialog progDailog;
    private boolean needRefresh = false;
    private String urlString;
    private LinearLayout loading_layout;
    private SliderLayout sliderLayout;
    private Dialog dialogPayType;
    private TextView dialogPayType_Online;
    private TextView dialogPayType_FromCredit;
    private TextView dialogPayType_Price;
    private TextView dialogPayType_Credit;
    private int selectedPrice;
    private ImageView btnLike;
    private MarkerOptions marker1;
    private String longtitude;
    private String latitude;
    private boolean creditIsLow;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
//        Intent intent = new Intent(AdsActivity.this, LoginActivity.class);
//        AdsActivity.this.startActivity(intent);


        if (drawer.isDrawerOpen(Gravity.RIGHT)) {
            drawer.closeDrawer(Gravity.RIGHT);
        } else {
            if (web.getVisibility() == View.GONE) {

                finish();

            } else if (web.getVisibility() == View.INVISIBLE) {
                finish();

            } else {
                web.setVisibility(View.GONE);
                loading_layout.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getOptions();

                    }
                }, 1000);


            }
        }

    }

    private void getOptions() {
        new getPayResponse().execute(Urls.BASE_URL2 + Urls.CHECK_VERIFY, urlString);


    }

    private class getPayResponse extends Webservice.checkPayment {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(final String result) {
            loading_layout.setVisibility(View.GONE);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(result);

                String Message = jsonObject.getString("Message");
                Boolean key = jsonObject.getBoolean("Key");
                Toast.makeText(AdsActivity_inner.this, Message, Toast.LENGTH_SHORT).show();
                if (key) {
                    runRentService();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            getUserCredit();
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
        if (G.GOING_TO_PAY){
            loading_layout.setVisibility(View.VISIBLE);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getOptions();

                }
            }, 500);
            G.GOING_TO_PAY = false;
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
        dialogReserveConfirm = new Dialog(AdsActivity_inner.this);
        dialogReserveConfirm.setContentView(R.layout.dialog_reserve_confirmation);
        dialogReserveConfirm.setCancelable(true);
        dialogReserveConfirm_cancel = (TextView) dialogReserveConfirm.findViewById(R.id.cancel);
        dialogReserveConfirm_return = (TextView) dialogReserveConfirm.findViewById(R.id.go_back);
        dialogReserveConfirm_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogReserveConfirm.dismiss();

                dialogPayType_Price.setText(String.valueOf(selectedPrice));
                String creditStr = G.CUSTOMER_CREDIT.getString("CUSTOMER_CREDIT", "0");
                dialogPayType_Credit.setText(String.valueOf(creditStr));

                if (selectedPrice > Double.parseDouble(creditStr)) {
//                    dialogPayType_FromCredit.setEnabled(false);
                    dialogPayType_Credit.setTextColor(getResources().getColor(R.color.Red));
                    creditIsLow = true;

                } else {
//                    dialogPayType_FromCredit.setEnabled(true);
                    dialogPayType_Credit.setTextColor(getResources().getColor(R.color.Green));
                    creditIsLow = false;
                }
                dialogPayType.show();


            }
        });

        dialogReserveConfirm_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogReserveConfirm.dismiss();
            }
        });


        // Dialog Reserve Cancel
        dialogCancelConfirm = new Dialog(AdsActivity_inner.this);
        dialogCancelConfirm.setContentView(R.layout.dialog_cancel_confirm);
        dialogCancelConfirm.setCancelable(true);
        dialogCancelConfirm_cancel = (TextView) dialogCancelConfirm.findViewById(R.id.cancel);
        dialogCancelConfirm_return = (TextView) dialogCancelConfirm.findViewById(R.id.go_back);
        dialogCancelConfirm_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCancelConfirm.dismiss();
                runUnRentService();

            }
        });

        dialogCancelConfirm_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCancelConfirm.dismiss();
            }
        });

        // Dialog pay type
        dialogPayType = new Dialog(AdsActivity_inner.this);
        dialogPayType.setContentView(R.layout.dialog_pay_type);
        dialogPayType.setCancelable(true);
        dialogPayType_Price = (TextView) dialogPayType.findViewById(R.id.price);
        dialogPayType_Credit = (TextView) dialogPayType.findViewById(R.id.credit);
        dialogPayType_Online = (TextView) dialogPayType.findViewById(R.id.online);
        dialogPayType_FromCredit = (TextView) dialogPayType.findViewById(R.id.from_credit);
        dialogPayType_Online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogPayType.dismiss();
                payRequest();

            }
        });

        dialogPayType_FromCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!creditIsLow) {
                    dialogPayType.dismiss();
                    runRentService();
                } else {
                    Toast.makeText(AdsActivity_inner.this, "اعتبار کافی نیست", Toast.LENGTH_SHORT).show();
                }
            }
        });


        sliderLayout = (SliderLayout) findViewById(R.id.slider);

        loading_layout = (LinearLayout) findViewById(R.id.loading_layout);
        btn_reserve = (TextView) findViewById(R.id.btn_reserve);
        btn_cancel_reserve = (TextView) findViewById(R.id.btn_cancel_reserve);
        fl_reserved = (FrameLayout) findViewById(R.id.fl_reserved);
        fl_reserve = (FrameLayout) findViewById(R.id.fl_reserve);
        demo = (FrameLayout) findViewById(R.id.demo);
        ad_state = (TextView) findViewById(R.id.ad_state);
//        item_image = (ImageView) findViewById(R.id.item_image);
        web = (WebView) findViewById(R.id.web);

        btn_reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean is_registered = G.IS_REGISTERED.getBoolean("IS_REGISTERED", false);
                if (is_registered) {
                    dialogReserveConfirm.show();
                } else {
                    Intent intent = new Intent(AdsActivity_inner.this, LoginActivity.class);
                    intent.putExtra("base", "Ads");
                    AdsActivity_inner.this.startActivity(intent);
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
                String number = PhoneNumber;  // The number on which you want to send SMS
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));

//                Intent intent = new Intent(AdsActivity_inner.this, MessageActivity.class);
//                AdsActivity_inner.this.startActivity(intent);
            }
        });

        //Toolbar
        btnLike = (ImageView) findViewById(R.id.btnlike);
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favoriteThisAd();
            }
        });
        ImageView btnNavigation = (ImageView) findViewById(R.id.btnNavigation);

        btnNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.RIGHT);
            }
        });
        toolbar_main_tv_header = (TextView) findViewById(R.id.toolbar_main_tv_header);
        toolbar_main_tv_header.setText("انتخاب شما");


        mPermission = new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.ACCESS_FINE_LOCATION,};


        activity = AdsActivity_inner.this;
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
                try {
                    AdsShift1_ID = arrayList.get(position).AdsShift1_ID;
                    IsReserved = arrayList.get(position).AdsShift1_IsReserve;
                    selectedPrice = arrayList.get(position).AdsShift1_FeeAfterOff;

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
                } catch (Exception e) {
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
//                AdsShift1_ID = arrayList.get(position).AdsShift1_ID;
//                IsReserved = arrayList.get(position).AdsShift1_IsReserve;
//                demo.setVisibility(View.GONE);
//                if (isMyAd(SenderID)) {
//
//                } else {
//                    if (IsReserved) {
//                        userID = arrayList.get(position).AdsShift1_UserId;
//                        String myID = G.CUSTOMER_ID.getString("CUSTOMER_ID", "x");
//                        if (String.valueOf(userID).equals(myID)) {
//                            rentID = arrayList.get(position).AdsShift1_RentId;
//                            cancelReserveView();
//                        } else {
//                            demo.setVisibility(View.VISIBLE);
//                        }
//                    } else {
//                        reserveView();
//                    }
//                }
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
            Object = new JSONObject(intent.getStringExtra("object"));
            EstateTypeID = intent.getIntExtra("EstateTypeID", 2);
            JSONObject mainObject = Object.getJSONObject("Ads");
            AdID = mainObject.getString("ID");

            sendRequest(EstateTypeID);


            String Name = mainObject.getString("Name");
            int Capacity = mainObject.getInt("Capacity");
            String Address = mainObject.getString("Address");
            String Location = mainObject.getString("Location");
            String CreateTime = mainObject.getString("CreateTime");
            int ClassSize = mainObject.getInt("ClassSize");
            String Email = mainObject.getString("Email");
            String PhoneNumber = mainObject.getString("PhoneNumber");
            Lat = mainObject.getDouble("Lat");
            Lng = mainObject.getDouble("Lng");
            int EstateTypeID = mainObject.getInt("EstateTypeID");
            SenderID = mainObject.getInt("UserID");


            LOCATION = new LatLng(Lat, Lng);
            marker1 = new MarkerOptions().position(new LatLng(LOCATION.latitude, LOCATION.longitude));
            marker1.icon(BitmapDescriptorFactory.fromBitmap(markerCreator(R.mipmap.ic_marker)));
            mMap.addMarker(marker1).showInfoWindow();

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
            JSONArray AdsOption = Object.getJSONArray("AdsOptions");
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
            resizeView(option_layout, option_arrayList.size(), 40);

            try {
                JSONArray adsImage = Object.getJSONArray("AdsImage");
                String image = null;
                for (int j = 0; j < adsImage.length(); j++) {
                    JSONObject imageObject = adsImage.getJSONObject(j);
                    image = imageObject.getString("ImageUrl") + imageObject.getString("ImageName");

                    TextSliderView textSliderView = new TextSliderView(AdsActivity_inner.this);
                    // initialize a SliderLayout

                    textSliderView
//                    .description(url_maps.get(i))
                            .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                            .image(image);
//                    .setScaleType(BaseSliderView.ScaleType.Fit);
//                    .setOnSliderClickListener(this);

                    //add your extra information
//                    textSliderView.bundle(new Bundle());
//                       textSliderView.getBundle()
//                    .putString("extra", name);

                    sliderLayout.addSlider(textSliderView);

                }
//                Glide.with(G.CONTEXT).load(image).placeholder(R.drawable.place_holder).centerCrop().into(item_image);
            } catch (Exception e) {

            }

//////////////////////////////////////////////////////

            JSONArray AdsShift = Object.getJSONArray("AdsShift");
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
            resizeView(shift_layout, AdsShift.length(), 44);


//            JSONObject AdsShift1 = AdsShift.getJSONObject(0);
//            int AdsShift1_ID = AdsShift1.getInt("ID");
//            String AdsShift1_FromTime = AdsShift1.getString("FromTime");
//            String AdsShift1_ToTime = AdsShift1.getString("ToTime");
//            String AdsShift1_Date = AdsShift1.getString("Date");
//            int AdsShift1_Fee = AdsShift1.getInt("Fee");
//            int AdsShift1_FeeAfterOff = AdsShift1.getInt("FeeAfterOff");
//            int AdsShift1_Off = AdsShift1.getInt("Off");
//            boolean AdsShift1_IsReserve = AdsShift1.getBoolean("IsReserve");
//            int AdsShift1_AdsId = AdsShift1.getInt("AdsId");

//            time1.setText(AdsShift1_FromTime + " - " + AdsShift1_ToTime);
//            price1.setText(String.valueOf(AdsShift1_Fee));
//            off1.setText(String.valueOf(AdsShift1_Off));
//            last_price_1.setText(String.valueOf(AdsShift1_FeeAfterOff));
//            date.setText(AdsShift1_Date);


//
//            JSONObject AdsShift2 = AdsShift.getJSONObject(1);
//            int AdsShift2_ID = AdsShift2.getInt("ID");
//            String AdsShift2_FromTime = AdsShift2.getString("FromTime");
//            String AdsShift2_ToTime = AdsShift2.getString("ToTime");
//            String AdsShift2_Date = AdsShift2.getString("Date");
//            int AdsShift2_Fee = AdsShift2.getInt("Fee");
//            int AdsShift2_FeeAfterOff = AdsShift2.getInt("FeeAfterOff");
//            int AdsShift2_Off = AdsShift2.getInt("Off");
//            boolean AdsShift2_IsReserve = AdsShift2.getBoolean("IsReserve");
//            int AdsShift2_AdsId = AdsShift2.getInt("AdsId");

//            time2.setText(AdsShift2_FromTime + " - " + AdsShift2_ToTime);
//            price2.setText(String.valueOf(AdsShift2_Fee));
//            off2.setText(String.valueOf(AdsShift2_Off));
//            last_price_2.setText(String.valueOf(AdsShift2_FeeAfterOff));

//            JSONObject AdsShift3 = AdsShift.getJSONObject(2);
//            int AdsShift3_ID = AdsShift3.getInt("ID");
//            String AdsShift3_FromTime = AdsShift3.getString("FromTime");
//            String AdsShift3_ToTime = AdsShift3.getString("ToTime");
//            String AdsShift3_Date = AdsShift3.getString("Date");
//            int AdsShift3_Fee = AdsShift3.getInt("Fee");
//            int AdsShift3_FeeAfterOff = AdsShift3.getInt("FeeAfterOff");
//            int AdsShift3_Off = AdsShift3.getInt("Off");
//            boolean AdsShift3_IsReserve = AdsShift3.getBoolean("IsReserve");
//            int AdsShift3_AdsId = AdsShift3.getInt("AdsId");

//            time3.setText(AdsShift3_FromTime + " - " + AdsShift3_ToTime);
//            price3.setText(String.valueOf(AdsShift3_Fee));
//            off3.setText(String.valueOf(AdsShift3_Off));
//            last_price_3.setText(String.valueOf(AdsShift3_FeeAfterOff));


        } catch (Exception e) {
        }


        ImageView fullscreen = (ImageView) findViewById(R.id.fullscreen);
        fullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdsActivity_inner.this, MapActivityView.class);
                intent.putExtra("latitude", String.valueOf(Lat));
                intent.putExtra("longtitude", String.valueOf(Lng));
                AdsActivity_inner.this.startActivity(intent);
            }
        });

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
            String x = result;
            try {
                JSONObject jsonObject = new JSONObject(result);
                Boolean like = jsonObject.getBoolean("Like");
                if (like) {
                    isFavorite();
                } else {
                    isNotFavorite();
                }
            } catch (Exception e) {
            }

        }
    }

    private void isFavorite() {
        btnLike.setImageResource(R.mipmap.ic_like);
    }

    private void isNotFavorite() {
        btnLike.setImageResource(R.mipmap.ic_dislike);

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
            String x = result;
            try {
                JSONObject jsonObject = new JSONObject(result);
                Boolean like = jsonObject.getBoolean("Like");
                if (like) {
                    isFavorite();
                    Toast.makeText(AdsActivity_inner.this, "در لیست مورد علاقه ها ثبت شد", Toast.LENGTH_SHORT).show();
                } else {
                    isNotFavorite();
                    Toast.makeText(AdsActivity_inner.this, "از لیست مورد علاقه ها حذف شد", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
            }

        }
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
            Intent intent = new Intent(AdsActivity_inner.this, LoginActivity.class);
            intent.putExtra("base", "AdsInner");
            AdsActivity_inner.this.startActivity(intent);
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
            Intent intent = new Intent(AdsActivity_inner.this, LoginActivity.class);
            intent.putExtra("base", "AdsInner");
            AdsActivity_inner.this.startActivity(intent);
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
                Toast.makeText(AdsActivity_inner.this, Message, Toast.LENGTH_SHORT).show();

                getUserCredit();


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
                Toast.makeText(AdsActivity_inner.this, Message, Toast.LENGTH_SHORT).show();

                Intent intent = getIntent();
                finish();
                startActivity(intent);

            } catch (Exception e) {
            }


//            try {
//                JSONObject mainObject = new JSONObject(result);
//
//                try {
//                    JSONObject error = mainObject.getJSONObject("Error");
//                    String message = error.getString("Message");
//                    Toast.makeText(AdsActivity_inner.this, message, Toast.LENGTH_SHORT).show();
//
//                } catch (Exception e) {
//
//                }
//
//                try {
//                    JSONObject Value = mainObject.getJSONObject("Value");
//                    String message = Value.getString("Message");
//                    Toast.makeText(AdsActivity_inner.this, message, Toast.LENGTH_SHORT).show();
//                    G.IS_REGISTERED.edit().putBoolean("IS_REGISTERED", true).apply();

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
//                } catch (Exception e) {
//
//                }

//            } catch (JSONException e) {
//            }


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


        LOCATION = new LatLng(Lat, Lng);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LOCATION, 14));

        MarkerOptions marker = new MarkerOptions().position(new LatLng(LOCATION.latitude, LOCATION.longitude));
        marker.icon(BitmapDescriptorFactory.fromBitmap(markerCreator(R.mipmap.ic_marker)));
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

    private void sendRequest(int EstateTypeID) {

        String sessionId = G.AUTHENTICATIONS_SESSION.getString("SESSION", "x");
        String userToken = G.AUTHENTICATIONS_TOKEN.getString("TOKEN", "x");
        String customerId = G.CUSTOMER_ID.getString("CUSTOMER_ID", "x");
        String cellPhone = G.CUSTOMER_PHONE.getString("CUSTOMER_PHONE", "x");
        String typeId = String.valueOf(EstateTypeID);

//        new resendAsync().execute(Urls.BASE_URL + Urls.GET_NEAR_STATES, String.valueOf(G.defaultGlat), String.valueOf(G.defaultGlng), typeId);
        new resendAsync().execute(Urls.BASE_URL + Urls.GET_NEAR_STATES, "35.6997475627189", "51.33894469589", typeId);


    }

    private class resendAsync extends Webservice.getNearStates {
        @Override
        protected void onPreExecute() {
            arrayList.clear();
        }

        @Override
        protected void onPostExecute(final String result) {
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    JSONObject mainObject = jsonObject.getJSONObject("Ads");

                    String ID = mainObject.getString("ID");
                    if (String.valueOf(AdID).equals(ID)) {
                        Object = jsonObject;
                    }
                }

                try {
//                    Intent intent = getIntent();
//                    Object = new JSONObject(intent.getStringExtra("object"));
//                    EstateTypeID = intent.getIntExtra("EstateTypeID",2);
                    JSONObject mainObject = Object.getJSONObject("Ads");
                    AdID = mainObject.getString("ID");

//                    sendRequest(EstateTypeID);


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

                    latitude = String.valueOf(Lat);
                    longtitude = String.valueOf(Lng);
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
                    JSONArray AdsOption = Object.getJSONArray("AdsOptions");
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

                    JSONArray AdsShift = Object.getJSONArray("AdsShift");
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

                    getFavoriteState();

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


        new payAsync().execute(Urls.BASE_URL2 + Urls.PAY, String.valueOf(selectedPrice), "test", cellPhone, email);


    }

    private class payAsync extends Webservice.payRequest {
        @Override
        protected void onPreExecute() {
            arrayList.clear();
        }

        @Override
        protected void onPostExecute(final String result) {
            String x = result;

//            web.setVisibility(View.VISIBLE);

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

            URI uri = null;
            try {
                uri = new URI(url);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            String path = uri.getPath();
            String idStr = path.substring(path.lastIndexOf('/') + 1);
            urlString = idStr;

//            web.loadUrl(url);
            final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            G.GOING_TO_PAY = true;
            activity.startActivity(intent);


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

    public void getUserCredit() {
        String phone = G.CUSTOMER_PHONE.getString("CUSTOMER_PHONE", "nothing");
        new CreditAsync().execute(Urls.BASE_URL + Urls.GET_USER_CREDIT, phone);

    }

    private class CreditAsync extends Webservice.getCredit {
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
                    Toast.makeText(AdsActivity_inner.this, message, Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                }

                try {
                    JSONObject Value = mainObject.getJSONObject("Value");
                    String message = Value.getString("Message");
                    String KeyStr = Value.getString("Value");
                    G.CUSTOMER_CREDIT.edit().putString("CUSTOMER_CREDIT", KeyStr).apply();
                } catch (Exception e) {

                }
                Intent intent = getIntent();
                finish();
                startActivity(intent);


            } catch (JSONException e) {
            }
        }
    }
}
