package me.dismadical.modsuite.xrayalerts;

import me.dismadical.modsuite.ModSuite;
import me.dismadical.modsuite.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dismadical (me.dismadical.modsuite.xrayalerts)
 * 7/16/2022
 * 11:49 AM
 * ModSuite
 * me.dismadical.modsuite.xrayalerts
 */

public class XRayListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("modsuite.xray")) {
            if (ModSuite.getInstance().getConfig().get("XRay." + player.getUniqueId().toString()) == null) {
                ModSuite.getInstance().getConfig().set("XRay." + player.getUniqueId().toString() + ".Coal", true);
                ModSuite.getInstance().getConfig().set("XRay." + player.getUniqueId().toString() + ".Iron", true);
                ModSuite.getInstance().getConfig().set("XRay." + player.getUniqueId().toString() + ".Gold", true);
                ModSuite.getInstance().getConfig().set("XRay." + player.getUniqueId().toString() + ".Emerald", true);
                ModSuite.getInstance().getConfig().set("XRay." + player.getUniqueId().toString() + ".Diamond", true);
                ModSuite.getInstance().getConfig().set("XRay." + player.getUniqueId().toString() + ".Netherite", true);
                ModSuite.getInstance().getConfig().set("XRay." + player.getUniqueId().toString() + ".Redstone", true);
                ModSuite.getInstance().getConfig().set("XRay." + player.getUniqueId().toString() + ".Lapis", true);
                ModSuite.getInstance().saveConfig();
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("modsuite.xray")) {
            ModSuite.getInstance().saveConfig();
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Material mat = event.getBlock().getType();
        if (ModSuite.getInstance().getXrayNames().containsKey(mat)) {
            Bukkit.getOnlinePlayers().stream().filter(p -> p.hasPermission("modsuite.xray")).filter(p -> ModSuite.getInstance().getConfig().getBoolean("XRay." + p.getUniqueId().toString() + "." + ModSuite.getInstance().getXrayNames().get(mat))).forEach(p -> p.sendMessage(CC.translate("&b&lXRAY &7» &b" + player.getName() + " &fhas just mined &b" + ModSuite.getInstance().getXrayNames().get(mat))));
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        if (event.getCurrentItem() == null) return;
        if (!event.getView().getTitle().equals(CC.translate("&b&lX-Ray Alerts"))) return;

        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        String name = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
        boolean value = ModSuite.getInstance().getConfig().getBoolean("XRay." + player.getUniqueId().toString() + "." + name);
        ModSuite.getInstance().getConfig().set("XRay." + player.getUniqueId().toString() + "." + name, !value);
        ItemMeta meta = event.getCurrentItem().getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate("&7Click to toggle X-Ray alerts"));
        lore.add(CC.translate("&7for this material."));
        lore.add("");
        lore.add(CC.translate("&bCurrent &7: " + (ModSuite.getInstance().getConfig().getBoolean("XRay." + player.getUniqueId().toString() + "." + name) ? CC.translate("&aYes") : CC.translate("&cNo"))));
        meta.setLore(lore);
        event.getCurrentItem().setItemMeta(meta);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getView().getTitle().equals(CC.translate("&b&lX-Ray Alerts"))) {
            ModSuite.getInstance().saveConfig();
        }
    }

}
