package gamebacklog.model

import kotlinx.serialization.Serializable

@Serializable
data class GenericStatisticsResponse(
    val totalPlaythroughs: Int,
    val completedPlaythroughs: Int,
    val totalPlaytime: Int,
    val averagePlaytime: Double,
    val gamesPerGenre: List<GenreCountResponse>
)

@Serializable
data class PlaythroughStatusByGenreResponse(
    val genre: Genre,
    val done: Int,
    val inProgress: Int,
    val aborted: Int,
    val total: Int,
)

@Serializable
data class GenreCountResponse(
    val genre: String,
    val count: Int
)

@Serializable
data class GameWithPlaythroughCountResponse(
    override val id: Int,
    override val name: String,
    override val platform: Platform,
    override val genre: Genre,
    override val gameTime: Int,
    val playthroughCount: Int,
) : GameInfo