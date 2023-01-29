package me.fapcs.shared.module.stripe.packet

import kotlinx.serialization.Serializable
import me.fapcs.shared.communication.IPacket
import me.fapcs.shared.module.general.model.Color

@Serializable
data class SetLedPacket(val led: Int, val color: Color) : IPacket