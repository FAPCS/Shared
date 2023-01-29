package me.fapcs.shared.module.light.model

import kotlinx.serialization.Serializable
import me.fapcs.shared.module.general.model.Color
import me.fapcs.shared.module.light.model.pattern.GroupPattern

@Serializable
data class GroupConfiguration(
    val color: Color,
    val pattern: GroupPattern,
    val lights: List<Lamp>,
    val intensity: Intensity
)