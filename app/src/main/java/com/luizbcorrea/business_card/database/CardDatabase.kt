package com.luizbcorrea.business_card.database

import android.content.Context
import androidx.room.*
import com.luizbcorrea.business_card.database.daos.CardDao
import com.luizbcorrea.business_card.database.models.Card

@Database( entities = [Card::class], version = 1,exportSchema = false)
abstract class CardDatabase : RoomDatabase() {

    abstract fun taskDao(): CardDao


    companion object {
        @Volatile
        private var INSTANCE: CardDatabase? = null

        fun getDatabase(ctx: Context): CardDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    ctx.applicationContext,
                    CardDatabase::class.java,
                    "cards_table"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
