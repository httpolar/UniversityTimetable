package moe.polar.timetable.db.entities

import java.time.DayOfWeek
import java.util.UUID
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import moe.polar.timetable.db.tables.LessonsTable
import moe.polar.timetable.enums.LessonType
import moe.polar.timetable.enums.WeekType
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Lesson(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Lesson>(LessonsTable)

    var name by LessonsTable.name
    var type by LessonsTable.type
    var dayOfWeek by LessonsTable.dayOfWeek
    var weekType by LessonsTable.weekType
    var majorGroup by LessonsTable.majorGroup
    var subGroup by LessonsTable.subGroup
    var auditorium by LessonsTable.auditorium
    var teacher by LessonsTable.teacher
    var startsAt by LessonsTable.startsAt
    var endsAt by LessonsTable.endsAt

    fun toSerializable(): LessonSerializable {
        return LessonSerializable(
            id = id.value,
            name,
            type,
            dayOfWeek,
            weekType,
            majorGroup,
            subGroup,
            auditorium,
            teacher,
            startsAt,
            endsAt
        )
    }
}

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class LessonSerializable(
    @Contextual
    val id: UUID? = null,
    val name: String,
    val type: LessonType,
    val dayOfWeek: DayOfWeek,
    val weekType: WeekType,
    val majorGroup: String,
    @EncodeDefault
    val subGroup: Short = -1,
    val auditorium: String?,
    val teacher: String?,
    val startsAt: LocalTime,
    val endsAt: LocalTime,
)
