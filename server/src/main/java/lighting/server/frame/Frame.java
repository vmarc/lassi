package lighting.server.frame;

import java.util.UUID;

public class Frame {

    private UUID id = UUID.randomUUID();
    private int[] dmxValues;
    private long startTime;
    private int universe;

    public Frame(int[] dmxValues) {
        this.dmxValues = dmxValues;
    }

    public Frame(int[] dmxValues, long startTime, int universe) {
        this.dmxValues = dmxValues;
        this.startTime = startTime;
        this.universe = universe;
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
}
