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

package com.rowanmcalpin.nextftc.hardware.positionable

import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand
import com.rowanmcalpin.nextftc.hardware.positionable.SetPositions

/**
 * A [Command] that moves one or more [Positionable]s to one or more positions
 * @param states the states to move to. A state includes a [Positionable] and a position.
 *
 * @author BeepBot99
 */
open class SetPositions(vararg val states: Pair<Positionable, Double>) : InstantCommand({
    states.forEach { it.first.position = it.second }
})

class SetPosition(positionable: Positionable, position: Double) : SetPositions(positionable to position)