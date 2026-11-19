package gamebacklog.services

import gamebacklog.model.DuplicateGameException
import gamebacklog.model.GameCreateRequest
import gamebacklog.model.GameResponse
import gamebacklog.model.GameSortEnum
import gamebacklog.model.GameUpdateRequest
import gamebacklog.model.SortOrderEnum
import gamebacklog.repositories.GameRepositoryInterface
import io.ktor.server.plugins.NotFoundException

class GameService(
    private val gameRepository: GameRepositoryInterface,
) {
    suspend fun getAllGames(orderByColumn: GameSortEnum, order: SortOrderEnum): List<GameResponse> {
        return gameRepository.getAllGames(orderByColumn, order)
    }

    suspend fun getGame(id: Int): GameResponse {
        val game = gameRepository.getGame(id) ?: throw NotFoundException("Game not found")

        return game
    }

    suspend fun getGameByName(name: String): GameResponse {
        require(name.isNotBlank()) { "Game name cannot be blank" }
        val game = gameRepository.getGameByName(name) ?: throw NotFoundException("Game not found")

        return game
    }

    suspend fun addGame(game: GameCreateRequest) {
        require(game.name.isNotBlank()) { "Game name cannot be blank" }
        require(game.gameTime > 0) { "Game time must be greater than 0" }
        if (gameRepository.getGameByName(game.name) != null) throw DuplicateGameException("Game already exists")

        gameRepository.addGame(game)
    }

    suspend fun updateGame(game: GameUpdateRequest, id: Int) {
        require(game.name == null || game.name.isNotBlank()) { "Game name cannot be blank" }
        require(game.gameTime == null || game.gameTime > 0) { "Game time must be greater than 0" }
        if (gameRepository.getGame(id) == null) throw NotFoundException("Game not found")

        gameRepository.updateGame(game, id)
    }

    suspend fun deleteGame(id: Int) {
        if (gameRepository.getGame(id) == null) throw NotFoundException("Game not found")

        gameRepository.deleteGame(id)
    }
}