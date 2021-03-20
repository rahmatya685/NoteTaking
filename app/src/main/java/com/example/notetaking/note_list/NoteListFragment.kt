package com.example.notetaking.note_list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notetaking.R
import com.example.notetaking.databinding.NoteListFragmentBinding
import com.example.notetaking.getViewModelsFactory

class NoteListFragment : Fragment() {

    private var _binding: NoteListFragmentBinding? = null

    private val viewModel by viewModels<NoteListViewModel> { getViewModelsFactory() }

    private val binding get() = _binding!!

    private val adator = NoteListAdaptor()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NoteListFragmentBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        btnAdd.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.addNoteFragment))
        rvNotes.adapter = adator
        rvNotes.layoutManager = LinearLayoutManager(requireContext())
        viewModel.notes.observe(viewLifecycleOwner, {
            it?.let { notes ->
                binding.also {
                    if (notes.isNotEmpty()) {
                        dataLayout.visibility = View.VISIBLE
                        noDataLayout.visibility = View.GONE
                    } else {
                        dataLayout.visibility = View.GONE
                        noDataLayout.visibility = View.VISIBLE
                    }
                }
                adator.submitList(notes)
            }
        })
        viewModel.loadNotes()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}