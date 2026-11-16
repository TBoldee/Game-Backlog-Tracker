package gamebacklog.database.tables

import gamebacklog.model.Genre
import gamebacklog.model.Platform
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object Games: IntIdTable() {
    val name = varchar("Name", 255).uniqueIndex();
    val platform = enumerationByName<Platform>("Platform", 50);
    val genre = enumerationByName<Genre>("Genre", 50);
    val gametime = integer("Game time").default(0);
}