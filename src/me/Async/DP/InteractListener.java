package me.Async.DP;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.metadata.FixedMetadataValue;

public class InteractListener implements Listener
{

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent ev)
	{
		if(ev.getMaterial() == Material.DIAMOND_SPADE)
		{
			if(ev.getItem().getItemMeta().getLore().contains("§cDP Wand"))
			{
				int x = ev.getClickedBlock().getX();
				int y = ev.getClickedBlock().getY();
				int z = ev.getClickedBlock().getZ();
				World w = ev.getClickedBlock().getWorld();
					
				if(ev.getAction() == Action.RIGHT_CLICK_BLOCK)
				{
					ev.getPlayer().sendMessage("Set pos2 to [" + x + "," + y + "," + z + "]");
					ev.getPlayer().setMetadata("dropparty.pos2", new FixedMetadataValue(Main.instance, new Location(w, x, y, z)));
				}
				else if (ev.getAction() == Action.LEFT_CLICK_BLOCK)
				{
					ev.getPlayer().sendMessage("Set pos1 to [" + x + "," + y + "," + z + "]");
					ev.getPlayer().setMetadata("dropparty.pos1", new FixedMetadataValue(Main.instance, new Location(w, x, y, z)));
				}
				ev.setCancelled(true);
			}
		}
		if(ev.getClickedBlock().getType() == Material.CHEST)
		{
			if(ev.getClickedBlock().hasMetadata("dropparty.chest"))
			{
				ev.getClickedBlock().setType(Material.AIR);
				Inventory inv = Bukkit.createInventory(null, 9, "Drop Party Chest");
				//inv.addItem(new ItemStack(Material.DIAMOND_BLOCK, 16));
				LootGenerator.FillLootChest(inv);
				ev.getPlayer().openInventory(inv);
				ev.setCancelled(true);
			}
		}
	}
}
