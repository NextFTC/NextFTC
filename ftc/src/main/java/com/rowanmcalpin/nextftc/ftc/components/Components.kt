package com.rowanmcalpin.nextftc.ftc.components

import com.rowanmcalpin.nextftc.core.Subsystem

/**
 * This is a collection of Components and includes a modified mutating builder
 */
class Components {
    private var components: List<NextComponent> = listOf()

    /**
     * Adds a [SubsystemComponent] to your OpMode
     */
    fun useSubsystems(vararg subsystems: Subsystem): Components {
        components += SubsystemComponent(*subsystems)

        return this
    }

    /**
     * Adds a [GamepadComponent] to your OpMode
     */
    fun useGamepads(): Components {
        components += GamepadComponent()

        return this
    }

    /**
     * Adds a [BulkReadComponent] to your OpMode
     */
    fun useBulkReading(): Components {
        components += BulkReadComponent()

        return this
    }

    /**
     * Adds a custom [NextComponent] to your OpMode
     */
    fun use(component: NextComponent): Components {
        components += component

        return this
    }

    fun preInit() {
        components.forEach { it.preInit() }
    }

    fun postInit() {
        components.reversed().forEach { it.postInit() }
    }

    fun preWaitForStart() {
        components.forEach { it.preWaitForStart() }
    }

    fun postWaitForStart() {
        components.reversed().forEach { it.postWaitForStart() }
    }

    fun preStartButtonPressed() {
        components.forEach { it.preStartButtonPressed() }
    }

    fun postStartButtonPressed() {
        components.reversed().forEach { it.postStartButtonPressed() }
    }

    fun preUpdate() {
        components.forEach { it.preUpdate() }
    }

    fun postUpdate() {
        components.reversed().forEach { it.postUpdate() }
    }

    fun preStop() {
        components.forEach { it.preStop() }
    }

    fun postStop() {
        components.reversed().forEach { it.postStop() }
    }
}