package com.example.controlevanescolar.model

data class Aluno(
    val id: Int,
    val nome: String,
    val responsavelId: Int?,
    val escolaId: Int?,
    val turmaId: Int?
) {
    // O toString não é mais útil para a lista simples, mas pode ser mantido por enquanto
    override fun toString(): String {
        return nome
    }
}
