package com.example.smis

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.smis.data.StudentDatabase
import com.example.smis.repository.StudentRepository
import com.example.smis.viewmodel.StudentViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: StudentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize database and viewmodel
        val database = StudentDatabase.getInstance(this)
        val repository = StudentRepository(database.studentDao())
        viewModel = ViewModelProvider(this,
            ViewModelFactory(repository))[StudentViewModel::class.java]

        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val btnViewStudents = findViewById<Button>(R.id.btnViewStudents)
        val btnGenerateReport = findViewById<Button>(R.id.btnGenerateReport)

        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterStudentActivity::class.java))
        }

        btnViewStudents.setOnClickListener {
            startActivity(Intent(this, ViewStudentsActivity::class.java))
        }

        btnGenerateReport.setOnClickListener {
            startActivity(Intent(this, GenerateReportActivity::class.java))
        }

    }
}

// ViewModel Factory
class ViewModelFactory(private val repository: StudentRepository) : ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StudentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}