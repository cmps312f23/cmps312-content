package qu.lingosnacks.entity

import kotlinx.serialization.Serializable

@Serializable
data class Score (val scoreId: String,
                  val packageId: String,
                  val uid: String,
                  val gameName: String,
                  val score: Int,
                  val outOf: Int,
                  val doneOn: String)