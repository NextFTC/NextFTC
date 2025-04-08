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

package com.rowanmcalpin.nextftc.hardware.driving

import com.rowanmcalpin.nextftc.core.units.Angle
import java.util.function.Supplier
import kotlin.math.cos
import kotlin.math.sin

data class HolonomicDrivePowers(val x: Double, val y: Double, val heading: Double) {

    fun rotate(mode: HolonomicMode) = mode.rotate(this)

    companion object {
        fun from(powers: DoubleArray): HolonomicDrivePowers {
            require(powers.size == 3) { "powers must have size 3 but size was ${powers.size}" }
            return HolonomicDrivePowers(powers[0], powers[1], powers[2])
        }
    }
}

sealed class HolonomicMode {
    abstract fun rotate(powers: HolonomicDrivePowers): HolonomicDrivePowers

    object RobotCentric : HolonomicMode() {
        override fun rotate(powers: HolonomicDrivePowers): HolonomicDrivePowers = powers
    }

    class FieldCentric(
        val headingSupplier: Supplier<Angle>
    ) : HolonomicMode() {
        override fun rotate(powers: HolonomicDrivePowers): HolonomicDrivePowers {
            val heading = headingSupplier.get().inRad
            val rotX = powers.x * cos(-heading) - powers.y * sin(heading)
            val rotY = powers.x * sin(-heading) + powers.y * cos(-heading)
            return powers.copy(x = rotX, y = rotY)
        }
    }
}
