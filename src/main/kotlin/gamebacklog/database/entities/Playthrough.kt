package gamebacklog.database.entities

import gamebacklog.database.tables.Playthroughs
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

class Playthrough(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Playthrough>(Playthroughs);

    val game_id by Playthroughs.game_id;
    val status by Playthroughs.status;
}