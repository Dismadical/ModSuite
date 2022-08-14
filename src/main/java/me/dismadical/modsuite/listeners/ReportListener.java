package me.dismadical.modsuite.listeners;

import me.dismadical.modsuite.Reports.ReportHandler;
import me.dismadical.modsuite.utils.CC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * @author Dismadical (me.dismadical.modsuite.listeners)
 * 7/15/2022
 * 6:19 PM
 * ModSuite
 * me.dismadical.modsuite.listeners
 */

public class ReportListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (event.getPlayer().hasPermission("modsuite.reports")) {
            if (ReportHandler.getReportList().size() > 0) {
                Player player = event.getPlayer();
                player.sendMessage(CC.translate("&7&m----------------------------------------"));
                player.sendMessage(CC.translate("&b&lREPORTS"));
                player.sendMessage(CC.translate(""));
                player.sendMessage(CC.translate("&7There are currently &b" + ReportHandler.getReportList().size() + " &7report(s)."));
                player.sendMessage(CC.translate("&7Type &b/reports &7to view them."));
                player.sendMessage(CC.translate("&7&m----------------------------------------"));
            }
        }
    }

}
