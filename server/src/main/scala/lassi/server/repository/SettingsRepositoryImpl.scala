package lassi.server.repository

import lassi.server.settings.Settings
import org.springframework.stereotype.Component

import java.io.File
import java.nio.file.Paths

@Component
class SettingsRepositoryImpl(rootDir: String) extends SettingsRepository {

  private val settingsDir = Paths.get(rootDir + "/settings/")
  private val settingsFile = new File((settingsDir) + "/settings.json")

  override def readSettings: Settings = {
    Json.objectMapper.readValue(settingsFile, classOf[Settings])
  }

  override def writeSettings(settings: Settings): Unit = {
    Json.objectMapper.writeValue(settingsFile, settings)
  }
}
