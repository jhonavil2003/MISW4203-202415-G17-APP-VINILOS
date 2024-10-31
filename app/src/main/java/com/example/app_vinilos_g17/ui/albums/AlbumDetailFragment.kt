package com.example.app_vinilos_g17.ui.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app_vinilos_g17.databinding.FragmentAlbumDetailBinding
import androidx.appcompat.app.AppCompatActivity
import com.example.app_vinilos_g17.models.Performer

class AlbumDetailFragment : Fragment() {
    private var _binding: FragmentAlbumDetailBinding? = null
    private val binding get() = _binding!!
    private val args: AlbumDetailFragmentArgs by navArgs()
    private lateinit var viewModel: AlbumDetailViewModel
    private lateinit var performerAdapter: PerformerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val albumId = args.id
        viewModel = ViewModelProvider(this, AlbumDetailViewModel.Factory(requireActivity().application, albumId)).get(AlbumDetailViewModel::class.java)

        // Inicializa el RecyclerView
        binding.recyclerViewPerformers.layoutManager = LinearLayoutManager(context)
        performerAdapter = PerformerAdapter() // Inicializa el Adapter vacío
        binding.recyclerViewPerformers.adapter = performerAdapter

        viewModel.album.observe(viewLifecycleOwner) { album ->
            binding.album = album
            (activity as? AppCompatActivity)?.supportActionBar?.title = album.name

            // Actualiza la lista de intérpretes
            performerAdapter.updatePerformers(album.performers)
        }

        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        Toast.makeText(activity, "Error de red al cargar el álbum", Toast.LENGTH_LONG).show()
        viewModel.onNetworkErrorShown()
    }
}

