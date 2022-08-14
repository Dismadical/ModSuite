package me.dismadical.modsuite.utils.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashMap;

/**
 * @author Dismadical (me.dismadical.survival.utils.menu)
 * 7/12/2022
 * 6:19 PM
 * Survival
 * me.dismadical.survival.utils.menu
 */

public abstract class Menu implements InventoryHolder {

   protected Inventory inventory;

   public Menu() {}

   public abstract String getTitle();

   public abstract int getSlots();

   public abstract HashMap<Integer, Button> getButtons();

   public void open(Player player) {
      inventory = Bukkit.createInventory(this, getSlots(), getTitle());
      getButtons().forEach((slot, button) -> inventory.setItem(slot, button.getItemStack()));
      player.openInventory(inventory);
   }

   @Override
   public Inventory getInventory() {
      return inventory;
   }

}
