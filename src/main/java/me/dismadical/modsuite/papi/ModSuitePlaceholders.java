package me.dismadical.modsuite.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.dismadical.modsuite.ModSuite;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dismadical (me.dismadical.modsuite.papi)
 * 7/14/2022
 * 6:13 PM
 * ModSuite
 * me.dismadical.modsuite.papi
 */

public class ModSuitePlaceholders extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "modsuite";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Dismadical";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean canRegister() { return true; }

    @Override
    public boolean persist() { return true; }

    @Override
    public String onPlaceholderRequest(Player player, String params) {
        if (player == null) {
            return "";
        }

        if (params.equals("vanish")) {
            if (ModSuite.getInstance().getHandler().getPlayersInVanish().contains(player)) {
                return "&7[M]";
            } else {
                return "";
            }
        }

        return null;
    }

}
