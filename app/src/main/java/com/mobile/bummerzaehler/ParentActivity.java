package com.mobile.bummerzaehler;




import java.util.ArrayList;

import com.mobile.bummerzaehler.listadapter.NavigationListAdapter;
import com.mobile.bummerzaehler.listadapter.NavigationListModel;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.legacy.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ParentActivity extends Activity {

	private DrawerLayout drawerLayout;
	private ListView drawerListView;
	private ActionBarDrawerToggle drawerToggle;
	private CharSequence drawerTitle;
	protected NavigationListAdapter navAdapter;
	private ArrayList<NavigationListModel> drawerMenuListModel = new ArrayList<NavigationListModel>();
	protected ViewTyp viewTyp;
	
	protected void setContentView(ViewTyp vt)
	{
		viewTyp = vt;
		switch (viewTyp)
		{
		case MAIN:
			setContentView(R.layout.activity_main);
			break;
		case CREATE_PLAYER:
			setContentView(R.layout.create_player);
			break;
		case TWO_PLAYER:
			setContentView(R.layout.two_player);
			break;
		case TWO_PLAYER_START:
			setContentView(R.layout.two_player_start);
			break;
		case THREE_PLAYER:
			setContentView(R.layout.three_player);
			break;
		case THREE_PLAYER_START:
			setContentView(R.layout.three_player_start);
			break;
		case OPTIONS:
			setContentView(R.layout.options);
			break;
		case FOUR_PLAYER:
			setContentView(R.layout.four_player);
			break;
		case FOUR_PLAYER_START:
			setContentView(R.layout.four_player_start);
			break;
		case PLAYERS:
			setContentView(R.layout.players);
			break;
		case HISTORY:
			setContentView(R.layout.history);
			break;
		}
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		initDrawerLayout();
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.navigation_main, menu);
		return super.onCreateOptionsMenu(menu);
	}
    
    @Override
	public boolean onPrepareOptionsMenu(Menu menu)
	{
		menu.findItem(R.id.action_start_game).setVisible( viewTyp == ViewTyp.TWO_PLAYER_START ||
				viewTyp == ViewTyp.THREE_PLAYER_START ||
				viewTyp == ViewTyp.FOUR_PLAYER_START ||
				viewTyp == ViewTyp.CREATE_PLAYER);

		menu.findItem(R.id.action_save_game).setVisible( viewTyp == ViewTyp.TWO_PLAYER||
				viewTyp == ViewTyp.THREE_PLAYER ||
				viewTyp == ViewTyp.FOUR_PLAYER );
		return super.onPrepareOptionsMenu(menu);
	}
    
	/************************* Drawerlayout **************************************/

	protected void initNavMenu()
	{
		String[] titleArray = getResources().getStringArray(
				R.array.menu_array);
		drawerMenuListModel = new ArrayList<NavigationListModel>();
		drawerMenuListModel
				.add(new NavigationListModel(
						R.drawable.home,
						titleArray[0]));
		drawerMenuListModel.add(new NavigationListModel(
				R.drawable.twoplayers, titleArray[1]));
		drawerMenuListModel.add(new NavigationListModel(
				R.drawable.threeplayers, titleArray[2]));
		drawerMenuListModel
				.add(new NavigationListModel(
						R.drawable.fourplayers,
						titleArray[3]));
		drawerMenuListModel.add(new NavigationListModel(
				R.drawable.players, titleArray[4]));
		drawerMenuListModel.add(new NavigationListModel(
				R.drawable.history, titleArray[5]));
		drawerMenuListModel.add(new NavigationListModel(
				R.drawable.options, titleArray[6]));
	}
	
	private void initDrawerLayout()
	{
		drawerTitle = getTitle();

		// Get the DrawerLayout which surrounds the UI
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		// Get the listView to display the items you want to show in the drawer
		drawerListView = (ListView) findViewById(R.id.left_drawer);

		// Initialize the Drawer data (The ListView will be displayed
		// with a CustomListAdapter as described in a previous chapter)
		initNavMenu();

		drawerListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener()
				{

					@Override
					public void onItemClick(AdapterView<?> parent,
							View view, int position, long id)
					{

						selectMenuDrawerItem(position);
					}
				});
		// set a custom shadow that overlays the main content when the drawer
		// opens
		drawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		setListAdapter();

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		//getActionBar().setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon

		drawerToggle = new ActionBarDrawerToggle(// //Add objects for
													// Actionbar!
				this, /* host Activity */
				drawerLayout, /* DrawerLayout object */
				R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
				R.string.drawer_open, /*
									 * "open drawer" description for
									 * accessibility
									 */
				R.string.drawer_close /*
									 * "close drawer" description for
									 * accessibility
									 */
		)
		{
			public void onDrawerClosed(View view)
			{
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView)
			{
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};
		drawerLayout.setDrawerListener(drawerToggle);

	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{

		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (drawerToggle.onOptionsItemSelected(item))
		{
			return true;
		}
		
	   return super.onOptionsItemSelected(item);
		
	}

	/*
	 * The click listner for ListView in the navigation drawer private class
	 * DrawerItemClickListener implements ListView.OnItemClickListener {
	 * 
	 * @Override public void onItemClick(AdapterView<?> parent, View view, int
	 * position, long id) { selectMenuDrawerItem(position); }
	 * 
	 * }
	 */

	private void closeDrawer()
	{
		View drawerView = (View) findViewById(R.id.left_drawer);
		drawerLayout.closeDrawer(drawerView);
	}

	public void selectMenuDrawerItem(int position)
	{
		
		// 0 - MAIN; 1 - zwara; 2 - dreier; 3- vierer; 4- spieler
		// <anmelden>
		Intent nextIntent = null;
		boolean isCloseDrawer = true;
		switch (position)
		{
		case 0:
			if (viewTyp != ViewTyp.MAIN)
			{
				nextIntent = new Intent(this, MainActivity.class);
				nextIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			}
			break;
		case 1:
			if (viewTyp != ViewTyp.TWO_PLAYER_START)
			{
				nextIntent = new Intent(this, TwoPlayerStartActivity.class);
				nextIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			}
			break;
		case 2:
			if (viewTyp != ViewTyp.THREE_PLAYER_START)
			{
				nextIntent = new Intent(this, ThreePlayerStartActivity.class);
				nextIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			}
			break;
		case 3:
			if (viewTyp != ViewTyp.FOUR_PLAYER_START)
			{
				nextIntent = new Intent(this, FourPlayerStartActivity.class);
				nextIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			}
			break;
		case 4:
			if (viewTyp != ViewTyp.PLAYERS)
			{
				nextIntent = new Intent(this, PlayersActivity.class);
				nextIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			}
			break;
		case 5:
			if (viewTyp != ViewTyp.HISTORY)
			{
				nextIntent = new Intent(this, HistoryActivity.class);
				nextIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			}
			break;
		case 6:
			if (viewTyp != ViewTyp.OPTIONS)
			{
				nextIntent = new Intent(this, OptionsActivity.class);
				nextIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			}
			break;
		
		}
		if (isCloseDrawer)
			closeDrawer();
		
		if(nextIntent != null)
		{
			startActivity(nextIntent);
			overridePendingTransition(R.anim.right_to_left,
					R.anim.noanimation);
		}

	}

	@Override
	public void setTitle(CharSequence title)
	{
		drawerTitle = title;
		getActionBar().setTitle(drawerTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		drawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggle
		drawerToggle.onConfigurationChanged(newConfig);
	}

	private void setListAdapter()
	{
		// To change body of created methods use File | Settings | File
		// Templates.

		// Insert Data into ArrayList CustomListViewValuesArr
		Resources res = getResources();

		/**************** Create Custom Adapter *********/
		navAdapter = new NavigationListAdapter(this,
				drawerMenuListModel, res);
		drawerListView.setAdapter(navAdapter);
	}

	protected void updateNavListAdapter()
	{
		navAdapter.clear();
		navAdapter.addData(drawerMenuListModel);
	}
}
