package dev.hikari

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.event.Level
import java.util.*

fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)

@Suppress("UNUSED_PARAMETER") // Referenced in application.conf
@JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(CallLogging) {
        level = Level.DEBUG
        filter { call -> call.request.path().startsWith("/") }
    }

    install(DefaultHeaders) {
        header("X-Engine", "NM\$L") // will send this header with each response
    }

    install(ContentNegotiation) {
        gson { }
    }

    connectDatabase()

    routing {
        route("/api") {
            get("setu") {
                val setu = fetchSeTuFromDatabase()
                if (setu != null) {
                    call.respond(setu)
                } else {
                    call.respondText("Something went wrong.")
                }
            }
        }

    }
}

const val count = 11137
fun fetchSeTuFromDatabase(): SeTu? {
    var resultRow: ResultRow? = null
    transaction {
//        val count = SeTuTable.selectAll().count()
        val randomId = Random().nextInt(count) + 1
        resultRow = SeTuTable.select { SeTuTable.id eq randomId }.firstOrNull()
    }
    return if (resultRow == null) {
        null
    } else {
        SeTu().saveBy(resultRow!!)
    }
}

fun connectDatabase() {
    val properties =
        Properties().apply { load(Thread.currentThread().contextClassLoader.getResourceAsStream("local.properties")) }
    val config = HikariConfig().apply {
        jdbcUrl = properties.getProperty("jdbcUrl")
        driverClassName = "com.mysql.cj.jdbc.Driver"
        username = properties.getProperty("username")
        password = properties.getProperty("password")
        maximumPoolSize = 5
    }
    val dataSource = HikariDataSource(config)
    Database.connect(dataSource)
}

