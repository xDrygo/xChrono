package org.eldrygo.Managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.eldrygo.XChrono;
import org.eldrygo.Utils.ChatUtils;
import org.eldrygo.Models.Split;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ConfigManager {

    private final XChrono plugin;

    // Instanciamos el ChatUtils directamente para usarlo
    private final ChatUtils chatUtils;

    public ConfigManager(XChrono plugin) {
        this.plugin = plugin;
        this.chatUtils = new ChatUtils(plugin, this);  // Inicializamos ChatUtils con el plugin y ConfigManager
    }

    // Método para obtener el prefijo
    public String getPrefix() {
        return plugin.prefix;
    }

    // Método para obtener la configuración de mensajes
    public FileConfiguration getMessageConfig() {
        return plugin.messagesConfig;
    }

    // Cargar la configuración general
    public void loadConfig() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        loadMessages();
    }

    // Cargar la configuración de mensajes
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
            plugin.prefix = chatUtils.getMessage("prefix", null);  // Usamos el método getMessage de ChatUtils para el prefijo
        } catch (Exception e) {
            plugin.getLogger().severe("❌ Failed to load messages configuration!");
            e.printStackTrace();
        }
    }
    public void createDataFiles() {
        File dataFolder = new File(plugin.getDataFolder(), "data");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        createFile(new File(dataFolder, "chronometers.json"));
        createFile(new File(dataFolder, "timers.json"));
        createFile(new File(dataFolder, "splits.json"));
    }

    private void createFile(File file) {
        try {
            if (!file.exists()) {
                file.createNewFile();
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write("[]"); // Iniciar el archivo JSON vacío
                }
            }
        } catch (IOException e) {
            plugin.getLogger().severe("Error al crear archivo: " + file.getName());
            e.printStackTrace();
        }
    }
}
