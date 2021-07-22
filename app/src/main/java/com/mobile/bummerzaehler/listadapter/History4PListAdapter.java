package com.mobile.bummerzaehler.listadapter;

/**
 * Created with IntelliJ IDEA.
 * User: gangsta
 * Date: 10.10.13
 * Time: 15:35
 * To change this template use File |  Settings | File Templates.
 */

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.mobile.bummerzaehler.R;
import com.mobile.bummerzaehler.bummerl.AllBummerls4P;
import com.mobile.bummerzaehler.listadapter.History3PListAdapter.ViewHolder;


/********* Adapter class extends with BaseAdapter and implements with OnClickListener ************/
public class History4PListAdapter extends BaseAdapter {

	/*********** Declare Used Variables *********/
	private Activity activity;
	private ArrayList<AllBummerls4P> data;
	private static LayoutInflater inflater = null;
	public Resources res;
	AllBummerls4P tempValues = null;
	int i = 0;

	/************* CustomAdapter Constructor *****************/
	public History4PListAdapter(Activity a, ArrayList<AllBummerls4P> d) {

		/********** Take passed values **********/
		activity = a;
		data = d;

		/*********** Layout inflator to call external xml layout () ***********/
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	/******** What is the size of Passed Arraylist Size ************/
	public int getCount() {
		return data.size();
	}

	public AllBummerls4P getItem(int position) {
		return data.get(position);
	}

	public long getItemId(int position) {
		return position;
	}
	public void clear(){
		data.clear();
	}

	public void addData(ArrayList<AllBummerls4P> data1) {
		data.addAll(data1);
		notifyDataSetChanged();
	}
	
	

	/********* Create a holder Class to contain inflated xml file elements *********/
	public static class ViewHolder {

		public TextView p1;
		public TextView p2;
		public TextView p3;
		public TextView p4;
		public TextView bummerlT1;
		public TextView bummerlT2;
		public TextView schneiderT1;
		public TextView schneiderT2;

	}

	/****** Depends upon data size called for each row , Create each ListView row *****/
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		ViewHolder holder;

		if (convertView == null) {

			/****** Inflate drawer_list_item.xml file for each row ( Defined below ) *******/
			vi = inflater.inflate(R.layout.history_list_item_four_player, null);

			/****** View Holder Object to contain drawer_list_item.xml file elements ******/

			holder = new ViewHolder();
			holder.p1 = (TextView) vi
					.findViewById(R.id.tvHistoryFourPlayerP1);
			holder.p2 = (TextView) vi
					.findViewById(R.id.tvHistoryFourPlayerP2);
			holder.p3 = (TextView) vi
					.findViewById(R.id.tvHistoryFourPlayerP3);
			holder.p4 = (TextView) vi
					.findViewById(R.id.tvHistoryFourPlayerP4);
			holder.bummerlT1 = (TextView) vi
					.findViewById(R.id.tvHistoryFourPlayerBummerlT1);
			holder.bummerlT2 = (TextView) vi
					.findViewById(R.id.tvHistoryFourPlayerBummerlT2);
			holder.schneiderT1 = (TextView) vi
					.findViewById(R.id.tvHistoryFourPlayerSchneiderT1);
			holder.schneiderT2 = (TextView) vi
					.findViewById(R.id.tvHistoryFourPlayerSchneiderT2);

			/************ Set holder with LayoutInflater ************/
			vi.setTag(holder);
		} else
			holder = (ViewHolder) vi.getTag();

		if (data.size() <= 0) {
			setVisibility(View.GONE, holder);

		} else {
			/***** Get each Model object from Arraylist ********/
			tempValues = null;
			tempValues = (AllBummerls4P) data.get(position);

			/************ Set Model values in Holder elements ***********/
			try {
				setVisibility(View.VISIBLE, holder);
				holder.p1.setText(tempValues.getT1()[0].getName());
				holder.p2.setText(tempValues.getT1()[1].getName());
				holder.p3.setText(tempValues.getT2()[0].getName());
				holder.p4.setText(tempValues.getT2()[1].getName());
				holder.bummerlT1.setText(tempValues.getBummerlT1()+" * ");
				holder.bummerlT2.setText(tempValues.getBummerlT2()+" * ");
				holder.schneiderT1.setText(tempValues.getSchneiderT1()+" * ");
				holder.schneiderT2.setText(tempValues.getSchneiderT2()+" * ");
				
				
			} catch (Exception e) {
				System.out.println("history 4p adapter exc:"
						+ e.getMessage());
			}

		}
		return vi;
	}
	private void setVisibility(int visibility, ViewHolder holder)
	{
		holder.bummerlT1.setVisibility(visibility);
		holder.bummerlT2.setVisibility(visibility);
		holder.schneiderT1.setVisibility(visibility);
		holder.schneiderT2.setVisibility(visibility);
	}
}