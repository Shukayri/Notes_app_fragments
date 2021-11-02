package com.example.fullnotesapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

class UpdatePage : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_updatepage, container, false)

        val sharedPreferences = requireActivity().getSharedPreferences(
            getString(R.string.file_key), Context.MODE_PRIVATE)

            val updateViewModel = ViewModelProvider(this).get(UpdateViewModel::class.java)

        val etNote = view.findViewById<EditText>(R.id.updateNoteET)
        val btUpdate = view.findViewById<Button>(R.id.updateNoteBTN)
        btUpdate.setOnClickListener {

            if(etNote.text.isNotEmpty()){
                val noteId = sharedPreferences.getString("NoteID", "").toString()
            updateViewModel.editNote(noteId, etNote.text.toString())
            with(sharedPreferences.edit()) {
                putString("NoteID", etNote.text.toString())
                apply()
            }
            findNavController().navigate(R.id.action_updatepage_to_back_to_list)
        }
            else{
                Toast.makeText(view.context, "Error: Please Enter something!", Toast.LENGTH_LONG)
                    .show()
            }


    }
        return view
}}