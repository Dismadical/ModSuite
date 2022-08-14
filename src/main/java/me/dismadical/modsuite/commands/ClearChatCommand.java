package me.dismadical.modsuite.commands;

import me.dismadical.modsuite.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dismadical (me.dismadical.modsuite.commands)
 * 7/16/2022
 * 5:31 PM
 * ModSuite
 * me.dismadical.modsuite.commands
 */

public class ClearChatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            System.out.println("Player only command.");
            return false;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("modsuite.clearchat")) {
            player.sendMessage(CC.translate("&cYou do not have permission to use this command."));
            return false;
        }

        for (int i = 0; i < 100; i++) {
            Bukkit.getOnlinePlayers().stream().filter(p -> !p.hasPermission("modsuite.staff")).forEach(p -> p.sendMessage(""));
        }
        Bukkit.getOnlinePlayers().stream().forEach(p -> p.sendMessage(CC.translate("&cChat has been cleared by " + player.getName() + ".")));

        return true;
    }
}
