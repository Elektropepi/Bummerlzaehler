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
import android.opengl.Visibility;
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
import com.mobile.bummerzaehler.bummerl.AllBummerls3P;
import com.mobile.bummerzaehler.bummerl.AllBummerls4P;


/********* Adapter class extends with BaseAdapter and implements with OnClickListener ************/
public class History3PListAdapter extends BaseAdapter {

	/*********** Declare Used Variables *********/
	private Activity activity;
	private ArrayList<AllBummerls3P> data;
	private static LayoutInflater inflater = null;
	public Resources res;
	AllBummerls3P tempValues = null;
	int i = 0;

	/************* CustomAdapter Constructor *****************/
	public History3PListAdapter(Activity a, ArrayList<AllBummerls3P> d) {

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

	public AllBummerls3P getItem(int position) {
		return data.get(position);
	}

	public long getItemId(int position) {
		return position;
	}
	public void clear(){
		data.clear();
	}

	public void addData(ArrayList<AllBummerls3P> data1) {
		data.addAll(data1);
		notifyDataSetChanged();
	}
	
	

	/********* Create a holder Class to contain inflated xml file elements *********/
	public static class ViewHolder {

		public TextView p1;
		public TextView p2;
		public TextView p3;
		public TextView bummerlP1;
		public TextView bummerlP2;
		public TextView bummerlP3;
		public TextView schneiderP1;
		public TextView schneiderP2;
		public TextView schneiderP3;

	}

	/****** Depends upon data size called for each row , Create each ListView row *****/
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		ViewHolder holder;

		if (convertView == null) {

			/****** Inflate drawer_list_item.xml file for each row ( Defined below ) *******/
			vi = inflater.inflate(R.layout.history_list_item_three_player, null);

			/****** View Holder Object to contain drawer_list_item.xml file elements ******/

			holder = new ViewHolder();
			holder.p1 = (TextView) vi
					.findViewById(R.id.tvHistoryThreePlayerP1);
			holder.p2 = (TextView) vi
					.findViewById(R.id.tvHistoryThreePlayerP2);
			holder.p3 = (TextView) vi
					.findViewById(R.id.tvHistoryThreePlayerP3);
			holder.bummerlP1 = (TextView) vi
					.findViewById(R.id.tvHistoryThreePlayerBummerlP1);
			holder.bummerlP2 = (TextView) vi
					.findViewById(R.id.tvHistoryThreePlayerBummerlP2);
			holder.bummerlP3 = (TextView) vi
					.findViewById(R.id.tvHistoryThreePlayerBummerlP3);
			holder.schneiderP1 = (TextView) vi
					.findViewById(R.id.tvHistoryThreePlayerSchneiderP1);
			holder.schneiderP2 = (TextView) vi
					.findViewById(R.id.tvHistoryThreePlayerSchneiderP2);
			holder.schneiderP3 = (TextView) vi
					.findViewById(R.id.tvHistoryThreePlayerSchneiderP3);

			/************ Set holder with LayoutInflater ************/
			vi.setTag(holder);
		} else
			holder = (ViewHolder) vi.getTag();

		if (data.size() <= 0) {
			setVisibility(View.GONE, holder);

		} else {
			/***** Get each Model object from Arraylist ********/
			tempValues = null;
			tempValues = (AllBummerls3P) data.get(position);

			/************ Set Model values in Holder elements ***********/
			try {
				setVisibility(View.VISIBLE, holder);
				holder.p1.setText(tempValues.getP1().getName());
				holder.p2.setText(tempValues.getP2().getName());
				holder.p3.setText(tempValues.getP3().getName());
				holder.bummerlP1.setText(tempValues.getBummerlP1()+" * ");
				holder.bummerlP2.setText(tempValues.getBummerlP2()+" * ");
				holder.bummerlP3.setText(tempValues.getBummerlP3()+" * ");
				holder.schneiderP1.setText(tempValues.getSchneiderP1()+" * ");
				holder.schneiderP2.setText(tempValues.getSchneiderP2()+" * ");
				holder.schneiderP3.setText(tempValues.getSchneiderP3()+" * ");
				
				
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
		holder.bummerlP3.setVisibility(visibility);
		holder.schneiderP1.setVisibility(visibility);
		holder.schneiderP2.setVisibility(visibility);
		holder.schneiderP3.setVisibility(visibility);
	}

}