package moe.polar.timetable.db.entities

import io.ktor.server.auth.Principal
import java.util.UUID
import kotlinx.datetime.LocalDateTime
import moe.polar.timetable.db.tables.Tokens
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Token(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Token>(Tokens)

    val token by Tokens.token
    val createdAt by Tokens.createdAt
    val accessedAt by Tokens.accessedAt
    val createdIpAddr by Tokens.createdIpAddr
    val accessedIpAddr by Tokens.accessedIpAddr
}

fun Token.asPrincipal() = TokenPrincipal(
    id = id.value,
    token = token,
    createdAt = createdAt,
    accessedAt = accessedAt,
    createdIpAddr = createdIpAddr,
    accessedIpAddr = accessedIpAddr,
)

data class TokenPrincipal(
    val id: UUID,
    val token: String,
    val createdAt: LocalDateTime,
    val accessedAt: LocalDateTime,
    val createdIpAddr: String? = null,
    val accessedIpAddr: String? = null,
) : Principal