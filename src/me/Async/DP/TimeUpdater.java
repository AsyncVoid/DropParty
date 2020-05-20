package me.Async.DP;

import java.util.Calendar;
import java.util.Random;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Bukkit;

public class TimeUpdater extends BukkitRunnable {

	@Override
	public void run()
	{
		if(Settings.Enabled)
		{
			Calendar rightNow = Calendar.getInstance();
			int minute = rightNow.get(Calendar.MINUTE);
			if(minute == 0)
			{
				if(Bukkit.getServer().getOnlinePlayers().size() >= Settings.NeedForDrop)
				{
					DP.start();
				}
				else
				{
					Bukkit.broadcastMessage("Not enough players for a drop party!");
				}
			}
			else if(minute == 45)
			{
				Random rand = new Random();
				int choice = rand.nextInt(Settings.Areas.size());
				Area area = (Area) Settings.Areas.toArray()[choice];
				DP.setNext(area);
				Bukkit.broadcastMessage("The drop party will be located in " + area.getName() + "!");
			}
			else if(minute >= 55)
			{
				Bukkit.broadcastMessage("Drop party will start in " + 
						(60-minute) + 
						" minute(s). " + 
						Math.max(0, Settings.NeedForDrop - Bukkit.getServer().getOnlinePlayers().size()) + 
						" more players are needed!");
			}
		}
	}
}
