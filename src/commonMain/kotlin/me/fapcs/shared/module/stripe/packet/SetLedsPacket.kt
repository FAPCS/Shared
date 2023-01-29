package me.fapcs.shared.module.stripe.packet

import kotlinx.serialization.Serializable
import me.fapcs.shared.communication.IPacket
import me.fapcs.shared.module.general.model.Color

@Serializable
@Suppress("unused")
data class SetLedsPacket(val leds: IntArray, val color: Color) : IPacket {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SetLedPacket) return false

        other as SetLedsPacket

        if (!leds.contentEquals(other.leds)) return false
        if (color != other.color) return false

        return true
    }

    override fun hashCode(): Int {
        var result = leds.contentHashCode()
        result = 31 * result + color.hashCode()

        return result
    }

}