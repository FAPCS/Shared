package me.fapcs.shared.log

import me.fapcs.shared.expect.LogProvider

@Suppress("unused", "MemberVisibilityCanBePrivate")
object Logger {

    fun log(level: LogLevel, message: String) = LogProvider.log(level, message)

    fun log(level: LogLevel, message: String, throwable: Throwable) = LogProvider.log(level, message, throwable)

    fun log(level: LogLevel, throwable: Throwable) = LogProvider.log(level, throwable)

    fun debug(message: String) = log(LogLevel.DEBUG, message)

    fun debug(message: String, throwable: Throwable) = log(LogLevel.DEBUG, message, throwable)

    fun debug(throwable: Throwable) = log(LogLevel.DEBUG, throwable)

    fun info(message: String) = log(LogLevel.INFO, message)

    fun info(message: String, throwable: Throwable) = log(LogLevel.INFO, message, throwable)

    fun info(throwable: Throwable) = log(LogLevel.INFO, throwable)

    fun warn(message: String) = log(LogLevel.WARN, message)

    fun warn(message: String, throwable: Throwable) = log(LogLevel.WARN, message, throwable)

    fun warn(throwable: Throwable) = log(LogLevel.WARN, throwable)

    fun error(message: String) = log(LogLevel.ERROR, message)

    fun error(message: String, throwable: Throwable) = log(LogLevel.ERROR, message, throwable)

    fun error(throwable: Throwable) = log(LogLevel.ERROR, throwable)

    fun fatal(message: String) = log(LogLevel.FATAL, message)

    fun fatal(message: String, throwable: Throwable) = log(LogLevel.FATAL, message, throwable)

    fun fatal(throwable: Throwable) = log(LogLevel.FATAL, throwable)

}