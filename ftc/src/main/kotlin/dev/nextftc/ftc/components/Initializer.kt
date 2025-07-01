package dev.nextftc.ftc.components

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import dev.nextftc.core.components.Component
import dev.nextftc.ftc.ActiveOpMode
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class Initializer<V>(val block: OpMode.() -> V) : Component, ReadOnlyProperty<OpMode, V> {
    var value: V? = null

    override fun preInit() {
        value = ActiveOpMode.it!!.block()
    }

    override fun getValue(
        thisRef: OpMode,
        property: KProperty<*>
    ): V = value!!
}