package gamebacklog.repositories

import gamebacklog.database.entities.Game
import gamebacklog.model.PlaythroughCreateRequest
import gamebacklog.model.PlaythroughResponse
import gamebacklog.model.PlaythroughUpdateRequest

interface PlaythroughRepositoryInterface {
    suspend fun getAllPlaythroughs(): List<PlaythroughResponse>
    suspend fun getPlaythrough(id: Int): PlaythroughResponse?
    suspend fun getPlaythroughByGameId(gameId: Int): PlaythroughResponse?
    suspend fun addPlaythrough(playthrough: PlaythroughCreateRequest, game: Game)
    suspend fun updatePlaythrough(playthrough: PlaythroughUpdateRequest, id: Int)
    suspend fun deletePlaythrough(id: Int)
}