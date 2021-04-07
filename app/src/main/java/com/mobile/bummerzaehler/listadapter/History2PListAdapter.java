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
import com.mobile.bummerzaehler.bummerl.AllBummerls2P;
import com.mobile.bummerzaehler.bummerl.AllBummerls4P;
import com.mobile.bummerzaehler.listadapter.History4PListAdapter.ViewHolder;


/********* Adapter class extends with BaseAdapter and implements with OnClickListener ************/
public class History2PListAdapter extends BaseAdapter {

	/*********** Declare Used Variables *********/
	private Activity activity;
	private ArrayList<AllBummerls2P> data;
	private static LayoutInflater inflater = null;
	public Resources res;
	AllBummerls2P tempValues = null;
	int i = 0;

	/************* CustomAdapter Constructor *****************/
	public History2PListAdapter(Activity a, ArrayList<AllBummerls2P> d) {

		/********** Take passed values **********/
		activity = a;
		data = d;

		/*********** Layout inflator to call external xml layout () ***********/
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	/******** What is the size of Passed Arraylist Size ************/
	public int getCount() {

		if (data.size() <= 0)
			return 1;
		return data.size();
	}

	public AllBummerls2P getItem(int position) {
		return data.get(position);
	}

	public long getItemId(int position) {
		return position;
	}
	public void clear(){
		data.clear();
	}

	public void addData(ArrayList<AllBummerls2P> data1) {
		data.addAll(data1);
		notifyDataSetChanged();
	}
	
	

	/********* Create a holder Class to contain inflated xml file elements *********/
	public static class ViewHolder {

		public TextView p1;
		public TextView p2;
		public TextView bummerlP1;
		public TextView bummerlP2;
		public TextView schneiderP1;
		public TextView schneiderP2;

	}

	/****** Depends upon data size called for each row , Create each ListView row *****/
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		ViewHolder holder;

		if (convertView == null) {

			/****** Inflate drawer_list_item.xml file for each row ( Defined below ) *******/
			vi = inflater.inflate(R.layout.history_list_item_two_player, null);

			/****** View Holder Object to contain drawer_list_item.xml file elements ******/

			holder = new ViewHolder();
			holder.p1 = (TextView) vi
					.findViewById(R.id.tvHistoryTwoPlayerP1);
			holder.p2 = (TextView) vi
					.findViewById(R.id.tvHistoryTwoPlayerP2);
			holder.bummerlP1 = (TextView) vi
					.findViewById(R.id.tvHistoryTwoPlayerBummerlP1);
			holder.bummerlP2 = (TextView) vi
					.findViewById(R.id.tvHistoryTwoPlayerBummerlP2);
			holder.schneiderP1 = (TextView) vi
					.findViewById(R.id.tvHistoryTwoPlayerSchneiderP1);
			holder.schneiderP2 = (TextView) vi
					.findViewById(R.id.tvHistoryTwoPlayerSchneiderP2);

			/************ Set holder with LayoutInflater ************/
			vi.setTag(holder);
		} else
			holder = (ViewHolder) vi.getTag();

		if (data.size() <= 0) {
			setVisibility(View.GONE, holder);

		} else {
			/***** Get each Model object from Arraylist ********/
			tempValues = null;
			tempValues = (AllBummerls2P) data.get(position);

			/************ Set Model values in Holder elements ***********/
			try {
				setVisibility(View.VISIBLE, holder);
				holder.p1.setText(tempValues.getP1().getName());
				holder.p2.setText(tempValues.getP2().getName());
				holder.bummerlP1.setText(tempValues.getBummerlP1()+" * ");
				holder.bummerlP2.setText(tempValues.getBummerlP2()+" * ");
				holder.schneiderP1.setText(tempValues.getSchneiderP1()+" * ");
				holder.schneiderP2.setText(tempValues.getSchneiderP2()+" * ");
				
				
			} catch (Exception e) {
				System.out.println("history 4p adapter exc:"
						+ e.getMessage());
			}

		}
		return vi;
	}
	private void setVisibility(int visibility, ViewHolder holder)
	{
		holder.bummerlP1.setVisibility(visibility);
		holder.bummerlP2.setVisibility(visibility);
		holder.schneiderP1.setVisibility(visibility);
		holder.schneiderP2.setVisibility(visibility);
	}
}