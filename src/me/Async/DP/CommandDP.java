package me.Async.DP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;

public class CommandDP implements CommandExecutor{

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if(args.length == 0)
		{
			
		}
		else if(args.length == 1)
		{
			switch(args[0].toLowerCase())
			{
				case "help":
				    sender.sendMessage("Commands: ");
				    sender.sendMessage(" - force ");
				    sender.sendMessage(" - when ");
				    sender.sendMessage(" - where");
				    return true;
				case "force":
					DP.start(sender.getName());
					return true;
				case "when":
					Calendar now = Calendar.getInstance();
					Calendar then = (Calendar) now.clone();
					then.set(Calendar.HOUR, now.get(Calendar.HOUR) + 1);
					then.set(Calendar.MINUTE, 0);
					then.set(Calendar.SECOND, 0);
					
					HashMap<TimeUnit, Long> diff = computeDiff(now.getTime(), then.getTime());
					
					sender.sendMessage("The current time is " + now.get(Calendar.HOUR) + ":" + now.get(Calendar.MINUTE));
					sender.sendMessage("The next drop party will be in " + diff.get(TimeUnit.MINUTES) + " minutes and " + diff.get(TimeUnit.SECONDS) + " seconds.");
					return true;
				case "where":
					if(DP.getNext() == null)
						sender.sendMessage("The next drop party's location is unknown!");
					else
						sender.sendMessage("The next drop party is located in the " + DP.getNext().getName());
					return true;
				case "wand":
					if(sender instanceof Player)
					{
						ItemStack is = new ItemStack(Material.DIAMOND_SPADE, 1);
						ItemMeta im = is.getItemMeta();
						im.setLore(Arrays.asList("§cDP Wand"));
						is.setItemMeta(im);
						((Player)sender).getInventory().addItem(is);
					}else sender.sendMessage("You need to be a player to perform this command!");
					return true;
				case "disable":
					Settings.Enabled = false;
					Main.instance.getConfig().set("Enabled", false);
					return true;
				case "enable":
					Settings.Enabled = true;
					Main.instance.getConfig().set("Enabled", true);
					return true;
			}
		}
		else if(args.length == 2)
		{
			switch(args[0].toLowerCase())
			{
				case "adddrop":
					if(sender instanceof Player)
					{
						ItemStack is = ((Player)sender).getInventory().getItemInMainHand();
						if(is.getType() != Material.AIR)
						{
							String id = UUID.randomUUID().toString();
							
							int type = is.getTypeId();
							int amount = is.getAmount();
							String name = "";
							List<String> lore = new ArrayList<String>();
							if(is.hasItemMeta())
							{
								if(is.getItemMeta().hasDisplayName())
									name = is.getItemMeta().getDisplayName();
								if(is.getItemMeta().hasLore())
									lore = is.getItemMeta().getLore();
							}
							List<String> Enchants = new ArrayList<String>();
							for (Enchantment enc : is.getEnchantments().keySet())
								Enchants.add(enc.getId() + ":" + is.getEnchantmentLevel(enc));
							
							
							FileConfiguration lootConfig = Main.instance.getLootConfig();
							
							lootConfig.set(id + ".name", name);
							lootConfig.set(id + ".type", type);
							lootConfig.set(id + ".amount", amount);
							lootConfig.set(id + ".enchants", Enchants);
							lootConfig.set(id + ".lore", lore);
							
						} else sender.sendMessage("You need to have an item in your hand!");
					} else sender.sendMessage("You need to be a player to perform this command!");
					return true;
				case "need":
					try
					{
						Settings.NeedForDrop = Integer.parseInt(args[1]);
						Main.instance.getConfig().set("DropPartyOn", Settings.NeedForDrop);
						sender.sendMessage("Now needs " + args[1] + " players to start drop party.");
					}catch(Exception ex) { sender.sendMessage(args[1] + "is not a valid integer!"); }
					return true;
			}
		}
		if(args.length >= 2){
			if(args[0].equalsIgnoreCase("create"))
			{
				if(sender instanceof Player)
				{
					Location pos1 = (Location) getMetadata((Metadatable) sender, "dropparty.pos1").value();
					Location pos2 = (Location) getMetadata((Metadatable) sender, "dropparty.pos2").value();
					Location pos3 = pos1.clone();
					
					pos1.setX(Math.min(pos1.getX(), pos2.getX()));
					pos1.setY(Math.min(pos1.getY(), pos2.getY()));
					pos1.setZ(Math.min(pos1.getZ(), pos2.getZ()));
					
					pos2.setX(Math.max(pos3.getX(), pos2.getX()));
					pos2.setY(Math.max(pos3.getY(), pos2.getY()));
					pos2.setZ(Math.max(pos3.getZ(), pos2.getZ()));
					
					if(pos1 == null || pos2 == null)
					{
						sender.sendMessage("You need to set your positions using the wand");
						return true;
					}
					
					FileConfiguration locationConfig = Main.instance.getLocationConfig();
					
					//Set<String> ids = locationConfig.getRoot().getKeys(false);
					//int id = Integer.parseInt((String)ids.toArray()[ids.size()-1]) + 1;
					
					String id = UUID.randomUUID().toString();
					
					String name = "";
					for(int i = 1; i < args.length; i++)
					{
						name += args[i] + " ";
					}
					name = name.trim();
					String world = ((Player) sender).getWorld().getName();
					
					Area area = new Area(/*id,*/ name, world, pos1, pos2);
					
					sender.sendMessage("A drop zone has been created!");
					sender.sendMessage("    ID: " + id);
					sender.sendMessage("    Name: " + name);
					sender.sendMessage("    World: " + world);
					sender.sendMessage("    Pos1: [" + pos1.getX() + "," + pos1.getY() + "," + pos1.getZ() + "]");
					sender.sendMessage("    Pos2: [" + pos2.getX() + "," + pos2.getY() + "," + pos2.getZ() + "]");
					
					Settings.Areas.add(area);
					
					locationConfig.set(id + ".name", name);
					locationConfig.set(id + ".world", world);
					locationConfig.set(id + ".pos1", Arrays.asList(pos1.getBlockX(), pos1.getBlockY(), pos1.getBlockZ()));
					locationConfig.set(id + ".pos2", Arrays.asList(pos2.getBlockX(), pos2.getBlockY(), pos2.getBlockZ()));
				}else sender.sendMessage("You need to be a player to perform this command!");
				return true;
			}
		}
		return false;
	}
	
	private static final HashMap<TimeUnit,Long> computeDiff(Date date1, Date date2) {
	    long diffInMillies = date2.getTime() - date1.getTime();
	    List<TimeUnit> units = new ArrayList<TimeUnit>(EnumSet.allOf(TimeUnit.class));
	    Collections.reverse(units);
	    HashMap<TimeUnit,Long> result = new LinkedHashMap<TimeUnit,Long>();
	    long milliesRest = diffInMillies;
	    for ( TimeUnit unit : units ) {
	        long diff = unit.convert(milliesRest,TimeUnit.MILLISECONDS);
	        long diffInMilliesForUnit = unit.toMillis(diff);
	        milliesRest = milliesRest - diffInMilliesForUnit;
	        result.put(unit,diff);
	    }
	    return result;
	}
	
	private static final MetadataValue getMetadata(Metadatable object, String s)
	{
		List<MetadataValue> mvs = object.getMetadata(s);
		for(MetadataValue mv : mvs)
		{
			if(mv.getOwningPlugin() == Main.instance)
			{
				return mv;
			}
		}
		return null;
	}
}
