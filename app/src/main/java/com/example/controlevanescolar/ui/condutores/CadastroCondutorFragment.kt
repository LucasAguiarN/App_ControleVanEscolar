package com.example.controlevanescolar.ui.condutores

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.controlevanescolar.dao.CondutorDAO
import com.example.controlevanescolar.databinding.FragmentCadastroCondutorBinding
import com.example.controlevanescolar.model.Condutor

class CadastroCondutorFragment : Fragment() {

    private var _binding: FragmentCadastroCondutorBinding? = null
    private val binding get() = _binding!!

    private val args: CadastroCondutorFragmentArgs by navArgs()
    private var condutorId: Int = -1

    private lateinit var condutorDAO: CondutorDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        condutorId = args.condutorId
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCadastroCondutorBinding.inflate(inflater, container, false)
        condutorDAO = CondutorDAO(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (condutorId != -1) {
            loadCondutorData()
        }

        binding.btnSalvarCondutor.setOnClickListener {
            salvarCondutor()
        }
    }

    private fun loadCondutorData() {
        val condutor = condutorDAO.getById(condutorId)
        if (condutor != null) {
            binding.editNomeCondutor.setText(condutor.nome)
            binding.editCnhCondutor.setText(condutor.cnh)
            binding.editCpfCondutor.setText(condutor.cpf)
            binding.editCargoCondutor.setText(condutor.cargo)
        }
    }

    private fun salvarCondutor() {
        val nome = binding.editNomeCondutor.text.toString()
        val cnh = binding.editCnhCondutor.text.toString()
        val cpf = binding.editCpfCondutor.text.toString()
        val cargo = binding.editCargoCondutor.text.toString()

        if (nome.isEmpty() || cnh.isEmpty() || cpf.isEmpty() || cargo.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            return
        }

        val condutor = Condutor(
            id = if (condutorId != -1) condutorId else 0,
            nome = nome,
            cnh = cnh,
            cpf = cpf,
            cargo = cargo
        )

        val sucesso = if (condutorId != -1) {
            condutorDAO.atualizar(condutor)
        } else {
            condutorDAO.adicionar(condutor)
        }

        if (sucesso) {
            val message = if (condutorId != -1) "Condutor atualizado com sucesso!" else "Condutor salvo com sucesso!"
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        } else {
            val message = if (condutorId != -1) "Falha ao atualizar o condutor." else "Falha ao salvar o condutor."
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
