package com.cogent.harikrishna.cogentcptopt.registration;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.cogent.harikrishna.cogentcptopt.R;
import com.cogent.harikrishna.cogentcptopt.adapterClasses.Signinadapter;

/**
 * Created by Hari Krishna on 7/15/2016.
 */
public class Signin extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final TabLayout tablayout = (TabLayout) findViewById(R.id.tablayout1);
        tablayout.addTab(tablayout.newTab().setText("SIGN-IN"));
        tablayout.addTab(tablayout.newTab().setText("REGISTER"));

        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager1);
        final Signinadapter adapter = new Signinadapter(getSupportFragmentManager(),tablayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }
}
