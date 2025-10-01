package com.example.smis.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smis.network.RetrofitInstance
import com.example.smis.network.StudentResult
import kotlinx.coroutines.launch

class ResultsViewModel : ViewModel() {

    private val _studentResults = MutableLiveData<List<StudentResult>>()
    val studentResults: LiveData<List<StudentResult>> = _studentResults

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun fetchStudentResults() {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.apiService.getStudentResults()
                if (response.isSuccessful && response.body()?.success == true) {
                    _studentResults.value = response.body()?.data ?: emptyList()
                } else {
                    _errorMessage.value = "Failed to fetch results"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Network error: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}