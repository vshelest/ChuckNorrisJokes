package com.training.chucknorrisjokessearch.presentation.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.training.chucknorrisjokessearch.R
import com.training.chucknorrisjokessearch.domain.entity.Joke

class JokesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var txtJoke: TextView

    init {
        txtJoke = itemView.findViewById(R.id.txt_joke)
    }

    fun bind(item: Joke) {
        txtJoke.text = item.text
    }
}