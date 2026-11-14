package gamebacklog

import io.ktor.server.application.Application
import gamebacklog.routes.configureRouting

fun Application.rootModule() {
    configureRouting()
}