package gamebacklog

import gamebacklog.database.DatabaseFactory
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main(args: Array<String>) {
    DatabaseFactory.init()

    embeddedServer(
        Netty,
        port = 8080,
        module = Application::rootModule
    ).start(wait = true)
}