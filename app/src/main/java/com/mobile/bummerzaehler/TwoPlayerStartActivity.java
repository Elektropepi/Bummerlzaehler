package com.mobile.bummerzaehler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mobile.bummerzaehler.bummerl.AllBummerls2P;
import com.mobile.bummerzaehler.bummerl.AllBummerls4P;
import com.mobile.bummerzaehler.bummerl.Bummerl2PController;
import com.mobile.bummerzaehler.bummerl.Bummerl4PController;
import com.mobile.bummerzaehler.helper.HelperClass;
import com.mobile.bummerzaehler.listadapter.History2PListAdapter;
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

public class TwoPlayerStartActivity extends ParentActivity {
	PlayerController pController;
	Spinner sp1, sp2;
	ArrayList<Player> players;
	public static String PLAYER_NAMES = "extra_key_player_names_2p";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	setContentView(ViewTyp.TWO_PLAYER_START);
        super.onCreate(savedInstanceState); 
        sp1 = (Spinner) findViewById(R.id.spPlayerOne2P);
        sp2 = (Spinner) findViewById(R.id.spPlayerTwo2P);
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
    	
    	
    	if(p1.getName()!= "---" && p1.getName() == p2.getName())
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

    	Intent nextIntent = null;
    	String extra = p1.getName()+";"+p2.getName();
		nextIntent = new Intent(TwoPlayerStartActivity.this, TwoPlayerActivity.class);
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
    					nextIntent = new Intent(TwoPlayerStartActivity.this, CreatePlayerActivity.class);
    					nextIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    					startActivity(nextIntent);
    					overridePendingTransition(R.anim.right_to_left,
    							R.anim.noanimation);
    					//((Spinner)parentView).setSelection(0);
    			   }
    			   Player p1 =  (Player) sp1.getSelectedItem();
					Player p2 =(Player) sp2.getSelectedItem();
					p1 = p1.getName().equals("---") ? null : p1;
					p2 = p2.getName().equals("---") ? null : p2;
					Bummerl2PController bc = new Bummerl2PController(TwoPlayerStartActivity.this);
					ArrayList<AllBummerls2P> list2P = bc.getAllBummerlsByPlayers(p1, p2);
					

		        	ListView lv2P =(ListView) findViewById(R.id.lvTwoPlayerStartSuggestions);
		        	final History2PListAdapter adapter2P = new History2PListAdapter(TwoPlayerStartActivity.this,list2P);
		            lv2P.setAdapter(adapter2P);
		            lv2P.setOnItemClickListener(new AdapterView.OnItemClickListener()
					{
						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id)
						{
							AllBummerls2P ab = adapter2P.getItem(position);
							
							sp1.setSelection(indexOf(ab.getP1()));
							sp2.setSelection(indexOf(ab.getP2()));
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
