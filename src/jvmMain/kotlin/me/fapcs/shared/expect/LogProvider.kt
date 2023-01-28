package me.fapcs.shared.expect

import me.fapcs.shared.log.LogLevel
import org.apache.logging.log4j.LogManager

actual object LogProvider {

    val logger = LogManager.getLogger("FAPCS")

    actual fun log(level: LogLevel, message: String) = when (level) {
        LogLevel.DEBUG -> logger.debug(message)
        LogLevel.INFO -> logger.info(message)
        LogLevel.WARN -> logger.warn(message)
        LogLevel.ERROR -> logger.error(message)
        LogLevel.FATAL -> logger.fatal(message)
    }

    actual fun log(level: LogLevel, message: String, throwable: Throwable) = when (level) {
        LogLevel.DEBUG -> logger.debug(message, throwable)
        LogLevel.INFO -> logger.info(message, throwable)
        LogLevel.WARN -> logger.warn(message, throwable)
        LogLevel.ERROR -> logger.error(message, throwable)
        LogLevel.FATAL -> logger.fatal(message, throwable)
    }

    actual fun log(level: LogLevel, throwable: Throwable) = when (level) {
        LogLevel.DEBUG -> logger.debug(throwable)
        LogLevel.INFO -> logger.info(throwable)
        LogLevel.WARN -> logger.warn(throwable)
        LogLevel.ERROR -> logger.error(throwable)
        LogLevel.FATAL -> logger.fatal(throwable)
    }

}