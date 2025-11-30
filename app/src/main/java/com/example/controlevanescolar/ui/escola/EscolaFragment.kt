package com.example.controlevanescolar.ui.escola

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.controlevanescolar.R
import com.example.controlevanescolar.databinding.FragmentEscolaBinding

class EscolaFragment : Fragment() {

    private var _binding: FragmentEscolaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEscolaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCadastrarEscola.setOnClickListener {
            findNavController().navigate(R.id.action_escolaFragment_to_cadastroEscolaFragment)
        }

        binding.btnListarEscolas.setOnClickListener {
            findNavController().navigate(R.id.action_escolaFragment_to_listaEscolasFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
