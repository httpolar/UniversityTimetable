package moe.polar.timetable.extensions

import org.jetbrains.exposed.exceptions.ExposedSQLException

fun ExposedSQLException.isUniqueConstraintViolation(): Boolean {
    return (this.sqlState == "23505")
}
