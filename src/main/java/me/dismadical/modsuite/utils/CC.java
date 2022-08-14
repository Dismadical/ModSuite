package me.dismadical.modsuite.utils;

import net.md_5.bungee.api.ChatColor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Dismadical (me.dismadical.modsuite.utils)
 * 7/13/2022
 * 5:47 PM
 * ModSuite
 * me.dismadical.modsuite.utils
 */

public class CC {

    private static final Pattern pattern = Pattern.compile("&#[a-fA-F0-9]{6}");

    public static String translate(String string) {
        Matcher match = pattern.matcher(string);
        while (match.find()) {
            String color = string.substring(match.start() + 1, match.end());
            string = string.replace("&" + color, net.md_5.bungee.api.ChatColor.of(color).toString() + "");
            match = pattern.matcher(string);
        }
        return ChatColor.translateAlternateColorCodes('&', string);}

    public static List<String> translateList(List<String> string) {
        return string.stream().map(CC::translate).collect(Collectors.toList());
    }

}
