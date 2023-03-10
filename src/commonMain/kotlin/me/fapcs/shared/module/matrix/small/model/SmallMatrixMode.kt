@file:Suppress("unused")

package me.fapcs.shared.module.matrix.small.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.fapcs.shared.module.general.model.Color

@Serializable
sealed interface SmallMatrixMode

@Serializable
@SerialName("static")
class StaticMode(val color: Color, val text: String) : SmallMatrixMode

@Serializable
@SerialName("running_text")
class ScrollingMode(
    val color: Color,
    val direction: Direction,
    val loop: Boolean,
    val text: String
) : SmallMatrixMode {

    enum class Direction {
        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT
    }

}

@Serializable
@SerialName("switching_text")
class SwitchingMode(val color: Color, val texts: List<String>) : SmallMatrixMode

@Serializable
@SerialName("split_mode")
class SplitMode(val left: SmallMatrixMode, val right: SmallMatrixMode) : SmallMatrixMode