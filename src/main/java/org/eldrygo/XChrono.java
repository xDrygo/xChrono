package org.eldrygo;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.eldrygo.Handlers.XChronoCommand;
import org.eldrygo.Managers.*;
import org.eldrygo.Models.Chronometer;
import org.eldrygo.Models.Split;
import org.eldrygo.Models.Timer;
import org.eldrygo.Utils.ChatUtils;
import org.eldrygo.Utils.LogsUtils;

import java.io.File;
import java.util.Map;

public class XChrono extends JavaPlugin {
    public String version;
    public String prefix;
    public FileConfiguration messagesConfig;
    private ChatUtils chatUtils;
    public LogsUtils logsUtils;
    public ConfigManager configManager;
    public DataManager dataManager;
    private TimerManager timerManager;
    private ChronometerManager chronometerManager;
    private SplitManager splitManager;
    private Map<String, Chronometer> chronometers;
    private Map<String, Timer> timers;
    private Map<String, Split> splits;
    private Plugin plugin;

    @Override
    public void onEnable() {
        this.version = getDescription().getVersion(); // Establecer versión dentro de onEnable
        this.configManager = new ConfigManager(this);
        this.messagesConfig = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "messages.yml"));
        this.chatUtils = new ChatUtils(this, configManager);
        this.logsUtils = new LogsUtils(this);
        this.timerManager = new TimerManager(this, chatUtils);;
        this.chronometerManager = new ChronometerManager(this, chatUtils);;
        this.splitManager = new SplitManager(this, chatUtils);
        configManager.loadConfig();
        configManager.loadMessages();
        configManager.createDataFiles();
        logsUtils.sendStartupMessage();
    }
    @Override
    public void onDisable() {
        dataManager.saveChronometers(chronometers);
        dataManager.saveTimers(timers);
        dataManager.saveSplits(splits);
        logsUtils.sendShutdownMessage();
    }
    public ConfigManager getConfigManager() { return configManager; }
    public ChatUtils getChatUtils() { return chatUtils; }
    public TimerManager getTimerManager() { return timerManager; }
    public ChronometerManager getChronometerManager() { return chronometerManager; }

    public SplitManager getSplitManager() { return splitManager; }

    public static String formatTime(long totalSeconds) {
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
    private void loadCommands() {
        if (getCommand("xchrono") == null) {
            getLogger().severe("❌ Error: xChrono command is not registered in plugin.yml");
        } else {
            getCommand("xchrono").setExecutor(new XChronoCommand(this, chronometerManager, timerManager, splitManager, plugin));
            //getCommand("xchrono").setTabCompleter(new XTeamsTabCompleter(this));
        }
    }
    private void loadFeatures() {
        chronometers = dataManager.loadChronometers();
        timers = dataManager.loadTimers();
        splits = dataManager.loadSplits();
    }
}
