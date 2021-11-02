package com.example.fullnotesapp


import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.fullnotesapp.databinding.NoteRowBinding
import kotlinx.android.synthetic.main.note_row.view.*

class MyAdapter(private val notesList: NotesList): RecyclerView.Adapter<MyAdapter.ItemViewHolder>() {
    private var items =  emptyList<NoteData>()
    class ItemViewHolder(val binding: NoteRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            NoteRowBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false)
            )

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]

        holder.binding.apply {
            tv.text = item.notesText
              //  .. if (position % 2 == 0) {
                notes.setBackgroundColor(Color.GRAY)
            //}
            editIcon.setOnClickListener {
            with(notesList.sharedPreferences.edit()){
            putString("NoteID", item.id)
                apply()
            }
                notesList.findNavController().navigate(R.id.action_back_to_list_to_updatepage)
            }
            delIcon.setOnClickListener {
                notesList.myViewModel.deleteNote(item.id)
            }
        }
    }

    override fun getItemCount() = items.size

    fun update(notes: List<NoteData>){
        this.items = notes
        this.notifyDataSetChanged()
    }
}