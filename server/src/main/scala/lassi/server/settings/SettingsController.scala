package lassi.server.settings

import org.apache.logging.log4j.LogManager
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

import java.io.IOException

@RestController
class SettingsController(settingsService: SettingsService) {

  private val log = LogManager.getLogger(classOf[SettingsController])

  @GetMapping(value = Array("/scala-api/settings/get"))
  def getSettings: Settings = {
    try {
      log.info("Retrieved settings from disk")
      settingsService.readSettings
    } catch {
      case e: IOException =>
        throw new IllegalStateException("Could not retrieve settings from disk", e)
    }
  }

  @PutMapping(value = Array("/scala-api/settings/save"))
  def saveSettings(@RequestBody settings: Settings): Unit = {
    try {
      settingsService.writeSettings(settings)
      var message = "Saved settings to disk: fade time in seconds: " + settings.fadeTimeInSeconds
      message += ", frames per seconds: " + settings.framesPerSecond
      message += ", button page count: " + settings.buttonPageCount
      log.info(message)
    } catch {
      case e: IOException =>
        var message = "Could not save settings to disk: fade time in seconds: " + settings.fadeTimeInSeconds
        message += ", frames per seconds: " + settings.framesPerSecond
        message += ", button page count: " + settings.buttonPageCount
        throw new IllegalStateException(message, e)
    }
  }
}
