package lighting.server.sceneX;

import lighting.server.frame.Frame;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

public class SceneX {

    private String id = UUID.randomUUID().toString();
    private String name;
    private Long duration;
    private int buttonId;
    private LocalDateTime createdOn;
    private List<Frame> frames = new ArrayList<>();

    public SceneX(String name, List<Frame> frames) {
        this.name = name;
        this.frames = frames;
    }

    public SceneX(String name, int buttonId, List<Frame> frames) {
        this.name = name;
        this.buttonId = buttonId;
        this.frames = frames;
    }

    public SceneX(String name, Long duration, int buttonId, LocalDateTime createdOn, List<Frame> frames) {
        this.name = name;
        this.duration = duration;
        this.buttonId = buttonId;
        this.createdOn = createdOn;
        this.frames = frames;
    }

    public SceneX() {
        createEmptyFrame();
    }

    public void createEmptyFrame() {
        int[] dmxValues = IntStream.generate(() -> new Random().nextInt(1)).limit(128).toArray();
        Frame frame = new Frame(dmxValues);
        this.getFrames().add(frame);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public int getButtonId() {
        return buttonId;
    }

    public void setButtonId(int buttonId) {
        this.buttonId = buttonId;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public List<Frame> getFrames() {
        return frames;
    }

    public void setFrames(List<Frame> frames) {
        this.frames = frames;
    }

    public String getId() { return id; }
}
