package me.dismadical.modsuite.commands.chat.menus.chatmenu.buttons;

import me.dismadical.modsuite.ModSuite;
import me.dismadical.modsuite.utils.CC;
import me.dismadical.modsuite.utils.ItemBuilder;
import me.dismadical.modsuite.utils.menu.Button;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dismadical (me.dismadical.modsuite.commands.chat.menus.chatmenu.buttons)
 * 7/14/2022
 * 4:39 PM
 * ModSuite
 * me.dismadical.modsuite.commands.chat.menus.chatmenu.buttons
 */

public class MuteChatButton extends Button {

    @Override
    public ItemStack getItemStack() {
        ItemBuilder itemBuilder = new ItemBuilder(Material.OAK_SIGN);
        itemBuilder.setAmount(1);
        itemBuilder.setName(CC.translate("&b&lMute Chat"));
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate("&7Click to mute the"));
        lore.add(CC.translate("&7chat for normal players."));
        lore.add("");
        lore.add(CC.translate("&cMuted&7: " + ((ModSuite.getInstance().isChatMuted()) ? CC.translate("&aYes") : CC.translate("&cNo"))));
        itemBuilder.setLore(lore);
        return itemBuilder.build();
    }

    @Override
    public void handleButton(Player player) {
        if (player.hasPermission("modsuite.mutechat")) {
            player.closeInventory();
            if (ModSuite.getInstance().isChatMuted()) {
                ModSuite.getInstance().setChatMuted(false);
            } else {
                ModSuite.getInstance().setChatMuted(true);
            }
            Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(CC.translate("&cChat has been " + (ModSuite.getInstance().isChatMuted() ? "&cmuted" : "&cunmuted") + " by " + player.getName() + ".")));
        } else {
            player.closeInventory();
            player.sendMessage(CC.translate("&cYou do not have permission to use this command."));
        }
    }

}
