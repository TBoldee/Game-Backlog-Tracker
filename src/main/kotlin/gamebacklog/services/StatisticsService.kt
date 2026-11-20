package gamebacklog.services

import gamebacklog.model.GameResponse
import gamebacklog.model.GameWithPlaythroughCountResponse
import gamebacklog.model.GenericStatisticsResponse
import gamebacklog.model.GenreCountResponse
import gamebacklog.model.PlaythroughStatus
import gamebacklog.model.PlaythroughStatusByGenreResponse
import gamebacklog.repositories.GameRepository
import gamebacklog.repositories.PlaythroughRepository

class StatisticsService (
    private val gameRepository: GameRepository,
    private val playthroughRepository: PlaythroughRepository,
) {
    suspend fun getGenericStatistics(): GenericStatisticsResponse {
        val playthroughCount = playthroughRepository.getAllPlaythroughs().size

        val donePlaythroughs = playthroughRepository.getAllPlaythroughsByStatus(PlaythroughStatus.DONE)
        val completedPlaythroughs = donePlaythroughs.size

        val allGames = gameRepository.getAllGames()
        val gamesById = allGames.associateBy { it.id }

        val totalPlaytime = donePlaythroughs.sumOf { playthrough ->
            gamesById[playthrough.gameId]?.gameTime ?:
                throw NoSuchElementException("Game is missing for playthrough id ${playthrough.id}")
        }

        val averagePlaytime = (totalPlaytime.toDouble() / completedPlaythroughs)

        val genreDistribution = allGames.groupingBy { it.genre }
            .eachCount()
            .map { (genre, count) ->
                GenreCountResponse(
                    genre = genre.name.lowercase().replaceFirstChar(Char::uppercase),
                    count = count,
                )
            }
            .toList()
            .sortedByDescending { (_, count) -> count }

        return GenericStatisticsResponse(
            totalPlaythroughs = playthroughCount,
            completedPlaythroughs = completedPlaythroughs,
            totalPlaytime = totalPlaytime,
            averagePlaytime = averagePlaytime,
            gamesPerGenre = genreDistribution,
        )
    }

    suspend fun getGamesWithoutPlaythroughs(): List<GameResponse> {
        val allPlaythroughs = playthroughRepository.getAllPlaythroughs()
        val playthroughsByGameId = allPlaythroughs.groupBy { it.gameId }

        val allGames = gameRepository.getAllGames()

        return allGames.filter { !playthroughsByGameId.containsKey(it.id) }
    }

    suspend fun getGamesWithPlaythroughsInProgress(): List<GameResponse> {
        val allPlaythroughsInProgress = playthroughRepository.getAllPlaythroughsByStatus(PlaythroughStatus.IN_PROGRESS)
        val playthroughsByGameId = allPlaythroughsInProgress.groupBy { it.gameId }

        val allGames = gameRepository.getAllGames()

        return allGames.filter { playthroughsByGameId.containsKey(it.id) }
    }

    suspend fun getGamesBeaten(): List<GameResponse> {
        val allPlaythroughsDone = playthroughRepository.getAllPlaythroughsByStatus(PlaythroughStatus.DONE)
        val playthroughsByGameId = allPlaythroughsDone.groupBy { it.gameId }

        val allGames = gameRepository.getAllGames()

        return allGames.filter { playthroughsByGameId.containsKey(it.id) }
    }

    suspend fun getPlaythroughBreakdownByGenres(): List<PlaythroughStatusByGenreResponse> {
        val allGames = gameRepository.getAllGames()
        val allPlaythroughs = playthroughRepository.getAllPlaythroughs()

        val gamesById = allGames.associateBy { it.id }

        val playthroughsByGenre = allPlaythroughs.groupBy {
            gamesById.getValue(it.gameId).genre
        }

        return playthroughsByGenre.entries.map { (genre, playthroughs) ->
            val playthroughsByStatus = playthroughs.groupBy { it.status }

            PlaythroughStatusByGenreResponse(
                genre = genre,
                done = playthroughsByStatus[PlaythroughStatus.DONE]?.count() ?: 0,
                inProgress = playthroughsByStatus[PlaythroughStatus.IN_PROGRESS]?.count() ?: 0,
                aborted = playthroughsByStatus[PlaythroughStatus.ABORTED]?.count() ?: 0,
                total = playthroughs.count()
            )
        }
    }

    suspend fun getTopThreeGamesByPlaythroughCount(): List<GameWithPlaythroughCountResponse> {
        val allPlaythroughs = playthroughRepository.getAllPlaythroughs()
        val allGames = gameRepository.getAllGames()
        val gamesById = allGames.associateBy { it.id }

        return allPlaythroughs.groupingBy { it.gameId }
            .eachCount()
            .toList()
            .sortedByDescending { (_, count) -> count }
            .take(3)
            .map { (gameid, count) ->
                val game = gamesById.getValue(gameid)

                GameWithPlaythroughCountResponse(
                    id = gameid,
                    name = game.name,
                    platform = game.platform,
                    genre = game.genre,
                    gameTime = game.gameTime,
                    playthroughCount = count,
                )
            }
    }
}