package me.fapcs.shared.module.general.model

import kotlinx.serialization.Serializable

@Serializable
data class Color(val red: Int, val green: Int, val blue: Int) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Color) return false

        if (red != other.red) return false
        if (green != other.green) return false
        if (blue != other.blue) return false

        return true
    }

    override fun hashCode(): Int {
        var result = red
        result = 31 * result + green
        result = 31 * result + blue

        return result
    }

    @Suppress("unused")
    companion object {

        val BLUE = Color(16, 112, 194)
        val ORANGE = Color(255, 120, 23)
        val RED = Color(238, 28, 3)
        val WHITE = Color(255, 255, 255)
        val GREEN = Color(11, 143, 75)

    }

}