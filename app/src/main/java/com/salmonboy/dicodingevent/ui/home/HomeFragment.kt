package com.salmonboy.dicodingevent.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.salmonboy.dicodingevent.data.Result
import com.salmonboy.dicodingevent.databinding.FragmentHomeBinding
import com.salmonboy.dicodingevent.ui.home.homeAdapter.HomeHorizontalAdapter
import com.salmonboy.dicodingevent.ui.home.homeAdapter.HomeVerticalAdapter
import com.salmonboy.dicodingevent.ui.viewmodel.ViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val homeViewModel: HomeViewModel by viewModels {
            factory
        }

        val upcomingAdapter = HomeHorizontalAdapter()

        val finishedAdapter = HomeVerticalAdapter()

        // Setup RecyclerViews
        setupRecyclerViewHorizontal(upcomingAdapter)
        setupRecyclerViewVertical(finishedAdapter)

        // Observe Upcoming Events
        homeViewModel.getUpcomingDicodingEvent().observe(viewLifecycleOwner) { result ->
            handleResult(result, upcomingAdapter, binding?.progressBarGrid)
        }

        // Observe Finished Events
        homeViewModel.getFinishedDicodingEvent().observe(viewLifecycleOwner) { result ->
            handleResult(result, finishedAdapter, binding?.progressBarList)
        }
//        homeViewModel.getUpcomingEvents("", 5).observe(viewLifecycleOwner) {result->
//            if (result != null) {
//                when (result) {
//                    is Result.Loading -> {
//                        binding?.progressBarGrid?.visibility =  View.VISIBLE
//                    }
//                    is Result.Success -> {
//                        binding?.progressBarGrid?.visibility = View.GONE
//                        val eventData = result.data
//                        upcomingAdapter.submitList(eventData)
//                    }
//                    is Result.Error -> {
//                        binding?.progressBarGrid?.visibility = View.GONE
//                        Toast.makeText(
//                            context,
//                            "Terjadi Kesalahan" + result.error,
//                            Toast.LENGTH_SHORT
//                        ) .show()
//                    }
//                }
//            }
//        }

//        homeViewModel.getFinishedEvents("", 5).observe(viewLifecycleOwner) {result->
//            if (result != null) {
//                when (result) {
//                    is Result.Loading -> {
//                        binding?.progressBarList?.visibility =  View.VISIBLE
//                    }
//                    is Result.Success -> {
//                        binding?.progressBarList?.visibility = View.GONE
//                        val eventData = result.data
//                        finishedAdapter.submitList(eventData)
//                    }
//                    is Result.Error -> {
//                        binding?.progressBarList?.visibility = View.GONE
//                        Toast.makeText(
//                            context,
//                            "Terjadi Kesalahan" + result.error,
//                            Toast.LENGTH_SHORT
//                        ) .show()
//                    }
//                }
//            }
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerViewHorizontal(adapter: HomeHorizontalAdapter) {
        val layoutManager = GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false)
        binding?.recyclerViewGridHorizontal?.layoutManager = layoutManager
        binding?.recyclerViewGridHorizontal?.adapter = adapter
    }

    private fun setupRecyclerViewVertical(adapter: HomeVerticalAdapter) {
        val layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.HORIZONTAL, false)
        binding?.recyclerViewLinearVertical?.layoutManager = layoutManager
        binding?.recyclerViewLinearVertical?.adapter = adapter
    }

    private fun <T> handleResult(
        result: Result<List<T>>,
        adapter: androidx.recyclerview.widget.ListAdapter<T, *>,
        progressBar: View?
    ) {
        when (result) {
            is Result.Loading -> {
                progressBar?.visibility = View.VISIBLE
            }

            is Result.Success -> {
                progressBar?.visibility = View.GONE
                adapter.submitList(result.data)
            }

            is Result.Error -> {
                progressBar?.visibility = View.GONE
                Toast.makeText(
                    context,
                    "Terjadi Kesalahan: ${result.error}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

//    private fun setUpcomingEventsData(events: EventEntity) {
//        val adapter = HomeHorizontalAdapter { event ->
//            val intent = Intent(requireContext(), EventDetailActivity::class.java)
//            intent.putExtra("EVENT_ID", event.id.toString())
//            startActivity(intent)
//        }
//        adapter.submitList(events)
//        binding?.recyclerViewGridHorizontal?.adapter = adapter
//    }
//
//    private fun setFinishedEventsData(events: List<ListEventsItem>) {
//        val adapter = HomeVerticalAdapter { event ->
//            val intent = Intent(requireContext(), EventDetailActivity::class.java)
//            intent.putExtra("EVENT_ID", event.id.toString())
//            startActivity(intent)
//        }
//        adapter.submitList(events)
//        binding?.recyclerViewLinearVertical?.adapter = adapter
//    }
}