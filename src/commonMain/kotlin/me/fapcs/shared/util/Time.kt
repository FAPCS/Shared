@file:Suppress("unused")
@file:JvmName("TimeImplKt")

package me.fapcs.shared.util

import kotlin.jvm.JvmName

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
