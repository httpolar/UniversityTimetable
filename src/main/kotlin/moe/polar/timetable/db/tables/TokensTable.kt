package moe.polar.timetable.db.tables

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object TokensTable : UUIDTable() {
    val token = text("token").uniqueIndex()
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
    val accessedAt = datetime("accessed_at").defaultExpression(CurrentDateTime)
    val createdIpAddr = text("created_ip_addr").nullable()
    val accessedIpAddr = text("accessed_ip_addr").nullable()
}
