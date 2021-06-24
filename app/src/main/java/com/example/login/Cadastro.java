package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.login.data.model.UserModel;
import com.example.login.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.GetTokenResult;

public class Cadastro extends AppCompatActivity {
    private Button btn_cad;
    private EditText nome, telefone, senha1, senha2, email;
    private FirebaseAuth mAuth;
    private UserModel u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);


        mAuth = FirebaseAuth.getInstance();

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

        btn_cad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String registerEmail = email.getText().toString();
                String senha = senha1.getText().toString();
                String senha3 = senha2.getText().toString();

                if (senha.equals(senha3)) {
                    mAuth.createUserWithEmailAndPassword(registerEmail, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                abrirTelaPrincipal();
                            } else {
                                String error = task.getException().getMessage();
                                Toast.makeText(Cadastro.this, "" + error, Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                } else {

                    Toast.makeText(Cadastro.this, "A senha deve ser a mesma em ambos os campos", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void abrirTelaPrincipal() {
        Intent it = new Intent(Cadastro.this, LoginActivity.class);
        startActivity(it);
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


    };

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;


    }
}



