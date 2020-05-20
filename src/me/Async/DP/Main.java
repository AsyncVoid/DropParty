package me.Async.DP;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	public static Main instance;
	private static FileConfiguration locations;
	private static File locationsFile; 
	private static FileConfiguration loot;
	private static File lootFile;
	
	public Main()
	{
		instance = this;
	}
	
	@Override
	public void onEnable()
	{
		this.saveDefaultConfig();
		
		locationsFile = new File(this.getDataFolder(), "locations.yml");
		locations = YamlConfiguration.loadConfiguration(locationsFile);
		
		lootFile = new File(this.getDataFolder(), "loot.yml");
		loot = YamlConfiguration.loadConfiguration(lootFile);
		
		Settings.Load();
		getCommand("dropparty").setExecutor(new CommandDP());
		
		int seconds = Calendar.getInstance().get(Calendar.SECOND);
		new TimeUpdater().runTaskTimerAsynchronously(this, (61-seconds)*20l, 1200l);
		
		getServer().getPluginManager().registerEvents(new InteractListener(), this);
		getServer().getPluginManager().registerEvents(new ChangeBlockListener(), this);
	}
	
	@Override
	public void onDisable()
	{
		this.saveConfig();
		this.saveLocationConfig();
		this.saveLootConfig();
	}
	
	public FileConfiguration getLocationConfig()
	{
	    return locations;
	}
	public void saveLocationConfig() 
	{
	    try {
	    	locations.save(locationsFile);
	    } catch (IOException ex) {
	        getLogger().log(Level.SEVERE, "Could not save config to " + locationsFile, ex);
	    }
	}
	
	public FileConfiguration getLootConfig()
	{
	    return loot;
	}
	public void saveLootConfig() 
	{
	    try {
	    	loot.save(lootFile);
	    } catch (IOException ex) {
	        getLogger().log(Level.SEVERE, "Could not save config to " + lootFile, ex);
	    }
	}
}
