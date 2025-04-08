package com.example.controledehabitos;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class HabitsFormActivity extends AppCompatActivity {

    EditText nomeInput, descricaoInput;
    DatabaseHelper dbHelper;
    int habitId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_form);

        dbHelper = new DatabaseHelper(this);
        nomeInput = findViewById(R.id.nomeInput);
        descricaoInput = findViewById(R.id.descricaoInput);
        Button salvarButton = findViewById(R.id.salvarButton);

        if (getIntent().hasExtra("habitId")) {
            habitId = getIntent().getIntExtra("habitId", -1);
            Habits h = dbHelper.getHabitById(habitId);
            if (h != null) {
                nomeInput.setText(h.nome);
                descricaoInput.setText(h.descricao);
            }
        }

        salvarButton.setOnClickListener(v -> {
            String nome = nomeInput.getText().toString();
            String desc = descricaoInput.getText().toString();

            if (habitId == -1) {
                dbHelper.insertHabit(new Habits(nome, desc, false));
            } else {
                Habits h = dbHelper.getHabitById(habitId);
                h.nome = nome;
                h.descricao = desc;
                dbHelper.updateHabit(h);
            }

            finish();
        });
    }
}
