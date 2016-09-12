package net.newtownia.NTApi.Config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.Map;

public class ConfigManager 
{
	/*
	 * Loads or create a config file. The original config file must be placed in the /src/ folder
	 * @param	fileName	The name of the file (original and copy)
	 * @param	plugin	The plugin calling using the api
	 */
	public static YamlConfiguration loadOrCreateConfigFile(String fileName, JavaPlugin plugin)
	{
		File configFile = getFile(fileName, plugin);
		
		if(!configFile.exists())
		{
			configFile.getParentFile().mkdirs();
			copy(plugin.getResource(fileName), configFile);
		}
		
		return loadConfig(configFile);
	}

    public static YamlConfiguration loadConfig(File file)
    {
        YamlConfiguration config = new YamlConfiguration();
        try
        {
            config.load(file);
            return config;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static File createConfigFile(String fileName, JavaPlugin plugin)
    {
        File configFile = getFile(fileName, plugin);
        configFile.getParentFile().mkdirs();
        copy(plugin.getResource(fileName), configFile);
        return configFile;
    }

    public static File createConfigFile(String source, String target, JavaPlugin plugin)
    {
        File configFile = getFile(target, plugin);
        configFile.getParentFile().mkdirs();
        copy(plugin.getResource(source), configFile);
        return configFile;
    }
	
	public static void SaveConfigurationToFile(YamlConfiguration config, String fileName, JavaPlugin plugin) throws IOException
	{
		config.save(getFile(fileName, plugin));
	}
	
    private static void copy(InputStream in, File file) //From https://www.spigotmc.org/wiki/config-files/
    { 
        try 
        {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) 
            {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();

        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

	public static ConfigurationSection getOrCreateSection(YamlConfiguration configuration, String name)
    {
        if(!configuration.isConfigurationSection(name))
            configuration.createSection(name);

        return configuration.getConfigurationSection(name);
    }

	public static ConfigurationSection getOrCreateSection(ConfigurationSection section, String name)
	{
		if(!section.isConfigurationSection(name))
			section.createSection(name);

		return section.getConfigurationSection(name);
	}

	public static boolean upgrade(YamlConfiguration original, YamlConfiguration pattern)
    {
        boolean changed = false;
        for (Map.Entry<String, Object> entry : pattern.getValues(true).entrySet())
        {
            boolean hasValue = true;
            try
            {
                Object value = original.get(entry.getKey());
                if (value == null)
                    hasValue = false;
            }
            catch (Exception e)
            {
                hasValue = false;
            }
            if (!hasValue)
            {
                original.set(entry.getKey(), entry.getValue());
                changed = true;
            }
        }
        return changed;
    }

    public static File getFile(String fileName, JavaPlugin plugin)
    {
        return new File(plugin.getDataFolder(), fileName);
    }
}
