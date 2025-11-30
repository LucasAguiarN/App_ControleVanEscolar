package com.example.controlevanescolar.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.controlevanescolar.database.DatabaseHelper
import com.example.controlevanescolar.model.Usuario

class UsuarioDAO(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    fun cadastrar(usuario: Usuario): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nome", usuario.nome)
            put("email", usuario.email)
            put("senha", usuario.senha)
        }
        val id = db.insert(DatabaseHelper.TBL_USUARIOS, null, values)
        db.close()
        return id != -1L
    }

    fun autenticar(email: String, senha: String): Boolean {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery(
            "SELECT id FROM ${DatabaseHelper.TBL_USUARIOS} WHERE email = ? AND senha = ?",
            arrayOf(email, senha)
        )
        val ok = cursor.moveToFirst()
        cursor.close()
        db.close()
        return ok
    }
}
