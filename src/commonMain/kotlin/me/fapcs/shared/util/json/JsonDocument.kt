package me.fapcs.shared.util.json

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*
import me.fapcs.shared.util.extention.toJsonObject

@Serializable(with = JsonDocument.JsonSerializer::class)
@Suppress("MemberVisibilityCanBePrivate", "unused")
class JsonDocument(private var jsonObject: JsonObject) {

    constructor() : this(JsonObject(emptyMap()))

    constructor(jsonElement: JsonElement) : this(
        if (jsonElement is JsonObject) jsonElement.jsonObject else JsonObject(
            emptyMap()
        )
    )

    constructor(jsonString: String) : this(Json.parseToJsonElement(jsonString))

    fun keys() = jsonObject.keys.toList()

    fun size() = jsonObject.size

    fun clear() = apply { jsonObject = JsonObject(emptyMap()) }

    fun remove(key: String) = apply { jsonObject = jsonObject.toMutableMap().apply { remove(key) }.toJsonObject() }

    operator fun contains(key: String) = key in jsonObject

    operator fun get(key: String) = jsonObject[key]

    operator fun get(key: String, default: JsonElement) = jsonObject[key] ?: default

    fun getByte(key: String) = jsonObject[key]?.jsonPrimitive?.content?.toByteOrNull()

    fun getByte(key: String, default: Byte) = getByte(key) ?: default

    fun getShort(key: String) = jsonObject[key]?.jsonPrimitive?.content?.toShortOrNull()

    fun getShort(key: String, default: Short) = getShort(key) ?: default

    fun getInt(key: String) = jsonObject[key]?.jsonPrimitive?.intOrNull

    fun getInt(key: String, default: Int) = getInt(key) ?: default

    fun getLong(key: String) = jsonObject[key]?.jsonPrimitive?.longOrNull

    fun getLong(key: String, default: Long) = getLong(key) ?: default

    fun getFloat(key: String) = jsonObject[key]?.jsonPrimitive?.floatOrNull

    fun getFloat(key: String, default: Float) = getFloat(key) ?: default

    fun getDouble(key: String) = jsonObject[key]?.jsonPrimitive?.doubleOrNull

    fun getDouble(key: String, default: Double) = getDouble(key) ?: default

    fun getBoolean(key: String) = jsonObject[key]?.jsonPrimitive?.booleanOrNull

    fun getBoolean(key: String, default: Boolean) = getBoolean(key) ?: default

    fun getString(key: String) = jsonObject[key]?.jsonPrimitive?.content

    fun getString(key: String, default: String) = getString(key) ?: default

    fun getCharacter(key: String) = jsonObject[key]?.jsonPrimitive?.content?.firstOrNull()

    fun getCharacter(key: String, default: Char) = getCharacter(key) ?: default

    fun getJsonArray(key: String) = jsonObject[key]?.jsonArray

    fun getJsonArray(key: String, default: JsonArray) = getJsonArray(key) ?: default

    fun getJsonObject(key: String) = jsonObject[key]?.jsonObject

    fun getJsonObject(key: String, default: JsonObject) = getJsonObject(key) ?: default

    fun getJsonDocument(key: String) = jsonObject[key]?.let { JsonDocument(it) }

    fun getJsonDocument(key: String, default: JsonDocument) = getJsonDocument(key) ?: default

    inline fun <reified T : Any> get(key: String) = Json.decodeFromJsonElement<T>(get(key) ?: JsonNull)

    operator fun set(key: String, value: JsonElement) =
        apply { jsonObject = jsonObject.toMutableMap().apply { put(key, value) }.toJsonObject() }

    fun set(key: String, value: Byte) = set(key, JsonPrimitive(value))

    fun set(key: String, value: Short) = set(key, JsonPrimitive(value))

    fun set(key: String, value: Int) = set(key, JsonPrimitive(value))

    fun set(key: String, value: Long) = set(key, JsonPrimitive(value))

    fun set(key: String, value: Float) = set(key, JsonPrimitive(value))

    fun set(key: String, value: Double) = set(key, JsonPrimitive(value))

    fun set(key: String, value: Boolean) = set(key, JsonPrimitive(value))

    fun set(key: String, value: String) = set(key, JsonPrimitive(value))

    fun set(key: String, value: JsonArray): JsonDocument = set(key, value)

    fun set(key: String, value: JsonObject): JsonDocument = set(key, value)

    fun set(key: String, value: JsonDocument) = set(key, value.jsonObject)

    inline fun <reified T : Any> set(key: String, value: T) = set(key, Json.encodeToJsonElement(value))

    fun toJsonObject() = jsonObject

    override fun toString() = Json.encodeToString(jsonObject)

    fun toJsonString() = toString()

    fun toPrettyString() = prettyPrinter.encodeToString(jsonObject)

    companion object {

        private val prettyPrinter = Json { prettyPrint = true }

    }

    @OptIn(ExperimentalSerializationApi::class)
    @Serializer(forClass = JsonDocument::class)
    object JsonSerializer : KSerializer<JsonDocument> {

        override val descriptor: SerialDescriptor = JsonObject.serializer().descriptor

        override fun serialize(encoder: Encoder, value: JsonDocument) {
            JsonObject.serializer().serialize(encoder, value.jsonObject)
        }

        override fun deserialize(decoder: Decoder): JsonDocument {
            return JsonDocument(JsonObject.serializer().deserialize(decoder))
        }

    }

}