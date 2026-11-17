package gamebacklog

import gamebacklog.model.DuplicateGameException
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.NotFoundException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond

fun Application.installStatusPages() {
    install(StatusPages) {
        exception<DuplicateGameException> { call, cause ->
            call.respond(
                HttpStatusCode.Conflict,
                cause.message ?: "Game already exists"
            )
        }

        exception<BadRequestException> { call, cause ->
            call.respond(
                HttpStatusCode.BadRequest,
                cause.message ?: "Bad Request"
            )
        }

        exception<NotFoundException> { call, cause ->
            call.respond(
                HttpStatusCode.NotFound,
                cause.message ?: "Not found"
            )
        }

        exception<IllegalArgumentException> { call, cause ->
            call.respond(
                HttpStatusCode.BadRequest,
                cause.message ?: "Bad Request"
            )
        }

        exception<NoSuchElementException> { call, cause ->
            call.respond(
                HttpStatusCode.NotFound,
                cause.message ?: "Something went wrong finding the game for playthrough"
            )
        }
    }
}