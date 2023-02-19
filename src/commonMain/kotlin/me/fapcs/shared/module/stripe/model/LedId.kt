package me.fapcs.shared.module.stripe.model

import kotlin.jvm.JvmName

enum class LedId(val leds: IntArray) {

    // <editor-fold desc="Lights">
    MIRROR_RIGHT(0..3),
    FRONT_RIGHT(4..7),
    RADIATOR_RIGHT(8..11),
    RADIATOR_LEFT(12..15),
    FRONT_LEFT(16..19),
    MIRROR_LEFT(20..23),
    SIDE_LEFT(24..27),
    BACK_CAR_LEFT(28..29),
    BACK_TRUNK_LEFT(30..31),
    TRUNK_LEFT(32..35),
    LICENSE_PLATE_LEFT(36..37),
    LICENSE_PLATE_RIGHT(38..39),
    TRUNK_RIGHT(40..43),
    BACK_TRUNK_RIGHT(44..45),
    BACK_CAR_RIGHT(46..47),
    SIDE_RIGHT(48..51),
    BACK_LEFT(52..55),
    BACK_RIGHT(56..59),
    // TODO: Check if this is correct
    // </editor-fold>
    // <editor-fold desc="Lightbar">
    // TODO: Add lightbar
    // </editor-fold>
    // <editor-fold desc="Matrix">
    // TODO: Add matrix
    // </editor-fold>
    ;

    constructor(leds: IntRange) : this(leds.toList().toIntArray())

}