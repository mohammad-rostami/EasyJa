package ir.EasyJa;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;



public class AccountActivity extends AppCompatActivity {

    RelativeLayout lin_credit_card;
    LinearLayout progresbar;
    LinearLayout lin_change_pas;

    TextView txt_user_name;
    TextView txt_phone;
    TextView txt_email;
    TextView txt_credit_ecount;
    EditText edt_shaba;
    SharedPreferences pref;
    TextView txt_car_model, txt_service;

    CardView gardesh_hesab;
    CustomTextView btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //Toolbar
        ImageView btnNavigation = (ImageView) findViewById(R.id.btnNavigation);
        TextView toolbar_main_tv_header = (TextView) findViewById(R.id.toolbar_main_tv_header);
        toolbar_main_tv_header.setText("کیف پول");
        btnNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final EditText edt_shaba = (EditText) findViewById(R.id.edt_shaba);
        TextView btn_submit = (TextView) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_shaba.getText().toString().length() < 2) {
                    final String typedText = edt_shaba.getText().toString();
                    edt_shaba.setText("");
                    edt_shaba.setHint("شماره حساب" + " صحیح را وارد کنید");
                    edt_shaba.setHintTextColor(getResources().getColor(R.color.Red));
                    edt_shaba.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.vibrate));
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            edt_shaba.setText(typedText);
                        }
                    }, 1000);
                }else {
                    String cellPhone =  G.CUSTOMER_MOBILE.getString("CUSTOMER_MOBILE", "x");
                    new newAsync().execute(Urls.BASE_URL + Urls.REGISTER_ACCOUNT, cellPhone, edt_shaba.getText().toString());

                }
            }
        });


    }// end of onCreate

    private class newAsync extends Webservice.addAccount {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String result) {
            String x = result;

            try {
                JSONObject jsonObject = new JSONObject(result);
                String key = jsonObject.getString("key");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


}// end of class
