package me.dismadical.modsuite;

import com.sun.org.apache.xpath.internal.operations.Mod;
import me.dismadical.modsuite.utils.CC;
import me.dismadical.modsuite.utils.ItemBuilder;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author Dismadical (me.dismadical.modsuite)
 * 7/13/2022
 * 5:38 PM
 * ModSuite
 * me.dismadical.modsuite
 */

public class ModHandler {

    public static List<Player> inStaffMode;
    public static List<Player> inVanish;
    public static List<Player> spectating;
    public static List<Player> frozen;
    public static List<Player> build;

    public List<Player> getBuildPlayers() { return build; }

    public void addBuildPlayer(Player player) { build.add(player); }

    public void removeBuildPlayer(Player player) {
        build.remove(player);
        Inventory inventory = player.getInventory();
        inventory.clear();
        inventory.setItem(0, getThruCompassItem());
        inventory.setItem(1, getInvseeItem());
        inventory.setItem(2, getReportsItem());
        inventory.setItem(4, getRandomTeleportItem());
        inventory.setItem(6, getVanishOnItem());
        inventory.setItem(7, getFreezeItem());
        inventory.setItem(8, getOnlinePlayersItem(player));
        player.updateInventory();
    }

    public List<Player> getFrozenPlayers() {
        return frozen;
    }

    public void addFrozenPlayer(Player player) {
        frozen.add(player);
    }

    public void removeFrozenPlayer(Player player) {
        frozen.remove(player);
    }

    public List<Player> getPlayersInStaffMode() {
        return inStaffMode;
    }

    public void addPlayerInStaffMode(Player player) {
        inStaffMode.add(player);
        addPlayerInVanish(player);
        Inventory inventory = player.getInventory();
        inventory.setItem(0, getThruCompassItem());
        inventory.setItem(1, getInvseeItem());
        inventory.setItem(2, getReportsItem());
        inventory.setItem(4, getRandomTeleportItem());
        inventory.setItem(6, getVanishOnItem());
        inventory.setItem(7, getFreezeItem());
        inventory.setItem(8, getOnlinePlayersItem(player));
        player.getInventory().setContents(inventory.getContents());
        player.setGameMode(GameMode.CREATIVE);
        player.sendTitle(CC.translate("&b&lStaff Mode Enabled!"), "", 10, 25, 10);
        player.updateInventory();
    }

    public void removePlayerInStaffMode(Player player) {
        inStaffMode.remove(player);
        removePlayerInVanish(player);
        player.getInventory().clear();
        player.setGameMode(GameMode.SURVIVAL);

        if (getBuildPlayers().contains(player)) {
            build.remove(player);
        }
        player.updateInventory();
    }

    public List<Player> getPlayersInVanish() {
        return inVanish;
    }

    public void addPlayerInVanish(Player player) {
        inVanish.add(player);
        Bukkit.getOnlinePlayers().stream().filter(p -> !(inVanish.contains(p))).forEach(p -> p.hidePlayer(ModSuite.getInstance(), player));
        Bukkit.getOnlinePlayers().stream().filter(p -> inVanish.contains(p)).forEach(p -> player.showPlayer(ModSuite.getInstance(), p));
        if (!player.hasPermission("modsuite.vanish.messages")) {
            Bukkit.getOnlinePlayers().stream().filter(p -> p.hasPermission("modsuite.staff")).forEach(p -> p.sendMessage(CC.translate("&4&lMurder &7-> &c" + player.getName() + " &fhas gone into &aVanish")));
        }
    }

    public void removePlayerInVanish(Player player) {
        inVanish.remove(player);
        Bukkit.getOnlinePlayers().stream().forEach(p -> p.showPlayer(ModSuite.getInstance(), player));
        Bukkit.getOnlinePlayers().stream().filter(p -> inVanish.contains(p)).forEach(p -> player.hidePlayer(ModSuite.getInstance(), p));
        if (!player.hasPermission("modsuite.vanish.messages")) {
            Bukkit.getOnlinePlayers().stream().filter(p -> p.hasPermission("modsuite.staff")).forEach(p -> p.sendMessage(CC.translate("&4&lMurder &7-> &c" + player.getName() + " &fhas gone out of &cVanish!")));
        }
    }

    public ItemStack getFreezeItem() {
        ItemBuilder itemBuilder = new ItemBuilder(Material.PACKED_ICE);
        itemBuilder.setAmount(1);
        itemBuilder.setName(CC.translate("&b&lFREEZE"));
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate("&7Click to freeze a"));
        lore.add(CC.translate("&7player and punish them"));
        itemBuilder.setLore(lore);
        return itemBuilder.build();
    }

    public ItemStack getInvseeItem() {
        ItemBuilder itemBuilder = new ItemBuilder(Material.BOOK);
        itemBuilder.setAmount(1);
        itemBuilder.setName(CC.translate("&b&lINVSEE"));
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate("&7Click to open an"));
        lore.add(CC.translate("&7inventory and see what"));
        lore.add(CC.translate("&7is inside of it"));
        itemBuilder.setLore(lore);
        return itemBuilder.build();
    }

    public ItemStack getFollowPlayerItem() {
        ItemBuilder itemBuilder = new ItemBuilder(Material.EMERALD);
        itemBuilder.setAmount(1);
        itemBuilder.setName(CC.translate("&b&lFOLLOW PLAYER"));
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate("&7Click to follow a"));
        lore.add(CC.translate("&7player"));
        itemBuilder.setLore(lore);
        return itemBuilder.build();
    }

    public ItemStack getRandomTeleportItem() {
        ItemBuilder itemBuilder = new ItemBuilder(Material.DIAMOND);
        itemBuilder.setAmount(1);
        itemBuilder.setName(CC.translate("&b&lRANDOM TELEPORT"));
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate("&7Click to teleport to"));
        lore.add(CC.translate("&7a random player"));
        itemBuilder.setLore(lore);
        return itemBuilder.build();
    }

    public ItemStack getReportsItem() {
        ItemBuilder itemBuilder = new ItemBuilder(Material.PAPER);
        itemBuilder.setAmount(1);
        itemBuilder.setName(CC.translate("&b&lREPORTS"));
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate("&7Click to open the"));
        lore.add(CC.translate("&7reports menu"));
        itemBuilder.setLore(lore);
        return itemBuilder.build();
    }

    public ItemStack getThruCompassItem() {
        ItemBuilder itemBuilder = new ItemBuilder(Material.COMPASS);
        itemBuilder.setAmount(1);
        itemBuilder.setName(CC.translate("&b&lTHRU COMPASS"));
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate("&7Click to phase"));
        lore.add(CC.translate("&7through walls"));
        itemBuilder.setLore(lore);
        return itemBuilder.build();
    }

    public ItemStack getVanishOnItem() {
        ItemBuilder itemBuilder = new ItemBuilder(Material.LIME_DYE);
        itemBuilder.setAmount(1);
        itemBuilder.setName(CC.translate("&b&lCLICK TO DISABLE VANISH"));
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate("&7Click to turn on"));
        lore.add(CC.translate("&7vanishing"));
        itemBuilder.setLore(lore);
        return itemBuilder.build();
    }

    public ItemStack getVanishOffItem() {
        ItemBuilder itemBuilder = new ItemBuilder(Material.GRAY_DYE);
        itemBuilder.setAmount(1);
        itemBuilder.setName(CC.translate("&b&lCLICK TO ENABLE VANISH"));
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate("&7Click to turn on"));
        lore.add(CC.translate("&7vanishing"));
        itemBuilder.setLore(lore);
        return itemBuilder.build();
    }

    public ItemStack getOnlinePlayersItem(Player player) {
        ItemBuilder itemBuilder = new ItemBuilder(Material.PLAYER_HEAD);
        itemBuilder.setPlayerHeadOwner(player.getName());
        itemBuilder.setAmount(1);
        itemBuilder.setName(CC.translate("&b&lONLINE STAFF"));
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate("&7Click to open the"));
        lore.add(CC.translate("&7online players menu"));
        itemBuilder.setLore(lore);
        return itemBuilder.build();
    }

    public void handleInvseeClick(Player player, Player target) {
        player.chat("/invsee " + target.getName());
    }

    public void handleReportsClick(Player player) {
        player.chat("/reports");
    }

    public void handleRandomTPClick(Player player) {
        Random random = new Random();
        List<Player> players = Bukkit.getOnlinePlayers().stream().filter(p -> !(getPlayersInStaffMode().contains(p))).collect(Collectors.toList());
        int max = players.size();
        try {
            int i = random.nextInt(max);
            Player target = players.get(i);
            player.teleport(target.getLocation());
            player.sendMessage(CC.translate("&cYou have been teleported to " + target.getName()));
        } catch (IllegalArgumentException e) {
            player.sendMessage(CC.translate("&cThere are no players online."));
        }
    }

    public void handleRandomTPBreak(Player player) {
        player.getInventory().setItem(4, getFollowPlayerItem());
    }

    public void handleFollowPlayerClick(Player player, Player target) {
        player.setGameMode(GameMode.SPECTATOR);
        player.chat("/spectate " + target.getName());
        player.sendMessage(CC.translate("&7Type '&cExit' &7to exit spectating"));
        spectating.add(player);
    }

    public void handleExitMessage(Player player) {
        if (spectating.contains(player)) {
            player.setGameMode(GameMode.CREATIVE);
            spectating.remove(player);
        }
    }

    public void handleFollowPlayerBreak(Player player) {
        player.getInventory().setItem(4, getRandomTeleportItem());
    }

    public void handleVanishOnClick(Player player) {
        removePlayerInVanish(player);
        player.getInventory().setItem(6, getVanishOffItem());
        player.updateInventory();
    }

    public void handleVanishOffClick(Player player) {
        addPlayerInVanish(player);
        player.getInventory().setItem(6, getVanishOnItem());
        player.updateInventory();
    }

    public void handleFreezeClick(Player player, Player target) {
        if (frozen.contains(target)) {
            Bukkit.getOnlinePlayers().stream().filter(p -> p.hasPermission("modsuite.staff")).forEach(p -> p.sendMessage(CC.translate("&4&lFROZEN &7» &4" + target.getName() + " &7is no longer frozen")));
            frozen.remove(target);
        } else {
            Bukkit.getOnlinePlayers().stream().filter(p -> p.hasPermission("modsuite.staff")).forEach(p -> p.sendMessage(CC.translate("&4&lFROZEN &7» &4" + target.getName() + " has been frozen by " + player.getName())));
            frozen.add(target);
        }
    }

    public void handleOnlinePlayerClick(Player player) {
        List<Player> players = getPlayersInStaffMode();
        int i = (int) Math.ceil((double) players.size() / 9);
        Inventory inventory = Bukkit.createInventory(player, 9 * i, CC.translate("&b&lONLINE STAFF"));
        LuckPerms api = LuckPermsProvider.get();
        players.forEach(p -> {
            String rank = api.getGroupManager().getGroup(api.getUserManager().getUser(p.getUniqueId()).getPrimaryGroup()).getFriendlyName();
            ItemBuilder itemBuilder = new ItemBuilder(Material.PLAYER_HEAD);
            itemBuilder.setPlayerHeadOwner(p.getName());
            itemBuilder.setAmount(1);
            itemBuilder.setName(CC.translate("&b&l" + p.getName()));
            List<String> lore = new ArrayList<>();
            lore.add(CC.translate("&bRank: &7" + rank));
            lore.add(CC.translate("&bVanish: &7" + (getPlayersInVanish().contains(p) ? "&aYes" : "&cNo")));
            lore.add(CC.translate("&bMod Mode: &7" + (getPlayersInStaffMode().contains(p) ? "&aYes" : "&cNo")));
            itemBuilder.setLore(lore);
            inventory.addItem(itemBuilder.build());
        });
        player.openInventory(inventory);
    }

    public void handlePlayerHeadClick(Player player, Player target) {
        player.closeInventory();
        player.teleport(target.getLocation());
    }


}
