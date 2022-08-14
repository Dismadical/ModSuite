package me.dismadical.modsuite.listeners;

import com.sun.org.apache.xpath.internal.operations.Mod;
import me.dismadical.modsuite.ModSuite;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Dismadical (me.dismadical.modsuite.listeners)
 * 7/13/2022
 * 9:11 PM
 * ModSuite
 * me.dismadical.modsuite.listeners
 */

public class VanishListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        ModSuite.getInstance().getHandler().getPlayersInVanish().forEach(p -> event.getPlayer().hidePlayer(ModSuite.getInstance(), p));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (ModSuite.getInstance().getHandler().getPlayersInVanish().contains(event.getPlayer())) {
            ModSuite.getInstance().getHandler().removePlayerInVanish(event.getPlayer());
        }
        if (ModSuite.getInstance().getHandler().getPlayersInStaffMode().contains(event.getPlayer())) {
            ModSuite.getInstance().getHandler().removePlayerInStaffMode(event.getPlayer());
        }
    }

}
