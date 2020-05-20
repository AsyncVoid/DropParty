package me.Async.DP;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Chest;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class ChangeBlockListener implements Listener
{
	@EventHandler
	public void onBlockPhysics(EntityChangeBlockEvent ev)
	{
		if(ev.getEntityType() == EntityType.FALLING_BLOCK)
		{
			if(ev.getTo() == Material.OBSIDIAN)
			{
				Location loc = ev.getBlock().getLocation();
				ev.getBlock().setType(Material.CHEST, false);
				loc.getWorld().strikeLightningEffect(loc);
				loc.getWorld().playEffect(loc, Effect.EXTINGUISH, 10);
				loc.getWorld().playEffect(loc, Effect.MOBSPAWNER_FLAMES, 0, 10);
				loc.getWorld().playSound(loc, Sound.BLOCK_PORTAL_TRAVEL, 1f, 1f);
				ev.setCancelled(true);
			    Chest chest = (Chest) loc.getBlock().getState();
			    chest.setMetadata("dropparty.chest", new FixedMetadataValue(Main.instance, true));
			}
		}
	}

}
