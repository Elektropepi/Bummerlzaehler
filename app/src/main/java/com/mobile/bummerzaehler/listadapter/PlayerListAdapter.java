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
public class PlayerListAdapter extends BaseAdapter {

	/*********** Declare Used Variables *********/
	private Activity activity;
	private ArrayList<PlayerListModel> data;
	private static LayoutInflater inflater = null;
	public Resources res;
	PlayerListModel tempValues = null;
	int i = 0;

	/************* CustomAdapter Constructor *****************/
	public PlayerListAdapter(Activity a, ArrayList d, Resources resLocal) {

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

	public PlayerListModel getItem(int position) {
		return data.get(position);
	}

	public long getItemId(int position) {
		return position;
	}
	public void clear(){
		data.clear();
	}
	public void removePlayerName(String name)
	{
		for(int i = 0; i< data.size(); i++)
		{
			if(data.get(i).getPlayerName() == name)
				{
				data.remove(i);
				
				notifyDataSetChanged();
				}
		}
	}

	public void addData(ArrayList<PlayerListModel> data1) {
		data.addAll(data1);
		notifyDataSetChanged();
	}
	
	

	/********* Create a holder Class to contain inflated xml file elements *********/
	public static class ViewHolder {

		public TextView tvName;
		public TextView tvBummerl2P;
		public TextView tvSchneider2P;
		public TextView tvAverage2P;
		public TextView tvWonGames2P;
		public TextView tvBummerl3P;
		public TextView tvSchneider3P;
		public TextView tvAverage3P;
		public TextView tvWonGames3P;
		public TextView tvBummerl4P;
		public TextView tvSchneider4P;
		public TextView tvAverage4P;
		public TextView tvWonGames4P;

	}

	/****** Depends upon data size called for each row , Create each ListView row *****/
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		ViewHolder holder;

		if (convertView == null) {

			/****** Inflate drawer_list_item.xml file for each row ( Defined below ) *******/
			vi = inflater.inflate(R.layout.player_list_item, null);

			/****** View Holder Object to contain drawer_list_item.xml file elements ******/

			holder = new ViewHolder();
			holder.tvName= (TextView) vi
					.findViewById(R.id.tvPlayerListItemName);
			holder.tvBummerl2P= (TextView) vi
					.findViewById(R.id.tvPlayerListItem2PBummerl);
			holder.tvSchneider2P= (TextView) vi
					.findViewById(R.id.tvPlayerListItem2PSchneider);
			holder.tvAverage2P= (TextView) vi
					.findViewById(R.id.tvPlayerListItem2PAverage);
			holder.tvBummerl3P= (TextView) vi
					.findViewById(R.id.tvPlayerListItem3PBummerl);
			holder.tvSchneider3P= (TextView) vi
					.findViewById(R.id.tvPlayerListItem3PSchneider);
			holder.tvAverage3P= (TextView) vi
					.findViewById(R.id.tvPlayerListItem3PAverage);
			holder.tvBummerl4P= (TextView) vi
					.findViewById(R.id.tvPlayerListItem4PBummerl);
			holder.tvSchneider4P= (TextView) vi
					.findViewById(R.id.tvPlayerListItem4PSchneider);
			holder.tvAverage4P= (TextView) vi
					.findViewById(R.id.tvPlayerListItem4PAverage);
			holder.tvWonGames2P= (TextView) vi
					.findViewById(R.id.tvPlayerListItem2PWonGames);
			holder.tvWonGames3P= (TextView) vi
					.findViewById(R.id.tvPlayerListItem3PWonGames);
			holder.tvWonGames4P= (TextView) vi
					.findViewById(R.id.tvPlayerListItem4PWonGames);

			/************ Set holder with LayoutInflater ************/
			vi.setTag(holder);
		} else
			holder = (ViewHolder) vi.getTag();

		if (data.size() <= 0) {
			//holder.title.setText("No Data found!");

		} else {
			/***** Get each Model object from Arraylist ********/
			tempValues = null;
			tempValues = (PlayerListModel) data.get(position);

			/************ Set Model values in Holder elements ***********/
			try {
				holder.tvName.setText(tempValues.getPlayerName()+"");
				holder.tvBummerl2P.setText(tempValues.getBummerl2P()+" * ");
				holder.tvSchneider2P.setText(tempValues.getSchneider2P()+" * ");
				holder.tvAverage2P.setText(String.format( "%.0f", tempValues.getAverage2P()*100)+"%");
				holder.tvBummerl3P.setText(tempValues.getBummerl3P()+" * ");
				holder.tvSchneider3P.setText(tempValues.getSchneider3P()+" * ");
				holder.tvAverage3P.setText(String.format( "%.0f", tempValues.getAverage3P()*100)+"%");
				holder.tvBummerl4P.setText(tempValues.getBummerl4P()+" * ");
				holder.tvSchneider4P.setText(tempValues.getSchneider4P()+" * ");
				holder.tvAverage4P.setText(String.format( "%.0f", tempValues.getAverage4P()*100)+"%");
				String wg2 = tempValues.getWonGames2P() == 1 ? tempValues.getWonGames2P()+" Sieg":tempValues.getWonGames2P()+" Siege";
				String wg3 = tempValues.getWonGames3P() == 1 ? tempValues.getWonGames3P()+" Sieg":tempValues.getWonGames3P()+" Siege";
				String wg4 = tempValues.getWonGames4P() == 1 ? tempValues.getWonGames4P()+" Sieg":tempValues.getWonGames4P()+" Siege";
				holder.tvWonGames2P.setText(wg2);
				holder.tvWonGames3P.setText(wg3);
				holder.tvWonGames4P.setText(wg4);
				
				
			} catch (Exception e) {
				System.out.println("playerlistadapter exc:"
						+ e.getMessage());
			}

		}
		return vi;
	}

}