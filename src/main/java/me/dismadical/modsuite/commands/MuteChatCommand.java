package me.dismadical.modsuite.commands;

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
 * 7/16/2022
 * 5:27 PM
 * ModSuite
 * me.dismadical.modsuite.commands
 */

public class MuteChatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            System.out.println("Player only command.");
            return false;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("modsuite.mutechat")) {
            player.sendMessage(CC.translate("&cYou do not have permission to use this command."));
            return false;
        }

        if (ModSuite.getInstance().isChatMuted()) {
            ModSuite.getInstance().setChatMuted(false);
        } else {
            ModSuite.getInstance().setChatMuted(true);
        }
        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(CC.translate("&cChat has been " + (ModSuite.getInstance().isChatMuted() ? "&cmuted" : "&cunmuted") + " by " + player.getName() + ".")));

        return true;
    }
}
