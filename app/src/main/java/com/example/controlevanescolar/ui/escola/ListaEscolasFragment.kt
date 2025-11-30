package com.example.controlevanescolar.ui.escola

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.controlevanescolar.dao.EscolaDAO
import com.example.controlevanescolar.databinding.FragmentListaEscolasBinding
import com.example.controlevanescolar.model.Escola

class ListaEscolasFragment : Fragment() {

    private var _binding: FragmentListaEscolasBinding? = null
    private val binding get() = _binding!!
    private lateinit var escolaDAO: EscolaDAO
    private lateinit var adapter: EscolaAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaEscolasBinding.inflate(inflater, container, false)
        escolaDAO = EscolaDAO(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        carregarEscolas()
    }

    private fun carregarEscolas() {
        val escolas = escolaDAO.listar()
        adapter.updateEscolas(escolas)
    }

    private fun setupRecyclerView() {
        adapter = EscolaAdapter(emptyList(), ::onEditClick, ::onDeleteClick)
        binding.recyclerViewEscolas.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewEscolas.adapter = adapter
    }

    private fun onEditClick(escola: Escola) {
        val action = ListaEscolasFragmentDirections.actionListaEscolasFragmentToCadastroEscolaFragment(escola.id)
        findNavController().navigate(action)
    }

    private fun onDeleteClick(escola: Escola) {
        AlertDialog.Builder(requireContext())
            .setTitle("Excluir Escola")
            .setMessage("Tem certeza que deseja excluir a escola ${escola.nome}?")
            .setPositiveButton("Excluir") { _, _ ->
                if (escolaDAO.excluir(escola.id)) {
                    carregarEscolas()
                    Toast.makeText(requireContext(), "Escola excluída com sucesso!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Falha ao excluir a escola.", Toast.LENGTH_SHORT).show()
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
