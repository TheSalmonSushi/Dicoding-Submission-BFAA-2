package com.salmonboy.dicodingevent.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.salmonboy.dicodingevent.data.Result
import com.salmonboy.dicodingevent.databinding.FragmentFavoriteBinding
import com.salmonboy.dicodingevent.ui.adapter.event.EventAdapter
import com.salmonboy.dicodingevent.ui.viewmodel.ViewModelFactory


class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory : ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: FavoriteViewModel by viewModels {
            factory
        }

        val eventAdapter = EventAdapter()

        setupRecyclerView()
        binding?.rvFavoriteEvent?.adapter = eventAdapter
        viewModel.getBookmarkedEvents().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding?.progressBar?.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding?.progressBar?.visibility = View.GONE
                    (binding?.rvFavoriteEvent?.adapter as EventAdapter).submitList(result.data)
                }
                is Result.Error -> {
                    binding?.progressBar?.visibility = View.GONE
                    Toast.makeText(requireContext(), "Error: ${result.error}", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }

    private fun setupRecyclerView() {
        binding?.rvFavoriteEvent?.layoutManager = GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}