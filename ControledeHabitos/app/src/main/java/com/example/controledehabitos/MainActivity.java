package com.example.controledehabitos;

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
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DatabaseHelper(this);

        ScrollView scroll = new ScrollView(this);
        container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setPadding(16, 16, 16, 16);

        Button btnAdicionar = new Button(this);
        btnAdicionar.setText("Adicionar Hábito");
        btnAdicionar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HabitsFormActivity.class);
            startActivity(intent);
        });

        container.addView(btnAdicionar);
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

        ArrayList<Habits> habitsList = dbHelper.getAllHabits();

        for (Habits h : habitsList) {
            LinearLayout itemLayout = new LinearLayout(this);
            itemLayout.setOrientation(LinearLayout.VERTICAL);
            itemLayout.setPadding(16, 16, 16, 16);

            CheckBox chkFeito = new CheckBox(this);
            chkFeito.setChecked(h.isFeitoHoje());
            chkFeito.setText(h.getNome());
            chkFeito.setTextSize(18);

            TextView txtDescricao = new TextView(this);
            txtDescricao.setText(h.getDescricao());
            txtDescricao.setTextSize(14);
            txtDescricao.setPadding(48, 4, 0, 0);

            // Atualizar status do hábito quando marcado/desmarcado
            chkFeito.setOnCheckedChangeListener((buttonView, isChecked) -> {
                h.setFeitoHoje(isChecked);
                dbHelper.updateHabit(h);
            });

            // Editar hábito ao clicar
            chkFeito.setOnClickListener(v -> {
                if (!chkFeito.isPressed()) return;

                Intent i = new Intent(this, HabitsFormActivity.class);
                i.putExtra("habitId", h.getId());
                startActivity(i);
            });

            // Excluir hábito ao pressionar longo
            chkFeito.setOnLongClickListener(v -> {
                dbHelper.deleteHabit(h.getId());
                carregarHabitos();
                return true;
            });

            itemLayout.addView(chkFeito);
            itemLayout.addView(txtDescricao);
            container.addView(itemLayout);
        }
    }
}
