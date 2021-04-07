package com.mobile.bummerzaehler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mobile.bummerzaehler.bummerl.AllBummerls3P;
import com.mobile.bummerzaehler.bummerl.AllBummerls4P;
import com.mobile.bummerzaehler.bummerl.Bummerl3PController;
import com.mobile.bummerzaehler.bummerl.Bummerl4PController;
import com.mobile.bummerzaehler.helper.HelperClass;
import com.mobile.bummerzaehler.listadapter.History3PListAdapter;
import com.mobile.bummerzaehler.listadapter.History4PListAdapter;
import com.mobile.bummerzaehler.player.Player;
import com.mobile.bummerzaehler.player.PlayerController;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class ThreePlayerStartActivity extends ParentActivity {
	PlayerController pController;
	Spinner sp1, sp2, sp3;
	ArrayList<Player> players;
	public static String PLAYER_NAMES = "extra_key_player_names_3p";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	setContentView(ViewTyp.THREE_PLAYER_START);
        super.onCreate(savedInstanceState); 
        sp1 = (Spinner) findViewById(R.id.spPlayerOne3P);
        sp2 = (Spinner) findViewById(R.id.spPlayerTwo3P);
        sp3 = (Spinner) findViewById(R.id.spPlayerThree3P);
    }
    
    @Override
    protected void onResume()
    {
    	 pController= new PlayerController(this);
         initSpinners();
    	super.onResume();
    }
    
    private void start()
    {
    	Player p1 = (Player)sp1.getSelectedItem();
    	Player p2 = (Player)sp2.getSelectedItem();
    	Player p3 = (Player)sp3.getSelectedItem();
    	
    	
    	if((p1.getName() == p2.getName() || 
    			p1.getName() == p3.getName() || 
    			p2.getName() == p3.getName()) &&
    			p1.getName()!= "---" &&
    			p2.getName()!= "---" &&
    			p3.getName()!= "---" )
    	{
    		HelperClass.showShortToast(getResources().getString(R.string.same_player_name), this);
    		return;
    	}
    	if(!pController.contains(p1.getName()))
    	{
    		HelperClass.showShortToast(p1.getName() +" gibts net.. wer zum teifl is des??", this);
    		return;
    	}
    	else if(!pController.contains(p2.getName()))
    	{
    		HelperClass.showShortToast(p2.getName() +" gibts net.. wer zum teifl is des??", this);
    		return;
    	}
    	else if(!pController.contains(p3.getName()))
    	{
    		HelperClass.showShortToast(p3.getName() +" gibts net.. wer zum teifl is des??", this);
    		return;
    	}
    	Intent nextIntent = null;
    	String extra = p1.getName()+";"+p2.getName()+";"+p3.getName();
		nextIntent = new Intent(ThreePlayerStartActivity.this, ThreePlayerActivity.class);
		nextIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    	nextIntent.putExtra(PLAYER_NAMES, extra);
		startActivity(nextIntent);
		overridePendingTransition(R.anim.right_to_left,
				R.anim.noanimation);
    }
    
    private void initSpinners()
    {
    	players = pController.getAllPlayers();
        Collections.sort(players);
    	players.add(0,new Player("---"));
    	players.add(new Player("+ "+getResources().getString(R.string.create_player)));
        
    	final int size = players.size();
    	ArrayAdapter adapter = new ArrayAdapter(this, R.layout.simple_spinner_item, players);
    	AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
    		 @Override
    		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
    			   if(position == size -1)
    			   {
    				   //last item
    				   Intent nextIntent = null;
    					nextIntent = new Intent(ThreePlayerStartActivity.this, CreatePlayerActivity.class);
    					nextIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    					startActivity(nextIntent);
    					overridePendingTransition(R.anim.right_to_left,
    							R.anim.noanimation);
    					//((Spinner)parentView).setSelection(0);
    			   }
    			   Player p1 =  (Player) sp1.getSelectedItem();
					Player p2 =(Player) sp2.getSelectedItem();
					Player p3 =  (Player) sp3.getSelectedItem();
					p1 = p1.getName().equals("---") ? null : p1;
					p2 = p2.getName().equals("---") ? null : p2;
					p3 = p3.getName().equals("---") ? null : p3;
					Bummerl3PController bc = new Bummerl3PController(ThreePlayerStartActivity.this);
					ArrayList<AllBummerls3P> list3P = bc.getAllBummerlsByPlayers(p1, p2, p3);
					

		        	ListView lv3P =(ListView) findViewById(R.id.lvThreePlayerStartSuggestions);
		        	final History3PListAdapter adapter3P = new History3PListAdapter(ThreePlayerStartActivity.this,list3P);
		            lv3P.setAdapter(adapter3P);
		            lv3P.setOnItemClickListener(new AdapterView.OnItemClickListener()
					{
						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id)
						{
							AllBummerls3P ab = adapter3P.getItem(position);
							
							sp1.setSelection(indexOf(ab.getP1()));
							sp2.setSelection(indexOf(ab.getP2()));
							sp3.setSelection(indexOf(ab.getP3()));
						}
					});
    		    }

    		    @Override
    		    public void onNothingSelected(AdapterView<?> parentView) {
    		        // your code here
    		    }

    	};
    	
    	sp1.setAdapter(adapter);
    	sp1.setOnItemSelectedListener(itemSelectedListener);
    	sp2.setAdapter(adapter);
    	sp2.setOnItemSelectedListener(itemSelectedListener);
    	sp3.setAdapter(adapter);
    	sp3.setOnItemSelectedListener(itemSelectedListener);
    }
    private int indexOf(Player p)
    {
    	for(int i = 0; i< players.size(); i++)
    		{
    		if(players.get(i).getName().equals(p.getName()))
    			return i;
    		}
    	return 0;
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item)
	{

		if (item.getItemId() == R.id.action_start_game) {
			start();
		}

		return super.onOptionsItemSelected(item);
	}
}
