package dev.nextftc.core.units

import kotlin.time.Duration

/**
 * Parses a provided string into a Duration. Differs from `Duration#parse` because it ignores spaces.
 */
fun parseDuration(string: String): Duration {
    return Duration.parse(string.replace("\\s".toRegex(), ""))
}
