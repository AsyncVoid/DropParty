package me.Async.DP;

import java.util.Random;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LootGenerator {
	
	public static void FillLootChest(Inventory inv)
	{
		Random rand = new Random();
		
		for(ItemStack is : Settings.Drops)
		{
			if(rand.nextBoolean() == true)
				inv.addItem(is);
		}
	}
}
