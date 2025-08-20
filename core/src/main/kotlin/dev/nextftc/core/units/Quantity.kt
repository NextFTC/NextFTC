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

package dev.nextftc.core.units

import kotlin.math.abs

/**
 * Represents a physical quantity
 * @param T the type of the quantity
 * @author BeepBot99
 */
abstract class Quantity<T : Quantity<T>> {
    /**
     * The value of the quantity
     */
    abstract val value: Double

    operator fun plus(other: T): T = newInstance(value + other.value)
    operator fun minus(other: T): T = newInstance(value - other.value)
    operator fun times(scalar: Double): T = newInstance(value * scalar)
    operator fun times(scalar: Int): T = newInstance(value * scalar)
    operator fun div(other: T): Double = value / other.value
    operator fun div(scalar: Double): T = newInstance(value / scalar)
    operator fun div(scalar: Int): T = newInstance(value / scalar)
    operator fun unaryPlus(): T = newInstance(value)
    operator fun unaryMinus(): T = newInstance(-value)
    operator fun rem(other: T): T = newInstance(value % other.value)
    operator fun rem(divisor: Double): T = newInstance(value % divisor)
    operator fun rem(divisor: Int): T = newInstance(value % divisor)
    operator fun compareTo(other: T): Int = value.compareTo(other.value)

    fun lessThan(other: T): Boolean = compareTo(other) < 0
    fun lessThanOrEqualTo(other: T): Boolean = compareTo(other) <= 0
    fun greaterThan(other: T): Boolean = compareTo(other) > 0
    fun greaterThanOrEqualTo(other: T): Boolean = compareTo(other) >= 0

    val sign: Int
        get() = when {
            value > 0 -> 1
            value < 0 -> -1
            else -> 0
        }

    @get:JvmName("abs")
    val abs: T get() = newInstance(abs(value))

    abstract override fun equals(other: Any?): Boolean
    abstract override fun hashCode(): Int
    abstract override fun toString(): String

    /**
     * @return if [value] is NaN
     */
    fun isNaN(): Boolean = value.isNaN()

    /**
     * Creates a new instance of the class with the given value
     * @param value the value to create an instance with
     */
    abstract fun newInstance(value: Double): T
}

fun <T : Quantity<T>> abs(quantity: T): T = quantity.abs
