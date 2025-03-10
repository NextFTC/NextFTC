/*
NextFTC: a user-friendly control library for FIRST Tech Challenge
    Copyright (C) 2025 Rowan McAlpin

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.rowanmcalpin.nextftc.core.control.controllers

import com.rowanmcalpin.nextftc.core.control.coefficients.PIDCoefficients
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.Feedforward
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.StaticFeedforward
import kotlin.math.abs
import kotlin.math.sign
import kotlin.math.sqrt

/**
 * This is a Square Root Controller, which is a proportional controller that uses the square root of
 * the error instead of just the error.
 *
 * @param kP proportional gain
 * @param kI integral gain
 * @param kD derivative gain
 * @param kF custom feedforward that depends on position
 * @param setPointTolerance the tolerance for being "at the target"
 */
class SqrtController @JvmOverloads constructor(
    kP: Double,
    kI: Double = 0.0,
    kD: Double = 0.0,
    kF: Feedforward = StaticFeedforward(0.0),
    setPointTolerance: Double = 10.0
) : PIDFController(kP, kI, kD, kF, setPointTolerance) {
    override fun calculate(
        pv: Double
    ): Double {
        val result = super.calculate(pv)
        return sqrt(abs(result)) * sign(result)
        /*
            FIXME: This is not quite what the docs describes it to be.
                   This calculates the square root of the result from the PIDF controller and returns it,
                   but doesn't make the PIDF use the square root of the error instead of the error.
         */
    }
}
