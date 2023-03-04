@file:Suppress("unused")

package me.fapcs.shared.module.matrix.small.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.fapcs.shared.module.general.model.Color

@Serializable
sealed interface MatrixMode

@Serializable
@SerialName("static")
class StaticMode(val color: Color, val text: String) : MatrixMode

@Serializable
@SerialName("running_text")
class RunningTextMode(
    val color: Color,
    val directionMode: Direction,
    val clear: Boolean,
    val text: String
) : MatrixMode {

    enum class Direction {
        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT
    }

}

@Serializable
@SerialName("switching_text")
class SwitchingTextMode(val color: Color, val texts: List<String>) : MatrixMode

@Serializable
@SerialName("split_mode")
class SplitMode(val left: MatrixMode, val right: MatrixMode) : MatrixMode