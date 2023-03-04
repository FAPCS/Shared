package me.fapcs.shared.module.matrix.small.packet

import kotlinx.serialization.Serializable
import me.fapcs.shared.communication.IPacket
import me.fapcs.shared.module.matrix.small.model.SmallMatrixMode
import me.fapcs.shared.module.matrix.small.model.Side

@Serializable
@Suppress("unused")
data class UpdateSmallMatrixPacket(val side: Side, val mode: SmallMatrixMode): IPacket