package gamebacklog.repositories

import gamebacklog.database.entities.Game
import gamebacklog.database.entities.Playthrough
import gamebacklog.database.tables.Playthroughs
import gamebacklog.model.GameResponse
import gamebacklog.model.PlaythroughCreateRequest
import gamebacklog.model.PlaythroughResponse
import gamebacklog.model.PlaythroughUpdateRequest
import gamebacklog.model.toResponse
import gamebacklog.model.withTransaction
import io.ktor.server.plugins.NotFoundException
import org.jetbrains.exposed.v1.core.eq

class PlaythroughRepository : PlaythroughRepositoryInterface {
    override suspend fun getAllPlaythroughs(): List<PlaythroughResponse> {
        return withTransaction {
            Playthrough.all().map { it.toResponse() }
        }
    }

    override suspend fun getPlaythrough(id: Int): PlaythroughResponse? {
        return withTransaction {
            Playthrough.findById(id)?.toResponse()
        }
    }

    override suspend fun getPlaythroughByGameId(gameId: Int): PlaythroughResponse? {
        return withTransaction {
            Playthrough.find { Playthroughs.game_id eq gameId }.firstOrNull()?.toResponse()
        }
    }

    override suspend fun addPlaythrough(playthrough: PlaythroughCreateRequest, game: GameResponse) {
        return withTransaction {
            val gameEntity = Game.findById(game.id) ?: throw NotFoundException("Game not found")

            Playthrough.new {
                game_id = gameEntity.id
                status = playthrough.status
            }
        }
    }

    override suspend fun updatePlaythrough(playthrough: PlaythroughUpdateRequest, id: Int) {
        return withTransaction {
            Playthrough.findByIdAndUpdate(id) {
                if (playthrough.status != null) it.status = playthrough.status
            }
        }
    }

    override suspend fun deletePlaythrough(id: Int) {
        return withTransaction {
            Playthrough.findById(id)?.delete()
        }
    }
}