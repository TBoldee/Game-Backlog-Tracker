package gamebacklog.database

import gamebacklog.database.tables.Games
import gamebacklog.database.tables.Playthroughs
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.io.File

object DatabaseFactory {
    fun init() {
        File("./data").mkdirs()

        Database.connect(
            "jdbc:sqlite:./data/data.db",
            "org.sqlite.JDBC",
            setupConnection = { connection ->
                connection.createStatement().execute("PRAGMA foreign_keys = ON")
            }
        );

        transaction {
            SchemaUtils.create(Games, Playthroughs);
        }
    }
}