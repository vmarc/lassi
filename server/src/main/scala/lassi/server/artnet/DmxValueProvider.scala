package lassi.server.artnet

trait DmxValueProvider {

  def getValue(subnet: Int, universe: Int): Array[Byte]

}
