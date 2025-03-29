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

package com.rowanmcalpin.nextftc.hardware

import com.rowanmcalpin.nextftc.core.Subsystem
import dev.nextftc.nextcontrol.ControlSystem
import dev.nextftc.nextcontrol.KineticState

/**
 * This command sets a controller goal to a specified position, and then waits until the controller
 * is within a specified tolerance
 *
 * @param system the system to control
 * @param goal the goal for the [system]
 * @param tolerance tolerance to be considered "at" the [goal]
 * @param subsystems  the list of [Subsystem]s this command interacts with (should be whatever
 *                      subsystem holds this command)
 */
class RunToPosition @JvmOverloads constructor(
    system: ControlSystem,
    goal: Double,
    tolerance: KineticState = KineticState(10.0, 5.0, Double.POSITIVE_INFINITY),
    subsystems: Set<Subsystem> = emptySet()
) : RunToState(
    system,
    KineticState(goal),
    tolerance,
    subsystems
) {

    @JvmOverloads
    constructor(
        system: ControlSystem,
        goal: Double,
        positionTolerance: Double = 10.0,
        subsystems: Set<Subsystem> = emptySet()
    ) : this(
        system,
        goal,
        KineticState(positionTolerance, 5.0, Double.POSITIVE_INFINITY),
        subsystems
    )

    @JvmOverloads
    constructor(
        system: ControlSystem,
        goal: Double,
        tolerance: KineticState = KineticState(10.0, 5.0, 5.0),
        subsystem: Subsystem
    ) : this(system, goal, tolerance, setOf(subsystem))

    @JvmOverloads
    constructor(
        system: ControlSystem,
        goal: Double,
        positionTolerance: Double = 10.0,
        subsystem: Subsystem
    ) : this(
        system,
        goal,
        KineticState(positionTolerance, 5.0, Double.POSITIVE_INFINITY),
        setOf(subsystem)
    )
}