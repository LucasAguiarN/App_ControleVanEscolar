package com.example.controlevanescolar.model

// Classe de dados para transportar informações completas para a UI
data class AlunoParaExibicao(
    val id: Int,
    val nome: String,
    val nomeResponsavel: String,
    val nomeEscola: String,
    val nomeTurma: String
)
