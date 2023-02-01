package me.fapcs.shared.expect

import kotlinx.browser.document
import me.fapcs.shared.util.json.JsonDocument

actual object ConfigProvider {

    actual fun get(): JsonDocument {
        val cookies = document.cookie.split("; ")
        val cookie = cookies.find { it.startsWith("config=") }

        return if (cookie != null) {
            val json = cookie.substring(7)
            JsonDocument(json)
        } else {
            JsonDocument()
        }
    }

    actual fun set(config: JsonDocument) {
        @Suppress("SpellCheckingInspection")
        document.cookie = "config=$config;expires=Fri, 31 Dec 9999 23:59:59 GMT;path=/;samesite=strict"
    }

}