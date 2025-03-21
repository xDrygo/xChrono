package org.eldrygo.Managers;

import org.bukkit.entity.Player;
import org.eldrygo.Models.Timer;
import org.eldrygo.Utils.ChatUtils;
import org.eldrygo.XChrono;

import java.util.HashMap;
import java.util.Map;

public class TimerManager {

    private final Map<String, Timer> globalTimers = new HashMap<>();
    private final Map<String, Map<Player, Timer>> playerTimers = new HashMap<>();
    private final ChatUtils chatUtils;

    public TimerManager(XChrono xChrono, ChatUtils chatUtils) {
        this.chatUtils = chatUtils;
    }

    // Crear un nuevo temporizador, ya sea global o por jugador
    public void createTimer(Player sender, String id, String type, long initialTime) {
        if (type.equalsIgnoreCase("global")) {
            globalTimers.put(id, new Timer(id, "global", initialTime));
        } else if (type.equalsIgnoreCase("player")) {
            playerTimers.put(id, new HashMap<>());
        }
    }

    // Eliminar un temporizador
    public void deleteTimer(Player sender, String id) {
        globalTimers.remove(id);
        playerTimers.remove(id);
    }

    // Establecer el tiempo de un temporizador
    public void setTimerTime(Player sender, String type, String id, Player player, int hours, int minutes, int seconds) {
        long timeInMillis = (hours * 3600 + minutes * 60 + seconds) * 1000L;
        if (type.equalsIgnoreCase("global")) {
            if (globalTimers.containsKey(id)) {
                globalTimers.get(id).addGlobalTime(timeInMillis);
            }
        } else if (type.equalsIgnoreCase("player")) {
            if (playerTimers.containsKey(id)) {
                playerTimers.get(id).put(player, new Timer(id, "player", timeInMillis));
            }
        }
    }
    // Agregar tiempo a un temporizador
    public void addTimeToTimer(Player sender, String type, String id, Player player, int hours, int minutes, int seconds) {
        long timeToAdd = (hours * 3600 + minutes * 60 + seconds) * 1000L;
        if (type.equalsIgnoreCase("global")) {
            if (globalTimers.containsKey(id)) {
                globalTimers.get(id).addGlobalTime(timeToAdd);
            }
        } else if (type.equalsIgnoreCase("player")) {
            if (playerTimers.containsKey(id) && playerTimers.get(id).containsKey(player)) {
                playerTimers.get(id).get(player).addPlayerTime(player.getName(), timeToAdd);
            }
        }
    }

    // Reducir tiempo de un temporizador
    public void reduceTimeFromTimer(Player sender, String type, String id, Player player, int hours, int minutes, int seconds) {
        long timeToReduce = (hours * 3600 + minutes * 60 + seconds) * 1000L;
        if (type.equalsIgnoreCase("global")) {
            if (globalTimers.containsKey(id)) {
                globalTimers.get(id).reduceGlobalTime(hours, minutes, seconds);
            }
        } else if (type.equalsIgnoreCase("player")) {
            if (playerTimers.containsKey(id) && playerTimers.get(id).containsKey(player)) {
                playerTimers.get(id).get(player).reducePlayerTime(player.getName(), hours, minutes, seconds);
            }
        }
    }

    // Iniciar un temporizador
    public void startTimer(Player sender, String type, String id, Player player) {
        if (type.equalsIgnoreCase("global")) {
            if (globalTimers.containsKey(id)) {
                globalTimers.get(id).start();
            }
        } else if (type.equalsIgnoreCase("player")) {
            if (playerTimers.containsKey(id) && playerTimers.get(id).containsKey(player)) {
                playerTimers.get(id).get(player).start();
            }
        }
    }

    // Pausar un temporizador
    public void pauseTimer(Player sender, String type, String id, Player player) {
        if (type.equalsIgnoreCase("global")) {
            if (globalTimers.containsKey(id)) {
                globalTimers.get(id).stop();
            }
        } else if (type.equalsIgnoreCase("player")) {
            if (playerTimers.containsKey(id) && playerTimers.get(id).containsKey(player)) {
                playerTimers.get(id).get(player).stop();
            }
        }
    }

    // Reiniciar un temporizador
    public boolean resetTimer(Player sender, String[] args) {
        if (args.length < 3) return false;

        String type = args[1].toLowerCase();
        String id = args[2];

        if (type.equals("global")) {
            if (!globalTimers.containsKey(id)) {
                sender.sendMessage(chatUtils.getMessage("error.timer.not_found", (Player) sender));
                return true;
            }
            globalTimers.get(id).reset();
            sender.sendMessage(chatUtils.getMessage("command.timer.reset.success", (Player) sender));
            return true;
        }

        if (type.equals("player")) {
            if (args.length < 4) return false;

            String playerName = args[3];
            Player targetPlayer = sender.getServer().getPlayer(playerName);

            if (targetPlayer == null || !playerTimers.containsKey(id) || !playerTimers.get(id).containsKey(targetPlayer)) {
                sender.sendMessage(chatUtils.getMessage("error.timer.player_not_found", (Player) sender));
                return true;
            }

            playerTimers.get(id).get(targetPlayer).reset();
            sender.sendMessage(chatUtils.getMessage("command.timer.reset.player.success", (Player) sender));
            return true;
        }

        return false;
    }

    // Reducir tiempo de un temporizador
    public boolean reduceTimeFromTimer(Player sender, String[] args) {
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
            if (!globalTimers.containsKey(id)) {
                sender.sendMessage(chatUtils.getMessage("error.timer.not_found", (Player) sender));
                return true;
            }
            globalTimers.get(id).reduceGlobalTime(hours, minutes, seconds);
            sender.sendMessage(chatUtils.getMessage("command.timer.reduce.success", (Player) sender));
            return true;
        }

        if (type.equals("player")) {
            if (args.length < 7) return false;

            String playerName = args[6];
            Player targetPlayer = sender.getServer().getPlayer(playerName);

            if (targetPlayer == null || !playerTimers.containsKey(id) || !playerTimers.get(id).containsKey(targetPlayer)) {
                sender.sendMessage(chatUtils.getMessage("error.timer.player_not_found", (Player) sender));
                return true;
            }

            playerTimers.get(id).get(targetPlayer).reducePlayerTime(playerName, hours, minutes, seconds);
            sender.sendMessage(chatUtils.getMessage("command.timer.reduce.player.success", (Player) sender));
            return true;
        }

        return false;
    }
    public Map<String, Timer> getActiveTimers() {
        Map<String, Timer> activeTimers = new HashMap<>();

        // Obtener timers activos de los timers de jugador
        for (Map.Entry<String, Map<Player, Timer>> entry : playerTimers.entrySet()) {
            // Recorremos los timers de cada jugador
            for (Map.Entry<Player, Timer> playerEntry : entry.getValue().entrySet()) {
                if (playerEntry.getValue().isActive()) { // Suponiendo que existe un método isActive()
                    activeTimers.put(entry.getKey() + ":" + playerEntry.getKey().getName(), playerEntry.getValue());
                }
            }
        }

        // Obtener timers activos de los timers globales
        for (Map.Entry<String, Timer> entry : globalTimers.entrySet()) {
            if (entry.getValue().isActive()) { // Suponiendo que existe un método isActive()
                activeTimers.put(entry.getKey(), entry.getValue());
            }
        }

        return activeTimers;
    }
}
