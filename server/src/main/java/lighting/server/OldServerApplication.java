package lighting.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
// migrated
public class OldServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(OldServerApplication.class, args);
    }
}
