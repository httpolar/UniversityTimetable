package moe.polar.timetable.db

import moe.polar.timetable.enums.IntEnum
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.vendors.currentDialect

inline fun <reified E> Table.intEnum(name: String): Column<E> where E : Enum<E>, E : IntEnum {
    return customEnumeration(
        name = name,
        sql = currentDialect.dataTypeProvider.integerType(),
        fromDb = { any -> enumValues<E>().first { it.value == any as Int } },
        toDb = { it.value },
    )
}
