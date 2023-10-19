package moe.polar.timetable.resources

import io.ktor.http.HttpStatusCode
import io.ktor.resources.Resource
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.resources.delete
import io.ktor.server.resources.get
import io.ktor.server.resources.patch
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import java.util.UUID
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.todayIn
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import moe.polar.timetable.db.entities.Lesson
import moe.polar.timetable.db.entities.LessonSerializable
import moe.polar.timetable.db.tables.LessonsTable
import moe.polar.timetable.extensions.LOCAL_TZ
import moe.polar.timetable.extensions.weekType
import moe.polar.timetable.db.query
import org.jetbrains.exposed.sql.and

@Resource("timetable")
class TimetableRoute(
    @Contextual
    val uuid: UUID? = null,
    val date: LocalDate = Clock.System.todayIn(LOCAL_TZ)
)

@Serializable
private class Requests {
    @Serializable
    data class Post(val data: List<LessonSerializable>)

    @Serializable
    data class Delete(val ids: List<@Contextual UUID>)
}

@Serializable
private class Responses {
    @Serializable
    data class Get(val date: LocalDate, val data: List<LessonSerializable>)

}

fun Route.configurePublicTimetableResource() {
    get<TimetableRoute> { it ->
        val id = it.uuid
        val date = it.date

        val result = query {
            Lesson.find {
                if (id != null) {
                    (LessonsTable.id eq id)
                } else {
                    (LessonsTable.weekType eq date.weekType) and (LessonsTable.dayOfWeek eq date.dayOfWeek)
                }
            }.map { lesson -> lesson.toSerializable() }
        }

        call.respond(Responses.Get(it.date, result))
    }
}

fun Route.configurePrivateTimetableResource() {
    post<TimetableRoute> {
        val body = call.receive<Requests.Post>()
        val lessons = body.data

        query {
            lessons.forEach { lesson ->
                Lesson.new {
                    dayOfWeek = lesson.dayOfWeek
                    weekType = lesson.weekType
                    name = lesson.name
                    type = lesson.type
                    majorGroup = lesson.majorGroup
                    subGroup = lesson.subGroup
                    auditorium = lesson.auditorium
                    teacher = lesson.teacher
                    startsAt = lesson.startsAt
                    endsAt = lesson.endsAt
                }
            }
        }

        call.respond(HttpStatusCode.OK)
    }

    patch<TimetableRoute> {
        call.respondText("this is PATCH response")
    }

    delete<TimetableRoute> {
        val body = call.receive<Requests.Delete>()

        val deletedLessons = buildList {
            query {
                Lesson.find { LessonsTable.id inList body.ids }.forEach { lesson ->
                    add(lesson.toSerializable())
                    lesson.delete()
                }
            }
        }

        call.respond(deletedLessons)
    }
}