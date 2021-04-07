package com.mobile.bummerzaehler.oldgame;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.mobile.bummerzaehler.player.Player;

import java.util.ArrayList;

public class OldGames3PController {
	public static String GAMES = "bummerzaehler_old_games_3p";
	private ArrayList<OldGame3P> allGames = new ArrayList<OldGame3P>();
	private final SharedPreferences preferences;

	public OldGames3PController(Activity a)
	{
		preferences = PreferenceManager.getDefaultSharedPreferences(a);
		String serializedString = preferences.getString(GAMES, "");
		String[] parts = serializedString.split(";");
		for(String p : parts)
		{
			String[] paparts = p.split(",");
			if(paparts.length == 8)
			{
				allGames.add(
						new OldGame3P(
								new Player(paparts[0]),
								new Player(paparts[1]),
								new Player(paparts[2]),
								paparts[3],
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
			OldGame3P og = allGames.get(i);
			if(og.getP1().getName().equals(p.getName())
					|| og.getP2().getName().equals(p.getName())
					|| og.getP3().getName().equals(p.getName()))
			{
				allGames.remove(i);
			}
		}
		commitChanges();
	}

	public void addOldGame(Player p1, Player p2, Player p3, String pointsP1, String pointsP2, String pointsP3, String geber, String bummerlGeber)
	{
		allGames.add(new OldGame3P(p1,p2,p3,pointsP1, pointsP2, pointsP3, geber, bummerlGeber));
		commitChanges();
	}

	public OldGame3P getOldGameByTeamCombination(Player p1, Player p2, Player p3)
	{
		if(allGames == null)
			return null;
		for(int i = 0; i < allGames.size(); i++)
	    {
	    	OldGame3P og = allGames.get(i);

			if(og.getP1().getName().equals(p1.getName())
					&& og.getP2().getName().equals(p2.getName())
					&& og.getP3().getName().equals(p3.getName()))
			{
				allGames.remove(i);
				commitChanges();
				return og;
			}
			else if(og.getP1().getName().equals(p1.getName())
					&& og.getP3().getName().equals(p2.getName())
					&& og.getP2().getName().equals(p3.getName()))
			{
				allGames.remove(i);
				commitChanges();
				OldGame3P newOg = new OldGame3P(p1, p2, p3, og.getPointsP1(), og.getPointsP3(), og.getPointsP2(), og.getGeber(), og.getBummerlGeber());
				return newOg;
			}
			else if(og.getP2().getName().equals(p1.getName())
					&& og.getP1().getName().equals(p2.getName())
					&& og.getP3().getName().equals(p3.getName()))
			{
				allGames.remove(i);
				commitChanges();
				OldGame3P newOg = new OldGame3P(p1, p2, p3, og.getPointsP2(), og.getPointsP1(), og.getPointsP3(), og.getGeber(), og.getBummerlGeber());
				return newOg;
			}
			else if(og.getP2().getName().equals(p1.getName())
					&& og.getP3().getName().equals(p2.getName())
					&& og.getP1().getName().equals(p3.getName()))
			{
				allGames.remove(i);
				commitChanges();
				OldGame3P newOg = new OldGame3P(p1, p2, p3, og.getPointsP2(), og.getPointsP3(), og.getPointsP1(), og.getGeber(), og.getBummerlGeber());
				return newOg;
			}
			else if(og.getP3().getName().equals(p1.getName())
					&& og.getP1().getName().equals(p2.getName())
					&& og.getP2().getName().equals(p3.getName()))
			{
				allGames.remove(i);
				commitChanges();
				OldGame3P newOg = new OldGame3P(p1, p2, p3, og.getPointsP3(), og.getPointsP1(), og.getPointsP2(), og.getGeber(), og.getBummerlGeber());
				return newOg;
			}
			else if(og.getP3().getName().equals(p1.getName())
					&& og.getP2().getName().equals(p2.getName())
					&& og.getP1().getName().equals(p3.getName()))
			{
				allGames.remove(i);
				commitChanges();
				OldGame3P newOg = new OldGame3P(p1, p2, p3, og.getPointsP3(), og.getPointsP2(), og.getPointsP1(), og.getGeber(), og.getBummerlGeber());
				return newOg;
			}
	    }
		return null;
	}
	
	public void removeBummerl(OldGame3P b)
	{
		if(allGames != null)
			allGames.remove(b);
		commitChanges();
	}

	public OldGame3P removeBummerl(int pos)
	{
		OldGame3P ab = null;
		if(allGames != null && allGames.size() > 0)
			ab = allGames.remove(pos);
		commitChanges();
		return ab;
	}
	
	public ArrayList<OldGame3P> getAllGames()
	{
		return allGames;
	}
	
	public void resetBummerls()
	{
		allGames = new ArrayList<OldGame3P>();
		commitChanges();
	}
	
	public void commitChanges()
	{
		Editor editor = preferences.edit();
		String serializedString = "";
    	for(OldGame3P b : allGames)
    	{
    		serializedString += b.getP1() + ","
    				+b.getP2() + ","
    				+b.getP3() + ","
    				+b.getPointsP1() + ","
    				+b.getPointsP2() + ","
					+b.getPointsP3() + ","
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
