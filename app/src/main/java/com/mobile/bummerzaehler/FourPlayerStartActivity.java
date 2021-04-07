package com.mobile.bummerzaehler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mobile.bummerzaehler.bummerl.AllBummerls4P;
import com.mobile.bummerzaehler.bummerl.Bummerl4PController;
import com.mobile.bummerzaehler.helper.HelperClass;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class FourPlayerStartActivity extends ParentActivity {
	PlayerController pController;
	Spinner sp1, sp2, sp3, sp4;
	ArrayList<Player> players = new ArrayList<Player>();
	ArrayAdapter adapter;
	boolean isFirstStart = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	setContentView(ViewTyp.FOUR_PLAYER_START);
        super.onCreate(savedInstanceState); 
        sp1 = (Spinner) findViewById(R.id.spPlayerOne4P);
        sp2 = (Spinner) findViewById(R.id.spPlayerTwo4P);
        sp3 = (Spinner) findViewById(R.id.spPlayerThree4P);
        sp4 = (Spinner) findViewById(R.id.spPlayerFour4P);
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
    	Player p4 = (Player)sp4.getSelectedItem();
    	
    	
    	if((p1.getName() == p2.getName() || 
    			p1.getName() == p3.getName() || 
    			p1.getName() == p4.getName() || 
    			p2.getName() == p3.getName() || 
    			p2.getName() == p4.getName() || 
    			p3.getName() == p4.getName())&&
    			p1.getName()!= "---" &&
    			p2.getName()!= "---" &&
    			p3.getName()!= "---" &&
    			p4.getName()!= "---")
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
    	else if(!pController.contains(p4.getName()))
    	{
    		HelperClass.showShortToast(p4.getName() +" gibts net.. wer zum teifl is des??", this);
    		return;
    	}
    	Intent nextIntent = null;
    	String extra = p1.getName()+";"+p2.getName()+";"+p3.getName()+";"+p4.getName();
		nextIntent = new Intent(FourPlayerStartActivity.this, FourPlayerActivity.class);
		nextIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    	nextIntent.putExtra(TwoPlayerStartActivity.PLAYER_NAMES, extra);
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
    	adapter = new ArrayAdapter(this, R.layout.simple_spinner_item, players);
    	OnItemSelectedListener itemSelectedListener = new OnItemSelectedListener() {
    		 @Override
    		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
    			 Spinner sp = (Spinner)parentView;
    			   if(position == sp.getCount() -1)
    			   {
    				   //last item
    				   Intent nextIntent = null;
    					nextIntent = new Intent(FourPlayerStartActivity.this, CreatePlayerActivity.class);
    					nextIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    					startActivity(nextIntent);
    					overridePendingTransition(R.anim.right_to_left,
    							R.anim.noanimation);
    					
    			   }
    			    Player p1 =  (Player) sp1.getSelectedItem();
					Player p2 =(Player) sp2.getSelectedItem();
					Player p3 =  (Player) sp3.getSelectedItem();
					Player p4 = (Player) sp4.getSelectedItem();
					p1 = p1.getName().equals("---") ? null : p1;
					p2 = p2.getName().equals("---") ? null : p2;
					p3 = p3.getName().equals("---") ? null : p3;
					p4 = p4.getName().equals("---") ? null : p4;
					Bummerl4PController bc = new Bummerl4PController(FourPlayerStartActivity.this);
					ArrayList<AllBummerls4P> list4P = bc.getAllBummerlsByPlayers(p1, p2, p3, p4);
					

		        	ListView lv4P =(ListView) findViewById(R.id.lvFourPlayerStartSuggestions);
		        	final History4PListAdapter adapter4P = new History4PListAdapter(FourPlayerStartActivity.this,list4P);
		            lv4P.setAdapter(adapter4P);
		            lv4P.setOnItemClickListener(new AdapterView.OnItemClickListener()
					{
						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id)
						{
							AllBummerls4P ab = adapter4P.getItem(position);
							
							sp1.setSelection(indexOf(ab.getT1()[0]));
							sp2.setSelection(indexOf(ab.getT1()[1]));
							sp3.setSelection(indexOf(ab.getT2()[0]));
							sp4.setSelection(indexOf(ab.getT2()[1]));
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
    	sp4.setAdapter(adapter);
    	sp4.setOnItemSelectedListener(itemSelectedListener);
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
