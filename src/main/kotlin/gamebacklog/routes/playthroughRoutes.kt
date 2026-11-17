package gamebacklog.routes

import gamebacklog.model.PlaythroughCreateRequest
import gamebacklog.model.PlaythroughUpdateRequest
import gamebacklog.services.PlaythroughService
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Route.playthroughRoutes (
    playthroughService: PlaythroughService
) {
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

        route("/{playthroughId}") {
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
