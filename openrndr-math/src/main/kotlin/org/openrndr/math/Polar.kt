package org.openrndr.math

import java.lang.Math.toDegrees
import kotlin.math.atan2

/**
 * Polar coordinate
 * Ref: https://en.wikipedia.org/wiki/Polar_coordinate_system
 * [theta] angle in degrees
 */
data class Polar(val theta: Double, val radius: Double = 1.0) {

    fun makeSafe() = Polar(
            radius,
            mod(theta, 360.0)
    )

    companion object {
        /**
         * Construct a polar coordinate representation for Cartesian vector 2
         */
        fun fromVector(vector: Vector2): Polar {
            val r = vector.length
            return Polar(
                    if (r == 0.0) 0.0 else toDegrees(atan2(vector.y, vector.x)),
                    r
            )
        }
    }

    /**
     * Construct a Cartesian coordinate from polar representation
     */
    val cartesian: Vector2
        get() {
            return Vector2.fromPolar(this)
        }

    operator fun plus(s: Polar) = Polar(theta + s.theta, radius + s.radius)
    operator fun minus(s: Polar) = Polar(theta - s.theta, radius - s.radius)
    operator fun times(s: Polar) = Polar(theta * s.theta, radius * s.radius)

    operator fun times(s: Double) = Polar(theta * s, radius * s)
    operator fun div(s: Double) = Polar(theta / s, radius / s)
}
