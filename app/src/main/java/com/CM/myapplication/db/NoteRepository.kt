package com.CM.myapplication.db

import androidx.lifecycle.LiveData
import com.CM.myapplication.dao.NoteDao
import com.CM.myapplication.entities.Note


class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()



    fun getNotaFromTitulo(titulo: String): LiveData<Note> {
        return noteDao.getNotaFromTitulo(titulo)
    }

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun deleteAll(){
        noteDao.deleteAll()
    }

    suspend fun deleteByTitulo(titulo: String){
        noteDao.deleteByTitulo(titulo)
    }

    suspend fun updateTitulo(note: Note) {
        noteDao.updateTitulo(note)
    }

    suspend fun updateNotaFromTitulo(titulo: String, nota: String){
        noteDao.updateNotaFromTitulo(titulo, nota)
    }

    suspend fun deleteById(id: Int?) {
        noteDao.deleteById(id)
    }
}