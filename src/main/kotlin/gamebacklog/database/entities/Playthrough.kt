package gamebacklog.database.entities

import gamebacklog.database.tables.Playthroughs
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

class Playthrough(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Playthrough>(Playthroughs);

    var game_id by Playthroughs.game_id;
    var status by Playthroughs.status;
}