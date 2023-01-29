@file:Suppress("unused")

package me.fapcs.shared.module.matrix.big.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.fapcs.shared.module.matrix.big.model.sign.AdditionalBoard
import me.fapcs.shared.module.matrix.big.model.sign.TrafficSign

@Serializable
sealed interface BigMatrixMode

@Serializable
@SerialName("one_sign")
class OneSignMode(val sign: TrafficSign, val blink: Boolean, val additionalBoard: AdditionalBoard) : BigMatrixMode

@Serializable
@SerialName("two_signs")
class TwoSignsMode(val sign1: TrafficSign, val sign2: TrafficSign, val blink: Boolean, val switch: Boolean) :
    BigMatrixMode

@Serializable
@SerialName("traffic_light")
class TrafficLightMode(val state: State) : BigMatrixMode {

    @Serializable
    enum class State {
        @SerialName("green")
        GREEN,

        @SerialName("green_blink")
        GREEN_BLINK,

        @SerialName("yellow")
        YELLOW,

        @SerialName("red")
        RED,

        @SerialName("red_yellow")
        RED_YELLOW;

        val next: State
            get() = when (this) {
                GREEN -> GREEN_BLINK
                GREEN_BLINK -> YELLOW
                YELLOW -> RED
                RED -> RED_YELLOW
                RED_YELLOW -> GREEN
            }
    }

}

@Serializable
@SerialName("direction")
data class DirectionMode(val direction: Direction) : BigMatrixMode {

    @Serializable
    enum class Direction {
        @SerialName("left")
        LEFT,

        @SerialName("right")
        RIGHT,

        @SerialName("not_here")
        NOT_HERE
    }

}

@Serializable
@SerialName("text")
data class TextMode(val text: String) : BigMatrixMode

@Serializable
@SerialName("road_block")
data class RoadBlockMode(val states: List<List<State>>, val animation: Boolean) : BigMatrixMode {

    @Serializable
    enum class State {
        BLOCK,
        OFF,
        LEFT,
        RIGHT,
        FREE
    }

}

@Serializable
@SerialName("ghost_driver")
data class GhostDriverMode(val lanes: Int, val fullBlock: Boolean) : BigMatrixMode

@Serializable
@SerialName("rescue_alley")
data class RescueAlleyMode(val blink: Boolean, val animation: Boolean) : BigMatrixMode

@Serializable
@SerialName("transition")
data class TransitionMode(
    val lanes: Int,
    val start: Boolean,
    val transitioned: Int,
    val left: Int,
    val right: Int
) : BigMatrixMode