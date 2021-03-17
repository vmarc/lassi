package lassi.server.settings

import org.apache.logging.log4j.LogManager
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SettingsController(settingsService: SettingsService) {

  private val log = LogManager.getLogger(classOf[SettingsController])

  @GetMapping(value = Array("/scala-api/settings"))
  def getSettings: Settings = {
    try {
      val settings = settingsService.readSettings
      log.info("Retrieved settings from disk " + settings)
      settings
    } catch {
      case e: Exception =>
        throw new RuntimeException("Could not retrieve settings from disk", e)
    }
  }

  @PutMapping(value = Array("/scala-api/settings"))
  def saveSettings(@RequestBody settings: Settings): Unit = {
    try {
      settingsService.writeSettings(settings)
      var message = "Saved settings to disk: fade time in seconds: " + settings.fadeTimeInSeconds
      message += ", frames per seconds: " + settings.framesPerSecond
      message += ", button page count: " + settings.buttonPageCount
      log.info(message)
    } catch {
      case e: Exception =>
        var message = "Could not save settings to disk: fade time in seconds: " + settings.fadeTimeInSeconds
        message += ", frames per seconds: " + settings.framesPerSecond
        message += ", button page count: " + settings.buttonPageCount
        throw new IllegalStateException(message, e)
    }
  }
}
