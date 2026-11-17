package gamebacklog.routes

import gamebacklog.model.GameCreateRequest
import gamebacklog.model.GameUpdateRequest
import gamebacklog.services.GameService
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Route.gameRoutes (
    gameService: GameService,
) {
    route("/games") {
        get {
            val games = gameService.getAllGames()

            call.respond(HttpStatusCode.OK, games)
        }

        post {
            val request = call.receive<GameCreateRequest>()
            gameService.addGame(request)

            call.respond(HttpStatusCode.Created)
        }

        route("/{gameId}") {
            get {
                val gameId = call.getIntParameter("gameId")
                val game = gameService.getGame(gameId)

                call.respond(HttpStatusCode.OK, game)
            }

            patch {
                val updatedGame = call.receive<GameUpdateRequest>()
                val gameId = call.getIntParameter("gameId")

                gameService.updateGame(updatedGame, gameId)

                call.respond(HttpStatusCode.NoContent)
            }

            delete {
                val gameId = call.getIntParameter("gameId")
                gameService.deleteGame(gameId)

                call.respond(HttpStatusCode.NoContent)
            }
        }
    }
}