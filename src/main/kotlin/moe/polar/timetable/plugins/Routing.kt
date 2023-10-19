package moe.polar.timetable.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.AuthenticationStrategy
import io.ktor.server.auth.authenticate
import io.ktor.server.plugins.autohead.AutoHeadResponse
import io.ktor.server.plugins.doublereceive.DoubleReceive
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.resources.Resources
import io.ktor.server.response.respondText
import io.ktor.server.routing.routing
import moe.polar.timetable.resources.configurePrivateTimetableResource
import moe.polar.timetable.resources.configurePublicTimetableResource


fun Application.configureRouting() {
    install(AutoHeadResponse)
    install(DoubleReceive)
    install(Resources)
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            val text = if (this@configureRouting.developmentMode) {
                "Internal Server Error\n---------------------\n${cause}"
            } else {
                "Internal Server Error"
            }

            call.respondText(text = text, status = HttpStatusCode.InternalServerError)
        }
    }

    routing {
        configurePublicTimetableResource()

        authenticate(strategy = AuthenticationStrategy.Required) {
            configurePrivateTimetableResource()
        }
    }
}
