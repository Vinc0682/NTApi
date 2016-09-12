package net.newtownia.NTApi.GUI;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/*
 * This is actually the main class for the GUI-API to manage all windows / add a simple management
 */
public class GUIManager<T> implements Listener
{
 	private T defaultState;
	private Map<T, IWindow> windows;
	private Map<UUID, T> playerStates;
	private String invTitle = "§6Using NTAPI";
	private JavaPlugin plugin;

	public GUIManager(JavaPlugin plugin, T defaultState, String inventoryTitle)
	{
		this.defaultState = defaultState;
		this.invTitle = inventoryTitle;
		this.plugin = plugin;

        windows = new HashMap<>();
        playerStates = new HashMap<>();
	}
	
	public void registerListener()
	{
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	public void Navigate(Player p, T state)
	{
		playerStates.put(p.getUniqueId(), state);
		ShowActual(p);
	}

	public void ShowActual(Player p)
	{
        UUID pUUID = p.getUniqueId();
		if(!playerStates.containsKey(pUUID))
			playerStates.put(pUUID, defaultState);
		T state = playerStates.get(pUUID);
		if(windows.containsKey(state))
		{
			windows.get(state).show(p);
		}
		else
		{
			plugin.getLogger().info("NTApi / GUIManager  No Window assigned to state " + state.toString() + " found.");
			p.sendMessage("§cAn internal error occured by processing this command.");
			p.sendMessage("§cPlease inform the server owner / an admin or the plugin developer.");
		}
	}
	
	@EventHandler
	public void inventoryClick(InventoryClickEvent event)
	{
		if (event.getInventory().getName().equalsIgnoreCase(invTitle))
		{
			Player p = (Player) event.getWhoClicked();
            UUID pUUID = p.getUniqueId();

			if(!playerStates.containsKey(pUUID))
				playerStates.put(pUUID, defaultState);
			T pState = playerStates.get(pUUID);
			if(!windows.containsKey(pState))
			{
				plugin.getLogger().info("NTApi / GUIManager  No Window assigned to state " + pState.toString() + " found.");
				p.sendMessage("§cAn internal error occurred by processing this command.");
				p.sendMessage("§cPlease inform the server owner / an admin or the plugin developer.");
				return;
			}
			IWindow window = windows.get(pState);
			if(window.getInvLock())
				event.setCancelled(true);
			if(event.getSlot() <= window.getLastClickableItem())
			{
				if(window.getIgnoreAir())
				{
					ItemStack clickedItem = event.getCurrentItem();
					if(clickedItem != null && clickedItem.getType() != Material.AIR)
						window.onClick(event);
				}
				else
					window.onClick(event);
			}
		}
	}

	public void setPlayerState(Player p, T state)
	{
		playerStates.put(p.getUniqueId(), state);
	}
	public T getPlayerState(Player p)
	{
		return playerStates.get(p.getUniqueId());
	}
	public void addWindow(T state, IWindow window)
	{
		windows.put(state, window);
	}
	public IWindow getWindow(T state)
	{
		return windows.get(state);
	}
	public void removeWindow(T state)
	{
		windows.remove(state);
	}
	public String getInventoryTitle()
	{
		return invTitle;
	}
	public void setInventoryTitle(String title)
	{
		this.invTitle = title;
	}
}
