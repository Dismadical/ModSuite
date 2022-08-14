package me.dismadical.modsuite.commands;

import me.dismadical.modsuite.ModSuite;
import me.dismadical.modsuite.utils.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author Dismadical (me.dismadical.modsuite.commands)
 * 7/13/2022
 * 7:03 PM
 * ModSuite
 * me.dismadical.modsuite.commands
 */

public class ModCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("modsuite.staff")) {
                if (ModSuite.getInstance().getHandler().getPlayersInStaffMode().contains(player)) {
                    ModSuite.getInstance().getHandler().removePlayerInStaffMode(player);
                } else {
                    int a = (int) Arrays.stream(player.getInventory().getArmorContents()).filter(Objects::nonNull).count();
                    int i = (int) Arrays.stream(player.getInventory().getContents()).filter(Objects::nonNull).count();
                    if (a > 0 || i > 0) {
                        player.sendMessage(CC.translate("&cYou can only enter staff mode with an empty inventory."));
                    } else {
                        ModSuite.getInstance().getHandler().addPlayerInStaffMode(player);
                    }
                }
            } else {
                player.sendMessage(CC.translate("&cYou do not have permission to use this command."));
            }
        } else {
            System.out.println("Player only command.");
        }

        return true;
    }

}
