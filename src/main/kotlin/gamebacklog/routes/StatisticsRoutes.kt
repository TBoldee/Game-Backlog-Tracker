package gamebacklog.routes

import gamebacklog.services.StatisticsService
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route

fun Route.statisticsRoutes (
    statisticsService: StatisticsService
) {
    route("/statistics") {
        get {
            val stats = statisticsService.getGenericStatistics()
            call.respond(HttpStatusCode.OK, stats)
        }
    }
}