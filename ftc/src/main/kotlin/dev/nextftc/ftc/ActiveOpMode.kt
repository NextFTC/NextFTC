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

import android.content.Context
import com.qualcomm.ftccommon.FtcEventLoop
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerNotifier
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.ftccommon.external.OnCreateEventLoop
import org.firstinspires.ftc.robotcore.external.Telemetry

@Suppress("unused")
object ActiveOpMode : OpModeManagerNotifier.Notifications {

    private fun <T> opMode(function: LinearOpMode.() -> T): T =
        it?.function() ?: error("OpMode has not yet been initialized.")

    @JvmField
    var it: LinearOpMode? = null

    @JvmStatic
    val runtime get() = opMode { runtime }

    @JvmStatic
    fun resetRuntime() = opMode { resetRuntime() }

    @JvmStatic
    fun terminateOpModeNow() = opMode { terminateOpModeNow() }

    @JvmStatic
    fun updateTelemetry(telemetry: Telemetry) = opMode { updateTelemetry(telemetry) }

    @JvmStatic
    @get:JvmName("opModeIsActive")
    val opModeIsActive get() = opMode { opModeIsActive() }

    @JvmStatic
    @get:JvmName("opModeInInit")
    val opModeInInit get() = opMode { opModeInInit() }

    @JvmStatic
    val isStarted get() = opMode { isStarted }

    @JvmStatic
    val isStopRequested get() = opMode { isStopRequested }

    @JvmStatic
    @get:JvmName("gamepad1")
    val gamepad1: Gamepad get() = opMode { gamepad1 }

    @JvmStatic
    @get:JvmName("gamepad2")
    val gamepad2: Gamepad get() = opMode { gamepad2 }

    @JvmStatic
    @get:JvmName("telemetry")
    val telemetry: Telemetry get() = opMode { telemetry }

    @JvmStatic
    @get:JvmName("hardwareMap")
    lateinit var hardwareMap: HardwareMap
        private set

    @OnCreateEventLoop
    @JvmStatic
    fun register(context: Context, eventLoop: FtcEventLoop) {
        eventLoop.opModeManager.registerListener(this)
        hardwareMap = eventLoop.opModeManager.hardwareMap
    }

    override fun onOpModePreInit(opMode: OpMode?) {
        if (opMode == null || opMode !is LinearOpMode) return

        it = opMode
    }

    override fun onOpModePreStart(opMode: OpMode?) {
        TODO("Not yet implemented")
    }

    override fun onOpModePostStop(opMode: OpMode?) {
        TODO("Not yet implemented")
    }
}
