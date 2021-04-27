package com.example.todoapp.model

import androidx.room.*

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg todo: Todo)

    @Query("Select * FROM todo WHERE is_done = 0 ORDER BY priority DESC ")
    suspend fun selectAllTodo(): List<Todo>

    @Query("SELECT * FROM todo WHERE uuid= :id")
    suspend fun selectTodo(id: Int): Todo

    @Query("UPDATE todo SET title=:title, notes=:notes, priority=:priority, is_done=:is_done WHERE uuid=:id")
    suspend fun update(title: String, notes: String, priority: Int, is_done: Int, id: Int)

    @Query("UPDATE todo SET is_done=:is_done WHERE uuid=:id")
    suspend fun updateIsDone(is_done:Int, id:Int)

    @Delete
    suspend fun deleteTodo(todo: Todo)
}