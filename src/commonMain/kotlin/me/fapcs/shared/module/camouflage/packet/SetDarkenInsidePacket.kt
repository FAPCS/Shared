package me.fapcs.shared.module.camouflage.packet

import kotlinx.serialization.Serializable
import me.fapcs.shared.communication.IPacket

@Serializable
@Suppress("unused")
data class SetDarkenInsidePacket(val active: Boolean) : IPacket