package lighting.server.monitor;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lighting.server.scene.Scene;

@Component
public class MonitorService {

	private Scene scene;

	private final Logger logger = LoggerFactory.getLogger(MonitorService.class);

	private final SimpMessagingTemplate messagingTemplate;

	public MonitorService(SimpMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	@Scheduled(fixedDelay = 200)
	public void simulateOutputUpdate() {
		updateScene();
		this.messagingTemplate.convertAndSend("/topic/output", scene);
	}

	private void updateScene() {
		int newDmxValue = 1;
		if (scene != null) {
			newDmxValue = scene.getDmxValue(0) + 1;
		}
		if (newDmxValue > 255) {
			newDmxValue = 0;
		}
		int[] dmxValues = new int[Scene.SCENE_DMX_VALUES];
		Arrays.fill(dmxValues, newDmxValue);
		//scene = new Scene(-1, "output", dmxValues);
	}
}
