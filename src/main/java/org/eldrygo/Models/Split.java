package org.eldrygo.Models;

import org.bukkit.plugin.Plugin;

public class Split {

    private String id;
    private String displayName;
    private String timerType;
    private String timerId;
    private boolean active;
    private long time; // Variable para almacenar el tiempo del split
    private Plugin plugin;
    private String linkedTimer;
    private String type;

    public Split(String id, String type, String linkedTimer, String displayName, Plugin plugin) {
        this.id = id;
        this.displayName = displayName != null ? displayName : id;
        this.timerType = null;  // Inicialmente no asignado
        this.timerId = null;    // Inicialmente no asignado
        this.active = false;    // Inicialmente inactivo
        this.time = 0;          // Inicialmente sin tiempo registrado
        this.plugin = plugin;
        this.linkedTimer = linkedTimer;
        this.type = type;
    }
    public String getId() { return id; }
    public String getLinkedTimer() { return linkedTimer;}
    public String getDisplayName() { return displayName; }
    public String getType() { return type; }

    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public String getTimerType() { return timerType; }

    public String getTimerId() { return timerId; }

    public void setTimer(String timerType, String timerId) {
        this.timerType = timerType;
        this.timerId = timerId;
    }

    public void unsetTimer() {
        this.timerType = null;
        this.timerId = null;
    }

    public void markSplit() {
        if (!this.active) {
            plugin.getLogger().info("El split no está activo, no se puede marcar.");
            return; // Si no está activo, no se puede marcar
        }

        this.time = System.currentTimeMillis(); // Marca el tiempo actual
        plugin.getLogger().info("Split marcado a: " + this.time);
    }

    public void reset() {
        this.time = 0;  // Reinicia el tiempo del split
        this.active = false;  // Marca el split como inactivo
        plugin.getLogger().info("El split ha sido reiniciado.");
    }

    public void reduceTime(long timeToReduce) {
        if (timeToReduce <= 0) {
            plugin.getLogger().info("El tiempo a reducir debe ser mayor que 0.");
            return;
        }

        this.time = Math.max(0, this.time - timeToReduce);  // Asegura que el tiempo no sea negativo
        plugin.getLogger().info("Tiempo del split reducido a: " + this.time);
    }

    public boolean isActive() { return this.active; }

    public void activate() {
        this.active = true; // Marca el split como activo
        plugin.getLogger().info("El split ha sido activado.");
    }

    public void deactivate() {
        this.active = false; // Marca el split como inactivo
        plugin.getLogger().info("El split ha sido desactivado.");
    }

    public long getTime() {
        return time; // Retorna el tiempo actual del split
    }

    public void setTime(long time) {
        this.time = time; // Establece un nuevo tiempo para el split
    }

}
