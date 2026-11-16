package gamebacklog.model

import gamebacklog.database.entities.Playthrough
import kotlinx.serialization.Serializable

@Serializable
data class PlaythroughResponse(
    val id: Int,
    val gameId: Int,
    val status: PlaythroughStatus
)

@Serializable
data class PlaythroughUpdateRequest(
    val status: PlaythroughStatus?
)

@Serializable
data class PlaythroughCreateRequest(
    val gameId: Int? = null,
    val gameName: String? = null,
    val status: PlaythroughStatus
)

fun Playthrough.toResponse() = PlaythroughResponse(
    id = id.value,
    gameId = game_id.value,
    status = status
)
