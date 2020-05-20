package me.Async.DP;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.FallingBlock;
import org.bukkit.material.MaterialData;

public final class DP {
	
	private static Area Next = null;
	
	public static void start()
	{
		if(Settings.Areas.size() <= 0)
		{
			Bukkit.broadcastMessage("A drop party failed to start due to a lack of  drop party zones!");
			return;
		}
		
		Random rand = new Random();
		if(Next == null)
		{
			int choice = rand.nextInt(Settings.Areas.size());
			Next = (Area) Settings.Areas.toArray()[choice];
			Bukkit.broadcastMessage("The drop party is located in " + Next.getName() + "!");
		}
		
		//drop(Next);
		new DropRunnable(Next).runTaskLaterAsynchronously(Main.instance, 0);
		Next = null;
	}
	
	public static void start(String name)
	{
		Bukkit.broadcastMessage("A drop party has been started by " + name + "!");
		start();
	}
	
	public static void drop(Area area)
	{
		Random rand = new Random();
		int xrange = area.getPos2().getBlockX() - area.getPos1().getBlockX();
		int yrange = area.getPos2().getBlockY() - area.getPos1().getBlockY();
		int zrange = area.getPos2().getBlockZ() - area.getPos1().getBlockZ();
		int x = area.getPos1().getBlockX();
		int y = area.getPos1().getBlockY();
		int z = area.getPos1().getBlockZ();
		if(xrange != 0) 
			x += rand.nextInt(xrange + 1);
		if(yrange != 0)
		    y += rand.nextInt(yrange + 1);
		if(zrange != 0)
			z += rand.nextInt(zrange + 1);
		World w = area.getPos1().getWorld();
		FallingBlock fallingBlock = w.spawnFallingBlock(new Location(w, x, y, z), new MaterialData(Material.OBSIDIAN)); // w.spawnFallingBlock(new Location(w, x, y, z), Material.OBSIDIAN, (byte)0x0);
		fallingBlock.setDropItem(false);
		fallingBlock.setFireTicks(100);
		w.spawnFallingBlock(new Location(w, x, y+1, z), new MaterialData(Material.FIRE)); //w.spawnFallingBlock(new Location(w, x, y+1, z), Material.FIRE, (byte)0x0);
	}
	
	public static Area getNext()
	{
		return Next;
	}

	public static void setNext(Area area)
	{
		Next = area;
	}
}
