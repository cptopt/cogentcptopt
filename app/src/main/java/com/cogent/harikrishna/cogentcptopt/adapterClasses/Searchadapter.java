package com.cogent.harikrishna.cogentcptopt.adapterClasses;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cogent.harikrishna.cogentcptopt.subTabs.CompaniesTab;
import com.cogent.harikrishna.cogentcptopt.subTabs.Jobstab;
import com.cogent.harikrishna.cogentcptopt.subTabs.Salariestab;

/**
 * Created by Hari Krishna on 7/15/2016.
 */
public class Searchadapter extends FragmentStatePagerAdapter {

    int mNumoftabs;

    public Searchadapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumoftabs = NumOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Jobstab job = new Jobstab();
                return job;
            case 1:
                CompaniesTab companies = new CompaniesTab();
                return companies;
            case 2:
                Salariestab salaries = new Salariestab();
                return salaries;
        }

        return null;
    }

    @Override
    public int getCount() {
        return mNumoftabs;
    }
}

