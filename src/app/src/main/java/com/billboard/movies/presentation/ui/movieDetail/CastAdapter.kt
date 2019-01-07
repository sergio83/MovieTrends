package com.billboard.movies.presentation.ui.movieDetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.billboard.movies.R
import com.billboard.movies.domain.model.Actor
import com.facebook.drawee.view.SimpleDraweeView

class CastAdapter(private var mList: List<Actor> = listOf()) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ActorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.title)
        val subtitleTextView: TextView = itemView.findViewById(R.id.subtitle)
        val actorImageView: SimpleDraweeView = itemView.findViewById(R.id.actorImageView)


        fun bind(item: Actor) {
            titleTextView.text = item.name
            subtitleTextView.text = item.character
            actorImageView.setImageURI(item.profileUri())
        }

    }

    fun size(): Int {
        return mList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.actor_item_view, parent, false)
        return ActorViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ActorViewHolder) {
            holder.bind(mList[position])
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return mList.size
    }

}