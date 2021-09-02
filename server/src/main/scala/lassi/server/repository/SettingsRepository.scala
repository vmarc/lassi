package lassi.server.repository

import lassi.server.settings.Settings

trait SettingsRepository {

  def readSettings: Settings

  def writeSettings(settings: Settings): Unit

}
