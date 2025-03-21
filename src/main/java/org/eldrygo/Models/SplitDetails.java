package org.eldrygo.Models;

public class SplitDetails {
    private Split split;  // Objeto Split
    private String timerOrChrono;  // Detalles adicionales, como timer o chrono
    private String timerOrChronoId;  // Id relacionado con el timer o chrono

    // Constructor
    public SplitDetails(Split split, String timerOrChrono, String timerOrChronoId) {
        this.split = split;
        this.timerOrChrono = timerOrChrono;
        this.timerOrChronoId = timerOrChronoId;
    }

    // Getter y Setter para el Split
    public Split getSplit() {
        return split;
    }

    public void setSplit(Split split) {
        this.split = split;
    }

    // Getter y Setter para timerOrChrono
    public String getTimerOrChrono() {
        return timerOrChrono;
    }

    public void setTimerOrChrono(String timerOrChrono) {
        this.timerOrChrono = timerOrChrono;
    }

    // Getter y Setter para timerOrChronoId
    public String getTimerOrChronoId() {
        return timerOrChronoId;
    }

    public void setTimerOrChronoId(String timerOrChronoId) {
        this.timerOrChronoId = timerOrChronoId;
    }
}
