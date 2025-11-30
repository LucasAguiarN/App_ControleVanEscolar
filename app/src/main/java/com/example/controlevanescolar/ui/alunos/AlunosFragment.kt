package com.example.controlevanescolar.ui.alunos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.controlevanescolar.R
import com.example.controlevanescolar.databinding.FragmentAlunosBinding

class AlunosFragment : Fragment() {

    private var _binding: FragmentAlunosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlunosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCadastrarAluno.setOnClickListener {
            findNavController().navigate(R.id.action_alunosFragment_to_cadastroAlunoFragment)
        }

        binding.btnListarAlunos.setOnClickListener {
            findNavController().navigate(R.id.action_alunosFragment_to_listaAlunosFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
