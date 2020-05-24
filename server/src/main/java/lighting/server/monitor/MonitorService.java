package lighting.server.monitor;

import lighting.server.IO.IOServiceImpl;
import lighting.server.artnet.ArtnetListener;
import lighting.server.frame.Frame;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.stream.IntStream;

@Component
public class MonitorService {

	private final ArtnetListener artnetListener;
	private final SimpMessagingTemplate messagingTemplate;
	private int count = 0;
	private Frame previousFrame = new Frame();

	public MonitorService(SimpMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
		this.artnetListener = new ArtnetListener(new IOServiceImpl());
		this.artnetListener.captureData();

	}

	@Scheduled(fixedDelay = 200)
	public void simulateOutputUpdate() {
		if (artnetListener.getCurrentFrame() == null) {

		} else {
			if (artnetListener.getCurrentFrame().equals(previousFrame)) {
				count++;
			} else {
				count = 0;
			}
			if (count > 25) {
				int[] emptyArray = IntStream.generate(() -> new Random().nextInt(1)).limit(512).toArray();
				artnetListener.setCurrentFrame(new Frame(emptyArray));
			}
			this.messagingTemplate.convertAndSend("/topic/output", artnetListener.getCurrentFrame());
			this.previousFrame = artnetListener.getCurrentFrame();

		}
	}

}
