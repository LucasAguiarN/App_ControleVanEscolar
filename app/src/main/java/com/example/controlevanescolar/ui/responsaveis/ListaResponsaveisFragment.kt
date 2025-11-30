package com.example.controlevanescolar.ui.responsaveis

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.controlevanescolar.dao.ResponsavelDAO
import com.example.controlevanescolar.databinding.FragmentListaResponsaveisBinding
import com.example.controlevanescolar.model.Responsavel

class ListaResponsaveisFragment : Fragment() {

    private var _binding: FragmentListaResponsaveisBinding? = null
    private val binding get() = _binding!!
    private lateinit var responsavelDAO: ResponsavelDAO
    private lateinit var adapter: ResponsavelAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaResponsaveisBinding.inflate(inflater, container, false)
        responsavelDAO = ResponsavelDAO(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        carregarResponsaveis()
    }

    private fun carregarResponsaveis() {
        val responsaveis = responsavelDAO.listar()
        adapter.updateResponsaveis(responsaveis)
    }

    private fun setupRecyclerView() {
        adapter = ResponsavelAdapter(emptyList(), ::onEditClick, ::onDeleteClick)
        binding.recyclerViewResponsaveis.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewResponsaveis.adapter = adapter
    }

    private fun onEditClick(responsavel: Responsavel) {
        val action = ListaResponsaveisFragmentDirections.actionListaResponsaveisFragmentToCadastroResponsavelFragment(responsavel.id)
        findNavController().navigate(action)
    }

    private fun onDeleteClick(responsavel: Responsavel) {
        AlertDialog.Builder(requireContext())
            .setTitle("Excluir Responsável")
            .setMessage("Tem certeza que deseja excluir o responsável ${responsavel.nome}?")
            .setPositiveButton("Excluir") { _, _ ->
                if (responsavelDAO.excluir(responsavel.id)) {
                    carregarResponsaveis()
                    Toast.makeText(requireContext(), "Responsável excluído com sucesso!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Falha ao excluir o responsável.", Toast.LENGTH_SHORT).show()
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
