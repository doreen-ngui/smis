package com.example.smis.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.smis.R
import com.example.smis.data.Student

class StudentAdapter(
    private val onItemClick: (Student) -> Unit
) : ListAdapter<Student, StudentAdapter.StudentViewHolder>(StudentDiffCallback()) {

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.tvStudentName)
        private val regTextView: TextView = itemView.findViewById(R.id.tvRegNumber)
        private val courseTextView: TextView = itemView.findViewById(R.id.tvCourse)

        fun bind(student: Student) {
            nameTextView.text = student.name
            regTextView.text = student.regNumber
            courseTextView.text = student.course
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = getItem(position)
        holder.bind(student)
        holder.itemView.setOnClickListener {
            onItemClick(student)
        }
    }
}

class StudentDiffCallback : DiffUtil.ItemCallback<Student>() {
    override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
        return oldItem == newItem
    }
}