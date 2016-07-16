package com.cogent.harikrishna.cogentcptopt;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.cogent.harikrishna.cogentcptopt.adapterClasses.Searchadapter;

/**
 * Created by Hari Krishna on 7/15/2016.
 */
public class Nextpage extends AppCompatActivity
{
    Toolbar toolbar,toolbar2;

    SearchView sv1,sv2;
    private SearchView.OnQueryTextListener queryTextListener;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nextpage);
        toolbar = (Toolbar)findViewById(R.id.toolbar11);


        sv1 = (SearchView) findViewById(R.id.sv1);
        sv1.setIconifiedByDefault(true);
        sv1.setQueryHint("Jobs");


        sv2 = (SearchView) findViewById(R.id.sv2);
        sv2.setIconifiedByDefault(true);
        sv2.setQueryHint("City, State or Zip");


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Jobs");
        toolbar.inflateMenu(R.menu.searchjob);
        //  toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
        //  @Override
        //   public boolean onMenuItemClick(MenuItem item) {
        //        onOptionsItemSelected(item);
        //     return false;
        //     }
        // });

        toolbar2 = (Toolbar) findViewById(R.id.toolbar2);


        toolbar2.inflateMenu(R.menu.searchlocation);
        // toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
        //   @Override
        //  public boolean onMenuItemClick(MenuItem item) {
        //      onOptionsItemSelected(item);
        //      return false;
        //    }
        //});





        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Jobs"));
        tabLayout.addTab(tabLayout.newTab().setText("Companies"));
        tabLayout.addTab(tabLayout.newTab().setText("Salaries"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final ViewPager pager = (ViewPager) findViewById(R.id.pager);
        final Searchadapter pagerAdapter = new Searchadapter(getSupportFragmentManager(),tabLayout.getTabCount());

        pager.setAdapter(pagerAdapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }

    //  @Override
    //  public boolean onCreateOptionsMenu(Menu menu) {
    //   MenuInflater inflater = getMenuInflater();
    //  inflater.inflate(R.menu.searchjob, menu);
    // SearchManager searchManager =
    //          (SearchManager) getSystemService(Context.SEARCH_SERVICE);
    //  searchView =
    //    (SearchView) menu.findItem(R.id.job).getActionView();
    //  searchView.setSearchableInfo(
    //       searchManager.getSearchableInfo(getComponentName()));
    //   searchView.setOnQueryTextListener(queryTextListener);
//inflater.inflate(R.menu.searchlocation,menu);
    //SearchManager searchManager1 =
    //    (SearchManager) getSystemService(Context.SEARCH_SERVICE);
    //  searchView1 =
    //         (SearchView) menu.findItem(R.id.location).getActionView();
    // searchView1.setSearchableInfo(
    //  searchManager1.getSearchableInfo(getComponentName()));
    // searchView1.setOnQueryTextListener(queryTextListener);
    // return true;
    //  }




}
