package gamebacklog.services

import gamebacklog.model.DuplicatePlaythroughException
import gamebacklog.model.PlaythroughCreateRequest
import gamebacklog.model.PlaythroughResponse
import gamebacklog.model.PlaythroughStatus
import gamebacklog.model.PlaythroughUpdateRequest
import gamebacklog.repositories.GameRepository
import gamebacklog.repositories.PlaythroughRepository
import io.ktor.server.plugins.NotFoundException

class PlaythroughService (
    private val playthroughRepository: PlaythroughRepository,
    private val gameRepository: GameRepository,
) {
    suspend fun getAllPlaythroughs(): List<PlaythroughResponse> {
        return playthroughRepository.getAllPlaythroughs()
    }

    suspend fun getPlaythrough(id: Int): PlaythroughResponse {
        return playthroughRepository.getPlaythrough(id) ?: throw NotFoundException("Playthrough not found")
    }

    suspend fun getPlaythroughsByGameId(gameId: Int): List<PlaythroughResponse> {
        return playthroughRepository.getPlaythroughsByGameId(gameId)
    }

    suspend fun addPlaythrough(playthrough: PlaythroughCreateRequest) {
        require((playthrough.gameId != null) xor (playthrough.gameName != null)) {"Provide gameId or gameName, but not both"}

        val referencedGame = when {
            playthrough.gameId != null -> gameRepository.getGame(playthrough.gameId)

            playthrough.gameName != null -> gameRepository.getGameByName(playthrough.gameName)

            else -> null
        } ?: throw NotFoundException("Game not found")

        val existingPlaythroughs = playthroughRepository.getPlaythroughsByGameId(referencedGame.id)
        if (existingPlaythroughs.any { it.status == PlaythroughStatus.IN_PROGRESS }) {
            throw DuplicatePlaythroughException("Playthrough already in progress. Finish it, or set it to aborted to start another")
        }

        playthroughRepository.addPlaythrough(playthrough, referencedGame)
    }

    suspend fun updatePlaythrough(playthrough: PlaythroughUpdateRequest, id: Int) {
        requirePlaythroughExists(id)

        playthroughRepository.updatePlaythrough(playthrough, id)
    }

    suspend fun deletePlaythrough(id: Int) {
        requirePlaythroughExists(id)

        playthroughRepository.deletePlaythrough(id)
    }

    private suspend fun requirePlaythroughExists(id: Int) {
        if (playthroughRepository.getPlaythrough(id) == null) throw NotFoundException("Playthrough not found")
    }
}