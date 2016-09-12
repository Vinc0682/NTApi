package net.newtownia.NTApi.Config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Vinc0682 on 04.12.2015.
 */
@Deprecated
public class ConfigInventory
{
    private Inventory inv;

    public ConfigInventory(Inventory inv)
    {
        this.inv = inv;
    }

    public Inventory getInventory() {
        return inv;
    }

    public void setInventory(Inventory inv) {
        this.inv = inv;
    }

    public void saveToConfig(ConfigurationSection section)
    {
        section.set("items", inv.getContents());
    }

    public void saveToConfig(YamlConfiguration configuration, String path)
    {
        saveToConfig(ConfigManager.getOrCreateSection(configuration, path));
    }

    public void loadFromConfig(ConfigurationSection section)
    {
        int counter = 0;

        List<String> items = section.getStringList("items");

        for(String item : items)
        {
            if(item != "null")
            {
            }
            counter += 1;
        }
    }
}
