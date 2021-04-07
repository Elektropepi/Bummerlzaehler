package com.mobile.bummerzaehler.bummerl;

import com.mobile.bummerzaehler.player.Player;

public class AllBummerls3P {
	private final Player p1, p2, p3;
	private final int bummerlP1Count;
	private final int schneiderP1Count;
	private final int bummerlP2Count;
	private final int schneiderP2Count;
	private final int bummerlP3Count;
	private final int schneiderP3Count;
	
	public AllBummerls3P(Player p1,Player p2, Player p3,
			int bummerlP1Count, int schneiderP1Count, int bummerlP2Count, 
			int schneiderP2Count, int bummerlP3Count, int schneiderP3Count)
	{
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.bummerlP1Count = bummerlP1Count;
		this.schneiderP1Count = schneiderP1Count;
		this.bummerlP2Count = bummerlP2Count;
		this.schneiderP2Count = schneiderP2Count;
		this.bummerlP3Count = bummerlP3Count;
		this.schneiderP3Count = schneiderP3Count;
	}
	public Player getP1()
	{
		return p1;
	}
	public Player getP2()
	{
		return p2;
	}
	public Player getP3()
	{
		return p3;
	}
	public int getBummerlP1()
	{
		return bummerlP1Count;
	}
	public int getSchneiderP1()
	{
		return schneiderP1Count;
	}
	public int getBummerlP2()
	{
		return bummerlP2Count;
	}
	public int getSchneiderP2()
	{
		return schneiderP2Count;
	}
	public int getBummerlP3()
	{
		return bummerlP3Count;
	}
	public int getSchneiderP3()
	{
		return schneiderP3Count;
	}
}
