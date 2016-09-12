package net.newtownia.NTApi;

import net.newtownia.NTApi.Config.ConfigInventorySerializer;
import net.newtownia.NTApi.Config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

/*
 * This is just a stump main class for plugin.yml
 * 
 */
public class Main extends JavaPlugin
{
    private final Main instance = this;

    @Override
    public void onEnable()
    {
        //testSaveInventory();
    }

    private void testSaveInventory()
    {
        Bukkit.getScheduler().runTask(this, new Runnable() {
            @Override
            public void run()
            {
                Inventory inv = Bukkit.createInventory(null, InventoryType.PLAYER);

                ItemStack stack = new ItemStack(Material.BEDROCK, 5);
                stack.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 5000);

                ItemMeta meta = stack.getItemMeta();

                meta.setDisplayName("§cOP-BEDROCK!");

                stack.setItemMeta(meta);

                inv.setItem(3, stack);

                YamlConfiguration configuration = ConfigManager.loadOrCreateConfigFile("tests.yml", instance);

                String serializedInv = ConfigInventorySerializer.inventoryToString(inv);

                configuration.set("TestInventory", serializedInv);

                try {
                    ConfigManager.SaveConfigurationToFile(configuration, "tests.yml", instance);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Inventory inv2 = ConfigInventorySerializer.stringToInventory(serializedInv);
                String serialized2 = ConfigInventorySerializer.inventoryToString(inv2);

                Bukkit.getLogger().info(serializedInv);
                Bukkit.getLogger().info(serialized2);
            }
        });
    }
}
