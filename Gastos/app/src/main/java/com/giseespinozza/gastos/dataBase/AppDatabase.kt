package com.giseespinozza.gastos.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.giseespinozza.gastos.data.dao.MedicionDao
import com.giseespinozza.gastos.data.entity.Medicion

@Database(entities = [Medicion::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun medicionDao(): MedicionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mediciones.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
