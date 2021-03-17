package lassi.server.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ServerConfiguration {

  @Bean
  def rootDir(@Value("${lassi.root-dir:/lassi}") value: String): String = {
    value
  }

}
