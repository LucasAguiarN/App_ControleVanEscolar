package com.example.controlevanescolar.model

data class Condutor(
    val id: Int,
    val nome: String,
    val cnh: String,
    val cpf: String,
    val cargo: String
) {
    override fun toString(): String {
        return nome
    }
}
