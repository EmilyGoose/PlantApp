package com.example.plantapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.plantapp.R
import org.json.JSONObject

class CommentsAdapter(private val commentList: List<JSONObject>) :
    RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    // Reference to view in custom ViewHolder
    class ViewHolder(private var view: View) : RecyclerView.ViewHolder(view) {
        fun bindToCard(comment: JSONObject) {
            val authorName = comment.getString("author")
            val commentBody = comment.getString("body")

            view.findViewById<TextView>(R.id.comment_name).text = authorName
            view.findViewById<TextView>(R.id.comment_text).text = commentBody
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsAdapter.ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.comment, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get plant for the list position
        val comment = commentList[position]
        // Bind it to the custom ViewHolder class
        holder.bindToCard(comment)
    }

    override fun getItemCount() = commentList.size

}