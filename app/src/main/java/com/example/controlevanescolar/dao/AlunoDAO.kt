package com.example.controlevanescolar.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.controlevanescolar.database.DatabaseHelper
import com.example.controlevanescolar.model.Aluno
import com.example.controlevanescolar.model.AlunoParaExibicao

class AlunoDAO(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    fun getById(id: Int): Aluno? {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT id, nome, responsavel_id, escola_id, turma_id FROM ${DatabaseHelper.TBL_ALUNOS} WHERE id = ?", arrayOf(id.toString()))
        var aluno: Aluno? = null
        if (cursor.moveToFirst()) {
            aluno = Aluno(
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                nome = cursor.getString(cursor.getColumnIndexOrThrow("nome")),
                responsavelId = cursor.getInt(cursor.getColumnIndexOrThrow("responsavel_id")),
                escolaId = cursor.getInt(cursor.getColumnIndexOrThrow("escola_id")),
                turmaId = cursor.getInt(cursor.getColumnIndexOrThrow("turma_id"))
            )
        }
        cursor.close()
        db.close()
        return aluno
    }

    fun listarParaExibicao(): List<AlunoParaExibicao> {
        val lista = mutableListOf<AlunoParaExibicao>()
        val db = dbHelper.readableDatabase

        val sql = """
            SELECT 
                A.id, 
                A.nome, 
                R.nome as nome_responsavel, 
                E.nome as nome_escola, 
                T.nome as nome_turma
            FROM ${DatabaseHelper.TBL_ALUNOS} A
            LEFT JOIN ${DatabaseHelper.TBL_RESPONSAVEIS} R ON A.responsavel_id = R.id
            LEFT JOIN ${DatabaseHelper.TBL_ESCOLAS} E ON A.escola_id = E.id
            LEFT JOIN ${DatabaseHelper.TBL_TURMAS} T ON A.turma_id = T.id
        """.trimIndent()

        val cursor: Cursor = db.rawQuery(sql, null)
        if (cursor.moveToFirst()) {
            do {
                lista.add(
                    AlunoParaExibicao(
                        id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        nome = cursor.getString(cursor.getColumnIndexOrThrow("nome")),
                        nomeResponsavel = cursor.getString(cursor.getColumnIndexOrThrow("nome_responsavel")) ?: "N/A",
                        nomeEscola = cursor.getString(cursor.getColumnIndexOrThrow("nome_escola")) ?: "N/A",
                        nomeTurma = cursor.getString(cursor.getColumnIndexOrThrow("nome_turma")) ?: "N/A"
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return lista
    }

    fun adicionar(aluno: Aluno): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nome", aluno.nome)
            put("responsavel_id", aluno.responsavelId)
            put("escola_id", aluno.escolaId)
            put("turma_id", aluno.turmaId)
        }
        val id = db.insert(DatabaseHelper.TBL_ALUNOS, null, values)
        db.close()
        return id != -1L
    }

    fun atualizar(aluno: Aluno): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nome", aluno.nome)
            put("responsavel_id", aluno.responsavelId)
            put("escola_id", aluno.escolaId)
            put("turma_id", aluno.turmaId)
        }
        val rows = db.update(DatabaseHelper.TBL_ALUNOS, values, "id = ?", arrayOf(aluno.id.toString()))
        db.close()
        return rows > 0
    }

    fun excluir(id: Int): Boolean {
        val db = dbHelper.writableDatabase
        val rows = db.delete(DatabaseHelper.TBL_ALUNOS, "id = ?", arrayOf(id.toString()))
        db.close()
        return rows > 0
    }
}
