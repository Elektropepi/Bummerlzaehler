package com.mobile.bummerzaehler.bummerl;

import java.io.IOException;
import java.util.ArrayList;







import com.mobile.bummerzaehler.player.Player;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class Bummerl4PController implements BummerlController {
	public static String BUMMERL = "bummerzaehler_bummerl_list_4p";
	private ArrayList<AllBummerls4P> allBummerls = new ArrayList<AllBummerls4P>();
	private final SharedPreferences preferences;
	
	public Bummerl4PController(Activity a)
	{
		preferences = PreferenceManager.getDefaultSharedPreferences(a);
		String serializedString = preferences.getString(BUMMERL, "");
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
				allBummerls.add(
						new AllBummerls4P(
								t1,
								t2,
								Integer.parseInt(paparts[4]),
								Integer.parseInt(paparts[5]),
								Integer.parseInt(paparts[6]),
								Integer.parseInt(paparts[7])));
			}
		}
	}
	
	
	private ArrayList<AllBummerls4P> getAllBummerlsOfListInit(ArrayList<AllBummerls4P> list, Player p, boolean isFirstTeam)
	{
		ArrayList<AllBummerls4P> foundItems = new ArrayList<AllBummerls4P>();
		for(int i = 0; i < allBummerls.size(); i++)
		{
			AllBummerls4P ab = allBummerls.get(i);
			if(ab.getT1()[0].getName().equals(p.getName()) ||
		 	    	   ab.getT1()[1].getName().equals(p.getName()))
				{
				if(isFirstTeam)
				    foundItems.add(ab);
				else
					foundItems.add(new AllBummerls4P(ab.getT2(), ab.getT1(), ab.getBummerlT2(), ab.getSchneiderT2(), ab.getBummerlT1(), ab.getSchneiderT1()));
				}
			else if(ab.getT2()[0].getName().equals(p.getName()) ||
		 	    	   ab.getT2()[1].getName().equals(p.getName()))
			{
				if(isFirstTeam)
					foundItems.add(new AllBummerls4P(ab.getT2(), ab.getT1(), ab.getBummerlT2(), ab.getSchneiderT2(), ab.getBummerlT1(), ab.getSchneiderT1()));
				else
					foundItems.add(ab);
				}
		}
		return foundItems;
	}
	
	private ArrayList<AllBummerls4P> getAllBummerlsOfListFirstTeam(ArrayList<AllBummerls4P> list, Player p)
	{
		if(p == null)
			return list;
		ArrayList<AllBummerls4P> foundItems = new ArrayList<AllBummerls4P>();
		for(int i = 0; i < list.size(); i++)
		{
			AllBummerls4P ab = list.get(i);
			if(ab.getT1()[0].getName().equals(p.getName()) ||
		 	    	   ab.getT1()[1].getName().equals(p.getName()))
				foundItems.add(ab);
		}
		return foundItems;
	}
	private ArrayList<AllBummerls4P> getAllBummerlsOfListSecondTeam(ArrayList<AllBummerls4P> list, Player p)
	{
		if(p == null)
			return list;
		ArrayList<AllBummerls4P> foundItems = new ArrayList<AllBummerls4P>();
		for(int i = 0; i < list.size(); i++)
		{
			AllBummerls4P ab = list.get(i);
			if(ab.getT2()[0].getName().equals(p.getName()) ||
		 	    	   ab.getT2()[1].getName().equals(p.getName()))
				foundItems.add(ab);
		}
		return foundItems;
	}
	
	public ArrayList<AllBummerls4P> getAllBummerlsByPlayers(Player p1, Player p2, Player p3, Player p4)
	{
		ArrayList<AllBummerls4P> list = allBummerls;
		
		if(p1 != null)
			{
			 list = getAllBummerlsOfListInit(list, p1, true);
			 list = getAllBummerlsOfListFirstTeam(list, p2);
			 list = getAllBummerlsOfListSecondTeam(list, p3);
			 list = getAllBummerlsOfListSecondTeam(list, p4);
				 
			 
			}
		else if(p2 != null)
			{list = getAllBummerlsOfListInit(list, p2, true);
		 list = getAllBummerlsOfListSecondTeam(list, p3);
		 list = getAllBummerlsOfListSecondTeam(list, p4);
			}
		else if(p3 != null)
			{list = getAllBummerlsOfListInit(list, p3, false);
		 list = getAllBummerlsOfListSecondTeam(list, p4);
			}
		else if(p4 != null)
			list = getAllBummerlsOfListInit(list, p4, false);
		ArrayList<AllBummerls4P> newList = new ArrayList<AllBummerls4P>();
		 for(int i = 0; i < list.size(); i++)
		 {
			 AllBummerls4P ab = list.get(i);
			        if(isEqual(ab.getT1()[0],p1) &&
		 	    	   isEqual(ab.getT1()[1],p2) &&
		 	    	   isEqual(ab.getT2()[0],p3) &&
		 	    	   isEqual(ab.getT2()[1],p4))
		 	    		{
		    		
		 	    		}
		 	   else if(isEqual(ab.getT1()[1],p1) &&
		 	    	   isEqual(ab.getT1()[0],p2) &&
		 	    	   isEqual(ab.getT2()[0],p3) &&
		 	    	   isEqual(ab.getT2()[1],p4))
		 	    	{
		 	    		Player[] t1 = {ab.getT1()[1], ab.getT1()[0]};
		 	    		Player[] t2 = {ab.getT2()[0], ab.getT2()[1]};
		 	    		
			    		ab = new AllBummerls4P(t1, t2, ab.getBummerlT1(), ab.getSchneiderT1(), ab.getBummerlT2(), ab.getSchneiderT2());
			 	    		}
		 	    	else if(isEqual(ab.getT1()[0],p1) &&
		 	 	    	   isEqual(ab.getT1()[1],p2) &&
		 		    	   isEqual(ab.getT2()[1],p3) &&
		 		    	   isEqual(ab.getT2()[0],p4))
		 	    	{
		 	    		Player[] t1 = {ab.getT1()[0], ab.getT1()[1]};
		 	    		Player[] t2 = {ab.getT2()[1], ab.getT2()[0]};
		 	    		
			    		ab = new AllBummerls4P(t1, t2, ab.getBummerlT1(), ab.getSchneiderT1(), ab.getBummerlT2(), ab.getSchneiderT2());
			 	    		}
		 	    	else if(isEqual(ab.getT1()[1],p1) &&
		 		 	    	   isEqual(ab.getT1()[0],p2) &&
		 			    	   isEqual(ab.getT2()[1],p3) &&
		 			    	   isEqual(ab.getT2()[0],p4))
		 	    	{
		 	    		Player[] t1 = {ab.getT1()[1], ab.getT1()[0]};
		 	    		Player[] t2 = {ab.getT2()[1], ab.getT2()[0]};
		 	    		
			    		ab = new AllBummerls4P(t1, t2, ab.getBummerlT1(), ab.getSchneiderT1(), ab.getBummerlT2(), ab.getSchneiderT2());
			 	    		}
		 	    	else if(isEqual(ab.getT2()[0],p1) &&
		 	    	   isEqual(ab.getT2()[1],p2) &&
		 	    	   isEqual(ab.getT1()[0],p3) &&
		 	    	   isEqual(ab.getT1()[1],p4))
		 	    	{
		 	    		Player[] t1 = {ab.getT2()[0], ab.getT2()[1]};
		 	    		Player[] t2 = {ab.getT1()[0], ab.getT1()[1]};
		 	    		
			    		ab = new AllBummerls4P(t1, t2, ab.getBummerlT2(), ab.getSchneiderT2(), ab.getBummerlT1(), ab.getSchneiderT1());
			 	    		}
		 	    	else if(isEqual(ab.getT2()[1],p1) &&
		 	    	   isEqual(ab.getT2()[0],p2) &&
		 	    	   isEqual(ab.getT1()[0],p3) &&
		 	    	   isEqual(ab.getT1()[1],p4))
		 	    	{
		 	    		Player[] t1 = {ab.getT2()[1], ab.getT2()[0]};
		 	    		Player[] t2 = {ab.getT1()[0], ab.getT1()[1]};
		 	    		
			    		ab = new AllBummerls4P(t1, t2, ab.getBummerlT2(), ab.getSchneiderT2(), ab.getBummerlT1(), ab.getSchneiderT1());
			 	    		}
		 	    	else if(isEqual(ab.getT2()[0],p1) &&
		 	 	    	   isEqual(ab.getT2()[1],p2) &&
		 		    	   isEqual(ab.getT1()[1],p3) &&
		 		    	   isEqual(ab.getT1()[0],p4))
		 	    	{
		 	    		Player[] t1 = {ab.getT2()[0], ab.getT2()[1]};
		 	    		Player[] t2 = {ab.getT1()[1], ab.getT1()[0]};
		 	    		
			    		ab = new AllBummerls4P(t1, t2, ab.getBummerlT2(), ab.getSchneiderT2(), ab.getBummerlT1(), ab.getSchneiderT1());
			 	    		}
		 	    	else if(isEqual(ab.getT2()[1],p1) &&
		 		 	    	   isEqual(ab.getT2()[0],p2) &&
		 			    	   isEqual(ab.getT1()[1],p3) &&
		 			    	   isEqual(ab.getT1()[0],p4))
		 	    	{
		 	    		Player[] t1 = {ab.getT2()[1], ab.getT2()[0]};
		 	    		Player[] t2 = {ab.getT1()[1], ab.getT1()[0]};
		 	    		
			    		ab = new AllBummerls4P(t1, t2, ab.getBummerlT2(), ab.getSchneiderT2(), ab.getBummerlT1(), ab.getSchneiderT1());
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
	public void deleteAllBummerlsOfPlayer(Player p)
	{
		for(int i = 0; i< allBummerls.size(); i++)
		{
			AllBummerls4P ab =allBummerls.get(i); 
			if(ab.getT1()[0].getName().equals(p.getName())
					|| ab.getT1()[1].getName().equals(p.getName())
					|| ab.getT2()[0].getName().equals(p.getName())
					|| ab.getT2()[1].getName().equals(p.getName()))
			{
				allBummerls.remove(i);
			}
		}
		commitChanges();
	}
	
	private boolean isLoserInT1 = false;
	public AllBummerls4P getAllBummerlsByTeamCombination(Player[] t1, Player[] t2)
	{
		if(allBummerls == null)
			return null;
		for(int i = 0; i < allBummerls.size(); i++)
	    {
	    	AllBummerls4P ab = allBummerls.get(i);
	    
	    	if(ab.getT1()[0].getName().equals(t1[0].getName()) &&
	 	    	   ab.getT1()[1].getName().equals(t1[1].getName()) &&
	 	    	   ab.getT2()[0].getName().equals(t2[0].getName()) &&
	 	    	   ab.getT2()[1].getName().equals(t2[1].getName()))
	 	    		{
	    		isLoserInT1 = true;
	    		return ab;
	 	    		}
	 	    	else if(ab.getT1()[1].getName().equals(t1[0].getName()) &&
	 	    	   ab.getT1()[0].getName().equals(t1[1].getName()) &&
	 	    	   ab.getT2()[0].getName().equals(t2[0].getName()) &&
	 	    	   ab.getT2()[1].getName().equals(t2[1].getName()))
	 	    	{
		    		isLoserInT1 = true;
		    		return ab;
		 	    		}
	 	    	else if(ab.getT1()[0].getName().equals(t1[0].getName()) &&
	 	 	    	   ab.getT1()[1].getName().equals(t1[1].getName()) &&
	 		    	   ab.getT2()[1].getName().equals(t2[0].getName()) &&
	 		    	   ab.getT2()[0].getName().equals(t2[1].getName()))
	 	    	{
		    		isLoserInT1 = true;
		    		return ab;
		 	    		}
	 	    	else if(ab.getT1()[1].getName().equals(t1[0].getName()) &&
	 		 	    	   ab.getT1()[0].getName().equals(t1[1].getName()) &&
	 			    	   ab.getT2()[1].getName().equals(t2[0].getName()) &&
	 			    	   ab.getT2()[0].getName().equals(t2[1].getName()))
	 	    	{
		    		isLoserInT1 = true;
		    		return ab;
		 	    		}
	 	    	else if(ab.getT2()[0].getName().equals(t1[0].getName()) &&
	 	    	   ab.getT2()[1].getName().equals(t1[1].getName()) &&
	 	    	   ab.getT1()[0].getName().equals(t2[0].getName()) &&
	 	    	   ab.getT1()[1].getName().equals(t2[1].getName()))
	 	    	{
		    		isLoserInT1 = false;
		    		return ab;
		 	    		}
	 	    	else if(ab.getT2()[1].getName().equals(t1[0].getName()) &&
	 	    	   ab.getT2()[0].getName().equals(t1[1].getName()) &&
	 	    	   ab.getT1()[0].getName().equals(t2[0].getName()) &&
	 	    	   ab.getT1()[1].getName().equals(t2[1].getName()))
	 	    	{
		    		isLoserInT1 = false;
		    		return ab;
		 	    		}
	 	    	else if(ab.getT2()[0].getName().equals(t1[0].getName()) &&
	 	 	    	   ab.getT2()[1].getName().equals(t1[1].getName()) &&
	 		    	   ab.getT1()[1].getName().equals(t2[0].getName()) &&
	 		    	   ab.getT1()[0].getName().equals(t2[1].getName()))
	 	    	{
		    		isLoserInT1 = false;
		    		return ab;
		 	    		}
	 	    	else if(ab.getT2()[1].getName().equals(t1[0].getName()) &&
	 		 	    	   ab.getT2()[0].getName().equals(t1[1].getName()) &&
	 			    	   ab.getT1()[1].getName().equals(t2[0].getName()) &&
	 			    	   ab.getT1()[0].getName().equals(t2[1].getName()))
	 	    	{
		    		isLoserInT1 = false;
		    		return ab;
		 	    		}
	    }
		return null;
	}
	
	
	public void updateBummerls(Player[] loser, Player[] winner, boolean isSchneider, boolean isIncrease)
	{
		if(allBummerls == null)
			{
			allBummerls = new ArrayList<AllBummerls4P>();
			}
		if(allBummerls.size() == 0)
		{
			if(isSchneider)
			   allBummerls.add(new AllBummerls4P(loser,winner,0,1,0,0));
			else
			   allBummerls.add(new AllBummerls4P(loser, winner, 1,0,0,0));
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
			
			AllBummerls4P ab = getAllBummerlsByTeamCombination(loser, winner);
			if(ab != null)
			{
				if(isLoserInT1)
				{
					loserSchneider = ab.getSchneiderT1();
					loserBummerl = ab.getBummerlT1();
					winnerSchneider = ab.getSchneiderT2();
					winnerBummerl = ab.getBummerlT2();
				}
				else
				{
					loserSchneider = ab.getSchneiderT2();
					loserBummerl = ab.getBummerlT2();
					winnerSchneider = ab.getSchneiderT1();
					winnerBummerl = ab.getBummerlT1();
					
				}
	    		isCombinationFound = true;
	    		combinationIndex = allBummerls.indexOf(ab);
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
		    	    
		    	    AllBummerls4P newEntry = new AllBummerls4P(loser,
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
					   allBummerls.add(new AllBummerls4P(loser,winner,0,1,0,0));
					else
					   allBummerls.add(new AllBummerls4P(loser, winner, 1,0,0,0));
		    }
		commitChanges();
		}
	}
	
	public void removeBummerl(AllBummerls4P b)
	{
		if(allBummerls != null)
			allBummerls.remove(b);
		commitChanges();
	}
	public AllBummerls4P removeBummerl(int pos)
	{
		AllBummerls4P ab = null;
		if(allBummerls != null && allBummerls.size() > 0)
			ab = allBummerls.remove(pos);
		commitChanges();
		return ab;
	}
	
	public ArrayList<AllBummerls4P> getAllBummerls()
	{
		return allBummerls;
	}
	
	public void resetBummerls()
	{
		allBummerls = new ArrayList<AllBummerls4P>();
		commitChanges();
	}
	
	public void commitChanges()
	{
		Editor editor = preferences.edit();
		String serializedString = "";
    	for(AllBummerls4P b : allBummerls)
    	{
    		serializedString += b.getT1()[0] + ","
    				+b.getT1()[1] + ","
    				+b.getT2()[0] + ","
    				+b.getT2()[1] + ","
    				+b.getBummerlT1() + ","
    				+b.getSchneiderT1() + ","
    				+b.getBummerlT2() + ","
    				+b.getSchneiderT2() + ";";
    	}
        editor.putString(BUMMERL, serializedString);
        editor.commit();
	}


	@Override
	public int getSize() {
		return allBummerls.size();
	}
}
