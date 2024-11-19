package com.salmonboy.dicodingevent.ui.upcoming

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
import com.salmonboy.dicodingevent.databinding.FragmentUpcomingBinding
import com.salmonboy.dicodingevent.ui.adapter.event.EventAdapter
import com.salmonboy.dicodingevent.ui.viewmodel.ViewModelFactory

class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        return binding?.root!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val upcomingViewModel: UpcomingViewModel by viewModels {
            factory
        }

        val eventAdapter = EventAdapter()
        setupRecyclerView()
        binding?.rvUpcomingEvent?.adapter = eventAdapter

        upcomingViewModel.getUpcomingDicodingEvent().observe(viewLifecycleOwner) {result->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding?.progressBar?.visibility =  View.VISIBLE
                    }
                    is Result.Success -> {
                        binding?.progressBar?.visibility = View.GONE
                        val eventData = result.data
                        Log.d("UpcomingFragment", "Events received: ${eventData.size}")
                        eventAdapter.submitList(eventData)
                        Log.d("UpcomingFragment", "Adapter updated with ${eventData.size} events")
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
//                    upcomingViewModel.filterEvents(query)
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
            GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false)
        } else {
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        }
        binding?.rvUpcomingEvent?.layoutManager = layoutManager
    }

}