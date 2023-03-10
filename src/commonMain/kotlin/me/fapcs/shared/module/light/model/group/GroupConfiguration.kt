package me.fapcs.shared.module.light.model.group

import kotlinx.serialization.Serializable
import me.fapcs.shared.module.general.model.Color
import me.fapcs.shared.module.light.model.Intensity
import me.fapcs.shared.module.light.model.lamp.Lamp

@Serializable
data class GroupConfiguration(
    val color: Color,
    val pattern: GroupPattern,
    val lights: List<Lamp>,
    val intensity: Intensity
)