@file:Suppress("unused")

package me.fapcs.shared.util

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

actual fun clearTimeout(id: Int) = unit(TimeImpl.timers[id]?.clear())
actual fun clearInterval(id: Int) = unit(TimeImpl.timers[id]?.clear())

@OptIn(DelicateCoroutinesApi::class)
actual fun setTimeout(function: Time.() -> Unit, delay: Long) {
    val time = TimeImpl(true)
    GlobalScope.launch {
        delay(delay)
        if (time.active) time.function()
    }
}

@OptIn(DelicateCoroutinesApi::class)
actual fun setInterval(function: Time.() -> Unit, delay: Long, executeNow: Boolean) {
    val time = TimeImpl(true)
    GlobalScope.launch {
        if (executeNow) time.function()
        while (time.active) {
            delay(delay)
            if (time.active) time.function()
        }
    }
}

internal class TimeImpl(var active: Boolean) : Time {

    override val id: Int
        get() = TimeImpl.id

    override fun clear() = expr { active = false }

    companion object {

        @Suppress("UNUSED_PARAMETER")
        private var id: Int = 0
            get() = field++
            set(value) = Unit

        val timers = mutableMapOf<Int, TimeImpl>()


    }

}
