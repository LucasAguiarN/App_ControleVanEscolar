package com.example.controlevanescolar.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.controlevanescolar.database.DatabaseHelper
import com.example.controlevanescolar.model.Escola

class EscolaDAO(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    fun getById(id: Int): Escola? {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT id, nome, endereco, telefone, cep FROM ${DatabaseHelper.TBL_ESCOLAS} WHERE id = ?", arrayOf(id.toString()))
        var escola: Escola? = null
        if (cursor.moveToFirst()) {
            escola = Escola(
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                nome = cursor.getString(cursor.getColumnIndexOrThrow("nome")),
                endereco = cursor.getString(cursor.getColumnIndexOrThrow("endereco")),
                telefone = cursor.getString(cursor.getColumnIndexOrThrow("telefone")),
                cep = cursor.getString(cursor.getColumnIndexOrThrow("cep"))
            )
        }
        cursor.close()
        db.close()
        return escola
    }

    fun listar(): List<Escola> {
        val lista = mutableListOf<Escola>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT id, nome, endereco, telefone, cep FROM ${DatabaseHelper.TBL_ESCOLAS}", null)
        if (cursor.moveToFirst()) {
            do {
                lista.add(
                    Escola(
                        id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        nome = cursor.getString(cursor.getColumnIndexOrThrow("nome")),
                        endereco = cursor.getString(cursor.getColumnIndexOrThrow("endereco")),
                        telefone = cursor.getString(cursor.getColumnIndexOrThrow("telefone")),
                        cep = cursor.getString(cursor.getColumnIndexOrThrow("cep"))
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return lista
    }

    fun adicionar(escola: Escola): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nome", escola.nome)
            put("endereco", escola.endereco)
            put("telefone", escola.telefone)
            put("cep", escola.cep)
        }
        val id = db.insert(DatabaseHelper.TBL_ESCOLAS, null, values)
        db.close()
        return id != -1L
    }

    fun atualizar(escola: Escola): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nome", escola.nome)
            put("endereco", escola.endereco)
            put("telefone", escola.telefone)
            put("cep", escola.cep)
        }
        val rows = db.update(DatabaseHelper.TBL_ESCOLAS, values, "id = ?", arrayOf(escola.id.toString()))
        db.close()
        return rows > 0
    }

    fun excluir(id: Int): Boolean {
        val db = dbHelper.writableDatabase
        val rows = db.delete(DatabaseHelper.TBL_ESCOLAS, "id = ?", arrayOf(id.toString()))
        db.close()
        return rows > 0
    }
}
