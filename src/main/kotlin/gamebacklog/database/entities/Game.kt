package gamebacklog.database.entities

import gamebacklog.database.tables.Games
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

class Game(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Game>(Games);

    var name by Games.name;
    var platform by Games.platform;
    var genre by Games.genre;
    var gametime by Games.gametime;
}