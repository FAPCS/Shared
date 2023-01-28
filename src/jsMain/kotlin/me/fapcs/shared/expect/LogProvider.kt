package me.fapcs.shared.expect

import me.fapcs.shared.log.LogLevel
import kotlin.js.Date

actual object LogProvider {

    actual fun log(level: LogLevel, message: String) = when (level) {
        LogLevel.DEBUG -> console.log(formatMessage(level, message))
        LogLevel.INFO -> console.info(formatMessage(level, message))
        LogLevel.WARN -> console.warn(formatMessage(level, message))
        LogLevel.ERROR -> console.error(formatMessage(level, message))
        LogLevel.FATAL -> console.error("%c${formatMessage(level, message)}", "background: red; color: white;")
    }

    actual fun log(level: LogLevel, message: String, throwable: Throwable) = when (level) {
        LogLevel.DEBUG -> console.log(formatMessage(level, message), throwable)
        LogLevel.INFO -> console.info(formatMessage(level, message), throwable)
        LogLevel.WARN -> console.warn(formatMessage(level, message), throwable)
        LogLevel.ERROR -> console.error(formatMessage(level, message), throwable)
        LogLevel.FATAL -> console.error("%c${formatMessage(level, message)}", "background: red; color: white;", throwable)
    }

    actual fun log(level: LogLevel, throwable: Throwable) = when (level) {
        LogLevel.DEBUG -> console.log(throwable)
        LogLevel.INFO -> console.info(throwable)
        LogLevel.WARN -> console.warn(throwable)
        LogLevel.ERROR -> console.error(throwable)
        LogLevel.FATAL -> console.error("%c", "background: red; color: white;", throwable)
    }

    private fun formatMessage(level: LogLevel, message: String) =
        "${getTime()} | ${level.name.padEnd(5, ' ')} | $message"

    private fun getTime(): String {
        val date = Date()
        return "${date.getHours()}:${date.getMinutes()}:${date.getSeconds()}.${date.getMilliseconds()}"
    }

}