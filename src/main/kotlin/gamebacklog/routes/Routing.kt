package gamebacklog.routes

import gamebacklog.services.GameService
import gamebacklog.services.PlaythroughService
import io.ktor.server.application.Application
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.routing.RoutingCall
import io.ktor.server.routing.routing

fun Application.configureRouting(gameService: GameService, playthroughService: PlaythroughService) {
    routing {
        swaggerUI (
            path = "swagger",
            swaggerFile = "openapi/documentation.yaml"
        )

        gameRoutes(gameService)
        playthroughRoutes(playthroughService)
    }
}

fun RoutingCall.getIntParameter(name: String): Int {
    return parameters[name]?.toIntOrNull() ?: throw BadRequestException("Invalid $name")
}