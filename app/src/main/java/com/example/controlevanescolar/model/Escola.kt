package com.example.controlevanescolar.model

data class Escola(
    val id: Int,
    val nome: String,
    val endereco: String,
    val telefone: String,
    val cep: String
) {
    override fun toString(): String {
        return nome
    }
}
