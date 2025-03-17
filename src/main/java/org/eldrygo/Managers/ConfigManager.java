package org.eldrygo.Managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.eldrygo.Utils.ChatUtils;
import org.eldrygo.XChrono;

import java.io.File;
import java.util.*;

public class ConfigManager {

    private final XChrono plugin;

    public ConfigManager(XChrono plugin) {
        this.plugin = plugin;
    }

    public String getPrefix() {
        return plugin.prefix;
    }

    public FileConfiguration getMessageConfig() {
        return plugin.messagesConfig;
    }

    public void loadConfig() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
    }

    public void loadMessages() {
        try {
            File messagesFile = new File(plugin.getDataFolder(), "messages.yml");
            if (!messagesFile.exists()) {
                plugin.saveResource("messages.yml", false);
                plugin.getLogger().info("✅ The messages.yml file did not exist, it has been created.");
            } else {
                plugin.getLogger().info("✅ The messages.yml file has been loaded successfully.");
            }
            plugin.messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
            plugin.prefix = ChatUtils.formatColor(getMessageConfig().getString("prefix", "#9BFF87&lx&r&lChrono &cDefault Prefix &8»&r"));
        } catch (Exception e) {
            plugin.getLogger().severe("❌ Failed to load messages configuration!");
            e.printStackTrace();
        }
    }
}
