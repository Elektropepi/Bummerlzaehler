package com.mobile.bummerzaehler.bummerl;

import java.io.IOException;
import java.util.ArrayList;









import com.mobile.bummerzaehler.player.Player;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class Bummerl2PController implements BummerlController{
	public static String BUMMERL = "bummerzaehler_bummerl_list_2p";
	private ArrayList<AllBummerls2P> allBummerls = new ArrayList<AllBummerls2P>();
	private final SharedPreferences preferences;
	
	public Bummerl2PController(Activity a)
	{
		preferences = PreferenceManager.getDefaultSharedPreferences(a);
		String serializedString = preferences.getString(BUMMERL, "");
		String[] parts = serializedString.split(";");
		for(String p : parts)
		{
			String[] paparts = p.split(",");
			if(paparts.length == 6)
				allBummerls.add(
						new AllBummerls2P(
								new Player(paparts[0]),
								new Player(paparts[1]),
								Integer.parseInt(paparts[2]),
								Integer.parseInt(paparts[3]),
								Integer.parseInt(paparts[4]),
								Integer.parseInt(paparts[5])));
		}
	}
	
	public void removeItem(AllBummerls2P ab)
	{
		allBummerls.remove(ab);
	}
	
	
	public ArrayList<AllBummerls2P> getAllBummerlsOfList(ArrayList<AllBummerls2P> list, Player p)
	{
		if(p == null)
			return list;
		ArrayList<AllBummerls2P> foundItems = new ArrayList<AllBummerls2P>();
		for(int i = 0; i < list.size(); i++)
		{
			AllBummerls2P ab = list.get(i);
			if(ab.getP1().getName().equals(p.getName()) ||
		 	    	   ab.getP2().getName().equals(p.getName()))
				foundItems.add(ab);
		}
		return foundItems;
	}
	
	
	public ArrayList<AllBummerls2P> getAllBummerlsByPlayers(Player p1, Player p2)
	{
		ArrayList<AllBummerls2P> list = getAllBummerlsOfList(allBummerls, p1);
		list = getAllBummerlsOfList(list, p2);
		ArrayList<AllBummerls2P> newList = new ArrayList<AllBummerls2P>();
		 for(int i = 0; i < list.size(); i++)
		 {
			 AllBummerls2P ab = list.get(i);
		    	if(isEqual(ab.getP1(),p2)  && isEqual(ab.getP2(),p1))
		    	{
		    		ab = new AllBummerls2P(ab.getP2(), ab.getP1() ,
	 	    				ab.getBummerlP2(), ab.getSchneiderP2(), 
	 	    				ab.getBummerlP1(), ab.getSchneiderP1());
		    	}
			 newList.add(ab);
		 
	 }
		 list = newList;
		return list;
	}
	
	private boolean isEqual(Player p1, Player p2)
	{
		if(p1 == null || p2 == null)
			return true;
		else
			return p1.getName().equals(p2.getName());
	}
	
	public AllBummerls2P getAllBummerlsByPlayerCombination(Player p1, Player p2)
	{
		if(allBummerls == null)
			return null;
		for(int i = 0; i < allBummerls.size(); i++)
	    {
	    	AllBummerls2P ab = allBummerls.get(i);
	    	if(ab.getP1().getName().equals(p1.getName())  && ab.getP2().getName().equals(p2.getName()))
	    	{
	    		return ab;
	    	}
	    	else if(ab.getP1().getName().equals(p2.getName())  && ab.getP2().getName().equals(p1.getName()))
	    	{
	    		return ab;
	    	}
	    }
		return null;
	}
	
	
	public void updateBummerls(Player loser, Player winner, boolean isSchneider, boolean isIncrease)
	{
		if(allBummerls == null)
			{
			allBummerls = new ArrayList<AllBummerls2P>();
			}
		if(allBummerls.size() == 0)
		{
			if(isSchneider)
			   allBummerls.add(new AllBummerls2P(loser,winner,0,1,0,0));
			else
			   allBummerls.add(new AllBummerls2P(loser, winner, 1,0,0,0));
			commitChanges();;
		}
		else
		{
			int loserSchneider = -1;
			int winnerSchneider = -1;
			int loserBummerl = -1;
			int winnerBummerl = -1;
			int combinationIndex = -1;
			boolean isCombinationFound = false;
		    for(int i = 0; i < allBummerls.size(); i++)
		    {
		    	AllBummerls2P ab = allBummerls.get(i);
		    	if(ab.getP1().getName().equals(loser.getName())  && ab.getP2().getName().equals(winner.getName()))
		    	{
		    		loserSchneider = ab.getSchneiderP1();
					loserBummerl = ab.getBummerlP1();
					winnerSchneider = ab.getSchneiderP2();
					winnerBummerl = ab.getBummerlP2();
		    		isCombinationFound = true;
		    		combinationIndex = i;
		    	}
		    	else if(ab.getP1().getName().equals(winner.getName())  && ab.getP2().getName().equals(loser.getName()))
		    	{
		    		loserSchneider = ab.getSchneiderP2();
					loserBummerl = ab.getBummerlP2();
					winnerSchneider = ab.getSchneiderP1();
					winnerBummerl = ab.getBummerlP1();
		    		
		    		isCombinationFound = true;
		    		combinationIndex = i;
		    	}
		    }
		    
		    if(isCombinationFound)
		    	{
		    	    allBummerls.remove(combinationIndex);
		    	    if(isSchneider)
		    	    	if(isIncrease)
		    	    	      loserSchneider++;
		    	    	else
		    	    		loserSchneider--;
		    	    else
		    	    	if(isIncrease)
		    	    	      loserBummerl++;
		    	    	else
		    	    		loserBummerl--;
		    	    
		    	    AllBummerls2P newEntry = new AllBummerls2P(loser,
		    	    		winner,
		    	    		loserBummerl,
		    	    		loserSchneider,
		    	    		winnerBummerl,
		    	    		winnerSchneider);

		    	    allBummerls.add(combinationIndex, newEntry);
		}
		    else
		    {
		    	if(isSchneider)
					   allBummerls.add(new AllBummerls2P(loser,winner,0,1,0,0));
					else
					   allBummerls.add(new AllBummerls2P(loser, winner, 1,0,0,0));
		    }
		commitChanges();
		}
	}
	
	public void removeBummerl(AllBummerls2P b)
	{
		if(allBummerls != null)
			allBummerls.remove(b);
		commitChanges();
	}

	public AllBummerls2P removeBummerl(int pos)
	{
		AllBummerls2P ab = null;
		if(allBummerls != null && allBummerls.size() > 0)
			ab = allBummerls.remove(pos);
		commitChanges();
		return ab;
	}
	
	public ArrayList<AllBummerls2P> getAllBummerls()
	{
		return allBummerls;
	}
	
	public void deleteAllBummerlsOfPlayer(Player p)
	{
		for(int i = 0; i< allBummerls.size(); i++)
		{
			AllBummerls2P ab =allBummerls.get(i); 
			if(ab.getP1().getName().equals(p.getName())
					|| ab.getP2().getName().equals(p.getName()))
			{
				allBummerls.remove(i);
			}
		}
		commitChanges();
	}
	
	public void resetBummerls()
	{
		allBummerls = new ArrayList<AllBummerls2P>();
		commitChanges();
	}
	
	public void commitChanges()
	{
		Editor editor = preferences.edit();
		String serializedString = "";
    	for(AllBummerls2P b : allBummerls)
    	{
    		serializedString += b.getP1() + ","
    				+b.getP2() + ","
    				+b.getBummerlP1() + ","
    				+b.getSchneiderP1() + ","
    				+b.getBummerlP2() + ","
    				+b.getSchneiderP2() + ";";
    	}
        editor.putString(BUMMERL, serializedString);
        editor.commit();
	}
	@Override
	public int getSize() {
		return allBummerls.size();
	}
}
