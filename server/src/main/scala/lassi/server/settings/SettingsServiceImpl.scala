package lassi.server.settings

import lassi.server.io.IoService
import org.springframework.stereotype.Component

@Component
class SettingsServiceImpl(ioService: IoService) extends SettingsService {

  override def readSettings: Settings = {
    ioService.readSettings
  }

  override def writeSettings(settings: Settings): Unit = {
    ioService.writeSettings(settings)
  }
}
