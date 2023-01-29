package me.fapcs.shared.module.brightness.packet

import kotlinx.serialization.Serializable
import me.fapcs.shared.communication.IPacket

@Serializable
@Suppress("unused")
data class BrightnessChangePacket(val dark: Boolean) : IPacket