package com.example.smis.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smis.R
import com.example.smis.data.StudentResult

class StudentResultsAdapter(private var studentResults: List<StudentResult>) :
    RecyclerView.Adapter<StudentResultsAdapter.StudentResultViewHolder>() {

    inner class StudentResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvStudentName)
        private val tvRegNumber: TextView = itemView.findViewById(R.id.tvRegNumber)
        private val tvCourse: TextView = itemView.findViewById(R.id.tvCourse)
        private val tvScore: TextView = itemView.findViewById(R.id.tvScore)
        private val tvGrade: TextView = itemView.findViewById(R.id.tvGrade)

        fun bind(studentResult: StudentResult) {
            tvName.text = studentResult.studentName
            tvRegNumber.text = studentResult.regNumber
            tvCourse.text = studentResult.course
            tvScore.text = "Marks: ${studentResult.marks}"
            tvGrade.text = "Grade: ${studentResult.grade}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentResultViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student_result, parent, false)
        return StudentResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentResultViewHolder, position: Int) {
        holder.bind(studentResults[position])
    }

    override fun getItemCount(): Int = studentResults.size

    fun updateData(newStudentResults: List<StudentResult>) {
        this.studentResults = newStudentResults
        notifyDataSetChanged()
    }
}