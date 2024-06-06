package com.openclassrooms.arista.ui.sleep

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.openclassrooms.arista.databinding.FragmentSleepBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SleepFragment : Fragment() {

    private var _binding: FragmentSleepBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SleepViewModel by viewModels()

    private val sleepAdapter = SleepAdapter(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSleepBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        observeErrors()
        binding.sleepRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.sleepRecyclerview.adapter = sleepAdapter
        viewModel.getSleeps()
    }

    /**
     * Affichage des datas
     */
    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.sleeps.collect { sleeps ->
                sleepAdapter.updateData(sleeps)
            }
        }
    }

    /**
     * Affichage des erreurs via le flow dédié
     */
    private fun observeErrors() {
        // Affichage des erreurs via le flow dédié
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.errorFlow .collect { error ->

                if (error!=null){ // A la création du Flow, on envoie null (voir code dans ViewModel)
                    // Affichage d'un Toast
                    Toast.makeText(requireContext(), "Error \n $error", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
