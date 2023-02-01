package me.fapcs.shared.config.handling

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import me.fapcs.shared.expect.ConfigProvider
import me.fapcs.shared.log.Logger
import me.fapcs.shared.util.json.JsonDocument
import kotlin.reflect.KClass
import kotlin.reflect.cast

internal class ConfigurationHandler : IConfigurationHandler {

    override fun <T : Any> create(klass: KClass<T>, key: String, value: T) {
        Logger.debug("Creating config entry: $key, $value")
        if (getPlain(key) != null) {
            Logger.debug("Config entry already exists: $key")
            return
        }

        useLivingDocument(key, create = true, update = true) { document, lastKey ->
            document.set(klass, lastKey, klass.cast(value))
        }

        Logger.debug("Config entry created: $key, $value")
    }

    @OptIn(InternalSerializationApi::class)
    override fun <T : Any> get(klass: KClass<T>, key: String): T? {
        useLivingDocument(key, false) { document, lastKey ->
            return Json.decodeFromJsonElement(klass.serializer(), document[lastKey] ?: return null)
        }

        return null
    }

    override fun <T : Any> get(klass: KClass<T>, key: String, default: T, create: Boolean): T =
        get(klass, key) ?: default.also {
            if (create) create(klass, key, it)
        }

    private fun getPlain(key: String): String? {
        useLivingDocument(key, false) { document, lastKey ->
            return document.getString(lastKey)
        }

        return null
    }

    private inline fun useLivingDocument(
        key: String,
        create: Boolean,
        update: Boolean = false,
        use: (JsonDocument, String) -> Unit
    ) {
        val root = ConfigProvider.get()
        val documentTree = mutableListOf<Pair<String, JsonDocument>>(
            "" to root
        )

        val split = key.split(".")
        for (i in 0 until split.size - 1) {
            val currentKey = split[i]
            val currentDocument = documentTree.last().second

            val nextDocument = if (currentKey !in currentDocument) {
                if (create) JsonDocument()
                else return
            } else currentDocument.getJsonDocument(currentKey)!!

            documentTree.add(currentKey to nextDocument)
        }

        val lastKey = split.last()
        val lastDocument = documentTree.last().second
        use(lastDocument, lastKey)

        if (update) {
            for (i in documentTree.size - 1 downTo 1) {
                val (currentKey, document) = documentTree[i]
                val parent = documentTree[i - 1].second
                parent.set(currentKey, document)
            }

            ConfigProvider.set(root)
        }
    }

}