package moe.polar.timetable.plugins

import io.ktor.server.application.Application
import io.ktor.server.auth.authentication
import io.ktor.server.auth.bearer
import moe.polar.timetable.db.entities.Token
import moe.polar.timetable.db.tables.Tokens
import moe.polar.timetable.db.entities.asPrincipal
import moe.polar.timetable.db.query

fun Application.configureSecurity() {
    authentication {
        bearer {
            authenticate { credential ->
                query {
                    Token.find { Tokens.token eq credential.token }.firstOrNull()?.asPrincipal()
                }
            }
        }
    }
}
