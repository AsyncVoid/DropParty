package me.Async.DP;

import org.bukkit.scheduler.BukkitRunnable;

public class DropRunnable extends BukkitRunnable
{
	private final Area area;
	
	public DropRunnable(Area area)
	{
		this.area = area;
	}
	
	@Override
	public void run() {
		for(int i = 0; i < 20; i++)
		{
			DP.drop(area);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
