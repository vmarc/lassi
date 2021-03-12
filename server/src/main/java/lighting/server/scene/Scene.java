package lighting.server.scene;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lighting.server.frame.Frame;

public class Scene {

	private final String id = UUID.randomUUID().toString();
	private String name;
	private Long duration;
	private int fadeTime;
	private int buttonId;
	private LocalDateTime createdOn;
	private List<Frame> frames = new ArrayList<>();

	public Scene(String name, List<Frame> frames) {
		this.name = name;
		this.frames = frames;
	}

	public Scene(String name, int buttonId, List<Frame> frames) {
		this.name = name;
		this.buttonId = buttonId;
		this.frames = frames;
	}

	public Scene(String name, Long duration, int buttonId, int fadeTime, LocalDateTime createdOn, List<Frame> frames) {
		this.name = name;
		this.duration = duration;
		this.buttonId = buttonId;
		this.fadeTime = fadeTime;
		this.createdOn = createdOn;
		this.frames = frames;
	}

	public Scene() {
		long duration = 0;
		if (frames.size() > 0) {
			for (Frame f : frames) {
				duration += f.getStartTime();
			}
		}
		setDuration(duration);
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

	public int getFadeTime() {
		return fadeTime;
	}

	public void setFadeTime(int fadeTime) {
		this.fadeTime = fadeTime;
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

	public String getId() {
		return id;
	}
}
