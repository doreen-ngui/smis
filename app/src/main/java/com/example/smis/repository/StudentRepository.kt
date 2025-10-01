package com.example.smis.repository

import com.example.smis.data.Student
import com.example.smis.data.StudentDao
import kotlinx.coroutines.flow.Flow

class StudentRepository(private val studentDao: StudentDao) {

    suspend fun insertStudent(student: Student): Long {
        return studentDao.insertStudent(student)
    }

    fun getAllStudents(): Flow<List<Student>> {
        return studentDao.getAllStudents()
    }

    suspend fun getStudentByRegNumber(regNumber: String): Student? {
        return studentDao.getStudentByRegNumber(regNumber)
    }
}