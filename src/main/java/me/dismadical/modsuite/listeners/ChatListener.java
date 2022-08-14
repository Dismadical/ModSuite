package me.dismadical.modsuite.listeners;

import me.dismadical.modsuite.ModSuite;
import me.dismadical.modsuite.utils.CC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Dismadical (me.dismadical.modsuite.listeners)
 * 7/14/2022
 * 5:01 PM
 * ModSuite
 * me.dismadical.modsuite.listeners
 */

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (player.hasMetadata("slowchat")) {
            event.setCancelled(true);
            try {
                int seconds = Integer.parseInt(event.getMessage());
                ModSuite.getInstance().setChatSlowTime(seconds);
                player.sendMessage(CC.translate("&aSlow chat speed set to &c" + seconds + "&a seconds."));
                player.removeMetadata("slowchat", ModSuite.getInstance());
            } catch (NumberFormatException e) {
                player.sendMessage(CC.translate("&cInvalid number."));
                player.removeMetadata("slowchat", ModSuite.getInstance());
            }

        }
        if (ModSuite.getInstance().isChatMuted()) {
            if (player.hasPermission("modsuite.mutechat.bypass")) {
                return;
            } else {
                event.setCancelled(true);
                player.sendMessage(CC.translate("&cChat is currently muted."));
            }
        } else if (ModSuite.getInstance().isChatSlowed()) {
            if (player.hasPermission("modsuite.chatslow.bypass")) {
                return;
            } else {
                if (!(ModSuite.getInstance().getChatSlowTimers().containsKey(player)) || ModSuite.getInstance().getChatSlowTimers().get(player) <= System.currentTimeMillis()) {
                    ModSuite.getInstance().getChatSlowTimers().put(player, System.currentTimeMillis() + (ModSuite.getInstance().getChatSlowTime() * 1000));
                } else {
                    event.setCancelled(true);
                    long time = (ModSuite.getInstance().getChatSlowTimers().get(player) - System.currentTimeMillis()) / 1000;
                    player.sendMessage(CC.translate("&cYou are currently on chat cooldown for " + time + " seconds."));
                }
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        ModSuite.getChat().setPlayerSuffix(event.getPlayer(), ModSuite.getChat().getPlayerSuffix(event.getPlayer()).replace(" &7[M]", ""));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (event.getPlayer().hasMetadata("slowchat")) {
            event.getPlayer().removeMetadata("slowchat", ModSuite.getInstance());
        }
    }

}
