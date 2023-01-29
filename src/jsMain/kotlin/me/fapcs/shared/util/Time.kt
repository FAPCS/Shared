@file:Suppress("unused")

package me.fapcs.shared.util

external fun setJsTimeout(function: () -> Unit, delay: Long): Int
external fun clearJsTimeout(handle: Int)
external fun setJsInterval(function: () -> Unit, delay: Long): Int
external fun clearJsInterval(id: Int)

fun setTimeout(function: Time.() -> Unit, delay: Long) {
    val time = TimeImpl(true)
    time.setId(setJsTimeout({ time.function() }, delay))
}

fun setTimeout(delay: Long, function: Time.() -> Unit) = setTimeout(function, delay)

fun setInterval(function: Time.() -> Unit, delay: Long, executeNow: Boolean = false) {
    val time = TimeImpl(false)
    time.setId(setJsInterval({ time.function() }, delay))
    if (executeNow) time.function()
}

fun setInterval(delay: Long, executeNow: Boolean = false, function: Time.() -> Unit) =
    setInterval(function, delay, executeNow)

interface Time {
    val id: Int
    fun clear()
}

internal class TimeImpl(private val timeout: Boolean) : Time {

    private var _id: Int? = null

    override val id: Int
        get() = _id ?: throw IllegalStateException("Time is not set")

    override fun clear() {
        if (timeout) clearJsTimeout(id)
        else clearJsInterval(id)
    }

    fun setId(id: Int) {
        _id = id
    }

}
