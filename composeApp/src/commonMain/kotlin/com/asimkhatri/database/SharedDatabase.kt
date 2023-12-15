package com.asimkhatri.database

class SharedDatabase(private val driverFactory: DatabaseDriverFactory) {
    private var database: AppDatabase? = null

    private fun initDatabase() {
        if (database == null) {
            database = AppDatabase(driverFactory.createDriver())
        }
    }

    suspend operator fun <R> invoke(block: suspend (AppDatabase) -> R): R {
        initDatabase()
        return block(database!!)
    }
}