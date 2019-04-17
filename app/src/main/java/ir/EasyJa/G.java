package ir.EasyJa;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import com.onesignal.OneSignal;

import java.util.Calendar;

import ir.EasyJa.Helper.GPSTracker;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by PC4 on 11/29/2017.
 */

public class G extends Application {
    public static SharedPreferences CUSTOMER_LAST_NAME;
    public static Context CONTEXT;
    public static SharedPreferences EVENTS;
    public static SharedPreferences FIRST_TIME_REQUEST;
    public static SharedPreferences AUTHENTICATIONS_SESSION;
    public static SharedPreferences AUTHENTICATIONS_TOKEN;
    public static SharedPreferences CUSTOMER_ID;
    public static SharedPreferences IS_REGISTERED;
    public static SharedPreferences GROUPS;
    public static SharedPreferences CREDIT;
    public static SharedPreferences CUSTOMER_NAME;
    public static SharedPreferences CUSTOMER_PHONE;
    public static SharedPreferences CUSTOMER_GENDER;
    public static SharedPreferences CUSTOMER_ADDRESS;
    public static SharedPreferences CUSTOMER_CITY;
    public static SharedPreferences CUSTOMER_BIRTH;
    public static SharedPreferences CUSTOMER_CODE;
    public static SharedPreferences CUSTOMER_EMAIL;
    public static SharedPreferences SERVICE_STATE;
    public static SharedPreferences CUSTOMER_IMAGE;
    public static String oneSignalUserID;
    public static String oneSignalRegistrationID;
    public static Boolean isRegistered = false;
    public static String PRODUCT_GROUPS;
    public static String TARGET_GROUPS;
    public static String ANDROID_ID;
    public static String APP_VERSION;
    public static String ANDROID_VERSION;
    public static String DEVICE_NAME;
    public static String[] ENDTableFieldNames = new String[]{"MONTH", "PERCENT", "TOPIC", "ID"};
    public static String[] ENDTableFieldTypes = new String[]{"INT", "INT", "TEXT", "INT"};
    public static String[] EVENTTableFieldNames = new String[]{"MONTH", "DAY", "TIME", "TOPIC"};
    public static String[] EVENTTableFieldTypes = new String[]{"INT", "INT", "TEXT", "TEXT"};
    public static String[] ProductTableFieldNames = new String[]{"GROUP_ID", "ID", "NAME", "CURRENT_PRICE", "PREVIOUS_PRICE", "QUANTITY", "COMMENT", "IMAGE", "DATE", "TIME", "UNIT"};
    public static String[] ProductTableFieldTypes = new String[]{"TEXT", "TEXT", "TEXT", "INT", "INT", "INT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT"};
    public static String[] OrderTableFieldNames = new String[]{"ID", "NAME", "CURRENT_PRICE", "PREVIOUS_PRICE", "QUANTITY", "COMMENT", "IMAGE"};
    public static String[] OrderTableFieldTypes = new String[]{"TEXT", "TEXT", "INT", "INT", "INT", "TEXT", "TEXT"};
    public static String[] HistoryTableFieldNames = new String[]{"JSON", "DATE", "Empty1", "Empty2", "Empty3", "TIME", "IMAGE"};
    public static String[] HistoryTableFieldTypes = new String[]{"TEXT", "TEXT", "INT", "INT", "INT", "TEXT", "TEXT"};
    public static String[] ConversationTableFieldNames = new String[]{"conversationId", "title", "unReadMessageCount", "senderName", "receiverName", "lastMessage", "theDate", "theTime"};
    public static String[] ConversationTableFieldTypes = new String[]{"TEXT", "TEXT", "INT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT"};
    public static double LATITUDE = 0, LONGTITUDE = 0;
    public static SharedPreferences TARGET_GROUPS_SP;
    public static SharedPreferences MEMBER_CODE;
    public static int month = 1;
    public static int year = 1397;
    public static String dayPhrase;
    public static String date;
    public static String PHRASE_OWNER;
    public static String monthName;
    public static boolean fromRent = false;
    public static boolean needToRefresh = false;
    public static int TYPE_ID;
    public static int selectedImag;
    public static boolean mainNeedRefresh = false;
    public static boolean ManageAdsNeedToRefresh = false;
    public static boolean creditRefresh =false;
    public static boolean GOING_TO_PAY = false;
    private GPSTracker gps;
    public static String georgianDate;
    private String strMonthGeorgian;
    public static String ACTIVITY_NAME;
    public static SharedPreferences CUSTOMER_CODE_MELLI;
    public static int APP_VERSION_CODE;
    public static SharedPreferences KEY;
    public static SharedPreferences GOING_TO_RENT;
    public static SharedPreferences CUSTOMER_CREDIT;
    public static Double defaultGlat = 35.6996863, defaultGlng = 51.3374805;
    public static SharedPreferences CUSTOMER_MOBILE;
    public static SharedPreferences CUSTOMER_LAT;
    public static SharedPreferences CUSTOMER_LNG;
    public static SharedPreferences CUSTOMER_ACTIVATION_CODE;
    public static SharedPreferences TOKEN;
    public static SharedPreferences TOKEN_TYPE;


    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT = getApplicationContext();

        EVENTS = getSharedPreferences("EVENTS", MODE_PRIVATE);

        //define font library
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("iran_light.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()


        );

        //initialise one signal
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        //get one signal user and registration id
        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                oneSignalUserID = userId;
                if (registrationId != null)
                    oneSignalRegistrationID = registrationId;
            }
        });

        ANDROID_ID = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        ANDROID_VERSION = Build.VERSION.RELEASE;
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            APP_VERSION = pInfo.versionName;
            APP_VERSION_CODE = pInfo.versionCode;


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        DEVICE_NAME = getDeviceName();

        Log.d("android_id", ANDROID_ID);
        Log.d("android_version", String.valueOf(ANDROID_VERSION));
        Log.d("app_version", APP_VERSION);
        Log.d("device_name", DEVICE_NAME);


        //defining shared preferences
        FIRST_TIME_REQUEST = getSharedPreferences("FIRST_TIME_CONNECTION", MODE_PRIVATE);
        AUTHENTICATIONS_SESSION = getSharedPreferences("SESSION", MODE_PRIVATE);
        AUTHENTICATIONS_TOKEN = getSharedPreferences("TOKEN", MODE_PRIVATE);
        CUSTOMER_ID = getSharedPreferences("CUSTOMER_ID", MODE_PRIVATE);
        KEY = getSharedPreferences("KEY", MODE_PRIVATE);
        IS_REGISTERED = getSharedPreferences("IS_REGISTERED", MODE_PRIVATE);
        GROUPS = getSharedPreferences("GROUPS", MODE_PRIVATE);
        TARGET_GROUPS_SP = getSharedPreferences("TARGET_GROUPS_SP", MODE_PRIVATE);
        CREDIT = getSharedPreferences("CREDIT", MODE_PRIVATE);
        CUSTOMER_CREDIT = getSharedPreferences("CUSTOMER_CREDIT", MODE_PRIVATE);
        CUSTOMER_NAME = getSharedPreferences("CUSTOMER_NAME", MODE_PRIVATE);
        CUSTOMER_LAST_NAME = getSharedPreferences("CUSTOMER_LAST_NAME", MODE_PRIVATE);
        CUSTOMER_PHONE = getSharedPreferences("CUSTOMER_PHONE", MODE_PRIVATE);
        CUSTOMER_GENDER = getSharedPreferences("CUSTOMER_GENDER", MODE_PRIVATE);
        CUSTOMER_ADDRESS = getSharedPreferences("CUSTOMER_ADDRESS", MODE_PRIVATE);
        CUSTOMER_EMAIL = getSharedPreferences("CUSTOMER_EMAIL", MODE_PRIVATE);
        CUSTOMER_CITY = getSharedPreferences("CUSTOMER_CITY", MODE_PRIVATE);
        CUSTOMER_BIRTH = getSharedPreferences("CUSTOMER_BIRTH", MODE_PRIVATE);
        CUSTOMER_CODE = getSharedPreferences("CUSTOMER_CODE", MODE_PRIVATE);
        CUSTOMER_MOBILE = getSharedPreferences("CUSTOMER_MOBILE", MODE_PRIVATE);
        CUSTOMER_LAT = getSharedPreferences("CUSTOMER_LAT", MODE_PRIVATE);
        CUSTOMER_LNG = getSharedPreferences("CUSTOMER_LNG", MODE_PRIVATE);
        MEMBER_CODE = getSharedPreferences("MEMBER_CODE", MODE_PRIVATE);
        SERVICE_STATE = getSharedPreferences("SERVICE_STATE", MODE_PRIVATE);
        CUSTOMER_CODE_MELLI = getSharedPreferences("CUSTOMER_CODE_MELLI", MODE_PRIVATE);
        GOING_TO_RENT = getSharedPreferences("GOING_TO_RENT", MODE_PRIVATE);
        CUSTOMER_IMAGE = getSharedPreferences("CUSTOMER_IMAGE", MODE_PRIVATE);
        CUSTOMER_ACTIVATION_CODE = getSharedPreferences("CUSTOMER_ACTIVATION_CODE", MODE_PRIVATE);
        TOKEN = getSharedPreferences("TOKEN", MODE_PRIVATE);
        TOKEN_TYPE = getSharedPreferences("TOKEN_TYPE", MODE_PRIVATE);


        gpsData();


        Calendar cal = Calendar.getInstance();

        int millisecond = cal.get(Calendar.MILLISECOND);
        int second = cal.get(Calendar.SECOND);
        int minute = cal.get(Calendar.MINUTE);
        //12 hour format
        int hour = cal.get(Calendar.HOUR);
        //24 hour format
        int hourofday = cal.get(Calendar.HOUR_OF_DAY);


        int dayofyear = cal.get(Calendar.DAY_OF_YEAR);
        int year = cal.get(Calendar.YEAR);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        int dayofmonth = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);

        switch (month) {
            case 1:
                strMonthGeorgian = "ژانویه";
                break;
            case 2:
                strMonthGeorgian = "فوریه";
                break;
            case 3:
                strMonthGeorgian = "مارس";
                break;
            case 4:
                strMonthGeorgian = "آپریل";
                break;
            case 5:
                strMonthGeorgian = "می";
                break;
            case 6:
                strMonthGeorgian = "ژوئن";
                break;
            case 7:
                strMonthGeorgian = "جولای";
                break;
            case 8:
                strMonthGeorgian = "آگوست";
                break;
            case 9:
                strMonthGeorgian = "سپتامبر";
                break;
            case 10:
                strMonthGeorgian = "اکتبر";
                break;
            case 11:
                strMonthGeorgian = "نوامبر";
                break;
            case 12:
                strMonthGeorgian = "دسامبر";
                break;
        }

        georgianDate = String.valueOf(dayofmonth) + " " + strMonthGeorgian + " " + String.valueOf(year);

    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) CONTEXT.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    private void gpsData() {
        GPSTracker gps;
        double latitude, longitude;
        latitude = 0;
        longitude = 0;
        gps = new GPSTracker();
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        }
        String LocationAddress = latitude + "/" + longitude;
//        Toast.makeText(CONTEXT, LocationAddress, Toast.LENGTH_SHORT).show();

    }

}
