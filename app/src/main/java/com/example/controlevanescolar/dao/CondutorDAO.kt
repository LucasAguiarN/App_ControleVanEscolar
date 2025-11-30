package com.example.controlevanescolar.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.controlevanescolar.database.DatabaseHelper
import com.example.controlevanescolar.model.Condutor

class CondutorDAO(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    fun getById(id: Int): Condutor? {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT id, nome, cnh, cpf, cargo FROM ${DatabaseHelper.TBL_CONDUTORES} WHERE id = ?", arrayOf(id.toString()))
        var condutor: Condutor? = null
        if (cursor.moveToFirst()) {
            condutor = Condutor(
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                nome = cursor.getString(cursor.getColumnIndexOrThrow("nome")),
                cnh = cursor.getString(cursor.getColumnIndexOrThrow("cnh")),
                cpf = cursor.getString(cursor.getColumnIndexOrThrow("cpf")),
                cargo = cursor.getString(cursor.getColumnIndexOrThrow("cargo"))
            )
        }
        cursor.close()
        db.close()
        return condutor
    }

    fun listar(): List<Condutor> {
        val lista = mutableListOf<Condutor>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT id, nome, cnh, cpf, cargo FROM ${DatabaseHelper.TBL_CONDUTORES}", null)
        if (cursor.moveToFirst()) {
            do {
                lista.add(
                    Condutor(
                        id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        nome = cursor.getString(cursor.getColumnIndexOrThrow("nome")),
                        cnh = cursor.getString(cursor.getColumnIndexOrThrow("cnh")),
                        cpf = cursor.getString(cursor.getColumnIndexOrThrow("cpf")),
                        cargo = cursor.getString(cursor.getColumnIndexOrThrow("cargo"))
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return lista
    }

    fun adicionar(condutor: Condutor): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nome", condutor.nome)
            put("cnh", condutor.cnh)
            put("cpf", condutor.cpf)
            put("cargo", condutor.cargo)
        }
        val id = db.insert(DatabaseHelper.TBL_CONDUTORES, null, values)
        db.close()
        return id != -1L
    }

    fun atualizar(condutor: Condutor): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nome", condutor.nome)
            put("cnh", condutor.cnh)
            put("cpf", condutor.cpf)
            put("cargo", condutor.cargo)
        }
        val rows = db.update(DatabaseHelper.TBL_CONDUTORES, values, "id = ?", arrayOf(condutor.id.toString()))
        db.close()
        return rows > 0
    }

    fun excluir(id: Int): Boolean {
        val db = dbHelper.writableDatabase
        val rows = db.delete(DatabaseHelper.TBL_CONDUTORES, "id = ?", arrayOf(id.toString()))
        db.close()
        return rows > 0
    }
}
