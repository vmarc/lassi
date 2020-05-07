package lighting.server.monitor;

import lighting.server.IO.IOServiceImpl;
import lighting.server.artnet.ArtnetListener;
import lighting.server.sceneX.SceneXXServiceImpl;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MonitorService {

	private final ArtnetListener artnetListener;
	private final SimpMessagingTemplate messagingTemplate;

	public MonitorService(SimpMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
		this.artnetListener = new ArtnetListener(new IOServiceImpl());
		this.artnetListener.captureData();

	}

	@Scheduled(fixedDelay = 200)
	public void simulateOutputUpdate() {
		if (artnetListener.getCurrentFrame() == null) {

		} else {
			this.messagingTemplate.convertAndSend("/topic/output", artnetListener.getCurrentFrame());

		}
	}

}
