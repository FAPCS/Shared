package me.fapcs.shared.module.camouflage.packet

import kotlinx.serialization.Serializable
import me.fapcs.shared.communication.IPacket

@Serializable
@Suppress("unused")
data class SetIRLightPacket(val active: Boolean) : IPacket