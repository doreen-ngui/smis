package com.example.smis.network

import com.example.smis.data.StudentResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("students")
    suspend fun getStudents(): Response<ApiResponse<List<Student>>>

    @POST("students")
    suspend fun createStudent(@Body student: StudentRequest): Response<ApiResponse<Student>>

    @GET("students/results")
    suspend fun getStudentResults(): Response<ApiResponse<List<StudentResult>>>
}

data class StudentRequest(
    val name: String,
    val regNumber: String,
    val course: String
)

data class Student(
    val id: Int,
    val name: String,
    val regNumber: String,
    val course: String
)



