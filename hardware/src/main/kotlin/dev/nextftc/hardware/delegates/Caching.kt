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

import kotlin.math.abs
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class Caching(
    private val cacheTolerance: Double,
    private val whenSet: (Double?) -> Unit
) : ReadWriteProperty<Any?, Double> {

    private var cachedValue = 0.0

    override fun getValue(
        thisRef: Any?,
        property: KProperty<*>
    ): Double = cachedValue

    override fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: Double
    ) {
        if (abs(cachedValue - value) > cacheTolerance) {
            cachedValue = value
            whenSet(value)
        } else {
            whenSet(null)
        }
    }
}