package gamebacklog.repositories

import gamebacklog.model.GameResponse
import gamebacklog.model.PlaythroughCreateRequest
import gamebacklog.model.PlaythroughResponse
import gamebacklog.model.PlaythroughUpdateRequest

interface PlaythroughRepositoryInterface {
    suspend fun getAllPlaythroughs(): List<PlaythroughResponse>
    suspend fun getPlaythrough(id: Int): PlaythroughResponse?
    suspend fun getPlaythroughsByGameId(gameId: Int): List<PlaythroughResponse>
    suspend fun addPlaythrough(playthrough: PlaythroughCreateRequest, game: GameResponse)
    suspend fun updatePlaythrough(playthrough: PlaythroughUpdateRequest, id: Int)
    suspend fun deletePlaythrough(id: Int)
    suspend fun getAllDonePlaythroughs(): List<PlaythroughResponse>
}