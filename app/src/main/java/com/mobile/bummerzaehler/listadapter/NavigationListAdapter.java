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


/********* Adapter class extends with BaseAdapter and implements with OnClickListener ************/
public class NavigationListAdapter extends BaseAdapter {

	/*********** Declare Used Variables *********/
	private Activity activity;
	private ArrayList<NavigationListModel> data;
	private static LayoutInflater inflater = null;
	public Resources res;
	NavigationListModel tempValues = null;
	int i = 0;

	/************* CustomAdapter Constructor *****************/
	public NavigationListAdapter(Activity a, ArrayList d, Resources resLocal) {

		/********** Take passed values **********/
		activity = a;
		data = d;
		res = resLocal;

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

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}
	public void clear(){
		data.clear();
	}

	public void addData(ArrayList<NavigationListModel> data1) {
		data.addAll(data1);
		notifyDataSetChanged();
	}
	
	

	/********* Create a holder Class to contain inflated xml file elements *********/
	public static class ViewHolder {

		public ImageView image;
		public TextView title;

	}

	/****** Depends upon data size called for each row , Create each ListView row *****/
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		ViewHolder holder;

		if (convertView == null) {

			/****** Inflate drawer_list_item.xml file for each row ( Defined below ) *******/
			vi = inflater.inflate(R.layout.navigation_drawer_list_item, null);

			/****** View Holder Object to contain drawer_list_item.xml file elements ******/

			holder = new ViewHolder();
			holder.title = (TextView) vi
					.findViewById(R.id.tvNavigationItemTitle);
			holder.image = (ImageView) vi
					.findViewById(R.id.ivNavigationItemImage);

			/************ Set holder with LayoutInflater ************/
			vi.setTag(holder);
		} else
			holder = (ViewHolder) vi.getTag();

		if (data.size() <= 0) {
			holder.title.setText("No Data found!");

		} else {
			/***** Get each Model object from Arraylist ********/
			tempValues = null;
			tempValues = (NavigationListModel) data.get(position);

			/************ Set Model values in Holder elements ***********/
			try {
				holder.title.setText(tempValues.getTitle());
				holder.image.setBackgroundResource(tempValues.getImageId());
				
				
			} catch (Exception e) {
				System.out.println("navigationlistadapter exc:"
						+ e.getMessage());
			}

		}
		return vi;
	}

}