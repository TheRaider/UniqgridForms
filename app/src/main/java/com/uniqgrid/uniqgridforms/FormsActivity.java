package com.uniqgrid.uniqgridforms;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class FormsActivity extends AppCompatActivity {

    TabLayout slidingTabs;
    ViewPager vpForms;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forms);

         toolbar = (Toolbar)findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);
         if(getSupportActionBar()!=null){
             getSupportActionBar().setDisplayHomeAsUpEnabled(true);
             getSupportActionBar().setTitle("Survey");
         }

        slidingTabs = (TabLayout) findViewById(R.id.slidingTabs);
        vpForms = (ViewPager) findViewById(R.id.vpForms);

        setupViewPager();

        slidingTabs.setupWithViewPager(vpForms);




    }


    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new BasicInfoFragment(),  "1"+"\n"+"Basic Information");
        adapter.addFragment(new PropertyDetailsFragment(), "2"+"\n"+"Property Details");
        adapter.addFragment(new PowerManagementFragment(), "3"+"\n"+"Power Management and Quality");
        adapter.addFragment(new ElectricityBillsFragment(), "4"+"\n"+"Electricity Bills");
        adapter.addFragment(new CompleteFormFragment(), "5"+"\n"+"Complete");
        vpForms.setAdapter(adapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
