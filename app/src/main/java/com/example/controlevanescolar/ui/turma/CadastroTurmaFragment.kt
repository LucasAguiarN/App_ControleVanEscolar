package com.example.controlevanescolar.ui.turma

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.controlevanescolar.dao.CondutorDAO
import com.example.controlevanescolar.dao.TurmaDAO
import com.example.controlevanescolar.databinding.FragmentCadastroTurmaBinding
import com.example.controlevanescolar.model.Condutor
import com.example.controlevanescolar.model.Turma

class CadastroTurmaFragment : Fragment() {

    private var _binding: FragmentCadastroTurmaBinding? = null
    private val binding get() = _binding!!

    private val args: CadastroTurmaFragmentArgs by navArgs()
    private var turmaId: Int = -1

    private lateinit var turmaDAO: TurmaDAO
    private lateinit var condutorDAO: CondutorDAO
    private lateinit var condutores: List<Condutor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        turmaId = args.turmaId
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCadastroTurmaBinding.inflate(inflater, container, false)
        turmaDAO = TurmaDAO(requireContext())
        condutorDAO = CondutorDAO(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSpinner()

        if (turmaId != -1) {
            loadTurmaData()
        }

        binding.btnSalvarTurma.setOnClickListener {
            salvarTurma()
        }
    }

    private fun loadTurmaData() {
        val turma = turmaDAO.getById(turmaId)
        if (turma != null) {
            binding.editNomeTurma.setText(turma.nome)
            val condutorPosition = condutores.indexOfFirst { it.id == turma.condutorId }
            if (condutorPosition != -1) binding.spinnerCondutor.setSelection(condutorPosition)
        }
    }

    private fun setupSpinner() {
        condutores = condutorDAO.listar()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, condutores.map { it.nome })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCondutor.adapter = adapter
    }

    private fun salvarTurma() {
        val nome = binding.editNomeTurma.text.toString()
        if (nome.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor, preencha o nome da turma.", Toast.LENGTH_SHORT).show()
            return
        }

        val condutorSelecionado = condutores.getOrNull(binding.spinnerCondutor.selectedItemPosition)
        if (condutorSelecionado == null) {
            Toast.makeText(requireContext(), "Nenhum condutor cadastrado. Cadastre um primeiro.", Toast.LENGTH_LONG).show()
            return
        }

        val turma = Turma(
            id = if (turmaId != -1) turmaId else 0,
            nome = nome,
            condutorId = condutorSelecionado.id
        )

        val sucesso = if (turmaId != -1) {
            turmaDAO.atualizar(turma)
        } else {
            turmaDAO.adicionar(turma)
        }

        if (sucesso) {
            val message = if (turmaId != -1) "Turma atualizada com sucesso!" else "Turma salva com sucesso!"
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        } else {
            val message = if (turmaId != -1) "Falha ao atualizar a turma." else "Falha ao salvar a turma."
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
