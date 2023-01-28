package me.fapcs.shared.util.extention

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

fun Map<String, JsonElement>.toJsonObject() = JsonObject(this)
