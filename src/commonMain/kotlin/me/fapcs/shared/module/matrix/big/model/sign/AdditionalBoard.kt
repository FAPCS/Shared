@file:Suppress("unused")

package me.fapcs.shared.module.matrix.big.model.sign

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.fapcs.shared.module.matrix.big.model.Distance

@Serializable
sealed interface AdditionalBoard

@Serializable
@SerialName("distance")
data class DistanceBoard(val distance: Distance) : AdditionalBoard

@Serializable
@SerialName("length")
data class LengthBoard(val length: Int) : AdditionalBoard

@Serializable
@SerialName("snow")
object SnowBoard : AdditionalBoard

@Serializable
@SerialName("rain")
object RainBoard : AdditionalBoard

@Serializable
@SerialName("text")
data class TextBoard(val text: String) : AdditionalBoard
