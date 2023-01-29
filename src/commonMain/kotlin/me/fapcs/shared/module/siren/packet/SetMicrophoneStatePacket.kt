package me.fapcs.shared.module.siren.packet

import kotlinx.serialization.Serializable
import me.fapcs.shared.communication.IPacket

@Serializable
@Suppress("unused")
data class SetMicrophoneStatePacket(val enabled: Boolean) : IPacket