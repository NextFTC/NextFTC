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

package com.rowanmcalpin.nextftc.core.command.utility.statemachine

import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.core.command.StateNotSetException

/**
 * A [Command] that advances through a list of commands
 * @throws StateNotSetException if the command returned by [advance] or [retreat] is called before a state has been added
 * @author BeepBot99
 */
class AdvancingCommand : StateMachineCommand<Int>() {
    private var states: Int = 0

    /**
     * Adds a new command to the advancing command
     * @param command the [Command] to add
     * @return the [AdvancingCommand] for use with a Fluent API
     */
    fun add(command: Command): AdvancingCommand = state(states++, command) as AdvancingCommand

    /**
     * Advances to the next command, looping back if it's on the last command
     * @return a [Command] that advances and ends instantly
     */
    fun advance(): Command {
        if (states == 0 || currentState == null) throw StateNotSetException()
        return setState { (currentState!! + 1) % states }
    }

    /**
     * Retreats to the previous command, looping back if it's on the first command
     * @return a [Command] that retreats and ends instantly
     */
    fun retreat(): Command {
        if (states == 0 || currentState == null) throw StateNotSetException()
        return setState { (currentState!! - 1 + states) % states }
    }
}