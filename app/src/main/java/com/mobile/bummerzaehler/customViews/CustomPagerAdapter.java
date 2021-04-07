package com.mobile.bummerzaehler.customViews;

import java.util.ArrayList;

import com.mobile.bummerzaehler.R;
import com.mobile.bummerzaehler.bummerl.AllBummerls2P;
import com.mobile.bummerzaehler.bummerl.AllBummerls3P;
import com.mobile.bummerzaehler.bummerl.AllBummerls4P;
import com.mobile.bummerzaehler.bummerl.Bummerl2PController;
import com.mobile.bummerzaehler.bummerl.Bummerl3PController;
import com.mobile.bummerzaehler.bummerl.Bummerl4PController;
import com.mobile.bummerzaehler.bummerl.BummerlController;
import com.mobile.bummerzaehler.listadapter.History3PListAdapter;
import com.mobile.bummerzaehler.listadapter.History4PListAdapter;
import com.mobile.bummerzaehler.listadapter.History2PListAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.viewpager.widget.PagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

public  class CustomPagerAdapter extends PagerAdapter {

	private Activity activity;
	private ArrayList<AllBummerls2P> list2P = new ArrayList<AllBummerls2P>();
	private ArrayList<AllBummerls3P> list3P = new ArrayList<AllBummerls3P>();
	private ArrayList<AllBummerls4P> list4P = new ArrayList<AllBummerls4P>();

    public CustomPagerAdapter(Activity a) {
        activity = a;
    }
    
    private void deleteBummerl(final Activity a,final int position, final BaseAdapter adapter, 
    		final boolean is2P, final boolean is3P, final boolean is4P,
    		final ListView lv)
    {
    	DialogInterface.OnClickListener dialogClickListener = null;
    	
    		
    		dialogClickListener = new DialogInterface.OnClickListener() {
    		    @Override
    		    public void onClick(DialogInterface dialog, int which) {
    		        switch (which){
    		        case DialogInterface.BUTTON_POSITIVE:
    		        	BummerlController bc = null;
    		        	if(is2P)
    		        	{
        		        	bc = new Bummerl2PController(a);
        		            list2P.remove(position);
    		        	}
    		        	if(is3P)
    		        	{
        		        	bc = new Bummerl3PController(a);
        		            list3P.remove(position);
        		            
    		        	}
    		        	if(is4P)
    		        	{
        		        	bc = new Bummerl4PController(a);
        		            list4P.remove(position);
    		        	}
    		        	Object o = bc.removeBummerl(position);
    		            if(o == null || bc.getSize() <= 0)
    		              lv.setVisibility(View.GONE);
    		            else 
    		            	lv.setVisibility(View.VISIBLE);
    		            adapter.notifyDataSetChanged();
    		            break;

    		        case DialogInterface.BUTTON_NEGATIVE:
    		            //No button clicked
    		            break;
    		        }
    		    }
    	
    		};
    	
    	
    	
		
		AlertDialog.Builder builder = new AlertDialog.Builder(a);
		builder.setMessage("Wollen Sie diesen Eintrag wirklich löschen?").setPositiveButton("Sia", dialogClickListener)
		    .setNegativeButton("Na", dialogClickListener).show();
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        CustomPagerEnum customPagerEnum = CustomPagerEnum.values()[position];
        LayoutInflater inflater = LayoutInflater.from(activity);
        ViewGroup layout = (ViewGroup) inflater.inflate(customPagerEnum.getLayoutResId(), collection, false);
        switch(position)
        {
case 0: {
        	
        	Bummerl2PController b2P = new Bummerl2PController(activity);
        	list2P = b2P.getAllBummerls();

        	final ListView lv2P =(ListView) layout.findViewById(R.id.lvHistoryTwoPlayer);
        	final History2PListAdapter adapter2P = new History2PListAdapter(activity,list2P);
            lv2P.setAdapter(adapter2P);
            lv2P.setOnItemClickListener(new AdapterView.OnItemClickListener()
			{
				@Override
				public void onItemClick(AdapterView<?> parent,
						View view, int position, long id)
				{
					deleteBummerl(activity, position, adapter2P, true, false, false, lv2P);
				}
			});
        	break;
        }
case 1: {
	
	Bummerl3PController b3P = new Bummerl3PController(activity);
	list3P = b3P.getAllBummerls();

	final ListView lv3P =(ListView) layout.findViewById(R.id.lvHistoryThreePlayer);
	final History3PListAdapter adapter3P = new History3PListAdapter(activity,list3P);
    lv3P.setAdapter(adapter3P);
    lv3P.setOnItemClickListener(new AdapterView.OnItemClickListener()
	{
		@Override
		public void onItemClick(AdapterView<?> parent,
				View view, int position, long id)
		{
			deleteBummerl(activity, position, adapter3P, false, true, false, lv3P);
		}
	});
	break;
}
        case 2: {
        	
        	Bummerl4PController b4P = new Bummerl4PController(activity);
        	list4P = b4P.getAllBummerls();

        	final ListView lv4P =(ListView) layout.findViewById(R.id.lvHistoryFourPlayer);
        	final History4PListAdapter adapter4P = new History4PListAdapter(activity,list4P);
            lv4P.setAdapter(adapter4P);
            lv4P.setOnItemClickListener(new AdapterView.OnItemClickListener()
			{
				@Override
				public void onItemClick(AdapterView<?> parent,
						View view, int position, long id)
				{
					deleteBummerl(activity, position, adapter4P, false, false, true, lv4P);
				}
			});
        	break;
        }
        }
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return CustomPagerEnum.values().length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        CustomPagerEnum customPagerEnum = CustomPagerEnum.values()[position];
        return activity.getString(customPagerEnum.getTitleResId());
    }
}
