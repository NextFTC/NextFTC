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

class ParallelDeadlineGroup(private val deadline: Command, vararg otherCommands: Command): CommandGroup(deadline, *otherCommands) {
    override var interruptible = true

    /**
     * Sets whether this command is [interruptible]. This functionality is a fluent API,
     * so you can use it inline with the Command Group declaration.
     * @param isInterruptible whether this group should be interruptible
     * @return this Command Group, with [interruptible] set to the value
     */
    fun setInterruptible(isInterruptible: Boolean) = apply { interruptible = isInterruptible }

    /**
     * This will return false until the deadline command is done.
     */
    override val isDone: Boolean
        get() = deadline.isDone

    override val subsystems: Set<Subsystem>
        get() = children.flatMap { it.subsystems }.toSet()

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