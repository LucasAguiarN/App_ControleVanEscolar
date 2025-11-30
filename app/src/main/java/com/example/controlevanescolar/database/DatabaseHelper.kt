package com.example.controlevanescolar.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "tiodaperua.db"
        private const val DATABASE_VERSION = 10 // Versão incrementada

        const val TBL_USUARIOS = "usuarios"
        const val TBL_ALUNOS = "alunos"
        const val TBL_TURMAS = "turmas"
        const val TBL_CONDUTORES = "condutores"
        const val TBL_RESPONSAVEIS = "responsaveis"
        const val TBL_ESCOLAS = "escolas"
    }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
        db?.execSQL("PRAGMA foreign_keys=ON;")
    }

    override fun onCreate(db: SQLiteDatabase) {
        val sqlUsuarios = """
            CREATE TABLE $TBL_USUARIOS (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                email TEXT NOT NULL UNIQUE,
                senha TEXT NOT NULL
            );
        """.trimIndent()

        val sqlAlunos = """
            CREATE TABLE $TBL_ALUNOS (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                responsavel_id INTEGER,
                escola_id INTEGER,
                turma_id INTEGER,
                FOREIGN KEY(responsavel_id) REFERENCES $TBL_RESPONSAVEIS(id),
                FOREIGN KEY(escola_id) REFERENCES $TBL_ESCOLAS(id),
                FOREIGN KEY(turma_id) REFERENCES $TBL_TURMAS(id)
            );
        """.trimIndent()

        val sqlTurmas = """
            CREATE TABLE $TBL_TURMAS (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                condutor_id INTEGER,
                FOREIGN KEY(condutor_id) REFERENCES $TBL_CONDUTORES(id)
            );
        """.trimIndent()

        val sqlCondutores = """
            CREATE TABLE $TBL_CONDUTORES (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                cnh TEXT NOT NULL,
                cpf TEXT NOT NULL,
                cargo TEXT NOT NULL
            );
        """.trimIndent()

        val sqlResponsaveis = """
            CREATE TABLE $TBL_RESPONSAVEIS (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                telefone TEXT NOT NULL,
                cpf TEXT NOT NULL,
                parentesco TEXT NOT NULL
            );
        """.trimIndent()

        val sqlEscolas = """
            CREATE TABLE $TBL_ESCOLAS (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                endereco TEXT NOT NULL,
                telefone TEXT NOT NULL,
                cep TEXT NOT NULL
            );
        """.trimIndent()

        db.execSQL(sqlUsuarios)
        db.execSQL(sqlCondutores)
        db.execSQL(sqlResponsaveis)
        db.execSQL(sqlEscolas)
        db.execSQL(sqlTurmas)
        db.execSQL(sqlAlunos)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TBL_ALUNOS")
        db.execSQL("DROP TABLE IF EXISTS $TBL_TURMAS")
        db.execSQL("DROP TABLE IF EXISTS $TBL_ESCOLAS")
        db.execSQL("DROP TABLE IF EXISTS $TBL_RESPONSAVEIS")
        db.execSQL("DROP TABLE IF EXISTS $TBL_CONDUTORES")
        db.execSQL("DROP TABLE IF EXISTS $TBL_USUARIOS")
        onCreate(db)
    }
}
