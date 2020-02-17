package lighting.server.sceneX;

import lighting.server.frame.Frame;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SceneX {

    private UUID id = UUID.randomUUID();
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

    public UUID getId() { return id; }
}
