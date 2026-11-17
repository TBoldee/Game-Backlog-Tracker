package gamebacklog

import gamebacklog.routes.configureRouting
import gamebacklog.configureSerialization
import gamebacklog.repositories.GameRepository
import gamebacklog.repositories.PlaythroughRepository
import gamebacklog.services.GameService
import gamebacklog.services.PlaythroughService
import gamebacklog.services.StatisticsService
import io.ktor.server.application.Application

fun Application.rootModule() {
    val gameRepository = GameRepository()
    val gameService = GameService(gameRepository)

    val playthroughRepository = PlaythroughRepository()
    val playthroughService = PlaythroughService(playthroughRepository, gameRepository)

    val statisticsService = StatisticsService(gameRepository, playthroughRepository)

    configureSerialization()
    installStatusPages()
    configureRouting(gameService, playthroughService, statisticsService)
}