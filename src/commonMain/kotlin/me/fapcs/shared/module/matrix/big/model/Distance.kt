package me.fapcs.shared.module.matrix.big.model

import kotlinx.serialization.Serializable

@Serializable
data class Distance(val distance: Double, val unit: DistanceUnit)

@Suppress("unused")
enum class DistanceUnit {

    METER,
    KILOMETER

}