package me.dismadical.modsuite.commands.chat.menus.chatmenu.buttons;

import me.dismadical.modsuite.ModSuite;
import me.dismadical.modsuite.commands.chat.menus.slowchatmenu.SlowChatMenu;
import me.dismadical.modsuite.utils.CC;
import me.dismadical.modsuite.utils.ItemBuilder;
import me.dismadical.modsuite.utils.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dismadical (me.dismadical.modsuite.commands.chat.menus.chatmenu.buttons)
 * 7/14/2022
 * 4:47 PM
 * ModSuite
 * me.dismadical.modsuite.commands.chat.menus.chatmenu.buttons
 */

public class SlowChatButton extends Button {

    @Override
    public ItemStack getItemStack() {
        ItemBuilder itemBuilder = new ItemBuilder(Material.OAK_SIGN);
        itemBuilder.setAmount(1);
        itemBuilder.setName(CC.translate("&b&lSlow Chat"));
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate("&7Click to slow the"));
        lore.add(CC.translate("&7chat for normal players."));
        lore.add("");
        lore.add(CC.translate("&cSlowed&7: " + ((ModSuite.getInstance().isChatSlowed()) ? CC.translate("&aYes") : CC.translate("&cNo"))));
        lore.add(CC.translate("&cSpeed&7: &c" + ModSuite.getInstance().getChatSlowTime()) + " Seconds");
        itemBuilder.setLore(lore);
        return itemBuilder.build();
    }

    @Override
    public void handleButton(Player player) {
        if (player.hasPermission("modsuite.slowchat")) {
            player.closeInventory();
            new SlowChatMenu().open(player);
        } else {
            player.closeInventory();
            player.sendMessage(CC.translate("&cYou do not have permission to use this command."));
        }
    }

}
