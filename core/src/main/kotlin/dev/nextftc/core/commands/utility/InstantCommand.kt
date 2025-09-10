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

package dev.nextftc.core.commands.utility

/**
 * This is a LambdaCommand that sets isDone to true instantly.
 * As such, there is no update or stop lambda
 * (since the command finishes instantly).
 * All code should be put in the lambda argument.
 *
 * @param name the name of the command
 * @param lambda the lambda to execute
 */
open class InstantCommand(name: String, lambda: Runnable) : LambdaCommand(name) {
    /**
     * Creates an InstantCommand with the given lambda.
     * @param lambda the lambda to execute
     */
    constructor(lambda: Runnable) : this("InstantCommand", lambda)

    init {
        super.setStart(lambda)
        super.setIsDone { true }
    }
}