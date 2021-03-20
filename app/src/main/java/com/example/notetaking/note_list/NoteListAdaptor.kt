package com.example.notetaking.note_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notetaking.databinding.ItemNoteBinding
import com.example.notetaking.inflater
import com.example.notetaking.model.Note


/**
 * Adapter for the note list.
 * Has a reference to the [NoteListViewModel] to send actions back to it.
 */
class NoteListAdaptor() : ListAdapter<Note, NoteListAdaptor.ViewHolder>(NoteDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))


    class ViewHolder private constructor(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.note = note
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val binding = ItemNoteBinding.inflate(parent.inflater(), parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class NoteDiffUtil : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note):
            Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Note, newItem: Note):
            Boolean = oldItem == newItem
}