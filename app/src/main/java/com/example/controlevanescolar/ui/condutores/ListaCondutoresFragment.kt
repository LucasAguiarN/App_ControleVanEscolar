package com.example.controlevanescolar.ui.condutores

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.controlevanescolar.dao.CondutorDAO
import com.example.controlevanescolar.databinding.FragmentListaCondutoresBinding
import com.example.controlevanescolar.model.Condutor

class ListaCondutoresFragment : Fragment() {

    private var _binding: FragmentListaCondutoresBinding? = null
    private val binding get() = _binding!!
    private lateinit var condutorDAO: CondutorDAO
    private lateinit var adapter: CondutorAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaCondutoresBinding.inflate(inflater, container, false)
        condutorDAO = CondutorDAO(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        carregarCondutores()
    }

    private fun carregarCondutores() {
        val condutores = condutorDAO.listar()
        adapter.updateCondutores(condutores)
    }

    private fun setupRecyclerView() {
        adapter = CondutorAdapter(emptyList(), ::onEditClick, ::onDeleteClick)
        binding.recyclerViewCondutores.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewCondutores.adapter = adapter
    }

    private fun onEditClick(condutor: Condutor) {
        val action = ListaCondutoresFragmentDirections.actionListaCondutoresFragmentToCadastroCondutorFragment(condutor.id)
        findNavController().navigate(action)
    }

    private fun onDeleteClick(condutor: Condutor) {
        AlertDialog.Builder(requireContext())
            .setTitle("Excluir Condutor")
            .setMessage("Tem certeza que deseja excluir o condutor ${condutor.nome}?")
            .setPositiveButton("Excluir") { _, _ ->
                if (condutorDAO.excluir(condutor.id)) {
                    carregarCondutores()
                    Toast.makeText(requireContext(), "Condutor excluído com sucesso!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Falha ao excluir o condutor.", Toast.LENGTH_SHORT).show()
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
