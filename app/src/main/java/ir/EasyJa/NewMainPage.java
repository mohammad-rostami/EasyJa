package ir.EasyJa;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class NewMainPage extends FragmentActivity {

    public LinearLayoutManager manager;
    ArrayList<AdsModel> arrayList = new ArrayList<>();
    private Adapter_Recycler_Ads adapter_recycler;
    private String names[] = new String[]{"اجاره می دهم", "اجاره می کنم"};
    private int[] images = new int[]{R.drawable.rent, R.drawable.rent1};
    private ViewPager pager;
    private boolean scrolling;
    private Typeface typeface;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_main_page);


        for (int i = 0; i < 2; i++) {
            AdsModel adsModel = new AdsModel();
            adsModel.drawable = images[i];
            adsModel.name = names[i];
            if (i == 0) {
                adsModel.isSelected = true;
            }
            adsModel.isSelected = false;
            arrayList.add(adsModel);
        }

        AssetManager am = getApplicationContext().getApplicationContext().getAssets();

        Typeface typeface = Typeface.createFromAsset(getAssets(),  "iransans_bold.ttf");

        final TextView title = (TextView) findViewById(R.id.title);
        title.setTypeface(typeface);
        final RecyclerView list = (RecyclerView) findViewById(R.id.list_layout);
        manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
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
                list.smoothScrollToPosition(position);

//                pager.setCurrentItem(position);
//                title.setText(arrayList.get(position).name);
//                list.smoothScrollToPosition(position);
//                LinearLayoutManager layoutManager = ((LinearLayoutManager) list.getLayoutManager());
//                int totalVisibleItems = layoutManager.findLastVisibleItemPosition() - layoutManager.findFirstVisibleItemPosition();
//                int centeredItemPosition = totalVisibleItems / 2;
//                list.setScrollY(centeredItemPosition);

            }

            @Override
            public void onEdit(int position) {

            }
        }, 7, false);
        list.setAdapter(adapter_recycler);
        list.setLayoutManager(manager);

        pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                list.smoothScrollToPosition(i);
                title.setText(arrayList.get(i).name);
//                LinearLayoutManager layoutManager = ((LinearLayoutManager) list.getLayoutManager());
//                int totalVisibleItems = layoutManager.findLastVisibleItemPosition() - layoutManager.findFirstVisibleItemPosition();
//                int centeredItemPosition = totalVisibleItems / 2;
//                list.setScrollY(centeredItemPosition);
//                pager.setCurrentItem(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(list);

        list.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                scrolling = true;

                final int c = manager.findLastCompletelyVisibleItemPosition();
                Log.d("position", String.valueOf(c));
                Log.d("position1", String.valueOf(i));
                Log.d("position2", String.valueOf(i1));
                Log.d("position3", String.valueOf(i2));
                Log.d("position4", String.valueOf(i3));

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!scrolling) {
                            try {
                                pager.setCurrentItem(c);
//                                title.setText(arrayList.get(c).name);

//                                LinearLayoutManager layoutManager = ((LinearLayoutManager) list.getLayoutManager());
//                                int totalVisibleItems = layoutManager.findLastVisibleItemPosition() - layoutManager.findFirstVisibleItemPosition();
//                                int centeredItemPosition = totalVisibleItems / 2;
//                                list.smoothScrollToPosition(c);
//                                list.setScrollY(centeredItemPosition);
                            } catch (Exception e) {
                            }
                        }
                    }
                }, 100);

                scrolling = false;
            }


        });


    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {

                case 0:
                    return new FragmentCategory1();
                case 1:
                    return new FragmentCategory2();

                default:
                    return new FragmentCategory1();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }


}
