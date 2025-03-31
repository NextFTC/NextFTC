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

import com.rowanmcalpin.nextftc.core.Subsystem
import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.core.command.CommandManager

/**
 * A [CommandGroup] that runs all of its children simultaneously until one of its children is done,
 * at which point it stops all of its children.
 */
class ParallelRaceGroup(vararg commands: Command): CommandGroup(*commands) {

    /**
     * This will return false until one of its children is done
     */
    override val isDone: Boolean
        get() = children.any { it.isDone }

    init {
        setSubsystems(children.flatMap { it.subsystems }.toSet())
    }

    /**
     * In a Parallel Group, we can just straight away add all of the commands to the CommandManager,
     * which can take care of the rest.
     */
    override fun start() {
        super.start()
        children.forEach {
            CommandManager.scheduleCommand(it)
        }
    }

    override fun stop(interrupted: Boolean) {
        children.forEach {
            CommandManager.cancelCommand(it)
        }
    }
}