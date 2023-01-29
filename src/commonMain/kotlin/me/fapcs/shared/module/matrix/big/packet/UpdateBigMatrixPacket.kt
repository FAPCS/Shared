package me.fapcs.shared.module.matrix.big.packet

import kotlinx.serialization.Serializable
import me.fapcs.shared.communication.IPacket
import me.fapcs.shared.module.matrix.big.model.BigMatrixMode

@Serializable
@Suppress("unused")
data class UpdateBigMatrixPacket(val mode: BigMatrixMode): IPacket