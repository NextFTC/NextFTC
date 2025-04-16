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

package com.rowanmcalpin.nextftc.ftc.hardware

import com.qualcomm.robotcore.hardware.Servo
import com.rowanmcalpin.nextftc.ftc.OpModeData
import com.rowanmcalpin.nextftc.ftc.hardware.delegates.Caching
import com.rowanmcalpin.nextftc.hardware.positionable.Positionable

open class ServoEx(cacheTolerance: Double, servoFactory: () -> Servo) : Positionable {

    val servo by lazy { servoFactory() }

    constructor(servoFactory: () -> Servo) : this(0.01, servoFactory)

    @JvmOverloads
    constructor(servo: Servo, cacheTolerance: Double = 0.01) : this(cacheTolerance, { servo })

    @JvmOverloads
    constructor(name: String, cacheTolerance: Double = 0.01) : this(
        cacheTolerance,
        { OpModeData.hardwareMap!![name] as Servo })

    override var position: Double by Caching(cacheTolerance) {
        it?.let { servo.position = it }
    }
}