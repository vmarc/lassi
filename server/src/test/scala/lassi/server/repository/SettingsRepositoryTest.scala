package lassi.server.repository

import lassi.server.settings.Settings
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import java.io.File

class SettingsRepositoryTest extends AnyFunSuite with Matchers {

  test("write/read settings file") {

    val rootDir = new File("/tmp/lassi")
    if (rootDir.exists()) {
      rootDir.delete()
    }
    val settingsDir = new File("/tmp/lassi/settings")
    settingsDir.mkdirs()

    val repository = new SettingsRepositoryImpl("/tmp/lassi")

    val settings = Settings(
      framesPerSecond = 1,
      fadeTimeInSeconds = 2,
      buttonPageCount = 3
    )

    repository.writeSettings(settings: Settings)
    val settingsFromDisk = repository.readSettings
    settingsFromDisk should equal(settings)
  }

}
