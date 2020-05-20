package me.Async.DP;

import org.bukkit.Location;

public class Area {
	//private final int id;
	private final String name;
	private final String world;
	private final Location pos1;
	private final Location pos2;

	public Area(/*int id,*/ String name, String world, Location pos1, Location pos2)
	{
		//this.id = id;
		this.name = name;
		this.world = world;
		this.pos1 = pos1;
		this.pos2 = pos2;
	}
	
	public String getName()
	{
		return this.name;
	}
	public Location getPos1()
	{
		return this.pos1;
	}
	public Location getPos2()
	{
		return this.pos2;
	}
	public String getWorld()
	{
		return this.world;
	}
	//public int getID()
	//{
	//	return this.id;
	//}
}
