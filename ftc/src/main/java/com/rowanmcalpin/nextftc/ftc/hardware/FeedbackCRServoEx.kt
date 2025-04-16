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

import com.qualcomm.robotcore.hardware.AnalogInput
import com.qualcomm.robotcore.hardware.CRServo
import com.rowanmcalpin.nextftc.ftc.hardware.delegates.AnalogFeedback
import com.rowanmcalpin.nextftc.ftc.hardware.delegates.Velocity
import com.rowanmcalpin.nextftc.hardware.controllable.Controllable

class FeedbackCRServoEx(
    cacheTolerance: Double,
    feedbackFactory: () -> AnalogInput,
    servoFactory: () -> CRServo
) :
    CRServoEx(cacheTolerance, servoFactory), Controllable {

    val feedback by lazy { feedbackFactory() }

    override val velocity: Double by Velocity { currentPosition }

    /**
     * The current position, in radians
     */
    override val currentPosition by AnalogFeedback { feedback.voltage }
}