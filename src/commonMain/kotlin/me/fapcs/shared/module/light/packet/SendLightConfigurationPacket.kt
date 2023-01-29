package me.fapcs.shared.module.light.packet

import kotlinx.serialization.Serializable
import me.fapcs.shared.communication.IPacket
import me.fapcs.shared.module.light.model.LightConfiguration

@Serializable
@Suppress("unused")
data class SendLightConfigurationPacket(val config: LightConfiguration) : IPacket