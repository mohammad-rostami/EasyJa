package ir.EasyJa;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by imac on 2/25/18.
 */

public class CreditActivity extends Activity {

    private Adapter_Recycler adapter_recycler;
    ArrayList<Struct> arrayList = new ArrayList<>();
    private TextView credit;
    private TextView income;
    private TextView outcome;
    private WebView web;
    private ProgressDialog progDailog;
    public CreditActivity activity;
    private String urlString;
    private LinearLayout loading_layout;
    private EditText pay_amount;
    private TextView pay;
    private AVLoadingIndicatorView progress;
    private boolean needRefresh = false;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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
        setContentView(R.layout.activity_credit);

        activity = CreditActivity.this;

        //Toolbar
        ImageView btnNavigation = (ImageView) findViewById(R.id.btnNavigation);
        TextView toolbar_main_tv_header = (TextView) findViewById(R.id.toolbar_main_tv_header);
        toolbar_main_tv_header.setText("افزایش اعتبار");
        btnNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        web = (WebView) findViewById(R.id.web);
        loading_layout = (LinearLayout) findViewById(R.id.loading_layout);
        progress = (AVLoadingIndicatorView) findViewById(R.id.progress);
        pay_amount = (EditText) findViewById(R.id.pay_amount);
        pay = (TextView) findViewById(R.id.pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pay_amount.getText().toString().length() < 3) {
                    Toast.makeText(G.CONTEXT, "مبلغ وارد شده صحیح نیست", Toast.LENGTH_SHORT).show();
                } else {
                    payRequest(pay_amount.getText().toString());
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (web.getVisibility() == View.GONE) {
            finish();
        } else if (web.getVisibility() == View.INVISIBLE) {
            finish();
        } else {
            web.setVisibility(View.GONE);
            getOptions();

        }

    }

    private void payRequest(String Amount) {

        String cellPhone = G.CUSTOMER_PHONE.getString("CUSTOMER_PHONE", "x");
        String email = G.CUSTOMER_EMAIL.getString("CUSTOMER_EMAIL", "x");


        new payAsync().execute(Urls.BASE_URL2 + Urls.PAY, Amount, "test", cellPhone, email);


    }

    private class payAsync extends Webservice.payRequest {
        @Override
        protected void onPreExecute() {
            arrayList.clear();
            progress.setVisibility(View.VISIBLE);
            progress.smoothToShow();
        }

        @Override
        protected void onPostExecute(final String result) {
            String x = result;
            progress.setVisibility(View.GONE);

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

    private void getOptions() {
        new getPayResponse().execute(Urls.BASE_URL2 + Urls.CHECK_VERIFY, urlString);


    }

    private class getPayResponse extends Webservice.checkPayment {
        @Override
        protected void onPreExecute() {
            loading_layout.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(final String result) {
            loading_layout.setVisibility(View.GONE);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(result);

                String Message = jsonObject.getString("Message");
                Boolean key = jsonObject.getBoolean("Key");
//                AdsId = jsonObject.getInt("AdsId");
                Toast.makeText(CreditActivity.this, Message, Toast.LENGTH_SHORT).show();
                if (key) {
//                    runRentService();
//                    onBackPressed();
                    G.creditRefresh = true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            getUserCredit();
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
                    Toast.makeText(CreditActivity.this, message, Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                }

                try {
                    JSONObject Value = mainObject.getJSONObject("Value");
                    String message = Value.getString("Message");
                    String KeyStr = Value.getString("Value");
                    G.CUSTOMER_CREDIT.edit().putString("CUSTOMER_CREDIT", KeyStr).apply();

                    onBackPressed();

                } catch (Exception e) {

                }


            } catch (JSONException e) {
            }
        }
    }

}
