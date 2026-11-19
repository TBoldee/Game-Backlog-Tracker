package gamebacklog.repositories

import gamebacklog.model.GameCreateRequest
import gamebacklog.model.GameResponse
import gamebacklog.model.GameSortEnum
import gamebacklog.model.GameUpdateRequest
import gamebacklog.model.SortOrderEnum

interface GameRepositoryInterface {
    suspend fun getAllGames(orderByColumn: GameSortEnum, order: SortOrderEnum): List<GameResponse>
    suspend fun getGame(id: Int): GameResponse?
    suspend fun getGameByName(name: String): GameResponse?
    suspend fun addGame(game: GameCreateRequest)
    suspend fun updateGame(game: GameUpdateRequest, id: Int)
    suspend fun deleteGame(id: Int)
}