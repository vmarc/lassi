package lassi.domain

import java.time.LocalDateTime

case class Frame(
  dmxValues: Array[Int],
  startTime: Long,
  universe: Int,
  createdOn: LocalDateTime
)
