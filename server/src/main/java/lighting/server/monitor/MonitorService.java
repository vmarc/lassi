package lighting.server.monitor;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lighting.server.artnet.ArtnetListener;

@Component
public class MonitorService {

	private final ArtnetListener artnetListener;
	private final SimpMessagingTemplate messagingTemplate;

	public MonitorService(final SimpMessagingTemplate messagingTemplate, final ArtnetListener artnetListener) {
		this.messagingTemplate = messagingTemplate;
		this.artnetListener = artnetListener;
		this.artnetListener.captureData();
	}

	@Scheduled(fixedDelay = 200)
	public void simulateOutputUpdate() {
		this.messagingTemplate.convertAndSend("/topic/output", artnetListener.getCurrentFrames());
	}
}
