package com.mobile.bummerzaehler.oldgame;

import com.mobile.bummerzaehler.player.Player;

public class OldGame2P {
	private final Player p1, p2;
	private final String pointsP1;
	private final String pointsP2;
	private final String geber;
	private final String bummerlGeber;

	public OldGame2P(Player p1, Player p2,
					 String pointsP1, String pointsP2, String geber, String bummerlGeber)
	{
		this.p1 = p1;
		this.p2 = p2;
		this.pointsP1 = pointsP1;
		this.pointsP2 = pointsP2;
		this.geber = geber;
		this.bummerlGeber = bummerlGeber;
	}

	public Player getP1() {
		return p1;
	}

	public Player getP2() {
		return p2;
	}


	public String getPointsP1() {
		return pointsP1;
	}

	public String getPointsP2() {
		return pointsP2;
	}


	public String getGeber() { return geber; }
	public String getBummerlGeber() { return bummerlGeber; }
}
