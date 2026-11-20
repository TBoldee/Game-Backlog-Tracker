package gamebacklog.model

import kotlinx.serialization.Serializable

@Serializable
enum class Genre {
    ACTION,
    ADVENTURE,
    RPG,
    JRPG,
    MMORPG,
    FPS,
    TPS,
    RTS,
    STRATEGY,
    SIMULATION,
    PUZZLE,
    PLATFORMER,
    HORROR,
    RACING,
    SPORTS,
    METROIDVANIA,
    SOULSLIKE,
    INCREMENTAL
}