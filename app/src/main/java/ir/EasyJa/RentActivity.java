package ir.EasyJa;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lukedeighton.wheelview.WheelView;
import com.lukedeighton.wheelview.adapter.WheelAdapter;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Mohammad on 5/22/2018.
 */

public class RentActivity extends AppCompatActivity  implements FragmentNavigation.OnFragmentInteractionListener {

    TextView toolbar_main_tv_header;
    private WheelView wheelView;
    private float angleDegree;
    private CountDownTimer countDownTimer;
    private DrawerLayout drawer;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (G.needToRefresh){
            Intent intent = getIntent();
            finish();
            startActivity(intent);
            G.needToRefresh=false;
        }
        if (G.creditRefresh) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);

            G.creditRefresh = false;
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(Gravity.RIGHT)){
            drawer.closeDrawer(Gravity.RIGHT);
        }else {
            finish();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);

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
        toolbar_main_tv_header.setText("اجاره");

        wheelView = (WheelView) findViewById(R.id.wheelview);
        wheelView.setAngle(0);

        wheelView.setOnWheelItemClickListener(new WheelView.OnWheelItemClickListener() {
            @Override
            public void onWheelItemClick(WheelView parent, int position, boolean isSelected) {
                //the position in the adapter and whether it is closest to the selection angle
                Log.d("position ", String.valueOf(position));

                if (position == 0) {
//                    Toast.makeText(RentActivity.this, "3 clicked", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RentActivity.this, PropertyActivity.class);
                    intent.putExtra("item", "3");
                    RentActivity.this.startActivity(intent);
                    G.GOING_TO_RENT.edit().putBoolean("GOING_TO_RENT", true).apply();

                }
                if (position == 1) {
//                    Toast.makeText(RentActivity.this, "4 clicked", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RentActivity.this, PropertyActivity.class);
                    intent.putExtra("item", "4");
                    RentActivity.this.startActivity(intent);
                    G.GOING_TO_RENT.edit().putBoolean("GOING_TO_RENT", false).apply();
                }

            }
        });

        wheelView.setAdapter(new WheelAdapter() {
            public Drawable drawable;

            @Override
            public Drawable getDrawable(int position) {
                if (position == 0) {
                    drawable = getResources().getDrawable(R.mipmap.ic_circle_green_2);
                } else if (position == 1) {
                    drawable = getResources().getDrawable(R.mipmap.ic_circle_green_1);
                } else {
                    drawable = getResources().getDrawable(R.drawable.bg_circle_hidden);
                }
                return drawable;
            }

            @Override
            public int getCount() {
                return 2;
            }
        });


        countDownTimer = new CountDownTimer(50, 10) { //40000 milli seconds is total time, 1000 milli seconds is time interval

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {

//                setSelected((int) angleDegree);


            }
        };


//        wheelView.setOnWheelAngleChangeListener(new WheelView.OnWheelAngleChangeListener() {
//            @Override
//            public void onWheelAngleChange(final float angle) {
//                //the new angle of the wheel
//
//                try {
//                    countDownTimer.cancel();
//                } catch (Exception e) {
//                }
//                countDownTimer.start();
//
//                angleDegree = angle;
//
//
//                if (angle == 23) {
//                    countDownTimer.cancel();
//                }
//
//
//                Log.d("angle ", String.valueOf(angle));
//                if (angle > 55) {
//                    wheelView.setAngle(55);
////                    setSelected(45);
//                } else if (angle < -15) {
//                    wheelView.setAngle(-15);
////                    setSelected(-5);
//                } else {
////                    setSelected((int) angle);
//
//                }
//
//
//            }
//        });
    }

    public void setSelected(int no) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(no, 23);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                wheelView.setAngle((Float) valueAnimator.getAnimatedValue());
            }
        });
        valueAnimator.setDuration(500);//animation duration
        valueAnimator.start();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
