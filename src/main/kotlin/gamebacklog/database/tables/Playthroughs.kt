package gamebacklog.database.tables

import gamebacklog.model.PlaythroughStatus
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object Playthroughs : IntIdTable() {
    val game_id = reference("game_id", Games)
    val status = enumerationByName<PlaythroughStatus>("status", 50)
}