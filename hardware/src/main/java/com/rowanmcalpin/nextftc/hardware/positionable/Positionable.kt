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

/**
 * An entity whose position target can be set directly
 *
 * @author BeepBot99
 */
interface Positionable {
    /**
     * The entity's target position
     */
    var position: Double

    infix fun to(position: Double) = PositionableState(this, position)
}

/**
 * A state a [Positionable] can be in
 * @param positionable The [Positionable] the state is for
 * @param position the state of [positionable]
 *
 * @author BeepBot99
 */
data class PositionableState(val positionable: Positionable, val position: Double)