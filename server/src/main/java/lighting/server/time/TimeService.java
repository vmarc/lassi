package lighting.server.time;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TimeService {

	private final Logger logger = LoggerFactory.getLogger(TimeService.class);

	private final SimpMessagingTemplate messagingTemplate;

	public TimeService(SimpMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	@Scheduled(fixedDelay = 1000)
	public void sendTime() {
		String now = now();
		this.messagingTemplate.convertAndSend("/topic/time", new TimeMessage(now));
		logger.debug(now);
	}

	private String now() {
		return LocalTime.now().format(DateTimeFormatter.ISO_TIME).substring(0, 8);
	}
}
