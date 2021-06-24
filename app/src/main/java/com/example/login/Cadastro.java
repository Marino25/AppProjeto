package com.example.login;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

public class Cadastro extends AppCompatActivity {
    private Button btn_cad;
    private EditText nome, telefone, senha1, senha2, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        nome = findViewById(R.id.ed_nome);
        telefone = findViewById(R.id.ed_tel);
        email = findViewById(R.id.ed_email);
        senha1 = findViewById(R.id.ed_senha);
        senha2 = findViewById(R.id.ed_senha2);

        btn_cad = findViewById(R.id.btnRegistar);

        nome.addTextChangedListener(cadText);
        telefone.addTextChangedListener(cadText);
        email.addTextChangedListener(cadText);
        senha1.addTextChangedListener(cadText);
        senha2.addTextChangedListener(cadText);

    }

    private TextWatcher cadText = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String userNome = nome.getText().toString().trim();
            String userTel = telefone.getText().toString().trim();
            String userEmail = email.getText().toString().trim();
            String userSenha1 = senha1.getText().toString().trim();
            String userSenha2 = senha2.getText().toString().trim();

            btn_cad.setEnabled(!userNome.isEmpty() && !userTel.isEmpty() && !userEmail.isEmpty() && !userSenha1.isEmpty() && !userSenha2.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable s) {



        }


    };}



