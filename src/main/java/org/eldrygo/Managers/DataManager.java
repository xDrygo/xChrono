package org.eldrygo.Managers;

import com.google.gson.reflect.TypeToken;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.eldrygo.Models.Chronometer;
import org.eldrygo.Models.Split;
import org.eldrygo.Models.Timer;
import org.eldrygo.XChrono;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import com.google.gson.*;
import java.io.*;

public class DataManager {
    private final XChrono plugin;
    private final ChronometerManager chronometerManager;
    private final TimerManager timerManager;
    private final SplitManager splitManager;
    private final File dataFolder;

    public DataManager(XChrono plugin, ChronometerManager chronometerManager, TimerManager timerManager, SplitManager splitManager) {
        this.plugin = plugin;
        this.chronometerManager = chronometerManager;
        this.timerManager = timerManager;
        this.splitManager = splitManager;
        this.dataFolder = new File(plugin.getDataFolder(), "data");

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
    }

    public void loadData() {
        loadFromConfig();
        loadFromFiles();
    }

    public void saveData() {
        saveToFiles();
    }

    private void loadFromConfig() {
        FileConfiguration config = plugin.getConfig();

        // Cargar cronómetros desde config.yml
        ConfigurationSection chronosSection = config.getConfigurationSection("chronometers");
        if (chronosSection != null) {
            for (String id : chronosSection.getKeys(false)) {
                String type = chronosSection.getString(id + ".type", "global");
                chronometerManager.createChrono(null, id, type);
            }
        }

        // Cargar timers desde config.yml
        ConfigurationSection timersSection = config.getConfigurationSection("timers");
        if (timersSection != null) {
            for (String id : timersSection.getKeys(false)) {
                String type = timersSection.getString(id + ".type", "global");
                long time = timersSection.getLong(id + ".time", 0);
                timerManager.createTimer(null, id, type, time);
            }
        }

        // Cargar splits desde config.yml
        ConfigurationSection splitsSection = config.getConfigurationSection("splits");
        if (splitsSection != null) {
            for (String id : splitsSection.getKeys(false)) {
                String type = splitsSection.getString(id + ".type", "global");
                String linkedTimer = splitsSection.getString(id + ".linkedTimer", "");
                // Usamos el id como displayName por defecto
                splitManager.createSplit(null, type, id, id, plugin);
            }
        }
    }

    private void loadFromFiles() {
        // Cargar cronómetros
        Map<String, Chronometer> chronometers = loadChronometers();
        for (Map.Entry<String, Chronometer> entry : chronometers.entrySet()) {
            chronometerManager.createChrono(null, entry.getKey(), entry.getValue().getType());
        }

        // Cargar timers
        Map<String, Timer> timers = loadTimers();
        for (Map.Entry<String, Timer> entry : timers.entrySet()) {
            timerManager.createTimer(null, entry.getKey(), entry.getValue().getType(), entry.getValue().getTime());
        }

        // Cargar splits
        Map<String, Split> splits = loadSplits();
        for (Map.Entry<String, Split> entry : splits.entrySet()) {
            String linkedTimer = entry.getValue().getLinkedTimer(); // Obtener el linkedTimer (ID del temporizador)

            // Si el displayName está vacío, usa el id como valor por defecto
            String displayName = entry.getValue().getDisplayName();
            if (displayName == null || displayName.isEmpty()) {
                displayName = entry.getKey(); // Usar el id como displayName por defecto
            }

            // Llamar a createSplit con los 4 parámetros esperados
            splitManager.createSplit(null, entry.getValue().getType(), entry.getKey(), displayName, plugin);
        }
    }

    public void saveToFiles() {
        Map<String, Chronometer> chronometers = chronometerManager.getActiveChronos();
        Map<String, Timer> timers = timerManager.getActiveTimers();
        Map<String, Split> splits = splitManager.getActiveSplits();

        // Guardar los datos a los archivos correspondientes
        saveChronometers(chronometers);
        saveTimers(timers);
        saveSplits(splits);
    }
    public Map<String, Chronometer> loadChronometers() {
        File file = new File(plugin.getDataFolder(), "data/chronometers.json");
        if (!file.exists()) return new HashMap<>();

        try (Reader reader = new FileReader(file)) {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, Chronometer>>(){}.getType();
            return gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
    public Map<String, Timer> loadTimers() {
        File file = new File(plugin.getDataFolder(), "data/timers.json");
        if (!file.exists()) return new HashMap<>();

        try (Reader reader = new FileReader(file)) {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, Timer>>() {}.getType();
            return gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public Map<String, Split> loadSplits() {
        File file = new File(plugin.getDataFolder(), "data/splits.json");
        if (!file.exists()) return new HashMap<>();

        try (Reader reader = new FileReader(file)) {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, Split>>() {}.getType();
            return gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
    public void saveChronometers(Map<String, Chronometer> chronometers) {
        File file = new File(plugin.getDataFolder(), "data/chronometers.json");
        try (FileWriter writer = new FileWriter(file)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            writer.write(gson.toJson(chronometers));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveTimers(Map<String, Timer> timers) {
        File file = new File(plugin.getDataFolder(), "data/timers.json");
        try (FileWriter writer = new FileWriter(file)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            writer.write(gson.toJson(timers));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveSplits(Map<String, Split> splits) {
        File file = new File(plugin.getDataFolder(), "data/splits.json");
        try (FileWriter writer = new FileWriter(file)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            writer.write(gson.toJson(splits));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
