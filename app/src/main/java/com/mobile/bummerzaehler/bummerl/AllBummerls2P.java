package com.mobile.bummerzaehler.bummerl;

import com.mobile.bummerzaehler.player.Player;

public class AllBummerls2P {
	private Player p1, p2;
	private int bummerlP1Count;
	private int schneiderP1Count;
	private int bummerlP2Count;
	private int schneiderP2Count;
	
	public AllBummerls2P(Player p1,Player p2, int bummerlP1Count, int schneiderP1Count, int bummerlP2Count, int schneiderP2Count)
	{
		this.p1 = p1;
		this.p2 = p2;
		this.bummerlP1Count = bummerlP1Count;
		this.schneiderP1Count = schneiderP1Count;
		this.bummerlP2Count = bummerlP2Count;
		this.schneiderP2Count = schneiderP2Count;
	}
	public Player getP1()
	{
		return p1;
	}
	public Player getP2()
	{
		return p2;
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
}
