package com.example.controlevanescolar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.controlevanescolar.dao.UsuarioDAO

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val editEmail = findViewById<EditText>(R.id.editEmail)
        val editSenha = findViewById<EditText>(R.id.editSenha)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnIrCadastrar = findViewById<Button>(R.id.btnIrCadastrar)

        btnLogin.setOnClickListener {
            val dao = UsuarioDAO(this)
            val email = editEmail.text.toString().trim()
            val senha = editSenha.text.toString().trim()
            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha email e senha", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (dao.autenticar(email, senha)) {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Usuário ou senha inválidos", Toast.LENGTH_SHORT).show()
            }
        }

        btnIrCadastrar.setOnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
        }
    }
}
