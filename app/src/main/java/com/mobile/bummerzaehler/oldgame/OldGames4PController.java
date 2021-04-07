package com.mobile.bummerzaehler.oldgame;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.mobile.bummerzaehler.bummerl.BummerlController;
import com.mobile.bummerzaehler.player.Player;

import java.util.ArrayList;

public class OldGames4PController {
	public static String GAMES = "bummerzaehler_old_games_4p";
	private ArrayList<OldGame4P> allGames = new ArrayList<OldGame4P>();
	private final SharedPreferences preferences;

	public OldGames4PController(Activity a)
	{
		preferences = PreferenceManager.getDefaultSharedPreferences(a);
		String serializedString = preferences.getString(GAMES, "");
		String[] parts = serializedString.split(";");
		for(String p : parts)
		{
			String[] paparts = p.split(",");
			if(paparts.length == 8)
			{
				Player[] t1= {new Player(paparts[0]),
						new Player(paparts[1])};
				Player[] t2= {new Player(paparts[2]),
						new Player(paparts[3])};
				allGames.add(
						new OldGame4P(
								t1,
								t2,
								paparts[4],
								paparts[5],
								paparts[6],
								paparts[7]));
			}
		}
	}

	public void deleteAllGamesOfPlayer(Player p)
	{
		for(int i = 0; i< allGames.size(); i++)
		{
			OldGame4P og = allGames.get(i);
			if(og.getT1()[0].getName().equals(p.getName())
					|| og.getT1()[1].getName().equals(p.getName())
					|| og.getT2()[0].getName().equals(p.getName())
					|| og.getT2()[1].getName().equals(p.getName()))
			{
				allGames.remove(i);
			}
		}
		commitChanges();
	}

	public void addOldGame(Player[] t1, Player[] t2, String pointsT1, String pointsT2, String geber, String bummerlGeber)
	{
		allGames.add(new OldGame4P(t1,t2,pointsT1, pointsT2, geber, bummerlGeber));
		commitChanges();
	}

	public OldGame4P getOldGameByTeamCombination(Player[] t1, Player[] t2)
	{
		if(allGames == null)
			return null;
		for(int i = 0; i < allGames.size(); i++)
	    {
	    	OldGame4P og = allGames.get(i);
	    
	    	if(og.getT1()[0].getName().equals(t1[0].getName()) &&
	 	    	   og.getT1()[1].getName().equals(t1[1].getName()) &&
	 	    	   og.getT2()[0].getName().equals(t2[0].getName()) &&
	 	    	   og.getT2()[1].getName().equals(t2[1].getName()))
	 	    		{
						allGames.remove(i);
						commitChanges();
	    		return og;
	 	    		}
	 	    	else if(og.getT1()[1].getName().equals(t1[0].getName()) &&
	 	    	   og.getT1()[0].getName().equals(t1[1].getName()) &&
	 	    	   og.getT2()[0].getName().equals(t2[0].getName()) &&
	 	    	   og.getT2()[1].getName().equals(t2[1].getName()))
	 	    	{
					allGames.remove(i);
					commitChanges();
		    		return og;
		 	    		}
	 	    	else if(og.getT1()[0].getName().equals(t1[0].getName()) &&
	 	 	    	   og.getT1()[1].getName().equals(t1[1].getName()) &&
	 		    	   og.getT2()[1].getName().equals(t2[0].getName()) &&
	 		    	   og.getT2()[0].getName().equals(t2[1].getName()))
	 	    	{
					allGames.remove(i);
					commitChanges();
		    		return og;
		 	    		}
	 	    	else if(og.getT1()[1].getName().equals(t1[0].getName()) &&
	 		 	    	   og.getT1()[0].getName().equals(t1[1].getName()) &&
	 			    	   og.getT2()[1].getName().equals(t2[0].getName()) &&
	 			    	   og.getT2()[0].getName().equals(t2[1].getName()))
	 	    	{
					allGames.remove(i);
					commitChanges();
		    		return og;
		 	    		}
	 	    	else if(og.getT2()[0].getName().equals(t1[0].getName()) &&
	 	    	   og.getT2()[1].getName().equals(t1[1].getName()) &&
	 	    	   og.getT1()[0].getName().equals(t2[0].getName()) &&
	 	    	   og.getT1()[1].getName().equals(t2[1].getName()))
	 	    	{
					allGames.remove(i);
					commitChanges();
					OldGame4P newOg = new OldGame4P(t1, t2, og.getPointsT2(), og.getPointsT1(), og.getGeber(), og.getBummerlGeber());
					return newOg;
		 	    		}
	 	    	else if(og.getT2()[1].getName().equals(t1[0].getName()) &&
	 	    	   og.getT2()[0].getName().equals(t1[1].getName()) &&
	 	    	   og.getT1()[0].getName().equals(t2[0].getName()) &&
	 	    	   og.getT1()[1].getName().equals(t2[1].getName()))
	 	    	{
					allGames.remove(i);
					commitChanges();
					OldGame4P newOg = new OldGame4P(t1, t2, og.getPointsT2(), og.getPointsT1(), og.getGeber(), og.getBummerlGeber());
					return newOg;
		 	    		}
	 	    	else if(og.getT2()[0].getName().equals(t1[0].getName()) &&
	 	 	    	   og.getT2()[1].getName().equals(t1[1].getName()) &&
	 		    	   og.getT1()[1].getName().equals(t2[0].getName()) &&
	 		    	   og.getT1()[0].getName().equals(t2[1].getName()))
	 	    	{
					allGames.remove(i);
					commitChanges();
					OldGame4P newOg = new OldGame4P(t1, t2, og.getPointsT2(), og.getPointsT1(), og.getGeber(), og.getBummerlGeber());
					return newOg;
		 	    		}
	 	    	else if(og.getT2()[1].getName().equals(t1[0].getName()) &&
	 		 	    	   og.getT2()[0].getName().equals(t1[1].getName()) &&
	 			    	   og.getT1()[1].getName().equals(t2[0].getName()) &&
	 			    	   og.getT1()[0].getName().equals(t2[1].getName()))
	 	    	{
					allGames.remove(i);
					commitChanges();
					OldGame4P newOg = new OldGame4P(t1, t2, og.getPointsT2(), og.getPointsT1(), og.getGeber(), og.getBummerlGeber());
					return newOg;
		 	    		}
	    }
		return null;
	}
	
	public void removeBummerl(OldGame4P b)
	{
		if(allGames != null)
			allGames.remove(b);
		commitChanges();
	}

	public OldGame4P removeBummerl(int pos)
	{
		OldGame4P ab = null;
		if(allGames != null && allGames.size() > 0)
			ab = allGames.remove(pos);
		commitChanges();
		return ab;
	}
	
	public ArrayList<OldGame4P> getAllGames()
	{
		return allGames;
	}
	
	public void resetBummerls()
	{
		allGames = new ArrayList<OldGame4P>();
		commitChanges();
	}
	
	public void commitChanges()
	{
		Editor editor = preferences.edit();
		String serializedString = "";
    	for(OldGame4P b : allGames)
    	{
    		serializedString += b.getT1()[0] + ","
    				+b.getT1()[1] + ","
    				+b.getT2()[0] + ","
    				+b.getT2()[1] + ","
    				+b.getPointsT1() + ","
					+b.getPointsT2() + ","
					+b.getGeber() + ","
					+b.getBummerlGeber() + ";";
    	}
        editor.putString(GAMES, serializedString);
        editor.commit();
	}

	public int getSize() {
		return allGames.size();
	}
}
