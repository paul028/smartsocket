package com.TeamNonat.SmartPowerPlug;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Welcome extends android.support.v4.app.Fragment {
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View x = inflater.inflate(R.layout.welcome_layout, null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);

        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
        viewPager.setCurrentItem(1, false);

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return x;

    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new Tab_Left();
                case 1:
                    return new Tab_Center();
                case 2:
                    return new Tab_Right();
            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "Plug Controller";
                case 1:
                    return "Home";
                case 2:
                    return "Data Usage";
            }
            return null;
        }
    }

    class ControlSwitch extends FragmentActivity implements View.OnClickListener {
        Button switch1, switch2, switch3;
        TextView switch1in, switch2in, switch3in;


        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.tab_left_layout);

            switch1 = (Button) findViewById(R.id.switch1);
            switch1.setOnClickListener(this);
            switch2 = (Button) findViewById(R.id.switch2);
            switch2.setOnClickListener(this);
            switch3 = (Button) findViewById(R.id.switch3);
            switch3.setOnClickListener(this);

            switch1in = (TextView) findViewById(R.id.switch1in);
            switch2in = (TextView) findViewById(R.id.switch2in);
            switch3in = (TextView) findViewById(R.id.switch3in);


        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.switch1:
                    if(v==switch1){
                        Toast.makeText(getApplicationContext(),"Plug 1 On", Toast.LENGTH_SHORT).show();
                    }
                case R.id.switch2:
                    Toast.makeText(getApplicationContext(),"Plug 2 On", Toast.LENGTH_SHORT).show();
                case R.id.switch3:
                    Toast.makeText(getApplicationContext(),"Plug 3 On", Toast.LENGTH_SHORT).show();
            }




        }
    }
}