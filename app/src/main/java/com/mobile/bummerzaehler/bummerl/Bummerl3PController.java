package com.mobile.bummerzaehler.bummerl;

import java.io.IOException;
import java.util.ArrayList;










import com.mobile.bummerzaehler.player.Player;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class Bummerl3PController implements BummerlController {
	public static String BUMMERL = "bummerzaehler_bummerl_list_3p";
	private ArrayList<AllBummerls3P> allBummerls = new ArrayList<AllBummerls3P>();
	private final SharedPreferences preferences;
	
	public Bummerl3PController(Activity a)
	{
		preferences = PreferenceManager.getDefaultSharedPreferences(a);
		String serializedString = preferences.getString(BUMMERL, "");
		String[] parts = serializedString.split(";");
		for(String p : parts)
		{
			String[] paparts = p.split(",");
			if(paparts.length == 9)
				allBummerls.add(
						new AllBummerls3P(
								new Player(paparts[0]),
								new Player(paparts[1]),
								new Player(paparts[2]),
								Integer.parseInt(paparts[3]),
								Integer.parseInt(paparts[4]),
								Integer.parseInt(paparts[5]),
								Integer.parseInt(paparts[6]),
								Integer.parseInt(paparts[7]),
								Integer.parseInt(paparts[8])));
		}
	}
	
	public ArrayList<AllBummerls3P> getAllBummerlsOfList(ArrayList<AllBummerls3P> list, Player p)
	{
		if(p == null)
			return list;
		ArrayList<AllBummerls3P> foundItems = new ArrayList<AllBummerls3P>();
		for(int i = 0; i < list.size(); i++)
		{
			AllBummerls3P ab = list.get(i);
			if(ab.getP1().getName().equals(p.getName()) ||
		 	    	   ab.getP2().getName().equals(p.getName()) ||
		 	    	   ab.getP3().getName().equals(p.getName()))
				foundItems.add(ab);
		}
		return foundItems;
	}
	
	
	public ArrayList<AllBummerls3P> getAllBummerlsByPlayers(Player p1, Player p2, Player p3)
	{
		ArrayList<AllBummerls3P> list = getAllBummerlsOfList(allBummerls, p1);
		list = getAllBummerlsOfList(list, p2);
		list = getAllBummerlsOfList(list, p3);
		
		ArrayList<AllBummerls3P> newList = new ArrayList<AllBummerls3P>();
		 for(int i = 0; i < list.size(); i++)
		 {
			 AllBummerls3P ab = list.get(i);
		    	if(isEqual(ab.getP1(),p1)  
		    		&& isEqual(ab.getP2(),p2)
		    		&& isEqual(ab.getP3(),p3))
		    	{
		    		}
		    	else if(isEqual(ab.getP1(),p1)  
			    		&& isEqual(ab.getP3(),p2)
			    		&& isEqual(ab.getP2(),p3))
		    	{	
 	    		ab = new AllBummerls3P(ab.getP1(), ab.getP3(), ab.getP2(), 
 	    				ab.getBummerlP1(), ab.getSchneiderP1(), 
 	    				ab.getBummerlP3(), ab.getSchneiderP3(), 
 	    				ab.getBummerlP2(), ab.getSchneiderP2());
	    
		    	}
		    	else if(isEqual(ab.getP2(),p1)  
			    		&& isEqual(ab.getP1(),p2)
			    		&& isEqual(ab.getP3(),p3))
		    	{
		    		ab = new AllBummerls3P(ab.getP2(), ab.getP1(), ab.getP3(), 
	 	    				ab.getBummerlP2(), ab.getSchneiderP2(), 
	 	    				ab.getBummerlP1(), ab.getSchneiderP1(), 
	 	    				ab.getBummerlP3(), ab.getSchneiderP3());
		    	}
		    	else if(isEqual(ab.getP2(),p1)  
			    		&& isEqual(ab.getP3(),p2)
			    		&& isEqual(ab.getP1(),p3))
		    	{
		    		ab = new AllBummerls3P(ab.getP2(), ab.getP3(), ab.getP1(), 
	 	    				ab.getBummerlP2(), ab.getSchneiderP2(), 
	 	    				ab.getBummerlP3(), ab.getSchneiderP3(), 
	 	    				ab.getBummerlP1(), ab.getSchneiderP1());
		    	}
		    	else if(isEqual(ab.getP3(),p1)  
			    		&& isEqual(ab.getP1(),p2)
			    		&& isEqual(ab.getP2(),p3))
		    	{
		    		ab = new AllBummerls3P(ab.getP3(), ab.getP1(), ab.getP2(), 
	 	    				ab.getBummerlP3(), ab.getSchneiderP3(), 
	 	    				ab.getBummerlP1(), ab.getSchneiderP1(), 
	 	    				ab.getBummerlP2(), ab.getSchneiderP2());
		    	}
		    	else if(isEqual(ab.getP3(),p1)  
			    		&& isEqual(ab.getP2(),p2)
			    		&& isEqual(ab.getP1(),p3))
		    	{
		    		ab = new AllBummerls3P(ab.getP3(), ab.getP2(), ab.getP1(), 
	 	    				ab.getBummerlP3(), ab.getSchneiderP3(), 
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
	
	public void deleteAllBummerlsOfPlayer(Player p)
	{
		for(int i = 0; i< allBummerls.size(); i++)
		{
			AllBummerls3P ab =allBummerls.get(i); 
			if(ab.getP1().getName().equals(p.getName())
					|| ab.getP2().getName().equals(p.getName())
					|| ab.getP3().getName().equals(p.getName()))
			{
				allBummerls.remove(i);
			}
		}
		commitChanges();
	}
	
	public AllBummerls3P getAllBummerlsByPlayerCombination(Player p1, Player p2, Player p3)
	{
		if(allBummerls == null)
			return null;
		for(int i = 0; i < allBummerls.size(); i++)
	    {
	    	AllBummerls3P ab = allBummerls.get(i);
	    	if(ab.getP1().getName().equals(p1.getName())  
	    		&& ab.getP2().getName().equals(p2.getName())
	    		&& ab.getP3().getName().equals(p3.getName()))
	    	{
	    		return ab;
	    	}
	    	else if(ab.getP1().getName().equals(p1.getName())  
		    		&& ab.getP3().getName().equals(p2.getName())
		    		&& ab.getP2().getName().equals(p3.getName()))
	    	{
	    		return ab;
	    	}
	    	else if(ab.getP2().getName().equals(p1.getName())  
		    		&& ab.getP1().getName().equals(p2.getName())
		    		&& ab.getP3().getName().equals(p3.getName()))
	    	{
	    		return ab;
	    	}
	    	else if(ab.getP2().getName().equals(p1.getName())  
		    		&& ab.getP3().getName().equals(p2.getName())
		    		&& ab.getP1().getName().equals(p3.getName()))
	    	{
	    		return ab;
	    	}
	    	else if(ab.getP3().getName().equals(p1.getName())  
		    		&& ab.getP1().getName().equals(p2.getName())
		    		&& ab.getP2().getName().equals(p3.getName()))
	    	{
	    		return ab;
	    	}
	    	else if(ab.getP3().getName().equals(p1.getName())  
		    		&& ab.getP2().getName().equals(p2.getName())
		    		&& ab.getP1().getName().equals(p3.getName()))
	    	{
	    		return ab;
	    	}
	    }
		return null;
	}
	
	
	public void updateBummerls(Player p1, Player p2, Player p3, 
			boolean isP1Loser, boolean isP2Loser, boolean isP3Loser, 
			boolean isP1Schneider, boolean isP2Schneider, boolean isP3Schneider,
			boolean isIncrease)
	{
		int p1Schneider = 0;
		int p1Bummerl = 0;
		int p2Schneider = 0;
		int p2Bummerl = 0;
		int p3Schneider = 0;
		int p3Bummerl = 0;
		if(allBummerls == null)
			{
			allBummerls = new ArrayList<AllBummerls3P>();
			}
		if(allBummerls.size() == 0)
		{
			if(isP1Loser)
			{
				if(isP1Schneider)
					p1Schneider++;
				else
					p1Bummerl++;
			}
			if(isP2Loser)
			{
				if(isP2Schneider)
					p2Schneider++;
				else
					p2Bummerl++;
			}
			if(isP3Loser)
			{
				if(isP3Schneider)
					p3Schneider++;
				else
					p3Bummerl++;
			}
			allBummerls.add(new AllBummerls3P(p1,p2,p3,p1Bummerl,p1Schneider,p2Bummerl, p2Schneider, p3Bummerl, p3Schneider));
			commitChanges();;
		}
		else
		{
			int combinationIndex = -1;
			boolean isCombinationFound = false;
		    for(int i = 0; i < allBummerls.size(); i++)
		    {
		    	AllBummerls3P ab = allBummerls.get(i);
		    	if(ab.getP1().getName().equals(p1.getName())  
		    		&& ab.getP2().getName().equals(p2.getName())
		    		&& ab.getP3().getName().equals(p3.getName()))
		    	{
		    		p1Bummerl = ab.getBummerlP1();
		    		p2Bummerl = ab.getBummerlP2();
		    		p3Bummerl = ab.getBummerlP3();
		    		p1Schneider = ab.getSchneiderP1();
		    		p2Schneider = ab.getSchneiderP2();
		    		p3Schneider = ab.getSchneiderP3();
		    		isCombinationFound = true;
		    		combinationIndex = i;
		    	}
		    	else if(ab.getP1().getName().equals(p1.getName())  
			    		&& ab.getP3().getName().equals(p2.getName())
			    		&& ab.getP2().getName().equals(p3.getName()))
		    	{
		    		p1Bummerl = ab.getBummerlP1();
		    		p2Bummerl = ab.getBummerlP3();
		    		p3Bummerl = ab.getBummerlP2();
		    		p1Schneider = ab.getSchneiderP1();
		    		p2Schneider = ab.getSchneiderP3();
		    		p3Schneider = ab.getSchneiderP2();
		    		isCombinationFound = true;
		    		combinationIndex = i;
		    	}
		    	else if(ab.getP2().getName().equals(p1.getName())  
			    		&& ab.getP1().getName().equals(p2.getName())
			    		&& ab.getP3().getName().equals(p3.getName()))
		    	{
		    		p1Bummerl = ab.getBummerlP2();
		    		p2Bummerl = ab.getBummerlP1();
		    		p3Bummerl = ab.getBummerlP3();
		    		p1Schneider = ab.getSchneiderP2();
		    		p2Schneider = ab.getSchneiderP1();
		    		p3Schneider = ab.getSchneiderP3();
		    		isCombinationFound = true;
		    		combinationIndex = i;
		    	}
		    	else if(ab.getP2().getName().equals(p1.getName())  
			    		&& ab.getP3().getName().equals(p2.getName())
			    		&& ab.getP1().getName().equals(p3.getName()))
		    	{
		    		p1Bummerl = ab.getBummerlP2();
		    		p2Bummerl = ab.getBummerlP3();
		    		p3Bummerl = ab.getBummerlP1();
		    		p1Schneider = ab.getSchneiderP2();
		    		p2Schneider = ab.getSchneiderP3();
		    		p3Schneider = ab.getSchneiderP1();
		    		isCombinationFound = true;
		    		combinationIndex = i;
		    	}
		    	else if(ab.getP3().getName().equals(p1.getName())  
			    		&& ab.getP1().getName().equals(p2.getName())
			    		&& ab.getP2().getName().equals(p3.getName()))
		    	{
		    		p1Bummerl = ab.getBummerlP3();
		    		p2Bummerl = ab.getBummerlP1();
		    		p3Bummerl = ab.getBummerlP2();
		    		p1Schneider = ab.getSchneiderP3();
		    		p2Schneider = ab.getSchneiderP1();
		    		p3Schneider = ab.getSchneiderP2();
		    		isCombinationFound = true;
		    		combinationIndex = i;
		    	}
		    	else if(ab.getP3().getName().equals(p1.getName())  
			    		&& ab.getP2().getName().equals(p2.getName())
			    		&& ab.getP1().getName().equals(p3.getName()))
		    	{
		    		p1Bummerl = ab.getBummerlP3();
		    		p2Bummerl = ab.getBummerlP2();
		    		p3Bummerl = ab.getBummerlP1();
		    		p1Schneider = ab.getSchneiderP3();
		    		p2Schneider = ab.getSchneiderP2();
		    		p3Schneider = ab.getSchneiderP1();
		    		isCombinationFound = true;
		    		combinationIndex = i;
		    	}
		    	
		    }
		    
		    if(isCombinationFound)
		    	{
		    	    allBummerls.remove(combinationIndex);
		    	    if(isP1Loser)
					{
						if(isP1Schneider)
							if(isIncrease)
							    p1Schneider++;
							else
								p1Schneider--;
						else
							if(isIncrease)
							    p1Bummerl++;
							else
								p1Bummerl--;
					}
					if(isP2Loser)
					{
						if(isP2Schneider)
							if(isIncrease)
							    p2Schneider++;
							else
								p2Schneider--;
						else
							if(isIncrease)
							    p2Bummerl++;
							else
								p2Bummerl--;
					}
					if(isP3Loser)
					{
						if(isP3Schneider)
							if(isIncrease)
							    p3Schneider++;
							else
								p3Schneider--;
						else
							if(isIncrease)
							    p3Bummerl++;
							else
								p3Bummerl--;
					}
					allBummerls.add(combinationIndex,new AllBummerls3P(p1,p2,p3,p1Bummerl,p1Schneider,p2Bummerl, p2Schneider, p3Bummerl, p3Schneider));
		}
		    else
		    {
		    	if(isP1Loser)
				{
					if(isP1Schneider)
						p1Schneider++;
					else
						p1Bummerl++;
				}
				if(isP2Loser)
				{
					if(isP2Schneider)
						p2Schneider++;
					else
						p2Bummerl++;
				}
				if(isP3Loser)
				{
					if(isP3Schneider)
						p3Schneider++;
					else
						p3Bummerl++;
				}
				allBummerls.add(new AllBummerls3P(p1,p2,p3,p1Bummerl,p1Schneider,p2Bummerl, p2Schneider, p3Bummerl, p3Schneider));
		    }
		commitChanges();
		}
	}
	
	public void removeBummerl(AllBummerls3P b)
	{
		if(allBummerls != null)
			allBummerls.remove(b);
		commitChanges();
	}

	public AllBummerls3P removeBummerl(int pos)
	{
		AllBummerls3P ab = null;
		if(allBummerls != null && allBummerls.size() > 0)
			ab = allBummerls.remove(pos);
		commitChanges();
		return ab;
	}
	
	public ArrayList<AllBummerls3P> getAllBummerls()
	{
		return allBummerls;
	}
	
	public void resetBummerls()
	{
		allBummerls = new ArrayList<AllBummerls3P>();
		commitChanges();
	}
	
	public void commitChanges()
	{
		Editor editor = preferences.edit();
		String serializedString = "";
    	for(AllBummerls3P b : allBummerls)
    	{
    		serializedString += b.getP1() + ","
    				+b.getP2() + ","
    				+b.getP3() + ","
    				+b.getBummerlP1() + ","
    				+b.getSchneiderP1() + ","
    	    		+b.getBummerlP2() + ","
    	    		+b.getSchneiderP2() + ","
    	    	    +b.getBummerlP3() + ","
    	    	    +b.getSchneiderP3() + ";";
    	}
        editor.putString(BUMMERL, serializedString);
        editor.commit();
	}
	@Override
	public int getSize() {
		return allBummerls.size();
	}
}
