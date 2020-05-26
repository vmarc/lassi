package lighting.server.monitor;

import lighting.server.IO.IOServiceImpl;
import lighting.server.artnet.ArtnetListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class MonitorService {

	private ArtnetListener artnetListener;
	private SimpMessagingTemplate messagingTemplate;

	public MonitorService(SimpMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
		this.artnetListener = new ArtnetListener(new IOServiceImpl());
		this.artnetListener.captureData();

	}

	@Scheduled(fixedDelay = 200)
	public void simulateOutputUpdate() {
		this.messagingTemplate.convertAndSend("/topic/output", artnetListener.getCurrentFrames());
		System.out.println("monitor: " + artnetListener.getCurrentFrames().get(1).getDmxValues()[0]);

	}

}
