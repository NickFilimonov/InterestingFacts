package com.practicum.interestingfacts

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FactAdapter() : RecyclerView.Adapter<FactViewHolder> (){

    var facts = ArrayList<Fact>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactViewHolder = FactViewHolder(parent)

    override fun onBindViewHolder(holder: FactViewHolder, position: Int) {
        holder.bind(facts[position])
    }

    override fun getItemCount(): Int = facts.size
}