package lighting.server;

import lighting.server.monitor.MonitorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ServerApplication {


	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
		//MonitorService monitorService = new MonitorService(null);
	}
}
