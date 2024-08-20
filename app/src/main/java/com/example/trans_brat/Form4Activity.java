package com.example.trans_brat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Form4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_4);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.form_4), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //requiredQuestions();

        /* Adicionando opções aos dropdowns */
        dropdownQuestions();

        /* Botões inferiores */
        Button buttonBack = findViewById(R.id.back_button);
        Button buttonCancel = findViewById(R.id.cancel_button);
        Button buttonNext = findViewById(R.id.next_button);

        /* (Voltar) Voltando para o formulário 3 */
        buttonBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        /* (Cancelar) Voltando para o menu principal */
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Form4Activity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        /* (Avançar) Avançando para o formulário 5 */
        buttonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Form4Activity.this, Form5Activity.class);
                startActivity(intent);
            }
        });
    }

    /* Função para perguntas obrigatórias */
    private void requiredQuestions() {
        /* Definindo perguntas obrigatórias */
        int[] questions_ids = {
                R.id.section_3_question_1
        };

        /* Para cada pergunta obrigatória */
        for (int question_id : questions_ids) {
            /* Obtendo texto pelos ids das perguntas */
            TextView textView = findViewById(question_id);
            String text = textView.getText().toString();

            /* Adicionando asteriscos nas perguntas obrigatórias */
            String textWithAsterisk = text + " *";
            textView.setText(textWithAsterisk);

            // Verificando se o texto não está vazio
            if (textWithAsterisk.length() > 0) {
                SpannableString spannableString = new SpannableString(textWithAsterisk);

                // Aplicando a cor vermelha ao último caractere (asteriscos)
                spannableString.setSpan(new ForegroundColorSpan(Color.RED), textWithAsterisk.length() - 1, textWithAsterisk.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView.setText(spannableString);
            }
        }
    }

    /* Função para dropdowns */
    private void dropdownQuestions() {
        int[] spinnerIds = {
                R.id.section_1_question_1_input
        };
        String[][] spinnerOptions = {
                {"1", "2", "3", "4", "5"}
        };

        for (int i = 0; i < spinnerIds.length; i++) {
            Spinner spinner = findViewById(spinnerIds[i]);
            String[] options = spinnerOptions[i];
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, options);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }
    }
}