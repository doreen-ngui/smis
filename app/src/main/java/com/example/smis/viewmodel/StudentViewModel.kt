package com.example.smis.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smis.data.Student
import com.example.smis.repository.StudentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StudentViewModel(private val repository: StudentRepository) : ViewModel() {

    val allStudents = repository.getAllStudents()

    private val _insertResult = MutableStateFlow<Long?>(null)
    val insertResult: StateFlow<Long?> = _insertResult.asStateFlow()

    fun insertStudent(student: Student) {
        viewModelScope.launch {
            try {
                val result = repository.insertStudent(student)
                _insertResult.value = result
            } catch (e: Exception) {
                _insertResult.value = -1
            }
        }
    }

    fun clearInsertResult() {
        _insertResult.value = null
    }
}