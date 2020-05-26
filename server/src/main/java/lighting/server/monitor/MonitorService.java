package lighting.server.monitor;

import ch.bildspur.artnet.packets.ArtDmxPacket;
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
	private HashMap<Integer, Frame> currentFrames = new HashMap<>();

	public MonitorService(SimpMessagingTemplate messagingTemplate, ArtnetListener artnetListener) {
		this.messagingTemplate = messagingTemplate;
		//this.artnetListener = new ArtnetListener(new IOServiceImpl());
		this.artnetListener = artnetListener;
		this.artnetListener.captureData();

	}

	//@Scheduled(fixedDelay = 2000)
	public void simulateOutputUpdate() {
		currentFrames = artnetListener.getCurrentFrames();
		//this.messagingTemplate.convertAndSend("/topic/output", currentFrames);
		System.out.println("monitor: " + currentFrames.get(1).getDmxValues()[0]);

	}

}
