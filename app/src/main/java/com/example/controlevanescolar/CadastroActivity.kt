package com.example.controlevanescolar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.controlevanescolar.dao.UsuarioDAO
import com.example.controlevanescolar.model.Usuario

class CadastroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        val editNome = findViewById<EditText>(R.id.editNome)
        val editEmail = findViewById<EditText>(R.id.editEmail)
        val editSenha = findViewById<EditText>(R.id.editSenha)
        val btnSalvar = findViewById<Button>(R.id.btnSalvar)
        val btnCancelar = findViewById<Button>(R.id.btnCancelar)

        btnSalvar.setOnClickListener {
            val nome = editNome.text.toString().trim()
            val email = editEmail.text.toString().trim()
            val senha = editSenha.text.toString().trim()
            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val dao = UsuarioDAO(this)
            val usuario = Usuario(nome = nome, email = email, senha = senha)
            if (dao.cadastrar(usuario)) {
                Toast.makeText(this, "Cadastro realizado", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Erro ao cadastrar (email já existe?)", Toast.LENGTH_SHORT).show()
            }
        }

        btnCancelar.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
