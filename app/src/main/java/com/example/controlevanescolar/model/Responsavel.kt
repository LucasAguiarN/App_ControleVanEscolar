package com.example.controlevanescolar.model

data class Responsavel(
    val id: Int,
    val nome: String,
    val telefone: String,
    val cpf: String,
    val parentesco: String
) {
    override fun toString(): String {
        return nome
    }
}
