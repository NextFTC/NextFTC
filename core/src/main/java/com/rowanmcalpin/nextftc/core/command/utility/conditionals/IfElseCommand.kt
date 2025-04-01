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

import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.core.command.CommandManager
import com.rowanmcalpin.nextftc.core.command.utility.NullCommand

/**
 * This command behaves as an `if` statement, and schedules commands based on the result of the if
 * statement. It is blocking, meaning `isDone` will not return `true` until the scheduled command
 * has completed running.
 * @param condition the condition to reference
 * @param trueCommand the command to schedule if the reference is true
 * @param falseCommand the command to schedule if the reference is false
 */
class IfElseCommand @JvmOverloads constructor(
    private val condition: () -> Boolean,
    private val trueCommand: Command,
    private val falseCommand: Command = NullCommand()
) : Command() {

    private lateinit var selectedCommand: Command

    override val isDone: Boolean by selectedCommand::isDone

    override fun start() {
        selectedCommand = if (condition()) trueCommand else falseCommand
        selectedCommand.start()
    }

    override fun update() = selectedCommand.update()

    override fun stop(interrupted: Boolean) = selectedCommand.stop(interrupted)
}