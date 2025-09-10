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

package dev.nextftc.core.commands.conditionals

import dev.nextftc.core.commands.Command
import dev.nextftc.core.commands.utility.NullCommand

class SwitchCommandBuilder<T> internal constructor(val value: () -> T) {
    var default: Command = NullCommand()
    private val outcomes: MutableMap<T, Command> = mutableMapOf()

    fun case(case: T, command: Command) {
        outcomes += case to command
    }

    internal fun build() = SwitchCommand(
        value,
        outcomes,
        default
    )
}

fun <T> switchCommand(value: () -> T, init: SwitchCommandBuilder<T>.() -> Unit): SwitchCommand<T> {
    val builder = SwitchCommandBuilder(value)
    builder.init()
    return builder.build()
}