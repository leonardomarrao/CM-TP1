package com.CM.myapplication.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.CM.myapplication.db.NoteRepository
import com.CM.myapplication.db.NoteDB
import com.CM.myapplication.entities.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NoteRepository

    val allNotes: LiveData<List<Note>>

    init {
        val notesDao = NoteDB.getDatabase(application, viewModelScope).noteDao()
        repository = NoteRepository(notesDao)
        allNotes = repository.allNotes
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }

    // delete all
    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }

    // delete by city
    fun deleteByTitulo(city: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteByTitulo(city)
    }

    fun deleteById(id: Int?) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteById(id)
    }


    fun getNotaFromTitulo(city: String): LiveData<Note> {
        return repository.getNotaFromTitulo(city)
    }

    fun updateTitulo(note: Note) = viewModelScope.launch {
        repository.updateTitulo(note)
    }

    fun updateNotaFromTitulo(city: String, country: String) = viewModelScope.launch {
        repository.updateNotaFromTitulo(city, country)
    }

    fun updateNotaFromId(id: Int, titulo: String, nota: String) = viewModelScope.launch {
        repository.updateNotaFromId(id, titulo, nota)
    }






}

