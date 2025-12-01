package com.example.controlevanescolar.ui.escola

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.controlevanescolar.api.RetrofitClient
import com.example.controlevanescolar.dao.EscolaDAO
import com.example.controlevanescolar.databinding.FragmentCadastroEscolaBinding
import com.example.controlevanescolar.model.Endereco
import com.example.controlevanescolar.model.Escola
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CadastroEscolaFragment : Fragment() {

    private var _binding: FragmentCadastroEscolaBinding? = null
    private val binding get() = _binding!!

    private val args: CadastroEscolaFragmentArgs by navArgs()
    private var escolaId: Int = -1

    private lateinit var escolaDAO: EscolaDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        escolaId = args.escolaId
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCadastroEscolaBinding.inflate(inflater, container, false)
        escolaDAO = EscolaDAO(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (escolaId != -1) {
            loadEscolaData()
        }

        binding.btnSalvarEscola.setOnClickListener {
            salvarEscola()
        }

        binding.editCepEscola.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 8) {
                    buscarEnderecoPorCep(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun buscarEnderecoPorCep(cep: String) {
        RetrofitClient.instance.getEndereco(cep).enqueue(object : Callback<Endereco> {
            override fun onResponse(call: Call<Endereco>, response: Response<Endereco>) {
                if (response.isSuccessful) {
                    val endereco = response.body()
                    if (endereco != null && endereco.erro != true) {
                        val logradouro = endereco.logradouro ?: ""
                        val bairro = endereco.bairro ?: ""
                        val localidade = endereco.localidade ?: ""
                        val uf = endereco.uf ?: ""
                        binding.editEnderecoEscola.setText("$logradouro, $bairro - $localidade/$uf")
                    } else {
                        Toast.makeText(requireContext(), "CEP não encontrado.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "CEP não encontrado.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Endereco>, t: Throwable) {
                Toast.makeText(requireContext(), "Falha ao buscar CEP.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadEscolaData() {
        val escola = escolaDAO.getById(escolaId)
        if (escola != null) {
            binding.editNomeEscola.setText(escola.nome)
            binding.editEnderecoEscola.setText(escola.endereco)
            binding.editTelefoneEscola.setText(escola.telefone)
            binding.editCepEscola.setText(escola.cep)
        }
    }

    private fun salvarEscola() {
        val nome = binding.editNomeEscola.text.toString().trim()
        val endereco = binding.editEnderecoEscola.text.toString().trim()
        val telefone = binding.editTelefoneEscola.text.toString().trim()
        val cep = binding.editCepEscola.text.toString().trim()

        if (nome.isEmpty() || endereco.isEmpty() || telefone.isEmpty() || cep.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            return
        }

        val escola = Escola(
            id = if (escolaId != -1) escolaId else 0,
            nome = nome,
            endereco = endereco,
            telefone = telefone,
            cep = cep
        )

        val sucesso = if (escolaId != -1) {
            escolaDAO.atualizar(escola)
        } else {
            escolaDAO.adicionar(escola)
        }

        if (sucesso) {
            val message = if (escolaId != -1) "Escola atualizada com sucesso!" else "Escola salva com sucesso!"
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        } else {
            val message = if (escolaId != -1) "Falha ao atualizar a escola." else "Falha ao salvar a escola."
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
