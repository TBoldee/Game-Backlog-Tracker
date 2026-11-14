package gamebacklog.database.entities

import gamebacklog.database.tables.Games
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

class Game(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Game>(Games);

    val name by Games.name;
    val platform by Games.platform;
    val genre by Games.genre;
    val gametime by Games.gametime;
}