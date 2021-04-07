package com.mobile.bummerzaehler.player;

import java.io.IOException;
import java.util.ArrayList;


import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class PlayerController {
	public static String PLAYERS = "bummerzaehler_players_list";
	private ArrayList<Player> players = new ArrayList<Player>();
	private final SharedPreferences preferences;
	
	public PlayerController(Activity a)
	{
		preferences = PreferenceManager.getDefaultSharedPreferences(a);
		String serializedString = preferences.getString(PLAYERS, "");
		String[] parts = serializedString.split(";");
		for(String p : parts)
		{
			if(!p.isEmpty())
			players.add(new Player(p));
		}
	}
	
	public boolean contains(String s)
	{
		for(Player p : players)
		{
			if(p.getName().equals(s))
				return true;
		}
		return false;
	}
	
	public void addPlayer(Player p)
	{
		if(players == null)
			players = new ArrayList<Player>(); 
		players.add(p);
		commitChanges();
	}
	
	public void removePlayer(Player p)
	{
		if(players != null)
			players.remove(p);
		commitChanges();
	}
	
	public ArrayList<Player> getAllPlayers()
	{
		return players;
	}
	
	public void commitChanges()
	{
		Editor editor = preferences.edit();
		String serializedString = "";
    	for(Player player : players)
    	{
    		serializedString += player.getName() + ";";
    	}
        editor.putString(PLAYERS, serializedString);
        editor.commit();
	}
}
