package com.mobile.bummerzaehler.bummerl;

import com.mobile.bummerzaehler.player.Player;

public interface BummerlController {
public void resetBummerls();
public void  deleteAllBummerlsOfPlayer(Player p);
public Object removeBummerl(int position);
public int getSize();
}
