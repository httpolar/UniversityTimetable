package moe.polar.timetable.plugins

import io.ktor.server.application.Application
import io.ktor.server.auth.authentication
import io.ktor.server.auth.bearer
import moe.polar.timetable.db.entities.Token
import moe.polar.timetable.db.tables.TokensTable
import moe.polar.timetable.db.entities.asPrincipal
import moe.polar.timetable.db.query

fun Application.configureSecurity() {
    authentication {
        bearer {
            authenticate { credential ->
                query {
                    Token.find { TokensTable.token eq credential.token }.firstOrNull()?.asPrincipal()
                }
            }
        }
    }
}
