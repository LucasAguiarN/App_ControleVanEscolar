package com.example.controlevanescolar.ui.responsaveis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.controlevanescolar.R
import com.example.controlevanescolar.databinding.FragmentResponsaveisBinding

class ResponsaveisFragment : Fragment() {

    private var _binding: FragmentResponsaveisBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResponsaveisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCadastrarResponsavel.setOnClickListener {
            findNavController().navigate(R.id.action_responsaveisFragment_to_cadastroResponsavelFragment)
        }

        binding.btnListarResponsaveis.setOnClickListener {
            findNavController().navigate(R.id.action_responsaveisFragment_to_listaResponsaveisFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
