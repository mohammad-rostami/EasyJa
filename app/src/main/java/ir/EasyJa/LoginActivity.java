package ir.EasyJa;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v13.app.ActivityCompat;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by imac on 2/25/18.
 */

public class LoginActivity extends Activity {

    private Dialog dialog_verify;
    private String oneSignalUserID;
    private String oneSignalRegistrationID;
    private TextView dialog_phone_txt1;
    private TextView dialog_phone_counter;
    private static EditText dialog_phone_edt_txt;
    private EditText dialog_phone_edt_txt_code;
    private TextView dialog_phone_resendCode;
    private Dialog dialog_confirm;
    private TextView dialog_confirm_resendCode;
    private static TextView dialog_confirm_txt1;
    private TextView dialog_confirm_counter;
    private EditText dialog_confirm_edt_txt;
    public static EditText dialog_confirm_edt_txt_code;
    private ImageView dialog_phone_btnNavigation;
    private ImageView dialog_confirm_btnNavigation;
    private TextView dialog_confirm_call_support;
    private CountDownTimer countDownTimer;
    private TextView dialog_confirm_register;
    private EditText dialog_confirm_edt_txt_phone;
    private TextView toolbar_main_tv_header;
    public static LoginActivity LOGIN_ACTIVITY;
    public static Animation animation;
    private String[] mPermission;
    private static final int REQUEST_CODE_PERMISSION = 2;
    private static String from = " ";


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LOGIN_ACTIVITY = LoginActivity.this;

        try {
            Intent intent = getIntent();
            from = intent.getStringExtra("base");
            if (from == null) {
                from = " ";
            }
        } catch (Exception e) {
        }

//        mPermission = new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.ACCESS_FINE_LOCATION,};
        mPermission = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,};

        toolbar_main_tv_header = (TextView) findViewById(R.id.toolbar_main_tv_header);
        toolbar_main_tv_header.setText("شماره موبایل");

        dialog_phone_resendCode = (TextView) findViewById(R.id.resendCode);
        TextView dialog_phone_txt = (TextView) findViewById(R.id.dialog_txt);
        dialog_phone_txt1 = (TextView) findViewById(R.id.dialog_txt1);
        dialog_phone_counter = (TextView) findViewById(R.id.dialog_counter);
        dialog_phone_edt_txt = (EditText) findViewById(R.id.dialog_edt_txt);
        dialog_phone_edt_txt_code = (EditText) findViewById(R.id.dialog_edt_txt_code);
        dialog_phone_btnNavigation = (ImageView) findViewById(R.id.btnNavigation);


        // Dialog_Confirm
        dialog_confirm = new Dialog(LoginActivity.this);
        dialog_confirm.setContentView(R.layout.dialog_phone_confirm);
        dialog_confirm_resendCode = (TextView) dialog_confirm.findViewById(R.id.resendCode);
        dialog_confirm_call_support = (TextView) dialog_confirm.findViewById(R.id.call_support);
        TextView dialog_confirm_txt = (TextView) dialog_confirm.findViewById(R.id.dialog_txt);
        dialog_confirm_txt1 = (TextView) dialog_confirm.findViewById(R.id.dialog_txt1);
        dialog_confirm_counter = (TextView) dialog_confirm.findViewById(R.id.dialog_counter1);
        dialog_confirm_edt_txt = (EditText) dialog_confirm.findViewById(R.id.dialog_edt_txt);
        dialog_confirm_btnNavigation = (ImageView) dialog_confirm.findViewById(R.id.btnNavigation);
        dialog_confirm_edt_txt_code = (EditText) dialog_confirm.findViewById(R.id.dialog_edt_txt_code);
        dialog_confirm_edt_txt_phone = (EditText) dialog_confirm.findViewById(R.id.dialog_edt_txt_phone);
        TextView dialog_confirm_cancel = (TextView) dialog_confirm.findViewById(R.id.cancel);
        dialog_confirm_register = (TextView) dialog_confirm.findViewById(R.id.register);
        dialog_confirm_btnNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_confirm.dismiss();
            }
        });
        dialog_confirm_call_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "02177647504", null));
                startActivity(intent);
            }
        });

        dialog_confirm_resendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sessionId = G.AUTHENTICATIONS_SESSION.getString("SESSION", "nothing");
                String userToken = G.AUTHENTICATIONS_TOKEN.getString("TOKEN", "nothing");

                Log.d("session", sessionId);
                Log.d("token", userToken);
                new sendCode().execute(Urls.BASE_URL + Urls.SEND_CODE, sessionId, userToken, dialog_phone_edt_txt.getText().toString());

                countDownTimer.start();
                dialog_confirm_resendCode.setVisibility(View.GONE);


            }
        });

        countDownTimer = new CountDownTimer(300000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                dialog_confirm_counter.setText(String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
//                dialogVerify_txt_counter.setText(String.valueOf("00:" + millisUntilFinished / 1000));
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                dialog_confirm_counter.setText("00:00");
//                dialogVerify_txt_counter.setVisibility(View.GONE);
                dialog_confirm_resendCode.setVisibility(View.VISIBLE);
            }

        };


        dialog_phone_resendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (dialog_phone_edt_txt.getText().toString().length() == 11) {

                    if (G.isNetworkAvailable()) {

                        if (ActivityCompat.checkSelfPermission(LoginActivity.this, mPermission[0]) != MockPackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(LoginActivity.this, mPermission, REQUEST_CODE_PERMISSION);

                            // If any permission aboe not allowed by user, this condition will execute every tim, else your else part will work
                        } else {
                            new sendCode().execute(Urls.BASE_URL + Urls.SEND_CODE, dialog_phone_edt_txt.getText().toString());

                            dialog_confirm_edt_txt_phone.setText(dialog_phone_edt_txt.getText().toString());
                            dialog_confirm.show();
                            dialog_confirm_resendCode.setVisibility(View.GONE);
                            countDownTimer.start();
                            dialog_confirm_edt_txt_code.requestFocus();
                            Window window = dialog_confirm.getWindow();
                            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        }
                    } else {
                        Toast.makeText(G.CONTEXT, "اتصال با اینترنت قطع شده است", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    dialog_phone_edt_txt.setText("");
                    dialog_phone_edt_txt.setHint("شماره تلفن خود را وارد کنید");
                }
            }
        });

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.vibrate);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dialog_phone_txt1.setTextColor(getResources().getColor(R.color.Red));
                dialog_phone_txt1.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        dialog_confirm_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dialog_phone_edt_txt.getText().toString().length() == 11 && dialog_confirm_edt_txt_code.getText().toString().length() > 3) {

                    if (G.isNetworkAvailable()) {
                        runSessionService();
                    } else {
                        Toast.makeText(G.CONTEXT, "اتصال با اینترنت قطع شده است", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    dialog_confirm_txt1.setText("کد عضویت اشتباه است");
                    dialog_confirm_txt1.startAnimation(animation);

                }

            }
        });
    }


    public static void runSessionService() {
        String phone = dialog_phone_edt_txt.getText().toString();
        String memberCode = dialog_confirm_edt_txt_code.getText().toString();
        G.CUSTOMER_ACTIVATION_CODE.edit().putString("CUSTOMER_ACTIVATION_CODE", memberCode).apply();
        new checkCode().execute(Urls.BASE_URL + Urls.CHECK_CODE, phone, memberCode);

    }

    public static void getUserDetail() {
        String phone = dialog_phone_edt_txt.getText().toString();
        new UserDetailAsync().execute(Urls.BASE_URL + Urls.GET_USER_DETAIL, phone);

    }

    public static void getUserCredit() {
        String phone = dialog_phone_edt_txt.getText().toString();
        new CreditAsync().execute(Urls.BASE_URL + Urls.GET_USER_CREDIT, phone);

    }


    public static void getToken() {
        String grant_type = "password";
        String phone = G.CUSTOMER_PHONE.getString("CUSTOMER_PHONE", "");
        String code = G.CUSTOMER_ACTIVATION_CODE.getString("CUSTOMER_ACTIVATION_CODE", "");
        new tokenAsync().execute(Urls.GET_TOKEN, grant_type, phone, code);

    }


    public static class tokenAsync extends Webservice.token {
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

                getUserDetail();



            } catch (Exception e) {

            }

        }
    }

    private class sendCode extends Webservice.sendCode {
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
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();

                } catch (Exception e) {

                }

                try {
                    JSONObject Value = mainObject.getJSONObject("Value");
                    String message = Value.getString("Message");
//                    String Key = Value.getString("Key");
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();

                } catch (Exception e) {

                }

            } catch (JSONException e) {

            }


        }
    }

    private static class checkCode extends Webservice.checkCode {
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
                    Toast.makeText(G.CONTEXT, message, Toast.LENGTH_SHORT).show();

                } catch (Exception e) {

                }

                try {
                    JSONObject Value = mainObject.getJSONObject("Value");
                    String message = Value.getString("Message");
                    String KeyStr = Value.getString("Key");
                    G.KEY.edit().putString("KEY", KeyStr).apply();
                    String phone = dialog_phone_edt_txt.getText().toString();
                    G.CUSTOMER_PHONE.edit().putString("CUSTOMER_PHONE", phone).apply();


                    boolean IsRegisterd = Value.getBoolean("IsRegisterd");
                    if (!IsRegisterd) {
                        LOGIN_ACTIVITY.finish();
                        Intent intent = new Intent(LOGIN_ACTIVITY, RegisterActivity.class);
                        intent.putExtra("base", from);
                        LOGIN_ACTIVITY.startActivity(intent);
                    } else {
                        G.IS_REGISTERED.edit().putBoolean("IS_REGISTERED", true).apply();
                        G.needToRefresh = true;

//                        getUserCredit();
                        getToken();


                    }
                    Toast.makeText(G.CONTEXT, message, Toast.LENGTH_SHORT).show();

                } catch (Exception e) {

                }

            } catch (JSONException e) {
            }

        }
    }

    private static class UserDetailAsync extends Webservice.userDetail {
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
                    Toast.makeText(G.CONTEXT, message, Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                }

                try {
                    JSONObject Value = mainObject.getJSONObject("Value");
                    String message = Value.getString("Message");
                    String KeyStr = Value.getString("Key");
                    String imageUrl = Value.getString("ImageAddress");

                    JSONObject x = mainObject.getJSONObject("Instance");
                    String id = x.getString("Id");
                    String name = x.getString("Name");
                    String mobile = x.getString("Mobile");
                    String phone = x.getString("PhoneNumber");
                    String Email = x.getString("Email");
                    String lat = x.getString("Lat");
                    String lng = x.getString("Lng");
                    String credit = x.getString("Credit");
                    String image = x.getString("ImageUrl");

                    try {
                        G.CUSTOMER_IMAGE.edit().putString("CUSTOMER_IMAGE", imageUrl + image).apply();
//                        Glide.with(G.CONTEXT).load(imageUrl + image).placeholder(R.drawable.place_holder).into(img);
                    } catch (Exception e) {
                    }


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


                    if (from.equals("Category")) {
                        Intent intent = new Intent(LOGIN_ACTIVITY, AddNewAds.class);
                        LOGIN_ACTIVITY.startActivity(intent);
                    } else if (from.equals("AdsInner")) {
                        Intent intent = new Intent(LOGIN_ACTIVITY, AdsActivity_inner.class);
                        LOGIN_ACTIVITY.startActivity(intent);
                    } else if (from.equals("Ads")) {

                    } else {
                        Intent intent = new Intent(LOGIN_ACTIVITY, RentActivity.class);
                        LOGIN_ACTIVITY.startActivity(intent);
                    }
//                    if (G.fromRent) {
//                        Intent intent = new Intent(LOGIN_ACTIVITY, AddNewAds.class);
//                        LOGIN_ACTIVITY.startActivity(intent);
//                        G.fromRent = false;
//                        LOGIN_ACTIVITY.finish();
//                    } else {
                    LOGIN_ACTIVITY.finish();


                } catch (Exception e) {

                }

            } catch (JSONException e) {
            }
        }
    }

    private static class CreditAsync extends Webservice.sendCode {
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
                    Toast.makeText(G.CONTEXT, message, Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                }

                try {
                    JSONObject Value = mainObject.getJSONObject("Value");
                    String message = Value.getString("Message");
                    String KeyStr = Value.getString("Value");
                    G.CUSTOMER_CREDIT.edit().putString("CUSTOMER_CREDIT", KeyStr).apply();


                    getUserDetail();

                } catch (Exception e) {

                }

            } catch (JSONException e) {
            }
        }
    }

    public static void verificationCodeSetter(String string) {
        dialog_confirm_edt_txt_code.setText("");
        dialog_confirm_edt_txt_code.setText(string);

        if (dialog_confirm_edt_txt_code.getText().toString().length() < 4) {
        } else {
            if (dialog_phone_edt_txt.getText().toString().length() == 11 && dialog_confirm_edt_txt_code.getText().toString().length() > 3) {

                if (G.isNetworkAvailable()) {
                    runSessionService();
                } else {
                    Toast.makeText(G.CONTEXT, "اتصال با اینترنت قطع شده است", Toast.LENGTH_SHORT).show();
                }
            } else {
                dialog_confirm_txt1.setText("کد عضویت اشتباه است");
                dialog_confirm_txt1.startAnimation(animation);

            }

        }

    }
}