package gamebacklog.model

import kotlinx.serialization.Serializable

@Serializable
data class GenericStatisticsResponse(
    val totalPlaythroughs: Int,
    val completedPlaythroughs: Int,
    val totalPlaytime: Int,
    val averagePlaytime: Double,
    val genreDistribution: Map<Genre, Int>
)