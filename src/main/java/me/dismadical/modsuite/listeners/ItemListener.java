package me.dismadical.modsuite.listeners;

import me.dismadical.modsuite.ModHandler;
import me.dismadical.modsuite.ModSuite;
import me.dismadical.modsuite.utils.CC;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

/**
 * @author Dismadical (me.dismadical.modsuite.listeners)
 * 7/13/2022
 * 7:08 PM
 * ModSuite
 * me.dismadical.modsuite.listeners
 */

public class ItemListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        if (event.getHand() == null) return;
        if (event.getHand().equals(EquipmentSlot.OFF_HAND)) return;
        if (!inModMode(event.getPlayer())) return;
        event.setCancelled(true);

        Player player = event.getPlayer();
        ModHandler handler = ModSuite.getInstance().getHandler();

        if (event.getClickedBlock() != null) {
            if (event.getClickedBlock().getType() == Material.CHEST || event.getClickedBlock().getType() == Material.TRAPPED_CHEST) {
                Inventory inventory = ((Chest) event.getClickedBlock().getState()).getInventory();
                ItemStack[] items = inventory.getContents();
                Inventory inventory1 = Bukkit.createInventory(player, inventory.getSize());
                inventory1.setContents(items);
                player.openInventory(inventory1);
                player.sendMessage(CC.translate("&7(Silent) &cOpening Chest..."));
            }
        }

        if (event.getItem() == null) return;
        ItemStack itemStack = event.getItem();

        if (handler.getReportsItem().isSimilar(itemStack)) {
            handler.handleReportsClick(player);
        } else if (handler.getRandomTeleportItem().isSimilar(itemStack)) {
            if (event.getClickedBlock() != null) {
                handler.handleRandomTPBreak(event.getPlayer());
            } else {
                handler.handleRandomTPClick(player);
            }
        } else if (handler.getFollowPlayerItem().isSimilar(itemStack)) {
            if (event.getClickedBlock() != null) {
                handler.handleFollowPlayerBreak(event.getPlayer());
            }
        } else if (handler.getVanishOnItem().isSimilar(itemStack)) {
            handler.handleVanishOnClick(player);
        } else if (handler.getVanishOffItem().isSimilar(itemStack)) {
            handler.handleVanishOffClick(player);
        } else if (handler.getOnlinePlayersItem(player).isSimilar(itemStack)) {
            handler.handleOnlinePlayerClick(player);
        }

    }

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        if (event.getHand().equals(EquipmentSlot.OFF_HAND)) return;
        if (!(event.getRightClicked() instanceof Player)) return;
        if (!inModMode(event.getPlayer())) return;
        event.setCancelled(true);
        Player player = event.getPlayer();
        Player target = (Player) event.getRightClicked();
        if (player.getItemInHand().getType() == Material.AIR) return;
        ItemStack item = player.getItemInHand();
        ModHandler handler = ModSuite.getInstance().getHandler();
        if (handler.getInvseeItem().isSimilar(item)) {
            handler.handleInvseeClick(player, target);
        } else if (handler.getFollowPlayerItem().isSimilar(item)) {
            handler.handleFollowPlayerClick(player, target);
        } else if (handler.getFreezeItem().isSimilar(item)) {
            handler.handleFreezeClick(player, target);
        }

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!inModMode(event.getPlayer())) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!inModMode(event.getPlayer())) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (event.getCurrentItem() == null) return;
        if (event.getClickedInventory() == null) return;

        Player player = (Player) event.getWhoClicked();
        if (inModMode(player)) {
            event.setCancelled(true);
            if (event.getView().getTitle().equals(CC.translate("&b&lONLINE STAFF"))) {
                ItemStack item = event.getCurrentItem();
                SkullMeta meta = (SkullMeta) item.getItemMeta();
                Player target = Bukkit.getPlayerExact(meta.getOwner());
                if (target != null) {
                    player.teleport(target.getLocation());
                    player.closeInventory();
                }
            }
        }

    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if (event.getCursor() == null) return;

        Player player = (Player) event.getWhoClicked();
        if (inModMode(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (!inModMode(event.getPlayer())) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            if (!inModMode((Player) event.getDamager())) return;
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        if (!inModMode(event.getPlayer())) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onMoveEvent(PlayerMoveEvent event) {
        if (ModSuite.getInstance().getHandler().getFrozenPlayers().contains(event.getPlayer())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(CC.translate("&c&lYou are currently &4&lFROZEN"));
            event.getPlayer().sendMessage("");
            event.getPlayer().sendMessage(CC.translate("&7Join our discord, https://dsc.gg/murderrip"));
            event.getPlayer().sendMessage(CC.translate("&7and wait for a staff member to move you"));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (ModSuite.getInstance().getHandler().getFrozenPlayers().contains(event.getPlayer())) {
            ModSuite.getInstance().getHandler().removeFrozenPlayer(event.getPlayer());
            ComponentBuilder text = new ComponentBuilder(CC.translate("&4&lFROZEN &7» &4" + event.getPlayer().getName() + " has logged out whilst frozen.")).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(CC.translate("&4&lCLICK TO BAN")))).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ban " + event.getPlayer().getName() + " 14d Logged whilst frozen -s"));
            Bukkit.getOnlinePlayers().stream().filter(p -> p.hasPermission("modsuite.staff")).forEach(p -> p.spigot().sendMessage(text.create()));
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (!inModMode(event.getPlayer())) return;
        if (event.getMessage().equalsIgnoreCase("exit")) {
            event.setCancelled(true);
            new BukkitRunnable() {
                @Override
                public void run() {
                    ModSuite.getInstance().getHandler().handleExitMessage(event.getPlayer());
                }
            }.runTaskLater(ModSuite.getInstance(), 1);
        }
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        if (!inModMode(event.getPlayer())) return;
        event.getPlayer().setGameMode(GameMode.CREATIVE);
    }

    private boolean inModMode(Player player) {
        return ModSuite.getInstance().getHandler().getPlayersInStaffMode().contains(player) && !(ModSuite.getInstance().getHandler().getBuildPlayers().contains(player));
    }



}
