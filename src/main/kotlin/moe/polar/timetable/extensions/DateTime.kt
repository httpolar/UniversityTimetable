package moe.polar.timetable.extensions

import java.time.temporal.WeekFields
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.asTimeZone
import kotlinx.datetime.toJavaLocalDate
import moe.polar.timetable.enums.WeekType

val LOCAL_TZ: TimeZone = UtcOffset(hours = +4).asTimeZone()

val LocalDate.isoWeekNumber: Int
    get() = toJavaLocalDate().get(WeekFields.ISO.weekOfWeekBasedYear())

val LocalDate.weekType: WeekType
    get() = if (isoWeekNumber % 2 == 0) {
        WeekType.EVEN
    } else {
        WeekType.ODD
    }