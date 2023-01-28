package me.fapcs.shared.config.handling

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import me.fapcs.shared.expect.ConfigProvider
import me.fapcs.shared.log.Logger
import me.fapcs.shared.util.json.JsonDocument
import kotlin.reflect.KClass

internal class ConfigurationHandler : IConfigurationHandler {

    override fun create(key: String, value: Any) {
        Logger.debug("Creating config entry: $key, $value")
        if (getPlain(key) != null) {
            Logger.debug("Config entry already exists: $key")
            return
        }

        useLivingDocument(key, true) { document, lastKey -> document.set(lastKey, value) }
        Logger.debug("Config entry created: $key, $value")
    }

    @OptIn(InternalSerializationApi::class)
    override fun <T : Any> get(klass: KClass<T>, key: String): T? =
        getPlain(key)?.let { return Json.decodeFromString(klass.serializer(), it) }

    override fun <T : Any> get(klass: KClass<T>, key: String, default: T, create: Boolean): T =
        get(klass, key) ?: default.also {
            if (create) create(key, it)
        }

    private fun getPlain(key: String): String? {
        useLivingDocument(key, false) { document, lastKey ->
            return document.getString(lastKey)
        }

        return null
    }

    private inline fun useLivingDocument(key: String, create: Boolean, use: (JsonDocument, String) -> Unit) {
        val split = key.split(".")

        if (split.size == 1) {
            use(ConfigProvider.get(), key)
            return
        }

        var document = ConfigProvider.get()
        for (i in 0 until split.size - 1) {
            val currentKey = split[i]
            document = if (currentKey in document) {
                if (create) document.set(currentKey, JsonDocument())
                else return
            } else document.getJsonDocument(currentKey)!!
        }

        use(document, split.last())
    }

}