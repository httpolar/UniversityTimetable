package moe.polar.timetable.plugins

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import moe.polar.timetable.serializers.UUIDSerializer


private val jsonFormat: Json = Json {
    serializersModule = SerializersModule {
        contextual(UUIDSerializer)
    }
}

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(jsonFormat)
    }
}
