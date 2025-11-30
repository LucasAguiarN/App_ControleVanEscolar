package com.example.controlevanescolar.ui.alunos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.controlevanescolar.databinding.ListItemAlunoBinding
import com.example.controlevanescolar.model.AlunoParaExibicao

class AlunoAdapter(
    private var alunos: List<AlunoParaExibicao>,
    private val onEditClick: (AlunoParaExibicao) -> Unit,
    private val onDeleteClick: (AlunoParaExibicao) -> Unit
) : RecyclerView.Adapter<AlunoAdapter.AlunoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlunoViewHolder {
        val binding = ListItemAlunoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlunoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlunoViewHolder, position: Int) {
        holder.bind(alunos[position])
    }

    override fun getItemCount() = alunos.size

    fun updateAlunos(newAlunos: List<AlunoParaExibicao>) {
        alunos = newAlunos
        notifyDataSetChanged()
    }

    inner class AlunoViewHolder(private val binding: ListItemAlunoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(aluno: AlunoParaExibicao) {
            binding.textNomeAluno.text = aluno.nome
            binding.textResponsavel.text = "Responsável: ${aluno.nomeResponsavel}"
            binding.textEscola.text = "Escola: ${aluno.nomeEscola}"
            // Poderíamos adicionar a turma aqui também se quiséssemos

            binding.btnEditar.setOnClickListener { onEditClick(aluno) }
            binding.btnExcluir.setOnClickListener { onDeleteClick(aluno) }
        }
    }
}
