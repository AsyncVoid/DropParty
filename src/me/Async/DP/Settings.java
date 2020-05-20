package me.Async.DP;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class Settings
{
	public static boolean Enabled;
	public static int NeedForDrop;
	public static HashSet<Area> Areas = new HashSet<Area>();
	public static List<ItemStack> Drops = new ArrayList<ItemStack>();
	
	@SuppressWarnings("deprecation")
	public static void Load()
	{
		Enabled = Main.instance.getConfig().getBoolean("Enabled");
		NeedForDrop = Main.instance.getConfig().getInt("DropPartyOn");
		
		FileConfiguration locationConfig = Main.instance.getLocationConfig();
		for(String node : locationConfig.getRoot().getKeys(false))
		{
			String name = locationConfig.getString(node + ".name");
			String world = locationConfig.getString(node + ".world");
			
			List<Integer> lpos1 = locationConfig.getIntegerList(node + ".pos1");
			int[] apos1 = ArrayUtils.toPrimitive(lpos1.toArray(new Integer[2]));
			Location pos1 = new Location(Bukkit.getWorld(world), apos1[0], apos1[1], apos1[2]);
			
			List<Integer> lpos2 = locationConfig.getIntegerList(node + ".pos2");
			int[] apos2 = ArrayUtils.toPrimitive(lpos2.toArray(new Integer[2]));
			Location pos2 = new Location(Bukkit.getWorld(world), apos2[0], apos2[1], apos2[2]);
			
			Areas.add(new Area(/*Integer.parseInt(node),*/ name, world, pos1, pos2));
		}
		
		FileConfiguration lootConfig = Main.instance.getLootConfig();
		for(String node : lootConfig.getRoot().getKeys(false))
		{
			String name = lootConfig.getString(node + ".name");
			int type = lootConfig.getInt(node + ".type");
			int amount = lootConfig.getInt(node + ".amount");
			
			ItemStack is = new ItemStack(type, amount);
			if(name != "")
				is.getItemMeta().setDisplayName(name);
			
			for(String enc : lootConfig.getStringList(node + ".enchants"))
			{
				String[] split = enc.split(":");
				is.addEnchantment(Enchantment.getById(Integer.parseInt(split[0])), Integer.parseInt(split[1]));
			}
			
			Drops.add(is);
		}
	}
}
