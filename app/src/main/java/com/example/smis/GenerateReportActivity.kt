package com.example.smis

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.smis.R
import com.example.smis.adapter.StudentResultsAdapter
import com.example.smis.network.RetrofitInstance

class GenerateReportActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentResultsAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var tvEmpty: TextView
    private lateinit var btnGenerate: Button

    // Add logging tag
    private companion object {
        const val TAG = "GenerateReportActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_report)

        setupViews()
        setupRecyclerView()

        btnGenerate.setOnClickListener {
            generateStudentResults()
        }
    }

    private fun setupViews() {
        recyclerView = findViewById(R.id.rvStudentResults)
        progressBar = findViewById(R.id.progressBar)
        tvEmpty = findViewById(R.id.tvEmpty)
        btnGenerate = findViewById(R.id.btnGenerateReport)
    }

    private fun setupRecyclerView() {
        adapter = StudentResultsAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun generateStudentResults() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                showLoading(true)
                Log.d(TAG, "Starting to fetch student results...")

                val response = withContext(Dispatchers.IO) {
                    RetrofitInstance.apiService.getStudentResults()
                }

                Log.d(TAG, "Response received: ${response.isSuccessful}")
                Log.d(TAG, "Response code: ${response.code()}")

                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    Log.d(TAG, "API Response: $apiResponse")

                    if (apiResponse?.success == true) {
                        val studentResults = apiResponse.data ?: emptyList()
                        Log.d(TAG, "Student results count: ${studentResults.size}")

                        if (studentResults.isNotEmpty()) {
                            adapter.updateData(studentResults)
                            showEmptyState(false)
                            Toast.makeText(
                                this@GenerateReportActivity,
                                "Retrieved ${studentResults.size} student results",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            showEmptyState(true)
                            Toast.makeText(
                                this@GenerateReportActivity,
                                "No student results found",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@GenerateReportActivity,
                            "API returned error: ${apiResponse?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                        showEmptyState(true)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e(TAG, "API Error: ${response.code()} - ${response.message()}")
                    Log.e(TAG, "Error body: $errorBody")

                    Toast.makeText(
                        this@GenerateReportActivity,
                        "Failed to fetch data: ${response.code()} - ${response.message()}",
                        Toast.LENGTH_LONG
                    ).show()
                    showEmptyState(true)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Network error: ${e.message}", e)
                Toast.makeText(
                    this@GenerateReportActivity,
                    "Network error: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
                showEmptyState(true)
            } finally {
                showLoading(false)
            }
        }
    }

    private fun showLoading(loading: Boolean) {
        progressBar.visibility = if (loading) android.view.View.VISIBLE else android.view.View.GONE
        btnGenerate.isEnabled = !loading
    }

    private fun showEmptyState(show: Boolean) {
        tvEmpty.visibility = if (show) android.view.View.VISIBLE else android.view.View.GONE
        recyclerView.visibility = if (show) android.view.View.GONE else android.view.View.VISIBLE
    }
}