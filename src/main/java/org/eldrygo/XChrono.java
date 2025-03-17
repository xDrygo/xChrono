package org.eldrygo;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.eldrygo.Managers.ConfigManager;
import org.eldrygo.Utils.ChatUtils;
import org.eldrygo.Utils.LogsUtils;

import java.io.File;

public class XChrono extends JavaPlugin {
    public String version;
    public String prefix;
    public FileConfiguration messagesConfig;
    private ChatUtils chatUtils;
    public LogsUtils logsUtils;
    public ConfigManager configManager;
    @Override
    public void onEnable() {
        this.version = getDescription().getVersion(); // Establecer versión dentro de onEnable
        this.configManager = new ConfigManager(this);
        this.messagesConfig = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "messages.yml"));
        this.chatUtils = new ChatUtils(this, configManager);
        this.logsUtils = new LogsUtils(this);
        configManager.loadConfig();
        configManager.loadMessages();
        logsUtils.sendStartupMessage();
    }
    @Override
    public void onDisable() {
        logsUtils.sendShutdownMessage();
    }
    public ConfigManager getConfigManager() { return configManager; }
    public ChatUtils getChatUtils() { return chatUtils; }
}
