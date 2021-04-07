package com.mobile.bummerzaehler.oldgame;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.mobile.bummerzaehler.player.Player;

import java.util.ArrayList;

public class OldGames2PController {
	public static String GAMES = "bummerzaehler_old_games_2p";
	private ArrayList<OldGame2P> allGames = new ArrayList<OldGame2P>();
	private final SharedPreferences preferences;

	public OldGames2PController(Activity a)
	{
		preferences = PreferenceManager.getDefaultSharedPreferences(a);
		String serializedString = preferences.getString(GAMES, "");
		String[] parts = serializedString.split(";");
		for(String p : parts)
		{
			String[] paparts = p.split(",");
			if(paparts.length == 6)
			{
				allGames.add(
						new OldGame2P(
								new Player(paparts[0]),
								new Player(paparts[1]),
								paparts[2],
								paparts[3],
								paparts[4],
								paparts[5]));
			}
		}
	}

	public void deleteAllGamesOfPlayer(Player p)
	{
		for(int i = 0; i< allGames.size(); i++)
		{
			OldGame2P og = allGames.get(i);
			if(og.getP1().getName().equals(p.getName())
					|| og.getP2().getName().equals(p.getName()))
			{
				allGames.remove(i);
			}
		}
		commitChanges();
	}

	public void addOldGame(Player p1, Player p2, String pointsP1, String pointsP2, String geber, String bummerlGeber)
	{
		allGames.add(new OldGame2P(p1,p2,pointsP1, pointsP2, geber, bummerlGeber));
		commitChanges();
	}

	public OldGame2P getOldGameByTeamCombination(Player p1, Player p2)
	{
		if(allGames == null)
			return null;
		for(int i = 0; i < allGames.size(); i++)
	    {
	    	OldGame2P og = allGames.get(i);

			if(og.getP1().getName().equals(p1.getName())  && og.getP2().getName().equals(p2.getName()))
			{
				allGames.remove(i);
				commitChanges();
				return og;
			}
			else if(og.getP1().getName().equals(p2.getName())  && og.getP2().getName().equals(p1.getName()))
			{
				allGames.remove(i);
				commitChanges();
				OldGame2P newOg = new OldGame2P(p1, p2,  og.getPointsP2(), og.getPointsP1(), og.getGeber(), og.getBummerlGeber());
				return newOg;
			}
	    }
		return null;
	}
	
	public void removeBummerl(OldGame2P b)
	{
		if(allGames != null)
			allGames.remove(b);
		commitChanges();
	}

	public OldGame2P removeBummerl(int pos)
	{
		OldGame2P ab = null;
		if(allGames != null && allGames.size() > 0)
			ab = allGames.remove(pos);
		commitChanges();
		return ab;
	}
	
	public ArrayList<OldGame2P> getAllGames()
	{
		return allGames;
	}
	
	public void resetBummerls()
	{
		allGames = new ArrayList<OldGame2P>();
		commitChanges();
	}
	
	public void commitChanges()
	{
		Editor editor = preferences.edit();
		String serializedString = "";
    	for(OldGame2P b : allGames)
    	{
    		serializedString += b.getP1() + ","
    				+b.getP2() + ","
    				+b.getPointsP1() + ","
    				+b.getPointsP2() + ","
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
