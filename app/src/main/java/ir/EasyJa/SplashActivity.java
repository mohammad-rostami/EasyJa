package ir.EasyJa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;

import org.json.JSONException;
import org.json.JSONObject;

import io.fabric.sdk.android.Fabric;
import ir.EasyJa.Helper.GPSTracker;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by imac on 2/25/18.
 */

public class SplashActivity extends AppCompatActivity {
    private String oneSignalUserID;
    private String oneSignalRegistrationID;
    private GPSTracker gps;
    private double LATITUDE;
    private double LONGTITUDE;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);

        Boolean isRegistered = G.IS_REGISTERED.getBoolean("IS_REGISTERED", false);
        if (isRegistered) {
            getToken();
        } else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, RentActivity.class);
                    SplashActivity.this.startActivity(intent);
                    finish();
                }
            },2000);
        }






//        if (G.isNetworkAvailable()) {
//            boolean firstTimeConnection = G.FIRST_TIME_REQUEST.getBoolean("FIRST_TIME_CONNECTION", false);
//            if (!firstTimeConnection) {
//                OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
//                    @Override
//                    public void idsAvailable(String userId, String registrationId) {
//                        oneSignalUserID = userId;
//                        if (registrationId != null)
//                            oneSignalRegistrationID = registrationId;
//
//                        Log.d("userId", oneSignalUserID);
//                        Log.d("registerId", oneSignalRegistrationID);
//                        String uniqueId = G.ANDROID_ID;
//                        String androidVersion = G.ANDROID_VERSION;
//                        String appVersion = G.APP_VERSION;
//                        String deviceName = G.DEVICE_NAME;
//
//                        new Async().execute(Urls.BASE_URL + Urls.REGISTER_SESSION, oneSignalUserID, oneSignalRegistrationID, uniqueId, androidVersion, appVersion, deviceName);
//
//                    }
//                });
//                G.FIRST_TIME_REQUEST.edit().putBoolean("FIRST_TIME_CONNECTION", true).apply();
//            } else {
//                loadNextPage();
//            }
//        } else {
//            Toast.makeText(G.CONTEXT, "اتصال با اینترنت قطع شده است", Toast.LENGTH_SHORT).show();
//        }
//        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//        boolean network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//
//        Location location;
//
//        if (network_enabled) {
//
//            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//            location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//
//            if (location != null) {
//                double longitude = location.getLongitude();
//                double latitude = location.getLatitude();
////                Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
//
//            }
//        }
    }

    public void getToken() {
        String grant_type = "password";
        String phone = G.CUSTOMER_PHONE.getString("CUSTOMER_PHONE", "");
        String code = G.CUSTOMER_ACTIVATION_CODE.getString("CUSTOMER_ACTIVATION_CODE", "");
        new tokenAsync().execute(Urls.GET_TOKEN, grant_type, phone, code);
    }


    public class tokenAsync extends Webservice.token {
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

//                Intent intent = new Intent(SplashActivity.this, RentActivity.class);
//                SplashActivity.this.startActivity(intent);
//                finish();

                Intent intent = new Intent(SplashActivity.this, RentActivity.class);
                SplashActivity.this.startActivity(intent);
                finish();

            } catch (Exception e) {

            }

        }
    }


    private class Async extends Webservice.sessionConnection {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(final String result) {
            try {
                JSONObject sessionData = new JSONObject(result);
                String sessionId = sessionData.getString("SessionId");
                String userToken = sessionData.getString("UserToken");

                G.AUTHENTICATIONS_SESSION.edit().putString("SESSION", sessionId).apply();
                G.AUTHENTICATIONS_TOKEN.edit().putString("TOKEN", userToken).apply();

                loadNextPage();


//                new getProductGroupAsync().execute(Urls.BASE_URL + Urls.GET_PRODUCT_GROUP, sessionId, userToken, String.valueOf(G.LATITUDE), String.valueOf(G.LONGTITUDE));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadNextPage() {
        if (registerCheck()) {
//            Intent intent = new Intent(SplashActivity.this, DashBoardActivity.class);
//            SplashActivity.this.startActivity(intent);
        } else {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            SplashActivity.this.startActivity(intent);
        }
        finish();

    }

    private class getProductGroupAsync extends Webservice.getProductGroup {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(final String result) {
            G.PRODUCT_GROUPS = result;
            G.GROUPS.edit().putString("GROUPS", result).apply();

            String sessionId = G.AUTHENTICATIONS_SESSION.getString("SESSION", "x");
            String userToken = G.AUTHENTICATIONS_TOKEN.getString("TOKEN", "x");

            new getTargetGroupAsync().execute(Urls.BASE_URL + Urls.GET_TARGET_GROUP, sessionId, userToken, String.valueOf(G.LATITUDE), String.valueOf(G.LONGTITUDE), "1396", "1");

        }
    }

    private class getTargetGroupAsync extends Webservice.getTargetGroup {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(final String result) {
            G.TARGET_GROUPS = result;
            G.TARGET_GROUPS_SP.edit().putString("TARGET_GROUPS_SP", result).apply();


            if (registerCheck()) {
//                Intent intent = new Intent(SplashActivity.this, DashBoardActivity.class);
//                SplashActivity.this.startActivity(intent);
            } else {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                SplashActivity.this.startActivity(intent);
            }
            finish();


        }
    }

    private Boolean registerCheck() {
        Boolean isRegistered = G.IS_REGISTERED.getBoolean("IS_REGISTERED", false);


        return isRegistered;
    }


}
