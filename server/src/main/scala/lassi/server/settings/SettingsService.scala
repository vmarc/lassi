package lassi.server.settings

trait SettingsService {

  def writeSettings(settings: Settings): Unit

  def readSettings: Settings
}
