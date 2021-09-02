package lassi.server.config

import com.fasterxml.jackson.databind.ObjectMapper
import lassi.server.repository.Json
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class ServerConfiguration {

  @Bean
  @Primary
  def objectMapper: ObjectMapper = Json.objectMapper

  @Bean
  def rootDir(@Value("${lassi.root-dir:/lassi}") value: String): String = {
    value
  }

}
