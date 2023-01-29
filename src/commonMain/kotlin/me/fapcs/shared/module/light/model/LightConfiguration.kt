package me.fapcs.shared.module.light.model

import kotlinx.serialization.Serializable

@Serializable
data class LightConfiguration(
    val groups: List<GroupConfiguration>,
    val lamps: Map<Lamp, LampConfiguration>
)