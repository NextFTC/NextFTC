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

package dev.nextftc.hardware.delegates

import com.qualcomm.hardware.lynx.Supplier
import java.lang.System.currentTimeMillis
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class Velocity(private val positionSupplier: Supplier<Double>) : ReadOnlyProperty<Any?, Double> {
    private var previousTime: Long? = null
    private var previousPosition: Double? = null

    override fun getValue(
        thisRef: Any?,
        property: KProperty<*>
    ): Double {
        if (previousTime == null || previousPosition == null) {
            previousTime = currentTimeMillis()
            previousPosition = positionSupplier.get()
            return 0.0
        }
        val currentTime = currentTimeMillis()
        val deltaTime = (currentTime - previousTime!!) / 1e3
        previousTime = currentTime

        val currentPosition = positionSupplier.get()
        val deltaPosition = currentPosition - previousPosition!!
        previousPosition = currentPosition

        return deltaPosition / deltaTime
    }
}