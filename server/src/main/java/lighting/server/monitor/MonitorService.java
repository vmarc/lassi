package lighting.server.monitor;

import lighting.server.artnet.ArtnetReceiver;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MonitorService {

	private final ArtnetReceiver artnetReceiver;
	private final SimpMessagingTemplate messagingTemplate;


	public MonitorService(SimpMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
		this.artnetReceiver = new ArtnetReceiver();
	}

	@Scheduled(fixedDelay = 200)
	public void simulateOutputUpdate() {
		this.messagingTemplate.convertAndSend("/topic/output", artnetReceiver.getScene());
	}

}
