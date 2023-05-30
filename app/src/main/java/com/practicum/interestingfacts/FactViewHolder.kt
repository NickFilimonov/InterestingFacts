package com.practicum.interestingfacts

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FactViewHolder(parent: ViewGroup):
    RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fact_item, parent, false)) {

    private val textFact: TextView = itemView.findViewById(R.id.text_fact)
    private val textSource: TextView = itemView.findViewById(R.id.text_source)

    fun bind(item: Fact) {
        textFact.text = item.text
        textSource.text = item.source
    }
}