package dev.nextftc.core.units

import kotlin.time.Duration

fun stringToDuration(string: String): Duration {
    return Duration.parse(string.replace("\\s".toRegex(), ""))
}