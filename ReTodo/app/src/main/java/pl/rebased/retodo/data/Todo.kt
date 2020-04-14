package pl.rebased.retodo.data

import androidx.room.*

typealias TodoList = List<Todo>

@Entity()
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var name: String,
    var completed: Boolean
)

@Dao
interface TodoDao {
    @Insert
    fun insert(item: Todo)

    @Update
    fun update(item: Todo)

    @Delete
    fun delete(item: Todo)

    @Query("SELECT * FROM todo WHERE NOT completed")
    fun getIncomplete(): TodoList

    @Query("SELECT * FROM todo")
    fun getAll(): TodoList
}