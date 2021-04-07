package com.mobile.bummerzaehler;




import java.util.ArrayList;

import com.mobile.bummerzaehler.bummerl.AllBummerls4P;
import com.mobile.bummerzaehler.bummerl.Bummerl4PController;
import com.mobile.bummerzaehler.customViews.CustomPagerAdapter;
import com.mobile.bummerzaehler.customViews.CustomPagerEnum;
import com.mobile.bummerzaehler.helper.HelperClass;
import com.mobile.bummerzaehler.listadapter.History4PListAdapter;
import com.mobile.bummerzaehler.listadapter.NavigationListAdapter;
import com.mobile.bummerzaehler.listadapter.NavigationListModel;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class HistoryActivity extends ParentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(ViewTyp.HISTORY); 
        super.onCreate(savedInstanceState);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.vpHistory);
        viewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.
                        getActionBar().setSelectedNavigationItem(position);
                    }
                });

        viewPager.setAdapter(new CustomPagerAdapter(this));
        
        final ActionBar actionBar = getActionBar();

        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
            	viewPager.setCurrentItem(tab.getPosition());

            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // hide the given tab
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // probably ignore this event
            }
        };

        for (int i = 0; i < CustomPagerEnum.values().length; i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(getResources().getString(CustomPagerEnum.values()[i].getTitleResId()))
                            .setTabListener(tabListener));
        }
        


    }
    
    
   
}
