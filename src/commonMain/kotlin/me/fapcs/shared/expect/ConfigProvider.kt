package me.fapcs.shared.expect

import me.fapcs.shared.util.json.JsonDocument

expect object ConfigProvider {

    fun get(): JsonDocument

    fun set(config: JsonDocument)

}