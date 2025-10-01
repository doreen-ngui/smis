package com.example.smis

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class StudentDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)

        val tvName = findViewById<TextView>(R.id.tvDetailName)
        val tvCourse = findViewById<TextView>(R.id.tvDetailCourse)
        val tvRegNumber = findViewById<TextView>(R.id.tvDetailRegNumber)

        val name = intent.getStringExtra("STUDENT_NAME") ?: ""
        val course = intent.getStringExtra("STUDENT_COURSE") ?: ""
        val regNumber = intent.getStringExtra("STUDENT_REG") ?: ""

        tvName.text = "Name: $name"
        tvCourse.text = "Course: $course"
        tvRegNumber.text = "Registration: $regNumber"
    }
}

