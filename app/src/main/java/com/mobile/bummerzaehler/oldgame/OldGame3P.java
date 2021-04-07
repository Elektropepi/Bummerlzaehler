package com.mobile.bummerzaehler.oldgame;

import com.mobile.bummerzaehler.player.Player;

public class OldGame3P {
	private final Player p1, p2, p3;
	private final String pointsP1;
	private final String pointsP2;
	private final String pointsP3;
	private final String geber;
	private final String bummerlGeber;

	public OldGame3P(Player p1,Player p2,Player p3,
					 String pointsP1, String pointsP2,String pointsP3, String geber, String bummerlGeber)
	{
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.pointsP1 = pointsP1;
		this.pointsP2 = pointsP2;
		this.pointsP3 = pointsP3;
		this.geber = geber;
		this.bummerlGeber = bummerlGeber;
	}

	public Player getP1() {
		return p1;
	}

	public Player getP2() {
		return p2;
	}

	public Player getP3() {
		return p3;
	}

	public String getPointsP1() {
		return pointsP1;
	}

	public String getPointsP2() {
		return pointsP2;
	}

	public String getPointsP3() {
		return pointsP3;
	}

	public String getGeber() { return geber; }
	public String getBummerlGeber() { return bummerlGeber; }
}
