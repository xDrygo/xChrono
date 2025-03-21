package org.eldrygo.Models;

public class Chronometer {

    private boolean isRunning;
    private String id;
    private long startTime;
    private long elapsedTime;
    private boolean active;
    private String type;

    public Chronometer(String id, String type) {
        this.id = id;
        this.startTime = System.currentTimeMillis();
        this.elapsedTime = 0;
        this.isRunning = true;
        this.type = type;
    }

    // Constructor para un cronómetro con tiempo guardado
    public Chronometer(String id, long elapsedTime, boolean isRunning) {
        this.id = id;
        this.elapsedTime = elapsedTime;
        this.isRunning = isRunning;
        this.startTime = isRunning ? System.currentTimeMillis() - elapsedTime : 0;
    }
    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    // Añadir tiempo al cronómetro
    public void addTime(long timeToAdd) {
        this.elapsedTime += timeToAdd;
    }

    // Reducir tiempo del cronómetro
    public void reduceTime(long timeToReduce) {
        this.elapsedTime -= timeToReduce;
    }
    public boolean isActive() {
        // Lógica para determinar si el cronómetro está activo
        return this.active; // Suponiendo que existe una variable 'active'
    }

    // Establecer el tiempo del cronómetro (usado en setChronoTime)
    public void setTime(long timeInMillis) {
        this.elapsedTime = timeInMillis;
    }

    // Reiniciar el cronómetro
    public void reset() {
        this.startTime = System.currentTimeMillis();
        this.elapsedTime = 0;
    }

    // Detener el cronómetro
    public void start() {
        if (!isRunning) {
            this.startTime = System.currentTimeMillis() - elapsedTime;
            isRunning = true;
        }
    }

    public void stop() {
        if (isRunning) {
            elapsedTime = getElapsedTime();
            isRunning = false;
        }
    }

    public long getElapsedTime() {
        return isRunning ? (System.currentTimeMillis() - startTime) : elapsedTime;
    }
}
