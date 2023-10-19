package moe.polar.timetable

import io.ktor.server.application.Application
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import moe.polar.timetable.db.generateDataSource
import moe.polar.timetable.db.hikariProperties
import moe.polar.timetable.db.query
import moe.polar.timetable.db.tables.LessonsTable
import moe.polar.timetable.db.tables.TokensTable
import moe.polar.timetable.plugins.configureHTTP
import moe.polar.timetable.plugins.configureMonitoring
import moe.polar.timetable.plugins.configureRouting
import moe.polar.timetable.plugins.configureSecurity
import moe.polar.timetable.plugins.configureSerialization
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils


suspend fun main() {
    val hikariProps = hikariProperties()
    val dataSource = generateDataSource(hikariProps)

    Database.connect(dataSource)

    query {
        SchemaUtils.createMissingTablesAndColumns(LessonsTable, TokensTable)
    }

    embeddedServer(CIO, port = 8080, host = "127.0.0.1", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSecurity()
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureRouting()
}
