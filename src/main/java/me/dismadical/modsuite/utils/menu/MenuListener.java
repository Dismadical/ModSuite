package me.dismadical.modsuite.utils.menu;

import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * @author Dismadical (me.dismadical.survival.utils.menu)
 * 7/12/2022
 * 6:27 PM
 * Survival
 * me.dismadical.survival.utils.menu
 */

public class MenuListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        if (event.getClickedInventory() == null) return;
        if (event.getCurrentItem() == null) return;

        Player player = (Player) event.getWhoClicked();

        if (event.getClickedInventory().getHolder() instanceof Menu) {

            event.setCancelled(true);
            Menu menu = (Menu) event.getClickedInventory().getHolder();
            menu.getButtons().forEach((slot, button) -> {
                if (event.getSlot() == slot) {
                    button.setClickType(event.getClick());
                    button.handleButton(player);
                }
            });
        }
    }

}
