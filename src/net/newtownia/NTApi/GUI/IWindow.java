package net.newtownia.NTApi.GUI;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public interface IWindow 
{
	/*
	 * Opens the Window
	 */
	public void show(Player p);
	
	public Inventory createInventory(Player p);
	/*
	 * Handles the click-event
	 */
	public void onClick(InventoryClickEvent event);
	
	/*
	 * Should the inventory click be aborted?
	 */
	public boolean getInvLock();
	
	/*
	 * Should air-blocks be ignored?
	 */
	public boolean getIgnoreAir();
	
	/*
	 * Which slot should be the last to be clickable?
	 */
	public int getLastClickableItem();
}
