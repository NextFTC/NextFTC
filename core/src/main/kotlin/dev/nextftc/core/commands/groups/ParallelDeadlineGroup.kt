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

package dev.nextftc.core.commands.groups

import dev.nextftc.core.commands.Command

class ParallelDeadlineGroup(private val deadline: Command, vararg otherCommands: Command) :
    ParallelGroup(deadline, *otherCommands) {

    init {
        named("ParallelDeadlineGroup(${deadline.name} | ${children.joinToString { it.name }})")
    }

    /**
     * This will return false until the deadline command is done.
     */
    override val isDone: Boolean by deadline::isDone
}