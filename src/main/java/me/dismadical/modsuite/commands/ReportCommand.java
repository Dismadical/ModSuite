package me.dismadical.modsuite.commands;

import me.dismadical.modsuite.ModSuite;
import me.dismadical.modsuite.reports.ReportHandler;
import me.dismadical.modsuite.utils.CC;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Dismadical (me.dismadical.modsuite.commands)
 * 7/13/2022
 * 7:51 PM
 * ModSuite
 * me.dismadical.modsuite.commands
 */

public class ReportCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length < 2) {
                player.sendMessage(CC.translate("&cUsage: /report <player> <reason>"));
            } else {
                if (Bukkit.getPlayerExact(args[0]) == null) {
                    player.sendMessage(CC.translate("&cPlayer not found."));
                } else {
                    if (ModSuite.getInstance().getReportCooldowns().get(player) == null || ModSuite.getInstance().getReportCooldowns().get(player) <= System.currentTimeMillis()) {
                        Player target = Bukkit.getPlayerExact(args[0]);
                        String reason = StringUtils.join(args, " ", 1, args.length);
                        ReportHandler.createReport(player, target, reason);
                        Bukkit.getOnlinePlayers().stream().filter(p -> p.hasPermission("modsuite.staff")).forEach(p -> {
                            p.sendMessage(CC.translate("&b&lREPORT"));
                            p.sendMessage(CC.translate(""));
                            p.sendMessage(CC.translate("&bReporter&7: &f" + player.getName()));
                            p.sendMessage(CC.translate("&bReported&7: &f" + target.getName()));
                            p.sendMessage(CC.translate("&bReason&7: &f" + reason));
                            p.sendMessage(CC.translate(""));
                        });
                        ModSuite.getInstance().getReportCooldowns().put(player, System.currentTimeMillis() + (60 * 1000));
                    } else {
                        player.sendMessage(CC.translate("&cYou must wait " + (ModSuite.getInstance().getReportCooldowns().get(player) - System.currentTimeMillis()) / 1000 + " seconds before using this command again."));
                    }

                }
            }
        } else {
            System.out.println("Player only command.");
        }

        return true;
    }

}
