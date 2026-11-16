package gamebacklog.routes

import gamebacklog.model.GameCreateRequest
import gamebacklog.model.GameUpdateRequest
import gamebacklog.model.PlaythroughCreateRequest
import gamebacklog.model.PlaythroughUpdateRequest
import gamebacklog.services.GameService
import gamebacklog.services.PlaythroughService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.response.respond
import io.ktor.server.request.receive
import io.ktor.server.routing.RoutingCall
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing

fun Application.configureRouting(gameService: GameService, playthroughService: PlaythroughService) {
    fun RoutingCall.getIntParameter(name: String): Int {
        return parameters[name]?.toIntOrNull() ?: throw BadRequestException("Invalid $name")
    }

    routing {
        swaggerUI (
            path = "swagger",
            swaggerFile = "openapi/documentation.yaml"
        )

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
        }

        route("/games/{gameId}") {
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

    routing {
        route("/playthroughs") {
            get {
                val playthroughs = playthroughService.getAllPlaythroughs()

                call.respond(HttpStatusCode.OK, playthroughs)
            }

            post {
                val request = call.receive<PlaythroughCreateRequest>()
                playthroughService.addPlaythrough(request)

                call.respond(HttpStatusCode.Created)
            }
        }

        route("/playthroughs/{playthroughId}") {
            get {
                val playthroughId = call.getIntParameter("playthroughId")
                val playthrough = playthroughService.getPlaythrough(playthroughId)

                call.respond(HttpStatusCode.OK, playthrough)
            }

            patch {
                val updatedPlaythrough = call.receive<PlaythroughUpdateRequest>()
                val playthroughId = call.getIntParameter("playthroughId")

                playthroughService.updatePlaythrough(updatedPlaythrough, playthroughId)

                call.respond(HttpStatusCode.NoContent)
            }

            delete {
                val playthroughId = call.getIntParameter("playthroughId")
                playthroughService.deletePlaythrough(playthroughId)

                call.respond(HttpStatusCode.NoContent)
            }
        }
    }
}