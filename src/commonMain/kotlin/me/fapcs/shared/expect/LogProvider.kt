package me.fapcs.shared.expect

import me.fapcs.shared.log.LogLevel

expect object LogProvider {

    fun log(level: LogLevel, message: String)

    fun log(level: LogLevel, message: String, throwable: Throwable)

    fun log(level: LogLevel, throwable: Throwable)

}