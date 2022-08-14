package me.dismadical.modsuite;

import lombok.Getter;
import lombok.Setter;
import me.dismadical.modsuite.commands.*;
import me.dismadical.modsuite.commands.chat.ChatCommand;
import me.dismadical.modsuite.listeners.ChatListener;
import me.dismadical.modsuite.listeners.ItemListener;
import me.dismadical.modsuite.listeners.ReportListener;
import me.dismadical.modsuite.listeners.VanishListener;
import me.dismadical.modsuite.papi.ModSuitePlaceholders;
import me.dismadical.modsuite.utils.CC;
import me.dismadical.modsuite.utils.menu.MenuListener;
import me.dismadical.modsuite.xrayalerts.XRayCommand;
import me.dismadical.modsuite.xrayalerts.XRayListener;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.command.Command;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.bukkit.plugin.java.annotation.dependency.Dependency;
import org.bukkit.plugin.java.annotation.dependency.SoftDependency;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.LogPrefix;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

import java.util.ArrayList;
import java.util.HashMap;

@Plugin(name = "ModSuite", version = "1.0")
@Description("ModSuite is a plugin that provides a variety of mod utilities.")
@Author("Dismadical")
@ApiVersion(ApiVersion.Target.v1_18)
@Dependency("LuckPerms")
@Dependency("Vault")

@Command(name = "mod", desc = "Mod Mode Command", aliases = {"v", "staffmode", "vanish"})
@Command(name = "report", desc = "Report Command")
@Command(name = "reports", desc = "Reports Command")
@Command(name = "chat", desc = "Chat Command")
@Command(name = "xray", desc = "XRay Command")
@Command(name = "build", desc = "Build Command")
@Command(name = "clearchat", desc = "Clear Chat Command", aliases = {"cc"})
@Command(name = "mutechat", desc = "Mute Chat Command")
@Command(name = "slowchat", desc = "Slow Chat Command")
@Command(name = "freeze", desc = "Freeze Command")

public final class ModSuite extends JavaPlugin {

    @Getter private static ModSuite instance;
    @Getter private ModHandler handler;
    @Getter @Setter private boolean chatMuted;
    @Getter @Setter private boolean chatSlowed;
    @Getter @Setter private int chatSlowTime;
    @Getter @Setter private HashMap<Player, Long> chatSlowTimers;
    @Getter @Setter private HashMap<Player, Long> reportCooldowns;
    @Getter private HashMap<Material, String> xrayNames;
    @Getter private HashMap<Material, String> xrayBlocks;

    private static Chat chat = null;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        setUpHandler();
        setUpCommands();
        setUpListeners();
        setUpChat();
        setUpXray();
        setupVaultChat();

        new ModSuitePlaceholders().register();

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (getHandler().getPlayersInStaffMode().contains(p)) {
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(CC.translate("&fYou are currently in &6Staff Mode &7| &fVanish: " + ((getHandler().getPlayersInVanish().contains(p)) ? "&aYes" : "&cNo") + " &7| &fBuild: " + ((getHandler().getBuildPlayers().contains(p)) ? "&aYes" : "&cNo"))));
                }
            }
        }, 30L, 30L);
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    private void setUpCommands() {
        getCommand("mod").setExecutor(new ModCommand());
        getCommand("report").setExecutor(new ReportCommand());
        getCommand("reports").setExecutor(new ReportsCommand());
        getCommand("chat").setExecutor(new ChatCommand());
        getCommand("xray").setExecutor(new XRayCommand());
        getCommand("build").setExecutor(new BuildCommand());
        getCommand("clearchat").setExecutor(new ClearChatCommand());
        getCommand("mutechat").setExecutor(new MuteChatCommand());
        getCommand("slowchat").setExecutor(new SlowChatCommand());
        getCommand("freeze").setExecutor(new FreezeCommand());
    }

    private void setUpListeners() {
        getServer().getPluginManager().registerEvents(new ItemListener(), this);
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new VanishListener(), this);
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new ReportListener(), this);
        getServer().getPluginManager().registerEvents(new XRayListener(), this);
    }

    private void setUpHandler() {
        handler = new ModHandler();

        ModHandler.inVanish = new ArrayList<>();
        ModHandler.inStaffMode = new ArrayList<>();
        ModHandler.frozen = new ArrayList<>();
        ModHandler.spectating = new ArrayList<>();
        ModHandler.build = new ArrayList<>();
    }


    private void setUpChat() {
        chatMuted = false;
        chatSlowed = false;
        chatSlowTime = 3;
        chatSlowTimers = new HashMap<>();
        reportCooldowns = new HashMap<>();
    }

    private void setUpXray() {
        xrayNames = new HashMap<>();
        xrayNames.put(Material.LAPIS_ORE, "Lapis");
        xrayNames.put(Material.DEEPSLATE_LAPIS_ORE, "Lapis");
        xrayNames.put(Material.COAL_ORE, "Coal");
        xrayNames.put(Material.DEEPSLATE_COAL_ORE, "Coal");
        xrayNames.put(Material.IRON_ORE, "Iron");
        xrayNames.put(Material.DEEPSLATE_IRON_ORE, "Iron");
        xrayNames.put(Material.GOLD_ORE, "Gold");
        xrayNames.put(Material.DEEPSLATE_GOLD_ORE, "Gold");
        xrayNames.put(Material.DIAMOND_ORE, "Diamond");
        xrayNames.put(Material.DEEPSLATE_DIAMOND_ORE, "Diamond");
        xrayNames.put(Material.EMERALD_ORE, "Emerald");
        xrayNames.put(Material.DEEPSLATE_EMERALD_ORE, "Emerald");
        xrayNames.put(Material.REDSTONE_ORE, "Redstone");
        xrayNames.put(Material.DEEPSLATE_REDSTONE_ORE, "Redstone");
        xrayNames.put(Material.ANCIENT_DEBRIS, "Netherite");

        xrayBlocks = new HashMap<>();
        xrayBlocks.put(Material.LAPIS_ORE, "Lapis");
        xrayBlocks.put(Material.COAL_ORE, "Coal");
        xrayBlocks.put(Material.IRON_ORE, "Iron");
        xrayBlocks.put(Material.GOLD_ORE, "Gold");
        xrayBlocks.put(Material.DIAMOND_ORE, "Diamond");
        xrayBlocks.put(Material.EMERALD_ORE, "Emerald");
        xrayBlocks.put(Material.REDSTONE_ORE, "Redstone");
        xrayBlocks.put(Material.ANCIENT_DEBRIS, "Netherite");
    }

    private boolean setupVaultChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    public static Chat getChat() {
        return chat;
    }

}
