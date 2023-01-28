package me.fapcs.shared.communication

import kotlinx.serialization.Serializable
import me.fapcs.shared.util.json.JsonDocument

interface IPacket

@Serializable
data class UnknownPacket(val data: JsonDocument) : IPacket

@Serializable
data class RawPacket(val data: JsonDocument) : IPacket
