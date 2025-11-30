package com.example.controlevanescolar.ui.escola

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.controlevanescolar.databinding.ListItemEscolaBinding
import com.example.controlevanescolar.model.Escola

class EscolaAdapter(
    private var escolas: List<Escola>,
    private val onEditClick: (Escola) -> Unit,
    private val onDeleteClick: (Escola) -> Unit
) : RecyclerView.Adapter<EscolaAdapter.EscolaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EscolaViewHolder {
        val binding = ListItemEscolaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EscolaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EscolaViewHolder, position: Int) {
        holder.bind(escolas[position])
    }

    override fun getItemCount() = escolas.size

    fun updateEscolas(newEscolas: List<Escola>) {
        escolas = newEscolas
        notifyDataSetChanged()
    }

    inner class EscolaViewHolder(private val binding: ListItemEscolaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(escola: Escola) {
            binding.textNomeEscola.text = escola.nome
            binding.textEnderecoEscola.text = escola.endereco
            binding.textTelefoneEscola.text = "Telefone: ${escola.telefone}"
            binding.textCepEscola.text = "CEP: ${escola.cep}"

            binding.btnEditar.setOnClickListener { onEditClick(escola) }
            binding.btnExcluir.setOnClickListener { onDeleteClick(escola) }
        }
    }
}
