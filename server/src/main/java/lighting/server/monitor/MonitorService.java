package lighting.server.monitor;

import lighting.server.IO.IOServiceImpl;
import lighting.server.artnet.ArtnetListener;
import lighting.server.frame.Frame;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;


@Component
public class MonitorService {

	private ArtnetListener artnetListener;
	private SimpMessagingTemplate messagingTemplate;

	public MonitorService(SimpMessagingTemplate messagingTemplate, ArtnetListener artnetListener) {
		this.messagingTemplate = messagingTemplate;
		this.artnetListener = artnetListener;
		this.artnetListener.captureData();

	}

	@Scheduled(fixedDelay = 200)
	public void simulateOutputUpdate() {
		this.messagingTemplate.convertAndSend("/topic/output", artnetListener.getCurrentFrames());

	}

}
