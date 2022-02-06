package com.example.plantapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.plantapp.R

// Maybe I should've adapted (get it?) the PlantListAdapter to do both
// But I am just too tired to think about how to do that right now

class SearchResultAdapter(private val resultList: Array<String>) :
    RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {

    // Reference to view in custom ViewHolder
    class ViewHolder(private var view: View) : RecyclerView.ViewHolder(view) {
        // Todo search result class to bind

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchResultAdapter.ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.plant_list_item, parent, false)

        return SearchResultAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchResultAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount() = resultList.size

}