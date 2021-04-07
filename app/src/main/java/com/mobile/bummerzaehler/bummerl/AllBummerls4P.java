package com.mobile.bummerzaehler.bummerl;

import com.mobile.bummerzaehler.player.Player;

public class AllBummerls4P {
	private final Player[] team1, team2;
	private final int bummerlT1Count;
	private final int schneiderT1Count;
	private final int bummerlT2Count;
	private final int schneiderT2Count;
	
	public AllBummerls4P(Player[] team1,Player[] team2 ,
			int bummerlT1Count, int schneiderT1Count, int bummerlT2Count, int schneiderT2Count)
	{
		this.team1 = team1;
		this.team2 = team2;
		this.bummerlT1Count = bummerlT1Count;
		this.schneiderT1Count = schneiderT1Count;
		this.bummerlT2Count = bummerlT2Count;
		this.schneiderT2Count = schneiderT2Count;
	}
	public Player[] getT1()
	{
		return team1;
	}
	public Player[] getT2()
	{
		return team2;
	}
	public int getBummerlT1()
	{
		return bummerlT1Count;
	}
	public int getSchneiderT1()
	{
		return schneiderT1Count;
	}
	public int getBummerlT2()
	{
		return bummerlT2Count;
	}
	public int getSchneiderT2()
	{
		return schneiderT2Count;
	}
}
