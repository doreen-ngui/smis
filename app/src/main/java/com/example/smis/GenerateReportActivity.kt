package com.example.smis

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.smis.viewmodel.ResultsViewModel

class GenerateReportActivity : AppCompatActivity() {

    private lateinit var viewModel: ResultsViewModel
    private lateinit var tvResults: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_report)

        tvResults = findViewById(R.id.tvResults)
        viewModel = ViewModelProvider(this)[ResultsViewModel::class.java]

        // Observe results
        viewModel.studentResults.observe(this) { results ->
            val resultsText = StringBuilder()
            results.forEach { result ->
                resultsText.append("${result.studentName} - ${result.grade}\n")
            }
            tvResults.text = resultsText.toString()
        }

        viewModel.loading.observe(this) { loading ->
            if (loading) {
                tvResults.text = "Loading..."
            }
        }

        viewModel.errorMessage.observe(this) { error ->
            if (error != null) {
                tvResults.text = "Error: $error"
            }
        }

        // Fetch results
        viewModel.fetchStudentResults()
    }
}