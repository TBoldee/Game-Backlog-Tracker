package gamebacklog.repositories

import gamebacklog.database.entities.Game
import gamebacklog.database.tables.Games
import gamebacklog.model.GameCreateRequest
import gamebacklog.model.GameResponse
import gamebacklog.model.GameSortEnum
import gamebacklog.model.GameUpdateRequest
import gamebacklog.model.SortOrderEnum
import gamebacklog.model.toResponse
import gamebacklog.model.withTransaction
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.eq

class GameRepository : GameRepositoryInterface {
    override suspend fun getAllGames(
        orderByColumn: GameSortEnum,
        order: SortOrderEnum,
    ): List<GameResponse> {
        return withTransaction {
            val column = when (orderByColumn) {
                GameSortEnum.ID -> Games.id
                GameSortEnum.NAME -> Games.name
                GameSortEnum.GENRE -> Games.genre
                GameSortEnum.GAMETIME -> Games.gametime
            }

            val sortOrder = when (order) {
                SortOrderEnum.ASC -> SortOrder.ASC
                SortOrderEnum.DESC -> SortOrder.DESC
            }

            Game.all().orderBy(column to sortOrder).map { it.toResponse() }
        }
    }

    override suspend fun getGame(id: Int): GameResponse? {
        return withTransaction {
            Game.findById(id)?.toResponse()
        }
    }

    override suspend fun getGameByName(name: String): GameResponse? {
        return withTransaction {
            Game.find { Games.name eq name}.firstOrNull()?.toResponse()
        }
    }

    override suspend fun addGame(game: GameCreateRequest) {
        return withTransaction {
            Game.new {
                this.name = game.name
                this.genre = game.genre
                this.platform = game.platform
                this.gametime = game.gameTime
            }
        }
    }

    override suspend fun updateGame(game: GameUpdateRequest, id: Int) {
        return withTransaction {
            Game.findByIdAndUpdate(id) {
                if (game.name != null) it.name = game.name
                if (game.genre != null) it.genre = game.genre
                if (game.platform != null) it.platform = game.platform
                if (game.gameTime != null) it.gametime = game.gameTime
            }
        }
    }

    override suspend fun deleteGame(id: Int) {
        return withTransaction {
            Game.findById(id)?.delete()
        }
    }
}