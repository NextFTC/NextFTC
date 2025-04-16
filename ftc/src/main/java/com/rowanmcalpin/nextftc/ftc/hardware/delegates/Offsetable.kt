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

package com.rowanmcalpin.nextftc.ftc.hardware.delegates

import java.util.function.Supplier
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class Offsetable(
    private val valueSupplier: Supplier<Double>
) : ReadWriteProperty<Any?, Double> {

    private var offset = 0.0
    private var center = 0.0
    var direction = 1
        set(value) {
            require(
                value in intArrayOf(
                    -1,
                    1
                )
            ) { "direction must be either 1 or -1 but was $value." }
            center = getValue()
            field = value
        }

    fun getValue(): Double = (offset + valueSupplier.get() - center) * direction + center

    override fun getValue(
        thisRef: Any?,
        property: KProperty<*>
    ): Double = getValue()

    override fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: Double
    ) {
        center = value
        offset = valueSupplier.get() - value
    }
}