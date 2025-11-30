package com.example.controlevanescolar.ui.turma

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.controlevanescolar.R
import com.example.controlevanescolar.databinding.FragmentTurmaBinding

class TurmaFragment : Fragment() {

    private var _binding: FragmentTurmaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTurmaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCadastrarTurma.setOnClickListener {
            findNavController().navigate(R.id.action_turmaFragment_to_cadastroTurmaFragment)
        }

        binding.btnListarTurmas.setOnClickListener {
            findNavController().navigate(R.id.action_turmaFragment_to_listaTurmasFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
