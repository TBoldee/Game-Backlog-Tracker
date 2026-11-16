package gamebacklog.repositories

import gamebacklog.model.GameCreateRequest
import gamebacklog.model.GameResponse
import gamebacklog.model.GameUpdateRequest

interface GameRepositoryInterface {
    suspend fun getAllGames(): List<GameResponse>
    suspend fun getGame(id: Int): GameResponse?
    suspend fun getGameByName(name: String): GameResponse?
    suspend fun addGame(game: GameCreateRequest)
    suspend fun updateGame(game: GameUpdateRequest, id: Int)
    suspend fun deleteGame(id: Int)
}