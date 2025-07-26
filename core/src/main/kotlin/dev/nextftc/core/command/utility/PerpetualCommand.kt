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

package dev.nextftc.core.command.utility

import dev.nextftc.core.command.Command

/**
 * This command executes indefinitely until stopped due to a conflict of requirements.
 * It uses the [start], [update], and [stop] functions of the passed command.
 *
 * @param command the command to execute
 */
class PerpetualCommand(val command: Command) : Command() {

    override val isDone: Boolean = false

    init {
        setRequirements(command.requirements)
    }

    override fun start() = command.start()

    override fun update() = command.update()

    override fun stop(interrupted: Boolean) = command.stop(interrupted)
}