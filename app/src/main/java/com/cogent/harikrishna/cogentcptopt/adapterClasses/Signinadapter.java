package com.cogent.harikrishna.cogentcptopt.adapterClasses;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cogent.harikrishna.cogentcptopt.registration.Register;
import com.cogent.harikrishna.cogentcptopt.tabsfragments.Login;

/**
 * Created by Hari Krishna on 7/15/2016.
 */
public class Signinadapter extends FragmentStatePagerAdapter {

    int mNumOFTabs;

    public Signinadapter(FragmentManager fm, int NumOfTabs){
        super(fm);
        this.mNumOFTabs = NumOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Login logined = new Login();
                return logined;
            case 1:
                Register reg = new Register();
                return reg;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOFTabs;
    }
}
