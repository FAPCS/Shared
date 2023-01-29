package me.fapcs.shared.module.light.model

import kotlinx.serialization.Serializable
import me.fapcs.shared.module.general.model.Color
import me.fapcs.shared.module.light.model.pattern.LampPattern

@Serializable
data class LampConfiguration(
    val color: Color,
    val pattern: LampPattern,
    val intensity: Intensity
)
