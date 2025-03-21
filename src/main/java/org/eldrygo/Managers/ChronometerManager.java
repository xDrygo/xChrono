package org.eldrygo.Managers;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.eldrygo.Models.Chronometer;
import org.eldrygo.Utils.ChatUtils;
import org.eldrygo.XChrono;

import java.util.HashMap;
import java.util.Map;

public class ChronometerManager {

    private final Map<String, Chronometer> globalChronometers = new HashMap<>();
    private final Map<String, Map<Player, Chronometer>> playerChronometers = new HashMap<>();
    private final ChatUtils chatUtils;

    public ChronometerManager(XChrono xChrono, ChatUtils chatUtils) {
        this.chatUtils = chatUtils;
    }

    // Crear un nuevo cronómetro, ya sea global o por jugador
    public void createChrono(Player sender, String id, String type) {
        if (type.equalsIgnoreCase("global")) {
            globalChronometers.put(id, new Chronometer(id, type));
        } else if (type.equalsIgnoreCase("player")) {
            playerChronometers.put(id, new HashMap<>());
        }
    }

    // Eliminar un cronómetro
    public void deleteChrono(Player sender, String id) {
        if ((globalChronometers.containsKey(id)) && (playerChronometers.containsKey(id))) {
            sender.sendMessage(chatUtils.getMessage("commands.chrono.not_found", (Player) sender)
                    .replace("%id%", id));
        } else {
            globalChronometers.remove(id);
            playerChronometers.remove(id);}
    }

    // Establecer el tiempo de un cronómetro
    public void setChronoTime(Player sender, String type, String id, Player player, int hours, int minutes, int seconds) {
        long timeInMillis = (hours * 3600 + minutes * 60 + seconds) * 1000L;

        if (type.equalsIgnoreCase("global")) {
            if (globalChronometers.containsKey(id)) {
                globalChronometers.get(id).setTime(timeInMillis);
            } else {
                player.sendMessage(chatUtils.getMessage("commands.chrono.not_found", player)
                        .replace("%id%", id));
            }
        } else if (type.equalsIgnoreCase("player")) {
            if (playerChronometers.containsKey(id)) {
                playerChronometers.get(id).put(player, new Chronometer(id, "player"));
                playerChronometers.get(id).get(player).setTime(timeInMillis);
            } else {
                player.sendMessage(chatUtils.getMessage("commands.chrono.not_found", player)
                        .replace("%id%", id));
            }
        }
    }

    // Agregar tiempo a un cronómetro
    public void addTimeToChrono(Player sender, String type, String id, Player player, int hours, int minutes, int seconds) {
        long timeToAdd = (hours * 3600 + minutes * 60 + seconds) * 1000L;

        if (type.equalsIgnoreCase("global")) {
            if (globalChronometers.containsKey(id)) {
                globalChronometers.get(id).addTime(timeToAdd);
            } else {
                player.sendMessage(chatUtils.getMessage("commands.chrono.not_found", player)
                        .replace("%id%", id));
            }
        } else if (type.equalsIgnoreCase("player")) {
            if (playerChronometers.containsKey(id) && playerChronometers.get(id).containsKey(player)) {
                playerChronometers.get(id).get(player).addTime(timeToAdd);
            } else {
                player.sendMessage(chatUtils.getMessage("commands.chrono.not_found", player)
                        .replace("%id%", id));
            }
        }
    }

    // Reducir tiempo de un cronómetro
    public void reduceTimeFromChrono(Player sender, String type, String id, Player player, int hours, int minutes, int seconds) {
        long timeToReduce = (hours * 3600 + minutes * 60 + seconds) * 1000L;

        if (type.equalsIgnoreCase("global")) {
            if (globalChronometers.containsKey(id)) {
                globalChronometers.get(id).reduceTime(timeToReduce);
            } else {
                player.sendMessage(chatUtils.getMessage("commands.chrono.not_found", player)
                        .replace("%id%", id));
            }
        } else if (type.equalsIgnoreCase("player")) {
            if (playerChronometers.containsKey(id) && playerChronometers.get(id).containsKey(player)) {
                playerChronometers.get(id).get(player).reduceTime(timeToReduce);
            } else {
                player.sendMessage(chatUtils.getMessage("commands.chrono.not_found", player)
                        .replace("%id%", id));
            }
        }
    }

    // Iniciar un cronómetro
    public void startChrono(Player sender, String type, String id, Player player) {
        if (type.equalsIgnoreCase("global")) {
            if (globalChronometers.containsKey(id)) {
                globalChronometers.get(id).start();
            } else {
                player.sendMessage(chatUtils.getMessage("commands.chrono.not_found", player)
                        .replace("%id%", id));
            }
        } else if (type.equalsIgnoreCase("player")) {
            if (playerChronometers.containsKey(id) && playerChronometers.get(id).containsKey(player)) {
                playerChronometers.get(id).get(player).start();
            } else {
                player.sendMessage(chatUtils.getMessage("commands.chrono.not_found", player)
                        .replace("%id%", id));
            }
        }
    }

    // Pausar un cronómetro
    public void pauseChrono(Player sender, String type, String id, Player player) {
        if (type.equalsIgnoreCase("global")) {
            if (globalChronometers.containsKey(id)) {
                globalChronometers.get(id).stop();
            } else {
                player.sendMessage(chatUtils.getMessage("commands.chrono.not_found", player)
                        .replace("%id%", id));
            }
        } else if (type.equalsIgnoreCase("player")) {
            if (playerChronometers.containsKey(id) && playerChronometers.get(id).containsKey(player)) {
                playerChronometers.get(id).get(player).stop();
            } else {
                player.sendMessage(chatUtils.getMessage("commands.chrono.not_found", player)
                        .replace("%id%", id));
            }
        }
    }

    // Reiniciar un cronómetro
    public boolean resetChrono(Player sender, String[] args) {
        if (args.length < 3) return false;

        String type = args[1].toLowerCase();
        String id = args[2];

        if (type.equals("global")) {
            if (!globalChronometers.containsKey(id)) {
                sender.sendMessage(chatUtils.getMessage("commands.chrono.not_found", (Player) sender)
                        .replace("%id%", id));
                return true;
            }
            globalChronometers.get(id).reset();
            sender.sendMessage(chatUtils.getMessage("command.chrono.reset.success", (Player) sender)
                    .replace("%id%", id));
            return true;
        }

        if (type.equals("player")) {
            if (args.length < 4) return false;

            String playerName = args[3];
            Player targetPlayer = sender.getServer().getPlayer(playerName);

            if (targetPlayer == null || !playerChronometers.containsKey(id) || !playerChronometers.get(id).containsKey(targetPlayer)) {
                sender.sendMessage(chatUtils.getMessage("error.chrono.player_not_found", targetPlayer));
                return true;
            }

            playerChronometers.get(id).get(targetPlayer).reset();
            sender.sendMessage(chatUtils.getMessage("command.chrono.reset.player.success", targetPlayer)
                    .replace("%id%", id));
            return true;
        }

        return false;
    }
    public Map<String, Chronometer> getActiveChronos() {
        Map<String, Chronometer> activeChronos = new HashMap<>();

        // Obtener cronómetros activos de los cronómetros de jugador
        for (Map.Entry<String, Map<Player, Chronometer>> entry : playerChronometers.entrySet()) {
            // Recorremos los cronómetros de cada jugador
            for (Map.Entry<Player, Chronometer> playerEntry : entry.getValue().entrySet()) {
                if (playerEntry.getValue().isActive()) { // Supuesto método isActive()
                    activeChronos.put(entry.getKey() + ":" + playerEntry.getKey().getName(), playerEntry.getValue());
                }
            }
        }

        // Obtener cronómetros activos de los cronómetros globales
        for (Map.Entry<String, Chronometer> entry : globalChronometers.entrySet()) {
            if (entry.getValue().isActive()) { // Supuesto método isActive()
                activeChronos.put(entry.getKey(), entry.getValue());
            }
        }

        return activeChronos;
    }
}
