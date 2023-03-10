package me.fapcs.shared.module.light.model

import kotlinx.serialization.Serializable
import me.fapcs.shared.module.light.model.group.GroupConfiguration
import me.fapcs.shared.module.light.model.lamp.Lamp
import me.fapcs.shared.module.light.model.lamp.LampConfiguration

@Serializable
data class LightConfiguration(
    val groups: List<GroupConfiguration>,
    val lamps: Map<Lamp, LampConfiguration>
)