package dev.nextftc.ftc.components

import dev.nextftc.core.components.Component
import dev.nextftc.ftc.ActiveOpMode
import kotlin.time.ComparableTimeMark
import kotlin.time.TimeSource.Monotonic.markNow

class LoopTimeComponent : Component {
    private var lastTime: ComparableTimeMark? = null

    override fun preWaitForStart() = update()
    override fun preUpdate() = update()

    private fun update() {
        val currentTime = markNow()
        if (lastTime != null) {
            ActiveOpMode.telemetry.addData("Loop Time", currentTime - lastTime!!)
        }
        lastTime = currentTime
    }
}