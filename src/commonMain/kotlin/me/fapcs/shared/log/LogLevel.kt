package me.fapcs.shared.log

import kotlinx.serialization.Serializable

@Serializable
enum class LogLevel {

    DEBUG,
    INFO,
    WARN,
    ERROR,
    FATAL

}