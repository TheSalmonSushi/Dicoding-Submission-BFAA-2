package com.salmonboy.dicodingevent.ui.finished

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.salmonboy.dicodingevent.data.Result
import com.salmonboy.dicodingevent.databinding.FragmentFinishedBinding
import com.salmonboy.dicodingevent.ui.adapter.event.EventAdapter
import com.salmonboy.dicodingevent.ui.viewmodel.ViewModelFactory

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding
    private val finishedViewModel: FinishedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        return binding?.root!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val finishedViewModel: FinishedViewModel by viewModels {
            factory
        }

        val eventAdapter = EventAdapter()

        setupRecyclerView()
        binding?.rvFinishedEvent?.adapter = eventAdapter

        finishedViewModel.getFinishedDicodingEvent().observe(viewLifecycleOwner) {result->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding?.progressBar?.visibility =  View.VISIBLE
                    }
                    is Result.Success -> {
                        binding?.progressBar?.visibility = View.GONE
                        val eventData = result.data
                        Log.d("Finished", "Events received: ${eventData.size}")
                        eventAdapter.submitList(eventData)
                        Log.d("Finished", "Adapter updated with ${eventData.size} events")
                    }
                    is Result.Error -> {
                        binding?.progressBar?.visibility = View.GONE
                        Toast.makeText(
                            context,
                            "Terjadi Kesalahan" + result.error,
                            Toast.LENGTH_SHORT
                        ) .show()
                    }
                }
            }
        }

//        with(binding) {
//            searchView.setupWithSearchBar(searchBar)
//            searchView
//                .editText
//                .setOnEditorActionListener { _, _, _ ->
//                    val query = searchView.text.toString()
//                    finishedViewModel.filterEvents(query)
//                    searchView.hide()
//                    searchBar.setText(query)
//                    false
//                }
//        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        val orientation = resources.configuration.orientation
        val layoutManager = if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            GridLayoutManager(requireContext(), 2, GridLayoutManager.HORIZONTAL, false)
        } else {
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        }
        binding?.rvFinishedEvent?.layoutManager = layoutManager
    }
}