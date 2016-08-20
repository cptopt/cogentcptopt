package com.cogent.harikrishna.cogentcptopt;

import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.cogent.harikrishna.cogentcptopt.adapterClasses.Pageradapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        final TabLayout tabLayout = (TabLayout) findViewById(R.id.Tabs);

        //tabLayout.isClickable();

        tabLayout.addTab(tabLayout.newTab().setText("").setIcon(R.drawable.mag));
        tabLayout.addTab(tabLayout.newTab().setText("").setIcon(R.drawable.heart));
        tabLayout.addTab(tabLayout.newTab().setText("").setIcon(R.drawable.building));
        tabLayout.addTab(tabLayout.newTab().setText("").setIcon(R.drawable.pro));

        tabLayout.addTab(tabLayout.newTab().setText("").setIcon(R.drawable.add));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager pager =(ViewPager)findViewById(R.id.Viewpage);
        final Pageradapter pageradapter = new Pageradapter(getSupportFragmentManager(),tabLayout.getTabCount());

        pager.setAdapter(pageradapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                int tabIconColor = ContextCompat.getColor(getApplicationContext(),R.color.tabSelected);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);

                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {



                int tabIconColor = ContextCompat.getColor(getApplicationContext(),R.color.tabUnSelected);
                tab.getIcon().setColorFilter(tabIconColor,PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


}