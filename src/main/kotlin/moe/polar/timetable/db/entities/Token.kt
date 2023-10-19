package moe.polar.timetable.db.entities

import io.ktor.server.auth.Principal
import java.util.UUID
import kotlinx.datetime.LocalDateTime
import moe.polar.timetable.db.tables.TokensTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Token(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Token>(TokensTable)

    val token by TokensTable.token
    val createdAt by TokensTable.createdAt
    val accessedAt by TokensTable.accessedAt
    val createdIpAddr by TokensTable.createdIpAddr
    val accessedIpAddr by TokensTable.accessedIpAddr
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