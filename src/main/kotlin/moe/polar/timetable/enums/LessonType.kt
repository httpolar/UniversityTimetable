package moe.polar.timetable.enums

enum class LessonType(override val value: Int) : IntEnum {
    LECTURE(1),
    PRACTICUM(2),
    LAB(3),
}