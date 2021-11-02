package com.example.fullnotesapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fullnotesapp.MyAdapter
import kotlinx.coroutines.*
class NotesList: Fragment() {
    private lateinit var rvNotes: RecyclerView
    private lateinit var mainRV: MyAdapter
    private lateinit var editText: EditText
    private lateinit var submitBtn: Button
    private lateinit var notesList: ArrayList<NoteData>

    //lateinit var sharedPreferences: SharedPreferences
    lateinit var myViewModel: MyViewModel

    lateinit var sharedPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_back_to_list, container, false)

             sharedPreferences = requireActivity().getSharedPreferences(
            getString(R.string.file_key), Context.MODE_PRIVATE
        )

        notesList = arrayListOf()
        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        myViewModel.getNotes().observe(viewLifecycleOwner, { notes -> mainRV.update(notes) })

        editText = view.findViewById(R.id.tvNewNote)
        submitBtn = view.findViewById(R.id.btSubmit)

        submitBtn.setOnClickListener {
            if (editText.text.isNotEmpty()) {
                myViewModel.postNote(NoteData("", editText.text.toString()))
                editText.text.clear()
                editText.clearFocus()
            } else {
                Toast.makeText(view.context, "Error: Please Enter something!", Toast.LENGTH_LONG)
                    .show()
            }
        }
        rvNotes = view.findViewById(R.id.rvNotes)
        mainRV = MyAdapter(this)
        rvNotes.adapter = mainRV

        rvNotes.layoutManager = LinearLayoutManager(requireContext())
        rvNotes.adapter?.notifyDataSetChanged()

        myViewModel.getNotes()
        return view
    }

    override fun onResume() {
        super.onResume()
        // We call the 'getData' function from our ViewModel after a one second delay because Firestore takes some time
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            myViewModel.getNotes()
        }
    }
}

