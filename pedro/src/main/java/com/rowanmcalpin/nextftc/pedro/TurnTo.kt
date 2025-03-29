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

package com.rowanmcalpin.nextftc.pedro

import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.core.units.Angle
import com.rowanmcalpin.nextftc.core.units.abs
import com.rowanmcalpin.nextftc.core.units.deg

/**
 * A [Command] that turns the robot to a specified heading
 * @param targetHeading the heading to turn to
 * @param tolerance the maximum error between the current heading and the target heading required to finish
 * @throws FollowerNotInitializedException if the follower is not set
 */
class TurnTo @JvmOverloads constructor(private val targetHeading: Angle, private val tolerance: Angle = 5.deg): Command() {
    override val isDone: Boolean
        get() = abs(targetHeading.normalized - Angle.fromRad(PedroData.follower!!.totalHeading).normalized) <= tolerance.abs

    override fun start() {
        if (PedroData.follower == null) throw FollowerNotInitializedException()

        PedroData.follower!!.turnTo(targetHeading.inRad)
    }
}