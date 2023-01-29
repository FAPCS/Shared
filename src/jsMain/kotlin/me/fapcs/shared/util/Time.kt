@file:Suppress("unused")

package me.fapcs.shared.util

external fun setJsTimeout(function: () -> Unit, delay: Long): Int
external fun clearJsTimeout(handle: Int)
external fun setJsInterval(function: () -> Unit, delay: Long): Int
external fun clearJsInterval(id: Int)

actual fun clearTimeout(id: Int) = clearJsTimeout(id)

actual fun setTimeout(function: Time.() -> Unit, delay: Long) {
    val time = TimeImpl(true)
    time.setId(setJsTimeout({ time.function() }, delay))
}

actual fun clearInterval(id: Int) = clearJsInterval(id)

actual fun setInterval(function: Time.() -> Unit, delay: Long, executeNow: Boolean) {
    val time = TimeImpl(false)
    time.setId(setJsInterval({ time.function() }, delay))
    if (executeNow) time.function()
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