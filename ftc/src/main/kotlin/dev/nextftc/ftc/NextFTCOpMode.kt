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

package dev.nextftc.ftc

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotorEx
import dev.nextftc.core.command.CommandManager
import dev.nextftc.core.components.Component
import dev.nextftc.ftc.components.Initializer
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


/**
 * This is a wrapper class for an OpMode that does the following:
 *  - Automatically initializes and runs the CommandManager
 *  - If desired, automatically implements and handles Gamepads
 */
abstract class NextFTCOpMode : LinearOpMode() {

    private val _components: MutableSet<Component> = mutableSetOf()
    val components: Set<Component> by ::_components

    fun addComponents(vararg components: Component) {
        _components.addAll(components)
    }

    override fun runOpMode() {
        try {

            components.forEach { it.preInit() }
            onInit()
            components.reversed().forEach { it.postInit() }

            // Wait for start
            while (opModeInInit()) {
                components.forEach { it.preWaitForStart() }
                onWaitForStart()
                components.reversed().forEach { it.postWaitForStart() }
            }

            // If we pressed stop after init (instead of start) we want to skip the rest of the OpMode
            // and jump straight to the end
            if (!isStopRequested) {
                components.forEach { it.preStartButtonPressed() }
                onStartButtonPressed()
                components.reversed().forEach { it.postStartButtonPressed() }

                while (opModeIsActive()) {
                    components.forEach { it.preUpdate() }
                    CommandManager.run()
                    onUpdate()
                    components.reversed().forEach { it.postUpdate() }
                }
            }

            components.forEach { it.preStop() }
            onStop()
            components.forEach { it.postStop() }
        } catch (e: Exception) {
            // Rethrow the exception as a RuntimeException with the original stack trace at the top
            val runtimeException = RuntimeException(e.message)
            runtimeException.stackTrace = e.stackTrace  // Set the original stack trace at the top
            throw runtimeException  // Throw the custom RuntimeException
        }
    }

    /**
     * Used to generate properties upon OpMode initialization.
     *
     * @param block function to be evaluated upon initialization
     */
    fun <V> onInit(block: OpMode.() -> V): Initializer<V> {
        val initializer = Initializer(block)
        addComponents(initializer)
        return initializer
    }

    /**
     * This function runs ONCE when the init button is pressed.
     */
    open fun onInit() {}

    /**
     * This function runs REPEATEDLY during initialization.
     */
    open fun onWaitForStart() {}

    /**
     * This function runs ONCE when the start button is pressed.
     */
    open fun onStartButtonPressed() {}

    /**
     * This function runs REPEATEDLY when the OpMode is running.
     */
    open fun onUpdate() {}

    /**
     * This function runs ONCE when the stop button is pressed.
     */
    open fun onStop() {}
}