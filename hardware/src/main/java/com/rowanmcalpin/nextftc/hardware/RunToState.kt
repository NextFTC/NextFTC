package com.rowanmcalpin.nextftc.hardware

import com.rowanmcalpin.nextftc.core.Subsystem
import com.rowanmcalpin.nextftc.core.command.Command
import dev.nextftc.nextcontrol.ControlSystem
import dev.nextftc.nextcontrol.KineticState

/**
 * This command sets a [ControlSystem] goal to the [goal], and then waits until the
 * system is within the [tolerance]
 *
 * @param system the system to control
 * @param goal the goal for the [system]
 * @param tolerance tolerance to be considered "at" the [goal]
 * @param subsystems  the list of [Subsystem]s this command interacts with (should be whatever
 *                      subsystem holds this command)
 */
open class RunToState @JvmOverloads constructor(
    val system: ControlSystem,
    val goal: KineticState,
    val tolerance: KineticState = KineticState(10.0, 5.0, Double.POSITIVE_INFINITY),
    override val subsystems: Set<Subsystem> = emptySet()): Command() {

    override val isDone: Boolean
        get() = system.isWithinTolerance(tolerance)

    override fun start() {
        system.goal = goal
    }
}