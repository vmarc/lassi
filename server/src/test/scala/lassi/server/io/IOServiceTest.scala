package lassi.server.io

import lassi.server.settings.Settings
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import java.io.File

class IOServiceTest extends AnyFunSuite with Matchers {

  test("write/read settings file") {

    val rootDir = new File("/tmp/lassi")
    if (rootDir.exists()) {
      rootDir.delete()
    }
    val settingsDir = new File("/tmp/lassi/settings")
    settingsDir.mkdirs()

    val service = new IoServiceImpl("/tmp/lassi")

    val settings = Settings(
      framesPerSecond = 1,
      fadeTimeInSeconds = 2,
      buttonPageCount = 3
    )

    service.writeSettings(settings: Settings)
    val settingsFromDisk = service.readSettings
    settingsFromDisk should equal(settings)
  }

}
