package com.example.provaac1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    LinearLayout container;
    BancoHelper banco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        banco = new BancoHelper(this);

        ScrollView scroll = new ScrollView(this);
        container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setPadding(16, 16, 16, 16);

        Button btnAdd = new Button(this);
        btnAdd.setText(getString(R.string.add_habit));
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HabitoActivity.class);
            startActivity(intent);
        });

        container.addView(btnAdd);
        scroll.addView(container);
        setContentView(scroll);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarHabitos();
    }

    private void carregarHabitos() {
        container.removeViews(1, container.getChildCount() - 1);

        ArrayList<Habito> habitos = banco.listar();

        for (Habito h : habitos) {
            LinearLayout itemLayout = new LinearLayout(this);
            itemLayout.setOrientation(LinearLayout.VERTICAL);
            itemLayout.setPadding(16, 16, 16, 16);


            CheckBox chkFeito = new CheckBox(this);
            chkFeito.setChecked(h.feito);
            chkFeito.setText(h.nome);
            chkFeito.setTextSize(18);


            TextView txtDescricao = new TextView(this);
            txtDescricao.setText(h.descricao);
            txtDescricao.setTextSize(14);
            txtDescricao.setPadding(48, 4, 0, 0);


            chkFeito.setOnCheckedChangeListener((buttonView, isChecked) -> {
                h.feito = isChecked;
                banco.atualizar(h);
            });


            chkFeito.setOnClickListener(v -> {
                if (!chkFeito.isPressed()) return;

                Intent i = new Intent(this, HabitoActivity.class);
                i.putExtra("id", h.id);
                startActivity(i);
            });


            chkFeito.setOnLongClickListener(v -> {
                banco.excluir(h.id);
                carregarHabitos();
                return true;
            });

            itemLayout.addView(chkFeito);
            itemLayout.addView(txtDescricao);
            container.addView(itemLayout);
        }
    }
}
