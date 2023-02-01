package me.fapcs.shared.expect

import me.fapcs.shared.config.ConfigurationHandler
import me.fapcs.shared.log.LogLevel
import kotlin.js.Date

actual object LogProvider {

    private val logLevel = ConfigurationHandler.get("logLevel", LogLevel.DEBUG, true)

    actual fun log(level: LogLevel, message: String) = finalLog(level, *formatMessage(level, message))

    actual fun log(level: LogLevel, message: String, throwable: Throwable) =
        finalLog(level, *arrayOf(formatMessage(level, message), arrayOf(throwable)).flatten().toTypedArray())

    actual fun log(level: LogLevel, throwable: Throwable) = finalLog(level, throwable)

    private fun formatMessage(level: LogLevel, message: String): Array<String> {
        if (level != LogLevel.FATAL) return arrayOf("${getTime()} | ${level.name.padEnd(5, ' ')} | $message")
        return arrayOf("%c${getTime()} | ${level.name.padEnd(5, ' ')} | $message", "background: red; color: white;")
    }

    private fun getTime(): String {
        val date = Date()
        return "${date.getHours()}:${date.getMinutes()}:${date.getSeconds()}.${date.getMilliseconds()}"
    }

    private fun finalLog(level: LogLevel, vararg messages: Any) {
        if (level < logLevel) return

        when (level) {
            LogLevel.DEBUG -> console.log(*messages)
            LogLevel.INFO -> console.info(*messages)
            LogLevel.WARN -> console.warn(*messages)
            LogLevel.ERROR -> console.error(*messages)
            LogLevel.FATAL -> console.error(*messages)
        }
    }

}