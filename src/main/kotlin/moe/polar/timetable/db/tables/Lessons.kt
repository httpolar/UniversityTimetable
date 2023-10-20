package moe.polar.timetable.db.tables

import kotlinx.datetime.DayOfWeek
import moe.polar.timetable.db.intEnum
import moe.polar.timetable.enums.LessonType
import moe.polar.timetable.enums.WeekType
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.time

object Lessons : UUIDTable("lessons") {
    val name = text("name")
    val type = intEnum<LessonType>("type")
    val dayOfWeek = enumerationByName<DayOfWeek>("day_of_week", 9)
    val weekType = intEnum<WeekType>("week_type")
    val majorGroup = text("major_group")
    val subGroup = short("sub_group").default(-1)
    val auditorium = text("auditorium").nullable()
    val teacher = text("teacher").nullable()
    val startsAt = time("starts_at")
    val endsAt = time("ends_at")

    init {
        // same group can not have lessons going at the same time in a day
        uniqueIndex(majorGroup, subGroup, startsAt, weekType, dayOfWeek)
    }
}
