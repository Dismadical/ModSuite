package me.dismadical.modsuite.commands.chat;

import me.dismadical.modsuite.commands.chat.menus.chatmenu.ChatMenu;
import me.dismadical.modsuite.utils.CC;
import me.dismadical.modsuite.utils.menu.Menu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Dismadical (me.dismadical.modsuite.commands)
 * 7/14/2022
 * 4:29 PM
 * ModSuite
 * me.dismadical.modsuite.commands
 */

public class ChatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("modsuite.chat")) {
                Menu menu = new ChatMenu();
                menu.open(player);
            } else {
                player.sendMessage(CC.translate("&cNo permission."));
            }
        } else {
            System.out.println("Player only command.");
        }

        return true;
    }

}
