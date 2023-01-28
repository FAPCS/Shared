package me.fapcs.shared.expect

import me.fapcs.shared.util.json.JsonDocument
import java.io.File

actual object ConfigProvider {

    private val file = File(".", "config.json")

    actual fun get() = if (file.exists()) JsonDocument(file.readText()) else JsonDocument()

    actual fun set(config: JsonDocument) = file.writeText(config.toPrettyString())

}