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

/**
 * A [CommandGroup] that runs all of its children simultaneously.
 */
open class ParallelGroup(vararg commands: Command) : CommandGroup(*commands) {
    init {
        named("ParallelGroup(${children.joinToString { it.name }})")
    }

    /**
     * This will return false until all of its children are done
     */
    override val isDone: Boolean
        get() = children.all { it.isDone }

    init {
        val noConflicts = commands
            .flatMap { it.requirements }
            .groupBy { it }
            .none { it.value.size > 1 }
        check(noConflicts) { "Two or more commands passed to ParallelGroup share one or more requirements" }
    }

    override fun start() {
        children.forEach {
            it.start()
        }
    }

    override fun update() {
        children.forEachIndexed { index, command ->
            command.update()
            if (!command.isDone) return

            command.stop(false)
            children.removeAt(index)
        }
    }

    override fun stop(interrupted: Boolean) {
        children.forEach {
            it.stop(interrupted)
        }

        super.stop(interrupted)
    }

    override fun and(vararg commands: Command) =
        ParallelGroup(*children.toTypedArray(), *commands)
}