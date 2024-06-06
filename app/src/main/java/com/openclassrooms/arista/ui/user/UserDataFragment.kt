package com.openclassrooms.arista.ui.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.openclassrooms.arista.databinding.FragmentUserDataBinding
import com.openclassrooms.arista.domain.model.User
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch


@AndroidEntryPoint
class UserDataFragment : Fragment() {

    private lateinit var binding: FragmentUserDataBinding

    private val viewModel: UserDataViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        // Dans une coroutine, collecte du Flow
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.userFlow.collect { resultUser: Result<User>? ->
                resultUser?.let {

                    // TODO : A montrer : Gestion du try catch via l'objet Result
                    // Il y a plusieurs façon de faire, quelle est la meilleure ?
                    // - Ici, j'utilise une variable de StateFlow<Result<User>?> pour communiquer entre le ViewModel et le fragment (mais ca pollue un peu le code je trouve)
                    // - je pourrais aussi créer un autre flow dédié au erreur (ce que j'ai fait pour les exercices et les sommeils

                    // TODO : Je me pose aussi la question de la couche à choisir pour gérer les exceptions :
                    //  Je les ai gérées au plus bas dès le Repository (je me dis que mais on pourrait les laissser remonter jusqu'aux UseCase ou au ViewModel

                    resultUser.onSuccess {
                        binding.etName.setText(it.name)
                        binding.etEmail.setText(it.email)
                    }.onFailure {
                        Toast.makeText(requireContext(), "Error login \n ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }
}
