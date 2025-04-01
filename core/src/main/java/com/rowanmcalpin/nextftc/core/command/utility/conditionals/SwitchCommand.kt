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

package com.rowanmcalpin.nextftc.core.command.utility.conditionals

import android.widget.Switch
import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.core.command.CommandManager
import com.rowanmcalpin.nextftc.core.command.utility.NullCommand

/**
 * This behaves like the command-form of a switch statement. You provide it with a value to
 * reference, and a list of options of outcomes. It is blocking, meaning `isDone` will not return
 * `true` until the scheduled command(s) have completed running.
 * @param value the value to reference
 * @param outcomes all of the options for outcomes
 * @param default the command to schedule if none of the outcomes are fulfilled
 */
class SwitchCommand<T> @JvmOverloads constructor(
    private val value: () -> T,
    private val outcomes: Map<T, Command> = emptyMap(),
    private val default: Command = NullCommand()
) : Command() {

    private lateinit var selectedCommand: Command

    override val isDone: Boolean by selectedCommand::isDone

    override fun start() {
        selectedCommand = outcomes[value()] ?: default
        selectedCommand.start()
    }

    override fun update() = selectedCommand.update()

    override fun stop(interrupted: Boolean) = selectedCommand.stop(interrupted)

    fun withCase(case: T, command: Command) =
        SwitchCommand(value, outcomes + (case to command), default)

    fun withDefault(command: Command) = SwitchCommand(value, outcomes, command)
}
