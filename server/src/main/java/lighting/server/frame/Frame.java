package lighting.server.frame;

import java.time.LocalDateTime;
import java.util.UUID;

public class Frame {

    private final UUID id = UUID.randomUUID();
    private int[] dmxValues;
    private long startTime;
    private int universe;
    private LocalDateTime createdOn;

    public Frame(int[] dmxValues) {
        this.dmxValues = dmxValues;
        this.createdOn = LocalDateTime.now();
    }

    public Frame(int[] dmxValues, long startTime, int universe) {
        this.dmxValues = dmxValues;
        this.startTime = startTime;
        this.universe = universe;
        this.createdOn = LocalDateTime.now();
    }

    public Frame() {
    }

    public int[] getDmxValues() {
        return dmxValues;
    }

    public void setDmxValues(int[] dmxValues) {
        this.dmxValues = dmxValues;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public UUID getId() {
        return id;
    }

    public int getUniverse() {
        return universe;
    }

    public void setUniverse(int universe) {
        this.universe = universe;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

}
