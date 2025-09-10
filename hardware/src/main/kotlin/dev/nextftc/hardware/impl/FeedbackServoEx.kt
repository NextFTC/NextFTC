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

package dev.nextftc.hardware.impl

import com.qualcomm.robotcore.hardware.AnalogInput
import com.qualcomm.robotcore.hardware.Servo
import dev.nextftc.ftc.ActiveOpMode
import dev.nextftc.hardware.delegates.AnalogFeedback

class FeedbackServoEx(
    cacheTolerance: Double,
    feedbackFactory: () -> AnalogInput,
    servoFactory: () -> Servo
) : ServoEx(cacheTolerance, servoFactory) {

    val feedback by lazy { feedbackFactory() }

    constructor(feedbackFactory: () -> AnalogInput, servoFactory: () -> Servo) : this(
        0.01,
        feedbackFactory,
        servoFactory
    )

    @JvmOverloads
    constructor(feedback: AnalogInput, servo: Servo, cacheTolerance: Double = 0.01) : this(
        cacheTolerance,
        { feedback },
        { servo })

    @JvmOverloads
    constructor(feedbackName: String, servoName: String, cacheTolerance: Double = 0.01) : this(
        cacheTolerance,
        { ActiveOpMode.hardwareMap[feedbackName] as AnalogInput },
        { ActiveOpMode.hardwareMap[servoName] as Servo })

    /**
     * The current position, in radians
     */
    val currentPosition by AnalogFeedback { feedback.voltage }
}