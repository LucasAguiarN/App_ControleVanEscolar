package com.example.controlevanescolar.model

data class Turma(
    val id: Int,
    val nome: String,
    val condutorId: Int?
) {
    override fun toString(): String {
        return nome
    }
}
