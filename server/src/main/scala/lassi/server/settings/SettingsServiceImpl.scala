package lassi.server.settings

import lassi.server.io.IOService
import org.springframework.stereotype.Component

@Component
class SettingsServiceImpl(iOService: IOService) extends SettingsService {

  override def readSettings: Settings = {
    iOService.readSettings
  }

  override def writeSettings(settings: Settings): Unit = {
    iOService.writeSettings(settings)
  }
}
