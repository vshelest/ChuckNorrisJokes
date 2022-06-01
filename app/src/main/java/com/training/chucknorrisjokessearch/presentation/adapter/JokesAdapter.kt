package com.training.chucknorrisjokessearch.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.training.chucknorrisjokessearch.R
import com.training.chucknorrisjokessearch.domain.entity.Joke
import com.training.chucknorrisjokessearch.presentation.viewholder.JokesViewHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@SuppressLint("NotifyDataSetChanged")
class JokesAdapter(
    jokesFlow: Flow<List<Joke>>
) : RecyclerView.Adapter<JokesViewHolder>() {

    //TODO: add pagination

    private val items: MutableList<Joke> = mutableListOf()

    init {
        jokesFlow.onEach {
            items.clear()
            items.addAll(it)
            notifyDataSetChanged()
        }.launchIn(CoroutineScope(Dispatchers.Main))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokesViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_joke, parent, false)
        return JokesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: JokesViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}