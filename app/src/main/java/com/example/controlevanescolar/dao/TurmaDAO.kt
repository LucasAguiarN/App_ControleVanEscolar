package com.example.controlevanescolar.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.controlevanescolar.database.DatabaseHelper
import com.example.controlevanescolar.model.Turma
import com.example.controlevanescolar.model.TurmaParaExibicao

class TurmaDAO(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    fun getById(id: Int): Turma? {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT id, nome, condutor_id FROM ${DatabaseHelper.TBL_TURMAS} WHERE id = ?", arrayOf(id.toString()))
        var turma: Turma? = null
        if (cursor.moveToFirst()) {
            turma = Turma(
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                nome = cursor.getString(cursor.getColumnIndexOrThrow("nome")),
                condutorId = cursor.getInt(cursor.getColumnIndexOrThrow("condutor_id"))
            )
        }
        cursor.close()
        db.close()
        return turma
    }

    fun listar(): List<Turma> {
        val lista = mutableListOf<Turma>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT id, nome, condutor_id FROM ${DatabaseHelper.TBL_TURMAS}", null)
        if (cursor.moveToFirst()) {
            do {
                lista.add(
                    Turma(
                        id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        nome = cursor.getString(cursor.getColumnIndexOrThrow("nome")),
                        condutorId = cursor.getInt(cursor.getColumnIndexOrThrow("condutor_id"))
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return lista
    }

    fun listarParaExibicao(): List<TurmaParaExibicao> {
        val lista = mutableListOf<TurmaParaExibicao>()
        val db = dbHelper.readableDatabase

        val sql = """
            SELECT 
                T.id, 
                T.nome, 
                C.nome as nome_condutor
            FROM ${DatabaseHelper.TBL_TURMAS} T
            LEFT JOIN ${DatabaseHelper.TBL_CONDUTORES} C ON T.condutor_id = C.id
        """.trimIndent()

        val cursor: Cursor = db.rawQuery(sql, null)
        if (cursor.moveToFirst()) {
            do {
                lista.add(
                    TurmaParaExibicao(
                        id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        nome = cursor.getString(cursor.getColumnIndexOrThrow("nome")),
                        nomeCondutor = cursor.getString(cursor.getColumnIndexOrThrow("nome_condutor")) ?: "N/A"
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return lista
    }

    fun adicionar(turma: Turma): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nome", turma.nome)
            put("condutor_id", turma.condutorId)
        }
        val id = db.insert(DatabaseHelper.TBL_TURMAS, null, values)
        db.close()
        return id != -1L
    }

    fun atualizar(turma: Turma): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nome", turma.nome)
            put("condutor_id", turma.condutorId)
        }
        val rows = db.update(DatabaseHelper.TBL_TURMAS, values, "id = ?", arrayOf(turma.id.toString()))
        db.close()
        return rows > 0
    }

    fun excluir(id: Int): Boolean {
        val db = dbHelper.writableDatabase
        val rows = db.delete(DatabaseHelper.TBL_TURMAS, "id = ?", arrayOf(id.toString()))
        db.close()
        return rows > 0
    }
}
