package com.mobile.bummerzaehler.listadapter;

import java.io.Serializable;

import com.mobile.bummerzaehler.player.Player;

/**
 * Created with IntelliJ IDEA.
 * User: gangsta
 * Date: 10.10.13
 * Time: 15:31
 * To change this template use File | Settings | File Templates.
 */
public class PlayerListModel implements Serializable {

    public int getWonGames2P() {
		return wonGames2P;
	}

	public int getWonGames3P() {
		return wonGames3P;
	}

	public int getWonGames4P() {
		return wonGames4P;
	}

	private final String playerName;
    private final int bummerl2P;
    private final int schneider2P;
    private final double average2P;
    private final int wonGames2P;
    private final int bummerl3P;
    private final int schneider3P;
    private final double average3P;
    private final int wonGames3P;
    private final int bummerl4P;
    private final int schneider4P;
    private final double average4P;
    private final int wonGames4P;
    
    public PlayerListModel( String playerName,
     int bummerl2P,
     int schneider2P,
     double average2P,
     int wonGames2P,
     int bummerl3P,
     int schneider3P,
     double average3P,
     int wonGames3P,
     int bummerl4P,
     int schneider4P,
     double average4P,
     int wonGames4P) {
    	this.playerName = playerName ;
        this.bummerl2P  = bummerl2P;
        this.schneider2P = schneider2P;
        this.average2P = average2P;
        this.bummerl3P = bummerl3P;
        this.schneider3P = schneider3P;
        this.average3P = average3P;
        this.bummerl4P = bummerl4P;
        this.schneider4P = schneider4P;
        this.average4P = average4P;
        this.wonGames2P = wonGames2P;
        this.wonGames3P = wonGames3P;
        this.wonGames4P = wonGames4P;
    }

	public String getPlayerName() {
		return playerName;
	}

	public int getBummerl2P() {
		return bummerl2P;
	}

	public int getSchneider2P() {
		return schneider2P;
	}

	public double getAverage2P() {
		return average2P;
	}

	public int getBummerl3P() {
		return bummerl3P;
	}

	public int getSchneider3P() {
		return schneider3P;
	}

	public double getAverage3P() {
		return average3P;
	}

	public int getBummerl4P() {
		return bummerl4P;
	}

	public int getSchneider4P() {
		return schneider4P;
	}

	public double getAverage4P() {
		return average4P;
	}

 
}
