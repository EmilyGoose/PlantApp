package com.example.plantapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.plantapp.R

class PlantListAdapter(private val plantList: Array<String>) :
    RecyclerView.Adapter<PlantListAdapter.ViewHolder>() {

    // Reference to view in custom ViewHolder
    class ViewHolder(private var view: View) : RecyclerView.ViewHolder(view) {
        // Todo make a plant class or something idk (figure it out later lmao)
        private lateinit var plantName: String

        fun testBindText(text: String) {
            this.plantName = text
            view.findViewById<TextView>(R.id.plant_name).text = this.plantName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.plant_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get plant for the list position
        val plant = plantList[position]
        // Bind it to the custom ViewHolder class
        holder.testBindText(plant)

    }

    // Fun shortcut, we love kotlin
    override fun getItemCount() = plantList.size

}