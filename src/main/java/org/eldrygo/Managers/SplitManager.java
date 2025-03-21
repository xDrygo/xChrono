package org.eldrygo.Managers;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.eldrygo.Models.Split;
import org.eldrygo.Utils.ChatUtils;
import org.eldrygo.XChrono;

import java.util.HashMap;
import java.util.Map;

public class SplitManager {

    private final Map<String, Split> globalSplits = new HashMap<>();
    private final Map<String, Map<Player, Split>> playerSplits = new HashMap<>();
    private final ChatUtils chatUtils;
    private Plugin plugin;

    public SplitManager(XChrono xChrono, ChatUtils chatUtils) {
        this.chatUtils = chatUtils;
    }

    // Crear un nuevo split, ya sea global o por jugador
    public void createSplit(Player sender, String type, String id, String displayName, Plugin plugin) {
        if (type.equalsIgnoreCase("global")) {
            // Aquí utilizamos el constructor de Split con 5 parámetros
            Split split = new Split(id, type, "", displayName, plugin);
            globalSplits.put(id, split); // Almacenamos el split global
        } else if (type.equalsIgnoreCase("player")) {
            playerSplits.put(id, new HashMap<>());
        }
    }


    // Eliminar un split
    public void deleteSplit(Player sender, String id) {
        globalSplits.remove(id);
        playerSplits.remove(id);
    }

    // Establecer el nombre de visualización de un split
    public void setDisplayName(Player sender, String type, String id, String displayName) {
        if (type.equalsIgnoreCase("global")) {
            if (globalSplits.containsKey(id)) {
                globalSplits.get(id).setDisplayName(displayName);
            }
        } else if (type.equalsIgnoreCase("player")) {
            if (playerSplits.containsKey(id)) {
                playerSplits.get(id).values().forEach(split -> split.setDisplayName(displayName));
            }
        }
    }

    // Asignar un temporizador o cronómetro a un split de un jugador
    public void setSplitTimer(Player sender, String type, String id, Player player, String timerType, String timerId) {
        if (type.equalsIgnoreCase("global")) {
            if (globalSplits.containsKey(id)) {
                globalSplits.get(id).setTimer(timerType, timerId);
            }
        } else if (type.equalsIgnoreCase("player")) {
            if (playerSplits.containsKey(id) && playerSplits.get(id).containsKey(player)) {
                playerSplits.get(id).get(player).setTimer(timerType, timerId);
            }
        }
    }

    // Eliminar la asignación de un temporizador o cronómetro de un split de un jugador
    public void unsetSplitTimer(Player sender, String type, String id, Player player) {
        if (type.equalsIgnoreCase("global")) {
            if (globalSplits.containsKey(id)) {
                globalSplits.get(id).unsetTimer();
            }
        } else if (type.equalsIgnoreCase("player")) {
            if (playerSplits.containsKey(id) && playerSplits.get(id).containsKey(player)) {
                playerSplits.get(id).get(player).unsetTimer();
            }
        }
    }

    // Reducir el tiempo de un split
    public boolean reduceTimeFromSplit(Player sender, String[] args) {
        if (args.length < 6) return false;

        String type = args[1].toLowerCase();
        String id = args[2];
        int hours, minutes, seconds;
        try {
            hours = Integer.parseInt(args[3]);
            minutes = Integer.parseInt(args[4]);
            seconds = Integer.parseInt(args[5]);
        } catch (NumberFormatException e) {
            sender.sendMessage(chatUtils.getMessage("error.invalid_time_format", (Player) sender));
            return true;
        }

        long timeToReduce = (hours * 3600 + minutes * 60 + seconds) * 1000L;

        if (type.equals("global")) {
            if (!globalSplits.containsKey(id)) {
                sender.sendMessage(chatUtils.getMessage("error.split.not_found", (Player) sender));
                return true;
            }
            globalSplits.get(id).reduceTime(timeToReduce);
            sender.sendMessage(chatUtils.getMessage("command.split.reduce.success", (Player) sender));
            return true;
        }

        if (type.equals("player")) {
            if (args.length < 7) return false;

            String playerName = args[6];
            Player targetPlayer = sender.getServer().getPlayer(playerName);

            if (targetPlayer == null || !playerSplits.containsKey(id) || !playerSplits.get(id).containsKey(targetPlayer)) {
                sender.sendMessage(chatUtils.getMessage("error.split.player_not_found", (Player) sender));
                return true;
            }

            playerSplits.get(id).get(targetPlayer).reduceTime(timeToReduce);
            sender.sendMessage(chatUtils.getMessage("command.split.reduce.player.success", (Player) sender));
            return true;
        }

        return false;
    }
    public Map<String, Split> getActiveSplits() {
        Map<String, Split> activeSplits = new HashMap<>();

        // Obtener splits activos de los splits de jugador
        for (Map.Entry<String, Map<Player, Split>> entry : playerSplits.entrySet()) {
            // Recorremos los splits de cada jugador
            for (Map.Entry<Player, Split> playerEntry : entry.getValue().entrySet()) {
                if (playerEntry.getValue().isActive()) { // Suponiendo que existe un método isActive()
                    activeSplits.put(entry.getKey() + ":" + playerEntry.getKey().getName(), playerEntry.getValue());
                }
            }
        }

        // Obtener splits activos de los splits globales
        for (Map.Entry<String, Split> entry : globalSplits.entrySet()) {
            if (entry.getValue().isActive()) { // Suponiendo que existe un método isActive()
                activeSplits.put(entry.getKey(), entry.getValue());
            }
        }

        return activeSplits;
    }
}
