package dev.hikari

import org.jetbrains.exposed.dao.id.IntIdTable

object SeTuTable : IntIdTable() {
    val title = text("title")
    val artwork = integer("artwork")
    val author = text("author")
    val artist = integer("artist")
    val page = integer("page")
    val tags = text("tags")
    val type = text("type")
    val filename = text("filename")
    val original = text("original")
    val large = text("large")
    val medium = text("medium")
    val square_medium = text("square_medium")
}