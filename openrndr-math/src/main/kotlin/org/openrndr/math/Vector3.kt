package org.openrndr.math

import java.io.Serializable
import kotlin.math.*

/**
 * Double precision vector 3
 */
data class Vector3(val x: Double, val y: Double, val z: Double) : Serializable {
    constructor(x: Double) : this(x, x, x)

    operator fun invoke(x: Double = this.x, y: Double = this.y, z: Double = this.z) = Vector3(x, y, z)

    companion object {
        val ZERO = Vector3(0.0, 0.0, 0.0)
        val ONE = Vector3(1.0, 1.0, 1.0)
        val UNIT_X = Vector3(1.0, 0.0, 0.0)
        val UNIT_Y = Vector3(0.0, 1.0, 0.0)
        val UNIT_Z = Vector3(0.0, 0.0, 1.0)
        val INFINITY = Vector3(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)

        fun fromSpherical(s: Spherical): Vector3 {

            val phi = Math.toRadians(s.phi)
            val theta = Math.toRadians(s.theta)

            val sinPhiRadius = sin(phi) * s.radius
            return Vector3(
                    sinPhiRadius * sin(theta),
                    cos(phi) * s.radius,
                    sinPhiRadius * cos(theta))
        }
    }

    val xyz0 get() = Vector4(x, y, z, 0.0)
    val xyz1 get() = Vector4(x, y, z, 1.0)

    val xy get() = Vector2(x, y)
    val yx get() = Vector2(y, x)
    val zx get() = Vector2(z, x)
    val xz get() = Vector2(x, z)

    val normalized: Vector3
        get() {
            val l = 1.0 / length
            if (l.isNaN() || l.isInfinite()) {
                return ZERO
            }
            return this * l
        }

    infix fun reflect(surfaceNormal: Vector3) = this - surfaceNormal * (this dot surfaceNormal) * 2.0

    operator fun get(i: Int): Double {
        return when (i) {
            0 -> x
            1 -> y
            2 -> z
            else -> throw RuntimeException("unsupported index")
        }
    }

    operator fun unaryMinus() = Vector3(-x, -y, -z)
    operator fun plus(v: Vector3) = Vector3(x + v.x, y + v.y, z + v.z)
    operator fun plus(d: Double) = Vector3(x + d, y + d, z + d)
    operator fun minus(v: Vector3) = Vector3(x - v.x, y - v.y, z - v.z)
    operator fun minus(d: Double) = Vector3(x - d, y - d, z - d)
    operator fun times(v: Vector3) = Vector3(x * v.x, y * v.y, z * v.z)
    operator fun times(s: Double) = Vector3(x * s, y * s, z * s)
    operator fun div(s: Double) = Vector3(x / s, y / s, z / s)
    operator fun div(v: Vector3) = Vector3(x / v.x, y / v.y, z / v.z)

    infix fun dot(v: Vector3) = x * v.x + y * v.y + z * v.z

    infix fun cross(v: Vector3) = Vector3(
            y * v.z - z * v.y,
            -(x * v.z - z * v.x),
            x * v.y - y * v.x)

    infix fun projectedOn(v: Vector3) = (this dot v) / (v dot v) * v
    val length: Double get() = Math.sqrt(x * x + y * y + z * z)
    val squaredLength get() = x * x + y * y + z * z

    fun toDoubleArray() = doubleArrayOf(x, y, z)

    fun distanceTo(o: Vector3): Double {
        val dx = o.x - x
        val dy = o.y - y
        val dz = o.z - z
        return sqrt(dx * dx + dy * dy + dz * dz)
    }

    fun squaredDistanceTo(o: Vector3): Double {
        val dx = o.x - x
        val dy = o.y - y
        val dz = o.z - z
        return dx * dx + dy * dy + dz * dz
    }

    fun mix(o: Vector3, mix: Double): Vector3 = this * (1 - mix) + o * mix


    val spherical: Spherical
        get() {
            return Spherical.fromVector(this)
        }
}

operator fun Double.times(v: Vector3) = v * this

fun min(a: Vector3, b: Vector3) = Vector3(min(a.x, b.x), min(a.y, b.y), min(a.z, b.z))
fun max(a: Vector3, b: Vector3) = Vector3(max(a.x, b.x), max(a.y, b.y), max(a.z, b.z))

fun mix(a: Vector3, b: Vector3, mix:Double): Vector3 = a * (1 - mix) + b * mix
