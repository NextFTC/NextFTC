package dev.nextftc.ftc

import com.qualcomm.robotcore.hardware.Gamepad
import dev.nextftc.bindings.button
import dev.nextftc.bindings.range
import dev.nextftc.bindings.variable

object Gamepads {
    @JvmStatic
    @get:JvmName("gamepad1")
    val gamepad1 by lazy { GamepadEx(ActiveOpMode.gamepad1) }

    @JvmStatic
    @get:JvmName("gamepad2")
    val gamepad2 by lazy { GamepadEx(ActiveOpMode.gamepad2) }
}

class GamepadEx(val gamepad: Gamepad) {
    @get:JvmName("dpadUp")
    val dpadUp by lazy { button { gamepad.dpad_up } }

    @get:JvmName("dpadDown")
    val dpadDown by lazy { button { gamepad.dpad_down } }

    @get:JvmName("dpadLeft")
    val dpadLeft by lazy { button { gamepad.dpad_left } }

    @get:JvmName("dpadRight")
    val dpadRight by lazy { button { gamepad.dpad_right } }

    @get:JvmName("leftStickY")
    val leftStickY by lazy { range { gamepad.left_stick_y } }

    @get:JvmName("leftStickX")
    val leftStickX by lazy { range { gamepad.left_stick_x } }

    @get:JvmName("rightStickY")
    val rightStickY by lazy { range { gamepad.right_stick_y } }

    @get:JvmName("rightStickX")
    val rightStickX by lazy { range { gamepad.right_stick_x } }

    @get:JvmName("a")
    val a by lazy { button { gamepad.a } }

    @get:JvmName("b")
    val b by lazy { button { gamepad.b } }

    @get:JvmName("x")
    val x by lazy { button { gamepad.x } }

    @get:JvmName("y")
    val y by lazy { button { gamepad.y } }

    @get:JvmName("guide")
    val guide by lazy { button { gamepad.guide } }

    @get:JvmName("start")
    val start by lazy { button { gamepad.start } }

    @get:JvmName("back")
    val back by lazy { button { gamepad.back } }

    @get:JvmName("leftBumper")
    val leftBumper by lazy { button { gamepad.left_bumper } }

    @get:JvmName("rightBumper")
    val rightBumper by lazy { button { gamepad.right_bumper } }

    @get:JvmName("leftStickButton")
    val leftStickButton by lazy { button { gamepad.left_stick_button } }

    @get:JvmName("rightStickButton")
    val rightStickButton by lazy { button { gamepad.right_stick_button } }

    @get:JvmName("leftTrigger")
    val leftTrigger by lazy { range { gamepad.left_trigger } }

    @get:JvmName("rightTrigger")
    val rightTrigger by lazy { range { gamepad.right_trigger } }

    @get:JvmName("circle")
    val circle by lazy { button { gamepad.circle } }

    @get:JvmName("cross")
    val cross by lazy { button { gamepad.cross } }

    @get:JvmName("triangle")
    val triangle by lazy { button { gamepad.triangle } }

    @get:JvmName("square")
    val square by lazy { button { gamepad.square } }

    @get:JvmName("share")
    val share by lazy { button { gamepad.share } }

    @get:JvmName("options")
    val options by lazy { button { gamepad.options } }

    @get:JvmName("touchpad")
    val touchpad by lazy { button { gamepad.touchpad } }

    @get:JvmName("touchpadFinger1Pressed")
    val touchpadFinger1Pressed by lazy { button { gamepad.touchpad_finger_1 } }

    @get:JvmName("touchpadFinger2Pressed")
    val touchpadFinger2Pressed by lazy { button { gamepad.touchpad_finger_2 } }

    @get:JvmName("touchpadFinger1x")
    val touchpadFinger1x by lazy { range { gamepad.touchpad_finger_1_x } }

    @get:JvmName("touchpadFinger1y")
    val touchpadFinger1y by lazy { range { gamepad.touchpad_finger_1_y } }

    @get:JvmName("touchpadFinger2x")
    val touchpadFinger2x by lazy { range { gamepad.touchpad_finger_2_x } }

    @get:JvmName("touchpadFinger2y")
    val touchpadFinger2y by lazy { range { gamepad.touchpad_finger_2_y } }

    @get:JvmName("ps")
    val ps by lazy { button { gamepad.ps } }

    @get:JvmName("leftStick")
    val leftStick by lazy {
        variable {
            JoystickValues(
                gamepad.left_stick_x.toDouble(),
                gamepad.left_stick_y.toDouble(),
                gamepad.left_stick_button
            )
        }
    }

    @get:JvmName("rightStick")
    val rightStick by lazy {
        variable {
            JoystickValues(
                gamepad.right_stick_x.toDouble(),
                gamepad.right_stick_y.toDouble(),
                gamepad.right_stick_button
            )
        }
    }

    @get:JvmName("touchpadFinger1")
    val touchpadFinger1 by lazy {
        variable {
            TouchpadFinger(
                gamepad.touchpad_finger_1_x.toDouble(),
                gamepad.touchpad_finger_1_y.toDouble(),
                gamepad.touchpad_finger_1
            )
        }
    }

    @get:JvmName("touchpadFinger2")
    val touchpadFinger2 by lazy {
        variable {
            TouchpadFinger(
                gamepad.touchpad_finger_2_x.toDouble(),
                gamepad.touchpad_finger_2_y.toDouble(),
                gamepad.touchpad_finger_2
            )
        }
    }
}

data class JoystickValues(
    @JvmField val x: Double,
    @JvmField val y: Double,
    @JvmField val pressed: Boolean
)

data class TouchpadFinger(
    @JvmField val x: Double,
    @JvmField val y: Double,
    @JvmField val pressed: Boolean
)