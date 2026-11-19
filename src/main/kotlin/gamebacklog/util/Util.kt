package gamebacklog.util

inline fun <reified E: Enum<E>> String?.toEnumOrNull(): E? {
    return try {
        this?.let {
            enumValueOf<E>(it.uppercase())
        }
    } catch (e: IllegalArgumentException) {
        null
    }
}