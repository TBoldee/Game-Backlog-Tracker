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

        route("/games") {
            route("/without-playthroughs") {
                get {
                    val stats = statisticsService.getGamesWithoutPlaythroughs()
                    call.respond(HttpStatusCode.OK, stats)
                }
            }

            route("/with-playthroughs-in-progress") {
                get {
                    val stats = statisticsService.getGamesWithPlaythroughsInProgress()
                    call.respond(HttpStatusCode.OK, stats)
                }
            }

            route("/beaten") {
                get {
                    val stats = statisticsService.getGamesBeaten()
                    call.respond(HttpStatusCode.OK, stats)
                }
            }

            route("/top-three-by-playthroughs") {
                get {
                    val stats = statisticsService.getTopThreeGamesByPlaythroughCount()
                    call.respond(HttpStatusCode.OK, stats)
                }
            }
        }

        route("/playthroughs") {
            route("/breakdown") {
                get {
                    val stats = statisticsService.getPlaythroughBreakdownByGenres()
                    call.respond(HttpStatusCode.OK, stats)
                }
            }
        }
    }
}