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

package com.rowanmcalpin.nextftc.ftc

import com.qualcomm.hardware.lynx.LynxModule
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.rowanmcalpin.nextftc.core.command.CommandManager
import com.rowanmcalpin.nextftc.ftc.components.Components
import com.rowanmcalpin.nextftc.ftc.gamepad.GamepadManager
import java.lang.RuntimeException


/**
 * This is a wrapper class for an OpMode that does the following:
 *  - Automatically initializes and runs the CommandManager
 *  - If desired, automatically implements and handles Gamepads
 */
abstract class NextFTCOpMode: LinearOpMode() {

    abstract val components: Components

    override fun runOpMode() {
        try {
            processAnnotations()

            OpModeData.opMode = this
            OpModeData.hardwareMap = hardwareMap
            OpModeData.gamepad1 = gamepad1
            OpModeData.gamepad2 = gamepad2
            OpModeData.telemetry = telemetry

            CommandManager.runningCommands.clear()

            components.preInit()
            onInit()
            components.postInit()

            // Wait for start
            while (!isStarted && !isStopRequested) {
                components.preWaitForStart()
                CommandManager.run()
                onWaitForStart()
                components.postWaitForStart()
            }

            // If we pressed stop after init (instead of start) we want to skip the rest of the OpMode
            // and jump straight to the end
            if (!isStopRequested) {
                components.preStartButtonPressed()
                onStartButtonPressed()
                components.postStartButtonPressed()

                while (!isStopRequested && isStarted) {
                    components.preUpdate()
                    CommandManager.run()
                    onUpdate()
                    components.postUpdate()
                }
            }

            components.preStop()
            onStop()
            components.postStop()

            // Since users might schedule a command that stops things, we want to be able to run it
            // (one update of it, anyways) before we cancel all of our commands.
            CommandManager.run()
            CommandManager.cancelAll()
        } catch (e: Exception) {
            // Rethrow the exception as a RuntimeException with the original stack trace at the top
            val runtimeException = RuntimeException(e.message)
            runtimeException.stackTrace = e.stackTrace  // Set the original stack trace at the top
            throw runtimeException  // Throw the custom RuntimeException
        }
    }

    /**
     * This function runs ONCE when the init button is pressed.
     */
    open fun onInit() { }

    /**
     * This function runs REPEATEDLY during initialization.
     */
    open fun onWaitForStart() { }

    /**
     * This function runs ONCE when the start button is pressed.
     */
    open fun onStartButtonPressed() { }

    /**
     * This function runs REPEATEDLY when the OpMode is running.
     */
    open fun onUpdate() { }

    /**
     * This function runs ONCE when the stop button is pressed.
     */
    open fun onStop() { }

    /**
     * This class automatically identifies what type of OpMode it is annotated as, thereby allowing
     * it to set the [OpModeData.opModeType] variable correctly.
     */
    private fun processAnnotations() {
        for (annotation in this::class.annotations) {
            if (annotation is TeleOp) {
                OpModeData.opModeType = OpModeData.OpModeType.TELEOP
            }
            if (annotation is Autonomous) {
                OpModeData.opModeType = OpModeData.OpModeType.AUTO
            }
        }
    }
}