package dev.nextftc.hardware.delegates

import android.util.Log
import dev.nextftc.functionalInterfaces.Configurator
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class LazyHardware<T>(private val initializer: () -> T) : ReadOnlyProperty<Any?, T> {

    private var value: T? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        if (value != null) return value!!
        return initializer.invoke().also {
            value = it
            onInit.forEach { block -> block.configure(it) }
            Log.d("LazyHardware", "Initialized $it in property ${property.name} in class ${thisRef!!::class.simpleName}")
        }
    }

    private val onInit = mutableListOf<Configurator<T>>()

    fun applyAfterInit(block: Configurator<T>) {
        if (value != null) block.configure(value)
        else onInit += block
    }
}