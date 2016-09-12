package net.newtownia.NTApi.Config;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

/**
 * Created by Vinc0682 on 04.12.2015.
 */
public class ConfigInventorySerializer
{
    public static String inventoryToString(Inventory inv)
    {
        //String serialization = inv.getSize() + ";";

        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append(inv.getSize());
        resultBuilder.append(";");

        for (int i = 0; i < inv.getSize(); i++)
        {
            ItemStack item = inv.getItem(i);
            if (item != null)
            {
                //String serializedItemStack = new String();
                StringBuilder serializedItem = new StringBuilder();

                String itemMaterial = item.getType().name();
                //serializedItemStack += "t@" + isType;

                serializedItem.append("t@");
                serializedItem.append(itemMaterial);

                if (item.getDurability() != 0)
                {
                    String itemDurability = String.valueOf(item.getDurability());

                    //serializedItemStack += ":d@" + itemDurability;
                    serializedItem.append(":d@");
                    serializedItem.append(itemDurability);
                }

                if (item.getAmount() != 1)
                {
                    String itemAmount = String.valueOf(item.getAmount());

                    //serializedItemStack += ":a@" + itemAmount;
                    serializedItem.append(":a@");
                    serializedItem.append(itemAmount);
                }

                Map<Enchantment,Integer> itemEnchantments = item.getEnchantments();
                if (itemEnchantments.size() > 0)
                {
                    for (Map.Entry<Enchantment,Integer> enchantment : itemEnchantments.entrySet())
                    {
                        //serializedItemStack += ":e@" + ench.getKey().getId() + "@" + ench.getValue();
                        serializedItem.append(":e@");
                        serializedItem.append(enchantment.getKey().getName());
                        serializedItem.append("@");
                        serializedItem.append(enchantment.getValue());
                    }
                }

                //serialization += i + "#" + serializedItemStack + ";";
                resultBuilder.append(i);
                resultBuilder.append("#");
                resultBuilder.append(serializedItem.toString());
                resultBuilder.append(";");
            }
        }

        //return serialization;
        return resultBuilder.toString();
    }

    public static Inventory stringToInventory(String serializedInventory)
    {
        String[] serializedBlocks = serializedInventory.split(";");
        String invInfo = serializedBlocks[0];
        Inventory deserializedInventory = Bukkit.getServer().createInventory(null, Integer.valueOf(invInfo));

        for (int i = 1; i < serializedBlocks.length; i++)
        {
            String[] serializedBlock = serializedBlocks[i].split("#");
            int stackPosition = Integer.valueOf(serializedBlock[0]);

            if (stackPosition >= deserializedInventory.getSize())
            {
                continue;
            }

            ItemStack item = null;
            Boolean createdItemStack = false;

            String[] serializedItemStack = serializedBlock[1].split(":");
            for (String itemInfo : serializedItemStack)
            {
                String[] itemAttribute = itemInfo.split("@");
                if (itemAttribute[0].equals("t"))
                {
                    item = new ItemStack(Material.getMaterial(itemAttribute[1]));
                    createdItemStack = true;
                }
                else if (itemAttribute[0].equals("d") && createdItemStack)
                {
                    item.setDurability(Short.valueOf(itemAttribute[1]));
                }
                else if (itemAttribute[0].equals("a") && createdItemStack)
                {
                    item.setAmount(Integer.valueOf(itemAttribute[1]));
                }
                else if (itemAttribute[0].equals("e") && createdItemStack)
                {
                    item.addUnsafeEnchantment(Enchantment.getByName(itemAttribute[1]), Integer.valueOf(itemAttribute[2]));
                }
            }
            deserializedInventory.setItem(stackPosition, item);
        }

        return deserializedInventory;
    }
}
