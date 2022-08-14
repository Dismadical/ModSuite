package me.dismadical.modsuite.commands;

import me.dismadical.modsuite.Reports.ReportHandler;
import me.dismadical.modsuite.Reports.menu.ReportsMenu;
import me.dismadical.modsuite.utils.CC;
import me.dismadical.modsuite.utils.menu.Menu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Dismadical (me.dismadical.modsuite.commands)
 * 7/13/2022
 * 8:13 PM
 * ModSuite
 * me.dismadical.modsuite.commands
 */

public class ReportsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("modsuite.reports")) {
                if (ReportHandler.getReportList().isEmpty()) {
                    player.sendMessage(CC.translate("&cNo reports found."));
                } else {
                    Menu menu = new ReportsMenu();
                    menu.open(player);
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
