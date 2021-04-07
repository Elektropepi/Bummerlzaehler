package com.mobile.bummerzaehler.oldgame;

import com.mobile.bummerzaehler.player.Player;

public class OldGame4P {
	private final Player[] team1, team2;
	private final String pointsT1;
	private final String pointsT2;
	private final String geber;
	private final String bummerlGeber;

	public OldGame4P(Player[] team1, Player[] team2,
					 String pointsT1, String pointsT2, String geber, String bummerlGeber)
	{
		this.team1 = team1;
		this.team2 = team2;
		this.pointsT1 = pointsT1;
		this.pointsT2 = pointsT2;
		this.geber = geber;
		this.bummerlGeber = bummerlGeber;
	}
	public Player[] getT1()
	{
		return team1;
	}
	public Player[] getT2()
	{
		return team2;
	}
	public String getPointsT1()
	{
		return pointsT1;
	}
	public String getPointsT2()
	{
		return pointsT2;
	}
	public String getGeber() { return geber; }
	public String getBummerlGeber() { return bummerlGeber; }
}
