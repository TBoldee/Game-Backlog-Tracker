package gamebacklog.routes

import gamebacklog.model.GameCreateRequest
import gamebacklog.model.GameResponse
import gamebacklog.model.GameUpdateRequest
import gamebacklog.services.GameService
import gamebacklog.services.PlaythroughService
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.NotFoundException
import io.ktor.server.response.respond
import io.ktor.server.request.receive
import io.ktor.server.response.respondText
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
        route("/games") {
            get {
                val games = gameService.getAllGames()
                call.respond(HttpStatusCode.OK, games)
            }

            post {
                try {
                    val request = call.receive<GameCreateRequest>()
                    gameService.addGame(request)

                    call.respond(HttpStatusCode.Created)
                } catch (e: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "",)
                    return@post
                } catch (e: BadRequestException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "",)
                    return@post
                } catch (e: Exception) {
                    println(e::class.qualifiedName)
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "")
                    return@post
                }
            }
        }

        route("/games/{gameId}") {
            get {
                try {
                    val gameId = call.getIntParameter("gameId")

                    val game = gameService.getGame(gameId)

                    call.respond(HttpStatusCode.OK, game)
                } catch (e: BadRequestException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "")
                    return@get
                } catch (e: NotFoundException) {
                    call.respond(HttpStatusCode.NotFound, e.message ?: "")
                    return@get
                }
            }

            patch {
                try {
                    val updatedGame = call.receive<GameUpdateRequest>()

                    val gameId = call.getIntParameter("gameId")

                    gameService.updateGame(updatedGame, gameId)

                    call.respond(HttpStatusCode.NoContent)
                } catch (e: BadRequestException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "")
                    return@patch
                } catch (e: NotFoundException) {
                    call.respond(HttpStatusCode.NotFound, e.message ?: "")
                    return@patch
                }
            }

            delete {
                try {
                    val gameId = call.getIntParameter("gameId")

                    gameService.deleteGame(gameId)

                    call.respond(HttpStatusCode.NoContent)
                } catch (e: BadRequestException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "")
                    return@delete
                } catch (e: NotFoundException) {
                    call.respond(HttpStatusCode.NotFound, e.message ?: "")
                    return@delete
                }
            }
        }
    }
}