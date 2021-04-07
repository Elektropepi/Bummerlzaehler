package com.mobile.bummerzaehler;




import java.util.ArrayList;
import java.util.Collections;

import com.mobile.bummerzaehler.bummerl.AllBummerls2P;
import com.mobile.bummerzaehler.bummerl.AllBummerls3P;
import com.mobile.bummerzaehler.bummerl.AllBummerls4P;
import com.mobile.bummerzaehler.bummerl.Bummerl2PController;
import com.mobile.bummerzaehler.bummerl.Bummerl3PController;
import com.mobile.bummerzaehler.bummerl.Bummerl4PController;
import com.mobile.bummerzaehler.bummerl.BummerlController;
import com.mobile.bummerzaehler.listadapter.PlayerListAdapter;
import com.mobile.bummerzaehler.listadapter.PlayerListModel;
import com.mobile.bummerzaehler.oldgame.OldGames2PController;
import com.mobile.bummerzaehler.oldgame.OldGames3PController;
import com.mobile.bummerzaehler.oldgame.OldGames4PController;
import com.mobile.bummerzaehler.player.Player;
import com.mobile.bummerzaehler.player.PlayerController;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class PlayersActivity extends ParentActivity {

	private ListView lvMain;
	private TextView tvNoEntries;
	private ArrayList<Player> playerList = new ArrayList<Player>();
	private PlayerController pController ;
	private BummerlController b2C, b3C, b4C;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(ViewTyp.PLAYERS); 
        super.onCreate(savedInstanceState);
        lvMain = (ListView) findViewById(R.id.lvPlayersMain);
        tvNoEntries = (TextView) findViewById(R.id.tvPlayersNoPlayer);
    }
    
    @Override
    protected void onResume()
    {
        pController = new PlayerController(this);
        playerList = pController.getAllPlayers();
    	setListAdapter();
    	super.onResume();
    }
    
    public void createPlayer(View v)
    {
    	 Intent nextIntent = null;
			nextIntent = new Intent(this, CreatePlayerActivity.class);
			nextIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(nextIntent);
			overridePendingTransition(R.anim.right_to_left,
					R.anim.noanimation);
    }
    
    private void updateVisibility()
    {
    	if(playerList.size() == 0)
		{
		tvNoEntries.setVisibility(View.VISIBLE);
		lvMain.setVisibility(View.GONE);
		}
	else
	{
		tvNoEntries.setVisibility(View.GONE);
		lvMain.setVisibility(View.VISIBLE);
	}
    }
    
    private void setListAdapter()
    {
    	updateVisibility();
    	if(playerList.size() == 0)
    		return;
        Collections.sort(playerList);
        ArrayList<PlayerListModel> mainList = new ArrayList<PlayerListModel>();
        final Bummerl2PController b2C = new Bummerl2PController(this);
        final Bummerl3PController b3C = new Bummerl3PController(this);
        final Bummerl4PController b4C = new Bummerl4PController(this);
		final OldGames2PController o2C = new OldGames2PController(this);
		final OldGames3PController o3C = new OldGames3PController(this);
		final OldGames4PController o4C = new OldGames4PController(this);
        for(Player p : playerList)
        {
        	ArrayList<AllBummerls2P> bummerls2P = b2C.getAllBummerlsByPlayers(p, null);
        	int bummerl2P = 0;
            int schneider2P = 0;
            double wonGames2P = 0;
            double lostGames2P = 0;
            double average2P = 0;
            for(AllBummerls2P ab : bummerls2P)
            {
            	bummerl2P += ab.getBummerlP1();
            	schneider2P += ab.getSchneiderP1();
            	wonGames2P += ab.getBummerlP2() + ab.getSchneiderP2();
            }
            lostGames2P = bummerl2P + schneider2P;
            average2P = (wonGames2P+lostGames2P) == 0 ? 0: wonGames2P/(wonGames2P+lostGames2P);
            
            ArrayList<AllBummerls3P> bummerls3P = b3C.getAllBummerlsByPlayers(p, null, null);
        	int bummerl3P = 0;
            int schneider3P = 0;
            double wonGames3P = 0;
            double lostGames3P = 0;
            double average3P = 0;
            for(AllBummerls3P ab : bummerls3P)
            {
            	bummerl3P += ab.getBummerlP1();
            	schneider3P += ab.getSchneiderP1();
            	wonGames3P += Integer.parseInt(Math.round( (ab.getBummerlP2() + ab.getSchneiderP2() +ab.getBummerlP3() + ab.getSchneiderP3())/2)+"");
            }
            lostGames3P = bummerl3P + schneider3P;
            average3P = (wonGames3P+lostGames3P) == 0 ? 0: wonGames3P/(wonGames3P+lostGames3P);
            
            ArrayList<AllBummerls4P> bummerls4P = b4C.getAllBummerlsByPlayers(p, null, null, null);
        	int bummerl4P = 0;
            int schneider4P = 0;
            double wonGames4P = 0;
            double lostgames4P = 0;
            double average4P = 0;
            for(AllBummerls4P ab : bummerls4P)
            {
            	bummerl4P += ab.getBummerlT1();
            	schneider4P += ab.getSchneiderT1();
            	wonGames4P += ab.getBummerlT2() + ab.getSchneiderT2();
            }
            lostgames4P = bummerl4P + schneider4P;
            average4P = (wonGames4P+lostgames4P) == 0 ? 0: wonGames4P/(wonGames4P+lostgames4P);
            
            PlayerListModel plm = new PlayerListModel(p.getName(), bummerl2P, schneider2P, average2P,(int) wonGames2P,
            		bummerl3P, schneider3P, average3P, (int)wonGames3P,
            		bummerl4P, schneider4P, average4P, (int)wonGames4P);
            mainList.add(plm);
        }
        final PlayerListAdapter pla = new PlayerListAdapter(this, mainList, getResources());
        //final ArrayAdapter adapter = new ArrayAdapter(this, R.layout.simple_spinner_item, mainList);
    	lvMain.setAdapter(pla);
		
		lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{

			@Override
			public void onItemClick(final AdapterView<?> parent, View view,
					final int position, long id)
			{

				 DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
					    @Override
					    public void onClick(DialogInterface dialog, int which) {
					        switch (which){
					        case DialogInterface.BUTTON_POSITIVE:
								PlayerListModel plm = (PlayerListModel) parent
										.getItemAtPosition(position);
								
								Player p = new Player(plm.getPlayerName());
								
						        b2C.deleteAllBummerlsOfPlayer(p);
						        b3C.deleteAllBummerlsOfPlayer(p);
						        b4C.deleteAllBummerlsOfPlayer(p);
								o2C.deleteAllGamesOfPlayer(p);
								o3C.deleteAllGamesOfPlayer(p);
								o4C.deleteAllGamesOfPlayer(p);
						        pla.removePlayerName(plm.getPlayerName());
								pController.removePlayer(p);
								
								updateVisibility();
					            break;

					        case DialogInterface.BUTTON_NEGATIVE:
					            //No button clicked
					            break;
					        }
					    }
					};
				
				AlertDialog.Builder builder = new AlertDialog.Builder(PlayersActivity.this);
				builder.setMessage("Wollen Sie den Spieler löschen?").setPositiveButton("Sia", dialogClickListener)
				    .setNegativeButton("Na", dialogClickListener).show();
				
				
			}
		});
		
    }
    
}
