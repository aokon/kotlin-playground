package pl.rebased.retodo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities=[Todo::class], version=1)
abstract class TodoDatabase: RoomDatabase() {
    companion object {
        @Volatile private var instance: TodoDatabase? = null
        fun getInstance(context: Context): TodoDatabase {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(
                        context
                    ).also { instance = it }
            }
        }
        private fun buildDatabase(context: Context): TodoDatabase {
            return Room.databaseBuilder(context, TodoDatabase::class.java, "TodoApp")
                .fallbackToDestructiveMigration().allowMainThreadQueries()
                .createFromAsset("retodo.sqlite")
                .build()
        }
    }


    abstract fun dao(): TodoDao
}