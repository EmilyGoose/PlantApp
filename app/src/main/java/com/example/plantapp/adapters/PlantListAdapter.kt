package com.example.plantapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.plantapp.R
import com.example.plantapp.SearchResultActivity
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropSquareTransformation
import org.json.JSONObject

class PlantListAdapter(private val plantList: List<JSONObject>) :
    RecyclerView.Adapter<PlantListAdapter.ViewHolder>() {

    // Reference to view in custom ViewHolder
    class ViewHolder(private var view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        // Todo make a plant class or something idk (figure it out later lmao)
        private lateinit var plantName: String
        private lateinit var plant: JSONObject

        fun bindToCard(plant: JSONObject) {
            // Set plant name and data
            this.plantName = plant.getString("title")
            this.plant = plant
            view.findViewById<TextView>(R.id.plant_name).text = this.plantName
            //Set plant image
            val imgSrc = plant.getJSONObject("thumbnail").getString("source")
            Picasso.get().load(imgSrc).transform(CropSquareTransformation()).into(view.findViewById<ImageView>(R.id.plant_picture));
        }

        override fun onClick(clickedView: View?) {
            // Open the info page for the plant
            val intent = Intent(clickedView?.context, SearchResultActivity::class.java)
            intent.putExtra("data", this.plant.toString())
            // start activity
            clickedView?.context?.startActivity(intent)
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
        holder.bindToCard(plant)
    }

    // Fun shortcut, we love kotlin
    override fun getItemCount() = plantList.size

}