package com.example.controlevanescolar.ui.turma

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.controlevanescolar.dao.TurmaDAO
import com.example.controlevanescolar.databinding.FragmentListaTurmasBinding
import com.example.controlevanescolar.model.TurmaParaExibicao

class ListaTurmasFragment : Fragment() {

    private var _binding: FragmentListaTurmasBinding? = null
    private val binding get() = _binding!!
    private lateinit var turmaDAO: TurmaDAO
    private lateinit var adapter: TurmaAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaTurmasBinding.inflate(inflater, container, false)
        turmaDAO = TurmaDAO(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        carregarTurmas()
    }

    private fun carregarTurmas() {
        val turmas = turmaDAO.listarParaExibicao()
        adapter.updateTurmas(turmas)
    }

    private fun setupRecyclerView() {
        adapter = TurmaAdapter(emptyList(), ::onEditClick, ::onDeleteClick)
        binding.recyclerViewTurmas.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewTurmas.adapter = adapter
    }

    private fun onEditClick(turma: TurmaParaExibicao) {
        val action = ListaTurmasFragmentDirections.actionListaTurmasFragmentToCadastroTurmaFragment(turma.id)
        findNavController().navigate(action)
    }

    private fun onDeleteClick(turma: TurmaParaExibicao) {
        AlertDialog.Builder(requireContext())
            .setTitle("Excluir Turma")
            .setMessage("Tem certeza que deseja excluir a turma ${turma.nome}?")
            .setPositiveButton("Excluir") { _, _ ->
                if (turmaDAO.excluir(turma.id)) {
                    carregarTurmas()
                    Toast.makeText(requireContext(), "Turma excluída com sucesso!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Falha ao excluir a turma.", Toast.LENGTH_SHORT).show()
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
