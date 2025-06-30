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

import com.qualcomm.robotcore.hardware.AnalogInput
import java.util.function.Supplier
import kotlin.math.PI
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class AnalogFeedback(private val voltageSupplier: Supplier<Double>) :
    ReadOnlyProperty<Any?, Double> {

    override fun getValue(
        thisRef: Any?,
        property: KProperty<*>
    ): Double = voltageSupplier.get() / 3.3 * 2 * PI
}