package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.login.data.model.UserModel;
import com.example.login.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

                recuperarDados();

                String senha = senha1.getText().toString();
                String confSenha = senha2.getText().toString();

                if(senha.equals(confSenha)){
                    criarLogin();

                }else{

                    Toast.makeText(Cadastro.this, "As senhas não coincidem", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    private void recuperarDados(){

        if (nome.getText().toString()==""||telefone.getText().toString()==""||email.getText().toString()==""||senha1.getText().toString()==""||senha2.getText().toString()=="") {

            Toast.makeText(Cadastro.this, "Voce deve preencher todos os campos", Toast.LENGTH_SHORT).show();

        } else {

            u = new UserModel();
            u.setUsername(nome.getText().toString());
            u.setPassword(senha1.getText().toString());
            u.setEmail(email.getText().toString());
            u.setTelefone(telefone.getText().toString());

        }

    }

    private void criarLogin(){

        mAuth.createUserWithEmailAndPassword(u.getEmail(),u.getPassword()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    u.setId(user.getUid());
                    u.salvar();

                    abrirTelaPrincipal();

                }else{

                    Toast.makeText(Cadastro.this, "Erro ao criar Login", Toast.LENGTH_SHORT);
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


}



