package me.dismadical.modsuite.commands;

import me.dismadical.modsuite.ModSuite;
import me.dismadical.modsuite.utils.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dismadical (me.dismadical.modsuite.commands)
 * 7/16/2022
 * 4:27 PM
 * ModSuite
 * me.dismadical.modsuite.commands
 */

public class BuildCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            System.out.println("Player only command.");
            return false;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("modsuite.build")) {
            player.sendMessage(CC.translate("&cYou do not have permission to use this command."));
            return false;
        }

        if (!ModSuite.getInstance().getHandler().getPlayersInStaffMode().contains(player)) {
            player.sendMessage(CC.translate("&cYou must be in staff mode to use this command."));
            return false;
        }

        if (ModSuite.getInstance().getHandler().getBuildPlayers().contains(player)) {
            ModSuite.getInstance().getHandler().removeBuildPlayer(player);
        } else {
            ModSuite.getInstance().getHandler().addBuildPlayer(player);
        }

        player.sendMessage(CC.translate("&6Build&7: " + (ModSuite.getInstance().getHandler().getBuildPlayers().contains(player) ? "&aEnabled" : "&cDisabled")));

        return true;
    }
}
