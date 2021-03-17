package lassi.domain

import java.time.LocalDateTime

case class Scene(
  id: String,
  name: String,
  duration: Long,
  fadeTime: Int,
  buttonId: Int,
  createdOn: LocalDateTime,
  frames: Seq[Frame]
)
