package com.example.controlevanescolar.ui.alunos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.controlevanescolar.dao.AlunoDAO
import com.example.controlevanescolar.dao.EscolaDAO
import com.example.controlevanescolar.dao.ResponsavelDAO
import com.example.controlevanescolar.dao.TurmaDAO
import com.example.controlevanescolar.databinding.FragmentCadastroAlunoBinding
import com.example.controlevanescolar.model.Aluno
import com.example.controlevanescolar.model.Escola
import com.example.controlevanescolar.model.Responsavel
import com.example.controlevanescolar.model.Turma

class CadastroAlunoFragment : Fragment() {

    private var _binding: FragmentCadastroAlunoBinding? = null
    private val binding get() = _binding!!

    private val args: CadastroAlunoFragmentArgs by navArgs()
    private var alunoId: Int = -1

    private lateinit var alunoDAO: AlunoDAO
    private lateinit var responsavelDAO: ResponsavelDAO
    private lateinit var escolaDAO: EscolaDAO
    private lateinit var turmaDAO: TurmaDAO

    private lateinit var responsaveis: List<Responsavel>
    private lateinit var escolas: List<Escola>
    private lateinit var turmas: List<Turma>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        alunoId = args.alunoId
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCadastroAlunoBinding.inflate(inflater, container, false)
        
        alunoDAO = AlunoDAO(requireContext())
        responsavelDAO = ResponsavelDAO(requireContext())
        escolaDAO = EscolaDAO(requireContext())
        turmaDAO = TurmaDAO(requireContext())
        
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinners()

        if (alunoId != -1) {
            // Modo Edição
            loadAlunoData()
        } // else Modo Cadastro

        binding.btnSalvarAluno.setOnClickListener {
            salvarAluno()
        }
    }

    private fun loadAlunoData() {
        val aluno = alunoDAO.getById(alunoId)
        if (aluno != null) {
            binding.editNomeAluno.setText(aluno.nome)
            
            val responsavelPosition = responsaveis.indexOfFirst { it.id == aluno.responsavelId }
            val escolaPosition = escolas.indexOfFirst { it.id == aluno.escolaId }
            val turmaPosition = turmas.indexOfFirst { it.id == aluno.turmaId }

            if (responsavelPosition != -1) binding.spinnerResponsavel.setSelection(responsavelPosition)
            if (escolaPosition != -1) binding.spinnerEscola.setSelection(escolaPosition)
            if (turmaPosition != -1) binding.spinnerTurma.setSelection(turmaPosition)
        }
    }

    private fun setupSpinners() {
        responsaveis = responsavelDAO.listar()
        escolas = escolaDAO.listar()
        turmas = turmaDAO.listar()

        setupSpinner(binding.spinnerResponsavel, responsaveis.map { it.nome })
        setupSpinner(binding.spinnerEscola, escolas.map { it.nome })
        setupSpinner(binding.spinnerTurma, turmas.map { it.nome })
    }

    private fun setupSpinner(spinner: Spinner, data: List<String>) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, data)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun salvarAluno() {
        val nome = binding.editNomeAluno.text.toString()
        if (nome.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor, preencha o nome do aluno.", Toast.LENGTH_SHORT).show()
            return
        }

        val responsavelSelecionado = responsaveis.getOrNull(binding.spinnerResponsavel.selectedItemPosition)
        val escolaSelecionada = escolas.getOrNull(binding.spinnerEscola.selectedItemPosition)
        val turmaSelecionada = turmas.getOrNull(binding.spinnerTurma.selectedItemPosition)

        if (responsavelSelecionado == null || escolaSelecionada == null || turmaSelecionada == null) {
            Toast.makeText(requireContext(), "Certifique-se de que há responsáveis, escolas e turmas cadastrados.", Toast.LENGTH_LONG).show()
            return
        }

        val aluno = Aluno(
            id = if (alunoId != -1) alunoId else 0,
            nome = nome,
            responsavelId = responsavelSelecionado.id,
            escolaId = escolaSelecionada.id,
            turmaId = turmaSelecionada.id
        )

        val sucesso = if (alunoId != -1) {
            alunoDAO.atualizar(aluno)
        } else {
            alunoDAO.adicionar(aluno)
        }

        if (sucesso) {
            val message = if (alunoId != -1) "Aluno atualizado com sucesso!" else "Aluno salvo com sucesso!"
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        } else {
            val message = if (alunoId != -1) "Falha ao atualizar o aluno." else "Falha ao salvar o aluno."
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
