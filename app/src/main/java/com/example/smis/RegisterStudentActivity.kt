package com.example.smis

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.smis.data.Student
import com.example.smis.data.StudentDatabase
import com.example.smis.repository.StudentRepository
import com.example.smis.viewmodel.StudentViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RegisterStudentActivity : AppCompatActivity() {

    private lateinit var viewModel: StudentViewModel
    private lateinit var etName: EditText
    private lateinit var etRegNumber: EditText
    private lateinit var etCourse: EditText
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_student)

        // Initialize viewmodel
        val database = StudentDatabase.getInstance(this)
        val repository = StudentRepository(database.studentDao())
        viewModel = ViewModelProvider(this,
            ViewModelFactory(repository))[StudentViewModel::class.java]

        etName = findViewById(R.id.etName)
        etRegNumber = findViewById(R.id.etRegNumber)
        etCourse = findViewById(R.id.etCourse)
        btnRegister = findViewById(R.id.btnRegisterStudent)

        btnRegister.setOnClickListener {
            registerStudent()
        }

        // Observe insertion result using StateFlow
        lifecycleScope.launch {
            viewModel.insertResult.collect { result ->
                if (result != null) {
                    if (result > 0) {
                        Toast.makeText(this@RegisterStudentActivity, "Student registered successfully!", Toast.LENGTH_SHORT).show()
                        // Navigate to students list
                        val intent = Intent(this@RegisterStudentActivity, ViewStudentsActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@RegisterStudentActivity, "Registration failed!", Toast.LENGTH_SHORT).show()
                    }
                    viewModel.clearInsertResult()
                }
            }
        }
    }

    private fun registerStudent() {
        val name = etName.text.toString().trim()
        val regNumber = etRegNumber.text.toString().trim()
        val course = etCourse.text.toString().trim()

        if (name.isEmpty() || regNumber.isEmpty() || course.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val student = Student(
            name = name,
            regNumber = regNumber,
            course = course
        )

        viewModel.insertStudent(student)
    }
}