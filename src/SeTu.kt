package dev.hikari

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ResultRow

@Serializable
data class SeTu(
    var _id: Int = 0,
    var title: String = "",
    var artwork: Int = 0,
    var author: String = "",
    var artist: Int = 0,
    var page: Int = 0,
    var tags: String = "",
    var type: String = "",
    var filename: String = "",
    var original: String = "",
    var large: String = "",
    var medium: String = "",
    var square_medium: String = "",
) {
    fun saveBy(resultRow: ResultRow): SeTu {
        _id = resultRow[SeTuTable.id].value
        title = resultRow[SeTuTable.title]
        artwork = resultRow[SeTuTable.artwork]
        author = resultRow[SeTuTable.author]
        artist = resultRow[SeTuTable.artist]
        page = resultRow[SeTuTable.page]
        tags = resultRow[SeTuTable.tags]
        type = resultRow[SeTuTable.type]
        filename = resultRow[SeTuTable.filename]
        original = resultRow[SeTuTable.original]
        large = resultRow[SeTuTable.large]
        medium = resultRow[SeTuTable.medium]
        square_medium = resultRow[SeTuTable.square_medium]
        return this
    }
}