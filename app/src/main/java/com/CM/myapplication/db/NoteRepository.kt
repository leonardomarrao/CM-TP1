package com.CM.myapplication.db

import androidx.lifecycle.LiveData
import com.CM.myapplication.dao.NoteDao
import com.CM.myapplication.entities.Note


class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: LiveData<List<Note>> = noteDao.getAllCities()

    fun getCitiesByCountry(nota: String): LiveData<List<Note>> {
        return noteDao.getCitiesByCountry(nota)
    }

    fun getCountryFromCity(titulo: String): LiveData<Note> {
        return noteDao.getCountryFromCity(titulo)
    }

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun deleteAll(){
        noteDao.deleteAll()
    }

    suspend fun deleteByCity(titulo: String){
        noteDao.deleteByCity(titulo)
    }

    suspend fun updateCity(note: Note) {
        noteDao.updateCity(note)
    }

    suspend fun updateCountryFromCity(titulo: String, nota: String){
        noteDao.updateCountryFromCity(titulo, nota)
    }
}