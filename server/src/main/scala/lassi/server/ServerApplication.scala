package lassi.server

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling

// run with -Dserver.port=8081
object ServerApplication {
  def main(args: Array[String]): Unit = {
    val app: Array[Class[_]] = Array(classOf[ServerApplication])
    SpringApplication.run(app, args)
  }
}

@SpringBootApplication
@EnableScheduling
class ServerApplication {
}
