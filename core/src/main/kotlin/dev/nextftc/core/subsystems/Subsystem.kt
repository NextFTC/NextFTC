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

package dev.nextftc.core.subsystems

import dev.nextftc.core.commands.Command
import dev.nextftc.core.commands.utility.NullCommand
import kotlin.time.Duration

/**
 * A [Subsystem] represents a real-world system (such as a lift, intake, or claw) that cannot be
 * controlled by multiple commands simultaneously.
 */
@JvmDefaultWithoutCompatibility
interface Subsystem {
    val defaultCommand: Command
        get() = NullCommand()

    /**
     * Initializes this subsystem. This function is perfect for calling `hardwareMap.get` or
     * otherwise initializing hardware devices.
     */
    fun initialize() {}

    /**
     * This function is called every update.
     */
    fun periodic() {}

    val subsystems: Set<Subsystem>
        get() = setOf(this)

    /**
     * Creates a command that runs [action] once and requires this subsystem.
     */
    fun instant(name: String, action: Runnable) =
        dev.nextftc.core.commands.instant(name, action).requires(this)

    /**
     * Creates a command that runs [action] once and requires this subsystem.
     */
    fun instant(action: Runnable) =
        dev.nextftc.core.commands.instant(action).requires(this)

    /**
     * Creates a command that runs [action] repeatedly and requires this subsystem.
     */
    fun run(name: String, action: Runnable) =
        dev.nextftc.core.commands.run(name, action).requires(this)

    /**
     * Creates a command that runs [action] repeatedly and requires this subsystem.
     */
    fun run(action: Runnable) =
        dev.nextftc.core.commands.run(action).requires(this)
}
