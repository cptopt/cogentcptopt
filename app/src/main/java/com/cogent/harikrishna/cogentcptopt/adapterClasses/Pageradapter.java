package com.cogent.harikrishna.cogentcptopt.adapterClasses;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cogent.harikrishna.cogentcptopt.tabsfragments.Company;
import com.cogent.harikrishna.cogentcptopt.tabsfragments.Profile;
import com.cogent.harikrishna.cogentcptopt.tabsfragments.Saved;
import com.cogent.harikrishna.cogentcptopt.tabsfragments.Search;
import com.cogent.harikrishna.cogentcptopt.tabsfragments.Xtras;

/**
 * Created by Hari Krishna on 7/15/2016.
 */
public class Pageradapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public Pageradapter(FragmentManager fm,int NumOfTabs) {
        super(fm);
        this.mNumOfTabs =NumOfTabs;
        //super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                Search search = new Search();
                return search;
            case 1:
                Saved saved = new Saved();
                return saved;
            case 2:
                Company company = new Company();
                return company;
            case 3:
                Profile profile = new Profile();
                return profile;
            case 4:
                Xtras xtras = new Xtras();
                return xtras;

        }
        return null;
    }

    @Override
    public int getCount() {return mNumOfTabs;}
}
