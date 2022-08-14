package me.dismadical.modsuite.xrayalerts;

import me.dismadical.modsuite.ModSuite;
import me.dismadical.modsuite.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dismadical (me.dismadical.modsuite.xrayalerts)
 * 7/16/2022
 * 11:59 AM
 * ModSuite
 * me.dismadical.modsuite.xrayalerts
 */

public class XRayCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;
            if (!player.hasPermission("modsuite.xray")) {
                player.sendMessage(CC.translate("&cNo permission."));
                return false;
            }

            Inventory inventory = Bukkit.createInventory(player, 9, CC.translate("&b&lX-Ray Alerts"));
            ModSuite.getInstance().getXrayBlocks().forEach((mat, name) -> {
                ItemStack item = new ItemStack(mat, 1);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(CC.translate("&b&l" + name));
                List<String> lore = new ArrayList<>();
                lore.add(CC.translate("&7Click to toggle X-Ray alerts"));
                lore.add(CC.translate("&7for this material."));
                lore.add("");
                lore.add(CC.translate("&bCurrent &7: " + (ModSuite.getInstance().getConfig().getBoolean("XRay." + player.getUniqueId().toString() + "." + name) ? CC.translate("&aYes") : CC.translate("&cNo"))));
                meta.setLore(lore);
                item.setItemMeta(meta);
                inventory.addItem(item);
            });
            player.openInventory(inventory);

        } else {
            System.out.println("Player only command.");
        }

        return true;
    }
}
