package com.example.controlevanescolar.ui.condutores

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.controlevanescolar.R
import com.example.controlevanescolar.databinding.FragmentCondutoresBinding

class CondutoresFragment : Fragment() {

    private var _binding: FragmentCondutoresBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCondutoresBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCadastrarCondutor.setOnClickListener {
            findNavController().navigate(R.id.action_condutoresFragment_to_cadastroCondutorFragment)
        }

        binding.btnListarCondutores.setOnClickListener {
            findNavController().navigate(R.id.action_condutoresFragment_to_listaCondutoresFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
