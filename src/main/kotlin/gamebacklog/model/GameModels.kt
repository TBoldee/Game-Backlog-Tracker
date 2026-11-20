package gamebacklog.model

import gamebacklog.database.entities.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.v1.jdbc.JdbcTransaction
import org.jetbrains.exposed.v1.jdbc.transactions.inTopLevelSuspendTransaction

interface GameInfo {
    val id: Int
    val name: String
    val platform: Platform
    val genre: Genre
    val gameTime: Int
}

@Serializable
data class GameResponse(
    override val id: Int,
    override val name: String,
    override val platform: Platform,
    override val genre: Genre,
    override val gameTime: Int,
) : GameInfo {
}

@Serializable
data class GameUpdateRequest(
    val name: String?,
    val platform: Platform?,
    val genre: Genre?,
    val gameTime: Int?,
)

@Serializable
data class GameCreateRequest(
    val name: String,
    val platform: Platform,
    val genre: Genre,
    val gameTime: Int,
)

fun Game.toResponse() = GameResponse(
    id = id.value,
    name = name,
    platform = platform,
    genre = genre,
    gameTime = gametime
)

suspend fun <T> withTransaction(block: suspend JdbcTransaction.() -> T): T {
    return withContext(Dispatchers.IO) {
        inTopLevelSuspendTransaction { block() }
    }
}
