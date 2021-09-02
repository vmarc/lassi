package lassi.server.settings

import lassi.server.repository.SettingsRepository
import org.springframework.stereotype.Component

@Component
class SettingsServiceImpl(settingsRepository: SettingsRepository) extends SettingsService {

  override def readSettings: Settings = {
    settingsRepository.readSettings
  }

  override def writeSettings(settings: Settings): Unit = {
    settingsRepository.writeSettings(settings)
  }
}
