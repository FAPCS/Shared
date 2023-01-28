@file:Suppress("unused", "UNUSED_PARAMETER")

package me.fapcs.shared.util

fun unit(vararg value: Any?) = Unit

inline fun expr(run: () -> Unit) = run()

inline fun <T : Any> T.withThis(run: () -> Unit): T {
    run()
    return this
}
