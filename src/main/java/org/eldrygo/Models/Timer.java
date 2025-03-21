package org.eldrygo.Models;

import java.util.HashMap;
import java.util.Map;

public class Timer {

    private String id;
    private long timeRemaining;
    private Map<String, Long> playerTimes;  // Almacena los tiempos por jugador
    private boolean isRunning;
    private boolean active;
    private long time;
    private String type;

    public Timer(String id, String type, long initialTime) {
        this.id = id;
        this.timeRemaining = initialTime;
        this.playerTimes = new HashMap<>();
        this.isRunning = false;
        this.type = type;
    }

    public String getId() {
        return id;
    }
    public long getTime() { return time; }

    public long getTimeRemaining() {
        return timeRemaining;
    }

    // Reducir tiempo global
    public void reduceGlobalTime(int hours, int minutes, int seconds) {
        long reduction = (hours * 3600 + minutes * 60 + seconds) * 1000L;
        this.timeRemaining -= reduction;
    }

    // Reducir tiempo específico por jugador
    public void reducePlayerTime(String player, int hours, int minutes, int seconds) {
        long reduction = (hours * 3600 + minutes * 60 + seconds) * 1000L;
        long currentPlayerTime = playerTimes.getOrDefault(player, timeRemaining);
        playerTimes.put(player, currentPlayerTime - reduction);
    }
    public boolean isActive() {
        // Lógica para determinar si el temporizador está activo
        return this.active; // Suponiendo que existe una variable 'active'
    }

    // Obtener el tiempo restante de un jugador específico
    public long getPlayerTime(String player) {
        return playerTimes.getOrDefault(player, timeRemaining);
    }

    // Agregar tiempo global
    public void addGlobalTime(long timeToAdd) {
        this.timeRemaining += timeToAdd;
    }

    // Agregar tiempo específico por jugador
    public void addPlayerTime(String player, long timeToAdd) {
        long currentPlayerTime = playerTimes.getOrDefault(player, timeRemaining);
        playerTimes.put(player, currentPlayerTime + timeToAdd);
    }

    // Iniciar el temporizador
    public void start() {
        this.isRunning = true;
    }

    // Detener el temporizador
    public void stop() {
        this.isRunning = false;
    }
    public String getType() { return type; }

    // Reiniciar el temporizador
    public void reset() {
        this.timeRemaining = 0;
        this.playerTimes.clear();
        this.isRunning = false;
    }

    // Verificar si el temporizador está corriendo
    public boolean isRunning() {
        return isRunning;
    }
}
