@file:Suppress("unused")

package me.fapcs.shared.util

expect fun clearTimeout(id: Int)
expect fun setTimeout(function: Time.() -> Unit, delay: Long)
fun setTimeout(delay: Long, function: Time.() -> Unit) = setTimeout(function, delay)

expect fun clearInterval(id: Int)
expect fun setInterval(function: Time.() -> Unit, delay: Long, executeNow: Boolean = false)
fun setInterval(delay: Long, executeNow: Boolean = false, function: Time.() -> Unit) =
    setInterval(function, delay, executeNow)

interface Time {
    val id: Int
    fun clear()
}
