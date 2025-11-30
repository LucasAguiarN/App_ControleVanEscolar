package com.example.controlevanescolar.ui.responsaveis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.controlevanescolar.dao.ResponsavelDAO
import com.example.controlevanescolar.databinding.FragmentCadastroResponsavelBinding
import com.example.controlevanescolar.model.Responsavel

class CadastroResponsavelFragment : Fragment() {

    private var _binding: FragmentCadastroResponsavelBinding? = null
    private val binding get() = _binding!!

    private val args: CadastroResponsavelFragmentArgs by navArgs()
    private var responsavelId: Int = -1

    private lateinit var responsavelDAO: ResponsavelDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        responsavelId = args.responsavelId
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCadastroResponsavelBinding.inflate(inflater, container, false)
        responsavelDAO = ResponsavelDAO(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (responsavelId != -1) {
            loadResponsavelData()
        }

        binding.btnSalvarResponsavel.setOnClickListener {
            salvarResponsavel()
        }
    }

    private fun loadResponsavelData() {
        val responsavel = responsavelDAO.getById(responsavelId)
        if (responsavel != null) {
            binding.editNomeResponsavel.setText(responsavel.nome)
            binding.editTelefoneResponsavel.setText(responsavel.telefone)
            binding.editCpfResponsavel.setText(responsavel.cpf)
            binding.editParentescoResponsavel.setText(responsavel.parentesco)
        }
    }

    private fun salvarResponsavel() {
        val nome = binding.editNomeResponsavel.text.toString().trim()
        val telefone = binding.editTelefoneResponsavel.text.toString().trim()
        val cpf = binding.editCpfResponsavel.text.toString().trim()
        val parentesco = binding.editParentescoResponsavel.text.toString().trim()

        if (nome.isEmpty() || telefone.isEmpty() || cpf.isEmpty() || parentesco.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            return
        }

        val responsavel = Responsavel(
            id = if (responsavelId != -1) responsavelId else 0,
            nome = nome,
            telefone = telefone,
            cpf = cpf,
            parentesco = parentesco
        )

        val sucesso = if (responsavelId != -1) {
            responsavelDAO.atualizar(responsavel)
        } else {
            responsavelDAO.adicionar(responsavel)
        }

        if (sucesso) {
            val message = if (responsavelId != -1) "Responsável atualizado com sucesso!" else "Responsável salvo com sucesso!"
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        } else {
            val message = if (responsavelId != -1) "Falha ao atualizar o responsável." else "Falha ao salvar o responsável."
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
