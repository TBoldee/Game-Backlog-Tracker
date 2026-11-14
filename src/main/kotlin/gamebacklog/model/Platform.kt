package gamebacklog.model

import kotlinx.serialization.Serializable

@Serializable
enum class Platform {
    PC,
    XBOX,
    PLAYSTATION,
    NINTENDO_SWITCH,
    MOBILE
}