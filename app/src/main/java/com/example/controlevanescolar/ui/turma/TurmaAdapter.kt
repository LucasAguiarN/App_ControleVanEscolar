package com.example.controlevanescolar.ui.turma

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.controlevanescolar.databinding.ListItemTurmaBinding
import com.example.controlevanescolar.model.TurmaParaExibicao

class TurmaAdapter(
    private var turmas: List<TurmaParaExibicao>,
    private val onEditClick: (TurmaParaExibicao) -> Unit,
    private val onDeleteClick: (TurmaParaExibicao) -> Unit
) : RecyclerView.Adapter<TurmaAdapter.TurmaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TurmaViewHolder {
        val binding = ListItemTurmaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TurmaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TurmaViewHolder, position: Int) {
        holder.bind(turmas[position])
    }

    override fun getItemCount() = turmas.size

    fun updateTurmas(newTurmas: List<TurmaParaExibicao>) {
        turmas = newTurmas
        notifyDataSetChanged()
    }

    inner class TurmaViewHolder(private val binding: ListItemTurmaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(turma: TurmaParaExibicao) {
            binding.textNomeTurma.text = turma.nome
            // O layout de turma é simples, mas se quiséssemos mostrar o condutor, seria aqui.
            // Por exemplo: binding.textNomeCondutor.text = turma.nomeCondutor

            binding.btnEditar.setOnClickListener { onEditClick(turma) }
            binding.btnExcluir.setOnClickListener { onDeleteClick(turma) }
        }
    }
}
