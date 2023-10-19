package moe.polar.timetable.enums

import kotlinx.serialization.Serializable

@Serializable
enum class WeekType(override val value: Int) : IntEnum {
    ODD(1),
    EVEN(2),
}
