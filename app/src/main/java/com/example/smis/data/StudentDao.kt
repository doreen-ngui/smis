package com.example.smis.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {
    @Insert
    suspend fun insertStudent(student: Student): Long

    @Query("SELECT * FROM students ORDER BY name ASC")
    fun getAllStudents(): Flow<List<Student>>

    @Query("SELECT * FROM students WHERE regNumber = :regNumber")
    suspend fun getStudentByRegNumber(regNumber: String): Student?
}