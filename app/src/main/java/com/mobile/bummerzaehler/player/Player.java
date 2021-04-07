package com.mobile.bummerzaehler.player;

public class Player implements Comparable<Player> {
private final String name;
public Player(String name)
{
	this.name = name;
}

public String getName()
{
	return name;
}
public String toString()
{
	return name;
}

@Override
public int compareTo(Player another) {
	return this.name.compareToIgnoreCase(another.name);
}
}
