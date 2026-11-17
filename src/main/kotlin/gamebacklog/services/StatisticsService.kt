package gamebacklog.services

import gamebacklog.model.GenericStatisticsResponse
import gamebacklog.repositories.GameRepository
import gamebacklog.repositories.PlaythroughRepository

class StatisticsService (
    private val gameRepository: GameRepository,
    private val playthroughRepository: PlaythroughRepository,
) {
    suspend fun getGenericStatistics(): GenericStatisticsResponse {
        val playthroughCount = playthroughRepository.getAllPlaythroughs().size

        val donePlaythroughs = playthroughRepository.getAllDonePlaythroughs()
        val completedPlaythroughs = donePlaythroughs.size

        val allGames = gameRepository.getAllGames()
        val gamesById = allGames.associateBy { it.id }

        val totalPlaytime = donePlaythroughs.sumOf { playthrough ->
            gamesById[playthrough.gameId]?.gameTime ?:
                throw NoSuchElementException("Game is missing for playthrough id ${playthrough.id}")
        }

        val averagePlaytime = (totalPlaytime.toDouble() / completedPlaythroughs)

        val genreDistribution = allGames.groupingBy { it.genre }.eachCount()

        return GenericStatisticsResponse(
            totalPlaythroughs = playthroughCount,
            completedPlaythroughs = completedPlaythroughs,
            totalPlaytime = totalPlaytime,
            averagePlaytime = averagePlaytime,
            genreDistribution = genreDistribution,
        )
    }
}