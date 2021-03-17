package lassi.server.io

import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_ABSENT
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

object Json {

  val objectMapper: ObjectMapper = {

    val b = Jackson2ObjectMapperBuilder.json()
    b.featuresToEnable(DeserializationFeature.USE_LONG_FOR_INTS)
    b.serializationInclusion(NON_ABSENT)
    b.annotationIntrospector(new JacksonAnnotationIntrospector)

    val om: ObjectMapper = b.build()
    om.registerModule(DefaultScalaModule)
    om.registerModule(new JavaTimeModule)
    om
  }

  def string(o: Object): String = {
    objectMapper.writeValueAsString(o)
  }

  def value[T](string: String, valueType: Class[T]): T = {
    objectMapper.readValue(string, valueType)

  }
}
