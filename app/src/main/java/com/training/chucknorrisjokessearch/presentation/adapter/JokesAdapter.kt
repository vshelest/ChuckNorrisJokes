package com.training.chucknorrisjokessearch.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.training.chucknorrisjokessearch.R
import com.training.chucknorrisjokessearch.domain.model.Joke
import com.training.chucknorrisjokessearch.domain.model.JokeAction
import com.training.chucknorrisjokessearch.presentation.dialog.JokeActionMenu
import com.training.chucknorrisjokessearch.presentation.viewholder.JokesViewHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

@SuppressLint("NotifyDataSetChanged")
class JokesAdapter(
    jokesFlow: Flow<List<Joke>>
) : RecyclerView.Adapter<JokesViewHolder>() {

    //TODO: add pagination

    private val _longClickedJokeFlow: MutableSharedFlow<JokeAction> = MutableSharedFlow()
    val longClickedJokeFlow: SharedFlow<JokeAction> = _longClickedJokeFlow.asSharedFlow()

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

        itemView.setOnLongClickListener(onJokeLongClickListener)

        return JokesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: JokesViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private val onJokeLongClickListener = View.OnLongClickListener {
        val menu = JokeActionMenu(it)

        true
    }
}