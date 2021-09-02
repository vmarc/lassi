package lassi.server.settings

import lassi.log.Log
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SettingsController(settingsService: SettingsService) {

  private val log = Log(classOf[SettingsController])

  @GetMapping(value = Array("/api/settings"))
  def getSettings: Settings = {
    try {
      log.infoElapsed {
        val settings = settingsService.readSettings
        ("Read: " + settings, settings)
      }
    } catch {
      case e: Exception =>
        throw new RuntimeException("Could not read settings from disk", e)
    }
  }

  @PutMapping(value = Array("/api/settings"))
  def saveSettings(@RequestBody settings: Settings): Unit = {
    try {
      log.infoElapsed {
        settingsService.writeSettings(settings)
        ("Write: " + settings, ())
      }
    } catch {
      case e: Exception =>
        throw new RuntimeException("Could not write settings to disk: " + settings, e)
    }
  }
}
