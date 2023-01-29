package me.fapcs.shared.module.siren.packet

import kotlinx.serialization.Serializable
import me.fapcs.shared.communication.IPacket
import me.fapcs.shared.module.siren.model.Siren

@Serializable
@Suppress("unused")
data class SetSirenPacket(val hornId: Int) : IPacket {

    constructor(horn: Siren) : this(horn.id)

}