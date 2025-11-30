package com.example.controlevanescolar.ui.alunos

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.controlevanescolar.dao.AlunoDAO
import com.example.controlevanescolar.databinding.FragmentListaAlunosBinding
import com.example.controlevanescolar.model.AlunoParaExibicao

class ListaAlunosFragment : Fragment() {

    private var _binding: FragmentListaAlunosBinding? = null
    private val binding get() = _binding!!
    private lateinit var alunoDAO: AlunoDAO
    private lateinit var adapter: AlunoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaAlunosBinding.inflate(inflater, container, false)
        alunoDAO = AlunoDAO(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        carregarAlunos()
    }

    private fun carregarAlunos() {
        val alunos = alunoDAO.listarParaExibicao()
        adapter.updateAlunos(alunos)
    }

    private fun setupRecyclerView() {
        adapter = AlunoAdapter(emptyList(), ::onEditClick, ::onDeleteClick)
        binding.recyclerViewAlunos.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewAlunos.adapter = adapter
    }

    private fun onEditClick(aluno: AlunoParaExibicao) {
        val action = ListaAlunosFragmentDirections.actionListaAlunosFragmentToCadastroAlunoFragment(aluno.id)
        findNavController().navigate(action)
    }

    private fun onDeleteClick(aluno: AlunoParaExibicao) {
        AlertDialog.Builder(requireContext())
            .setTitle("Excluir Aluno")
            .setMessage("Tem certeza que deseja excluir o aluno ${aluno.nome}?")
            .setPositiveButton("Excluir") { _, _ ->
                if (alunoDAO.excluir(aluno.id)) {
                    carregarAlunos()
                    Toast.makeText(requireContext(), "Aluno excluído com sucesso!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Falha ao excluir o aluno.", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
