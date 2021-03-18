package lassi.tools

import lassi.pi.Pi

object LedTestTool {
  def main(args: Array[String]): Unit = {
    new LedTestTool().tryout()
  }
}

class LedTestTool() {

  def tryout(): Unit = {

    println("Led test tool")

    val pi = Pi()

    pi.leds.foreach { led =>
      println(led.name)
      led.output.pulseSync(1000)
    }
    Thread.sleep(1000)

    pi.shutdown()

    println("done")
  }

}
