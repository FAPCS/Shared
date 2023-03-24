package me.fapcs.shared.module.siren.packet

import kotlinx.serialization.Serializable

@Serializable
data class SetVolumePacket(val volumeType: VolumeType, val volume: Int) {

    constructor(volumeType: VolumeType, volume: Volume) : this(volumeType, volume.value)

}

enum class VolumeType {

    MICROPHONE,
    HORN

}

enum class Volume(val value: Int) {

    MUTED(0),
    LOW(25),
    MEDIUM(50),
    HIGH(75),
    MAX(100);

}
