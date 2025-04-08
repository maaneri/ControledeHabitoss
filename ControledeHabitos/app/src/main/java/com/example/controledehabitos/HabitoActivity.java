package com.example.provaac1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class HabitoActivity extends AppCompatActivity {

    EditText editNome, editDescricao;
    BancoHelper banco;
    int habitoId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habito_activity);

        banco = new BancoHelper(this);
        editNome = findViewById(R.id.editNome);
        editDescricao = findViewById(R.id.editDescricao);
        Button btnSalvar = findViewById(R.id.btnSalvar);

        if (getIntent().hasExtra("id")) {
            habitoId = getIntent().getIntExtra("id", -1);
            Habito h = banco.buscarPorId(habitoId);
            if (h != null) {
                editNome.setText(h.nome);
                editDescricao.setText(h.descricao);
            }
        }

        btnSalvar.setOnClickListener(v -> {
            String nome = editNome.getText().toString();
            String desc = editDescricao.getText().toString();

            if (habitoId == -1) {
                banco.inserir(nome, desc);
            } else {
                Habito h = banco.buscarPorId(habitoId);
                h.nome = nome;
                h.descricao = desc;
                banco.atualizar(h);
            }

            finish();
        });
    }
}
