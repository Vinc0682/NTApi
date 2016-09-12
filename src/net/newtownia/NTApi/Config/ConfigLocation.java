package net.newtownia.NTApi.Config;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigLocation 
{
	private Location loc;
	private String world;
	
	public ConfigLocation(Location loc)
	{
		this.loc = loc;
		this.world = loc.getWorld().getName();
	}
	
	public ConfigLocation(Location loc, String world)
	{
		this.loc = loc;
		this.world = world;
	}
	
	public ConfigLocation(YamlConfiguration config, String path)
	{
		ConfigLocation cLoc = loadFromConfig(config, path);
		this.loc = cLoc.getRawLocation();
		this.world = cLoc.getWorldName();
	}
	
	public ConfigLocation(ConfigurationSection section)
	{
		ConfigLocation cLoc = loadFromConfig(section);
		this.loc = cLoc.getRawLocation();
		this.world = cLoc.getWorldName();
	}
	
	public Location getLocation()
	{
		if(loc.getWorld() == null)
		{
			World w = Bukkit.getServer().getWorld(world);
			loc.setWorld(w);
		}
		
		return loc;
	}
	
	public String getWorldName()
	{
		return world;
	}
	
	public Location getRawLocation()
	{
		return loc;
	}
	
	public void writeToConfig(YamlConfiguration config, String path)
	{
		config.set(path + ".World", world);
		config.set(path + ".X", loc.getX());
		config.set(path + ".Y", loc.getY());
		config.set(path + ".Z", loc.getZ());
		config.set(path + ".Yaw", (double)loc.getYaw());
		config.set(path + ".Pitch", (double)loc.getPitch());
	}
	
	public void writeToConfig(ConfigurationSection section)
	{
		section.set("World", world);
		section.set("X", loc.getX());
		section.set("Y", loc.getY());
		section.set("Z", loc.getZ());
		section.set("Yaw", (double)loc.getYaw());
		section.set("Pitch", (double)loc.getPitch());
	}
	
	public static ConfigLocation loadFromConfig(YamlConfiguration config, String path)
	{
		String world = config.getString(path + ".World");
		double x = config.getDouble(path + ".X");
		double y = config.getDouble(path + ".Y");
		double z = config.getDouble(path + ".Z");
		float yaw = (float)config.getDouble(path + ".Yaw");
		float pitch = (float)config.getDouble(path + ".Pitch");
		
		Location loc = new Location(null, x, y, z, yaw, pitch);
		
		return new ConfigLocation(loc, world);
	}
	
	public static ConfigLocation loadFromConfig(ConfigurationSection section)
	{
		String world = section.getString("World");
		double x = section.getDouble("X");
		double y = section.getDouble("Y");
		double z = section.getDouble("Z");
		float yaw = (float)section.getDouble("Yaw");
		float pitch = (float)section.getDouble("Pitch");
		
		Location loc = new Location(null, x, y, z, yaw, pitch);
		
		return new ConfigLocation(loc, world);
	}
}
