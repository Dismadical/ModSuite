package me.dismadical.modsuite.commands;

import me.dismadical.modsuite.ModHandler;
import me.dismadical.modsuite.ModSuite;
import me.dismadical.modsuite.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dismadical (me.dismadical.modsuite.commands)
 * 8/1/2022
 * 8:45 PM
 * ModSuite
 * me.dismadical.modsuite.commands
 */

public class FreezeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            System.out.println("Player only command.");
            return false;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("modsuite.freeze")) {
            player.sendMessage(CC.translate("&cYou do not have permission to use this command."));
            return false;
        }

        if (!(ModSuite.getInstance().getHandler().getPlayersInStaffMode().contains(player))) {
            player.sendMessage(CC.translate("&cYou must be in staff mode to use this command."));
            return false;
        }

        if (args.length == 0) {
            player.sendMessage(CC.translate("&cUsage: /freeze <player>"));
            return false;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            player.sendMessage(CC.translate("&cPlayer not found."));
            return false;
        }

        if (ModSuite.getInstance().getHandler().getFrozenPlayers().contains(target)) {
            Bukkit.getOnlinePlayers().stream().filter(p -> p.hasPermission("modsuite.staff")).forEach(p -> p.sendMessage(CC.translate("&4&lFROZEN &7» &4" + target.getName() + " &7is no longer frozen")));
            ModSuite.getInstance().getHandler().removeFrozenPlayer(target);
        } else {
            Bukkit.getOnlinePlayers().stream().filter(p -> p.hasPermission("modsuite.staff")).forEach(p -> p.sendMessage(CC.translate("&4&lFROZEN &7» &4" + target.getName() + " has been frozen by " + player.getName())));
            ModSuite.getInstance().getHandler().addFrozenPlayer(target);
        }

        return true;
    }
}
