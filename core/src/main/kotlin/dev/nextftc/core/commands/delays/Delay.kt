/*
 * NextFTC: a user-friendly control library for FIRST Tech Challenge
 *     Copyright (C) 2025 Rowan McAlpin
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package dev.nextftc.core.commands.delays

import dev.nextftc.core.commands.Command
import dev.nextftc.core.commands.groups.ParallelGroup
import dev.nextftc.core.units.JDuration
import kotlin.time.ComparableTimeMark
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit
import kotlin.time.TimeSource.Monotonic.markNow
import kotlin.time.toKotlinDuration

/**
 * A [Command] that does nothing except wait until a certain amount of time has passed. Like all
 * delays, if placed directly in a [ParallelGroup], it will accomplish nothing except slowing loop
 * times and taking up memory.
 * @param time the desired duration of this command
 */
class Delay(
    private val time: Duration
) : Command() {
    init {
        named("Delay(${time.toDouble(DurationUnit.SECONDS)}s)")
    }

    /**
     * @param time the desired duration of this command, in seconds
     */
    constructor(time: Double) : this(time.seconds)

    constructor(time: JDuration) : this(time.toKotlinDuration())

    private lateinit var startTime: ComparableTimeMark

    override val isDone: Boolean
        get() = markNow() - startTime >= time

    override fun start() {
        startTime = markNow()
    }
}