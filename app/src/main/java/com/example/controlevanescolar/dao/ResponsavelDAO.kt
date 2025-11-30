package com.example.controlevanescolar.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.controlevanescolar.database.DatabaseHelper
import com.example.controlevanescolar.model.Responsavel

class ResponsavelDAO(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    fun getById(id: Int): Responsavel? {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT id, nome, telefone, cpf, parentesco FROM ${DatabaseHelper.TBL_RESPONSAVEIS} WHERE id = ?", arrayOf(id.toString()))
        var responsavel: Responsavel? = null
        if (cursor.moveToFirst()) {
            responsavel = Responsavel(
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                nome = cursor.getString(cursor.getColumnIndexOrThrow("nome")),
                telefone = cursor.getString(cursor.getColumnIndexOrThrow("telefone")),
                cpf = cursor.getString(cursor.getColumnIndexOrThrow("cpf")),
                parentesco = cursor.getString(cursor.getColumnIndexOrThrow("parentesco"))
            )
        }
        cursor.close()
        db.close()
        return responsavel
    }

    fun listar(): List<Responsavel> {
        val lista = mutableListOf<Responsavel>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT id, nome, telefone, cpf, parentesco FROM ${DatabaseHelper.TBL_RESPONSAVEIS}", null)
        if (cursor.moveToFirst()) {
            do {
                lista.add(
                    Responsavel(
                        id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        nome = cursor.getString(cursor.getColumnIndexOrThrow("nome")),
                        telefone = cursor.getString(cursor.getColumnIndexOrThrow("telefone")),
                        cpf = cursor.getString(cursor.getColumnIndexOrThrow("cpf")),
                        parentesco = cursor.getString(cursor.getColumnIndexOrThrow("parentesco"))
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return lista
    }

    fun adicionar(responsavel: Responsavel): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nome", responsavel.nome)
            put("telefone", responsavel.telefone)
            put("cpf", responsavel.cpf)
            put("parentesco", responsavel.parentesco)
        }
        val id = db.insert(DatabaseHelper.TBL_RESPONSAVEIS, null, values)
        db.close()
        return id != -1L
    }

    fun atualizar(responsavel: Responsavel): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nome", responsavel.nome)
            put("telefone", responsavel.telefone)
            put("cpf", responsavel.cpf)
            put("parentesco", responsavel.parentesco)
        }
        val rows = db.update(DatabaseHelper.TBL_RESPONSAVEIS, values, "id = ?", arrayOf(responsavel.id.toString()))
        db.close()
        return rows > 0
    }

    fun excluir(id: Int): Boolean {
        val db = dbHelper.writableDatabase
        val rows = db.delete(DatabaseHelper.TBL_RESPONSAVEIS, "id = ?", arrayOf(id.toString()))
        db.close()
        return rows > 0
    }
}
