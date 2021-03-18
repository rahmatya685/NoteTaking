package com.example.notetaking.note_list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.notetaking.R
import com.example.notetaking.databinding.NoteListFragmentBinding

class NoteListFragment : Fragment() {

    private var _binding: NoteListFragmentBinding? = null

    private val binding get() = _binding!!

    companion object {
        fun newInstance() = NoteListFragment()
    }

    private lateinit var viewModel: NoteListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding=NoteListFragmentBinding.inflate(inflater,container,false)
        return binding.root;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NoteListViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)= with(binding) {
        super.onViewCreated(view, savedInstanceState)
        btnAdd.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.addNoteFragment))

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}