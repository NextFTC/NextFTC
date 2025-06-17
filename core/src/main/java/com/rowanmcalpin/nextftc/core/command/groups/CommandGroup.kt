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

package com.rowanmcalpin.nextftc.core.command.groups

import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.core.command.EmptyGroupException

/**
 * A command that schedules other commands at certain times. Inherits all subsystems of its children.
 */
abstract class CommandGroup(val commands: List<Command>) : Command() {
    constructor(vararg commands: Command) : this(commands.toList())
    constructor(commands: Collection<Command>) : this(commands.toList())

    /**
     * The collection of all commands within this group.
     */
    val children: ArrayDeque<Command> = ArrayDeque(commands)

    init {
        setSubsystems(commands.flatMap { it.subsystems }.toSet())
        if (commands.isEmpty()) throw EmptyGroupException()
    }

    override fun stop(interrupted: Boolean) {
        children.clear()
        children.addAll(commands)
    }
}