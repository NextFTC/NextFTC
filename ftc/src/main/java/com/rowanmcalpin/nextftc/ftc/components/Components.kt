package com.rowanmcalpin.nextftc.ftc.components

import com.rowanmcalpin.nextftc.core.Subsystem

/**
 * This is a collection of Components and includes a modified mutating builder
 */
class Components: NextComponent {
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

    override fun preInit() {
        components.forEach { it.preInit() }
    }

    override fun postInit() {
        components.reversed().forEach { it.postInit() }
    }

    override fun preWaitForStart() {
        components.forEach { it.preWaitForStart() }
    }

    override fun postWaitForStart() {
        components.reversed().forEach { it.postWaitForStart() }
    }

    override fun preStartButtonPressed() {
        components.forEach { it.preStartButtonPressed() }
    }

    override fun postStartButtonPressed() {
        components.reversed().forEach { it.postStartButtonPressed() }
    }

    override fun preUpdate() {
        components.forEach { it.preUpdate() }
    }

    override fun postUpdate() {
        components.reversed().forEach { it.postUpdate() }
    }

    override fun preStop() {
        components.forEach { it.preStop() }
    }

    override fun postStop() {
        components.reversed().forEach { it.postStop() }
    }
}