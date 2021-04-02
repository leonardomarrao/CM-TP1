package com.CM.myapplication.dao


import androidx.lifecycle.LiveData
import androidx.room.*
import com.CM.myapplication.entities.Note

@Dao
interface NoteDao {

    @Query("SELECT * from note_table ORDER BY titulo ASC")
    fun getAllCities(): LiveData<List<Note>>

    @Query("SELECT * FROM note_table WHERE nota == :nota")
    fun getCitiesByCountry(nota: String): LiveData<List<Note>>

    @Query("SELECT * FROM note_table WHERE titulo == :titulo")
    fun getCountryFromCity(titulo: String): LiveData<Note>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Update
    suspend fun updateCity(note: Note)

    @Query("DELETE FROM note_table")
    suspend fun deleteAll()

    @Query("DELETE FROM note_table where titulo == :titulo")
    suspend fun deleteByCity(titulo: String)

    @Query("UPDATE note_table SET nota=:nota WHERE titulo == :titulo")
    suspend fun updateCountryFromCity(titulo: String, nota: String)
}