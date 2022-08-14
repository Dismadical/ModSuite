package me.dismadical.modsuite.commands.chat.menus.chatmenu.buttons;

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
 * 4:33 PM
 * ModSuite
 * me.dismadical.modsuite.commands.chat.menus.chatmenu.buttons
 */

public class ClearChatButton extends Button {


    @Override
    public ItemStack getItemStack() {
        ItemBuilder itemBuilder = new ItemBuilder(Material.OAK_SIGN);
        itemBuilder.setAmount(1);
        itemBuilder.setName(CC.translate("&b&lClear Chat"));
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate("&7Click to clear the"));
        lore.add(CC.translate("&7chat for normal players."));
        itemBuilder.setLore(lore);
        return itemBuilder.build();
    }

    @Override
    public void handleButton(Player player) {
        if (player.hasPermission("modsuite.clearchat")) {
            player.closeInventory();
            for (int i = 0; i < 100; i++) {
                Bukkit.getOnlinePlayers().stream().filter(p -> !p.hasPermission("modsuite.staff")).forEach(p -> p.sendMessage(""));
            }
            Bukkit.getOnlinePlayers().stream().forEach(p -> p.sendMessage(CC.translate("&cChat has been cleared by " + player.getName() + ".")));
        } else {
            player.closeInventory();
            player.sendMessage(CC.translate("&cYou do not have permission to use this command."));
        }
    }
}

