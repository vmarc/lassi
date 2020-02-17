package lighting.server.frame;

import java.util.UUID;

public class Frame {

    private UUID id = UUID.randomUUID();
    private int[] dmxValues;
    private long startTime;

    public Frame(int[] dmxValues) {
        this.dmxValues = dmxValues;
    }

    public Frame(int[] dmxValues, long startTime) {
        this.dmxValues = dmxValues;
        this.startTime = startTime;
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
}
