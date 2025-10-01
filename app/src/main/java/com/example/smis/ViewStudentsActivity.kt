package com.example.smis

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smis.adapter.StudentAdapter
import com.example.smis.data.StudentDatabase
import com.example.smis.repository.StudentRepository
import com.example.smis.viewmodel.StudentViewModel
import kotlinx.coroutines.launch

class ViewStudentsActivity : AppCompatActivity() {

    private lateinit var viewModel: StudentViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_students)

        // Initialize viewmodel
        val database = StudentDatabase.getInstance(this)
        val repository = StudentRepository(database.studentDao())
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(repository)
        )[StudentViewModel::class.java]

        recyclerView = findViewById(R.id.recyclerViewStudents)
        adapter = StudentAdapter { student ->
            // Handle item click - navigate to student details
            val intent = Intent(this, StudentDetailsActivity::class.java).apply {
                putExtra("STUDENT_NAME", student.name)
                putExtra("STUDENT_COURSE", student.course)
                putExtra("STUDENT_REG", student.regNumber)
            }
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Observe students list using Flow
        lifecycleScope.launch {
            viewModel.allStudents.collect { students ->
                adapter.submitList(students)
            }
        }
    }
}