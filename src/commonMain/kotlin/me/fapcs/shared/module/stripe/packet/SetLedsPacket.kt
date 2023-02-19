package me.fapcs.shared.module.stripe.packet

import kotlinx.serialization.Serializable
import me.fapcs.shared.communication.IPacket
import me.fapcs.shared.module.general.model.Color
import me.fapcs.shared.module.stripe.model.LedId
import me.fapcs.shared.module.stripe.model.LedStripe

@Serializable
@Suppress("unused")
data class SetLedsPacket internal constructor(val stripe: Int, val color: Color, val leds: IntArray) : IPacket {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as SetLedsPacket

        if (stripe != other.stripe) return false
        if (!leds.contentEquals(other.leds)) return false
        if (color != other.color) return false

        return true
    }

    override fun hashCode(): Int {
        var result = stripe
        result = 31 * result + leds.contentHashCode()
        result = 31 * result + color.hashCode()
        return result
    }

    companion object {

        fun create(stripe: Int, color: Color, leds: IntArray) = SetLedsPacket(stripe, color, leds)

        fun create(stripe: Int, color: Color, vararg leds: Int) = SetLedsPacket(stripe, color, leds)

        fun create(stripe: Int, color: Color, leds: IntRange) = SetLedsPacket(stripe, color, leds.toList().toIntArray())

        fun create(stripe: Int, color: Color, leds: LedId) = SetLedsPacket(stripe, color, leds.leds)

        fun create(stripe: LedStripe, color: Color, leds: IntArray) = SetLedsPacket(stripe.id, color, leds)

        fun create(stripe: LedStripe, color: Color, vararg leds: Int) = SetLedsPacket(stripe.id, color, leds)

        fun create(stripe: LedStripe, color: Color, leds: IntRange) =
            SetLedsPacket(stripe.id, color, leds.toList().toIntArray())

        fun create(stripe: LedStripe, color: Color, leds: LedId) = SetLedsPacket(stripe.id, color, leds.leds)

    }

}