package com.example.trans_brat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class Form1Activity extends AppCompatActivity {
    /* Definindo RadioGroup's obrigatórios */
    private final int[] questions_ids_inputs = {
            R.id.section_1_question_1_radio_group,
            R.id.section_2_question_1_radio_group,
            R.id.section_3_question_1_radio_group,
            R.id.section_4_question_1_radio_group,
            R.id.section_5_question_1_radio_group
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.form_1), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /* Desativando botão de avançar */
        disableNextButton();

        /* Definindo perguntas obrigatórias */
        requiredQuestions();

        /* Definindo RadioButton que bloqueiam o botão de avançar */
        setRadioButtonsEvent();

        /* Evento para verificar se as perguntas obrigatórias foram respondidas */
        requiredQuestionsEvent();

        /* Botões inferiores */
        Button buttonCancel = findViewById(R.id.cancel_button);
        Button buttonNext = findViewById(R.id.next_button);

        /* (Cancelar) Voltando para o menu principal */
        buttonCancel.setOnClickListener(v -> cancel());

        /* Avançando para o formulário 2 */
        buttonNext.setOnClickListener(v -> {
            /* Obtendo todas as respostas antes de avançar */
            RadioGroup RadioGroup1 = findViewById(R.id.section_1_question_1_radio_group);
            RadioGroup RadioGroup2 = findViewById(R.id.section_2_question_1_radio_group);
            RadioGroup RadioGroup3 = findViewById(R.id.section_3_question_1_radio_group);
            RadioGroup RadioGroup4 = findViewById(R.id.section_4_question_1_radio_group);
            RadioGroup RadioGroup5 = findViewById(R.id.section_5_question_1_radio_group);
            int selectedId1 = RadioGroup1.getCheckedRadioButtonId();
            int selectedId2 = RadioGroup2.getCheckedRadioButtonId();
            int selectedId3 = RadioGroup3.getCheckedRadioButtonId();
            int selectedId4 = RadioGroup4.getCheckedRadioButtonId();
            int selectedId5 = RadioGroup5.getCheckedRadioButtonId();
            RadioButton selectedRadioButton1 = findViewById(selectedId1);
            RadioButton selectedRadioButton2 = findViewById(selectedId2);
            RadioButton selectedRadioButton3 = findViewById(selectedId3);
            RadioButton selectedRadioButton4 = findViewById(selectedId4);
            RadioButton selectedRadioButton5 = findViewById(selectedId5);
            String selectedText1 = selectedRadioButton1.getText().toString();
            String selectedText2 = selectedRadioButton2.getText().toString();
            String selectedText3 = selectedRadioButton3.getText().toString();
            String selectedText4 = selectedRadioButton4.getText().toString();
            String selectedText5 = selectedRadioButton5.getText().toString();
            Intent intent = new Intent(Form1Activity.this, Form2Activity.class);
            startActivity(intent);
        });
    }

    /* Função para perguntas obrigatórias */
    private void requiredQuestions() {
        /* Definindo perguntas obrigatórias */
        int[] questions_ids = {
                R.id.section_1_question_1,
                R.id.section_2_question_1,
                R.id.section_3_question_1,
                R.id.section_4_question_1,
                R.id.section_5_question_1
        };

        /* Adicionando asteriscos para cada pergunta obrigatória */
        requiredQuestionsAddAsterisks(questions_ids);
    }

    /* Função para adicionar asteriscos a perguntas obrigatórias */
    private void requiredQuestionsAddAsterisks(int[] ids) {
        for (int question_id : ids) {
            /* Obtendo texto pelos ids das perguntas */
            TextView textView = findViewById(question_id);
            String text = textView.getText().toString();

            /* Adicionando asteriscos nas perguntas obrigatórias */
            String textWithAsterisk = text + " *";
            textView.setText(textWithAsterisk);
            SpannableString spannableString = new SpannableString(textWithAsterisk);

            // Aplicando a cor vermelha ao último caractere (asteriscos)
            spannableString.setSpan(new ForegroundColorSpan(Color.RED), textWithAsterisk.length() - 1, textWithAsterisk.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(spannableString);
        }
    }

    /* Evento para verificar se todas as perguntas obrigatórias foram respondidas */
    private void requiredQuestionsEvent() {
        for (int i = 0; i <= questions_ids_inputs.length - 1; i++) {
            /* Obtendo inputs pelos ids */
            RadioGroup radioGroup = findViewById(questions_ids_inputs[i]);
            radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                // Executar uma função quando a seleção mudar
                requiredQuestionsCheck();
            });
        }
    }

    /* Evento para verificar se todas as perguntas obrigatórias foram respondidas */
    private void requiredQuestionsCheck() {
        int answered = 0;
        for (int i = 0; i <= questions_ids_inputs.length - 1; i++) {
            RadioGroup radioGroup = findViewById(questions_ids_inputs[i]);
            int selectedId = radioGroup.getCheckedRadioButtonId();
            boolean groupSelected = selectedId != -1;
            if (groupSelected) {
                answered++;
            }
        }

        if(answered == questions_ids_inputs.length) {
            enableNextButton();
        } else {
            disableNextButton();
        }
    }

    /* Evento para verificar se algum RadioButton está selecionado */
    private void setRadioButtonsEvent() {
        int[] radios_ids = {
                R.id.section_1_question_1_radio_1,
                R.id.section_1_question_1_radio_2,
                R.id.section_2_question_1_radio_1,
                R.id.section_2_question_1_radio_2,
                R.id.section_3_question_1_radio_1,
                R.id.section_3_question_1_radio_2,
                R.id.section_4_question_1_radio_1,
                R.id.section_4_question_1_radio_2,
                R.id.section_5_question_1_radio_1,
                R.id.section_5_question_1_radio_2
        };

        int[] radios_block_ids = {
                R.id.section_3_question_1_radio_1,
                R.id.section_4_question_1_radio_1,
                R.id.section_5_question_1_radio_1
        };
        List<Integer> radio_block_ids_list = new ArrayList<>();
        for (int num : radios_block_ids) {
            radio_block_ids_list.add(num);
        }

        String[] radios_warnings = {
                "Acidentes ocorridos fora do estado do Rio de Janeiro ou em rodovias federais não podem ser registrados pela Polícia Militar do Estado do Rio de Janeiro.",
                "Acidentes de trânsito com vítimas, feridos ou mortos, não podem ser registrados eletronicamente. Neste caso, a Polícia Militar deve ser acionada para fazer o registro no local do acidente.",
                "Acidentes de trânsito sem vítimas com mais de 5 veículos envolvidos devem ser registrados no Batalhão de Polícia Militar mais próximo."
        };

        for (int i = 0; i <= radios_ids.length - 1; i++) {
            int finalI = i;
            RadioButton radioButton = findViewById(radios_ids[finalI]);
            radioButton.setOnClickListener(v -> {
                for(int j = 0; j <= radios_block_ids.length - 1; j++) {
                    RadioButton radioBlockButton = findViewById(radios_block_ids[j]);
                    if(radioBlockButton.isChecked()) {
                        disableNextButton();
                        if(radio_block_ids_list.contains(radios_ids[finalI])) {
                            showPopup(radios_warnings[j]);
                        }
                        return;
                    }
                }
            });
        }
        requiredQuestionsCheck();
    }

    /* Função para desativar botão de avançar */
    private void disableNextButton() {
        Button button = findViewById(R.id.next_button);
        button.setEnabled(false);
        button.setBackgroundResource(R.drawable.button_next_disabled);
    }

    /* Função para ativar botão de avançar */
    private void enableNextButton() {
        Button button = findViewById(R.id.next_button);
        button.setEnabled(true);
        button.setBackgroundResource(R.drawable.button_next);
    }

    /* Função para exibir popup */
    private void showPopup(String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Form1Activity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.alert_dialog_warning, null);
        builder.setView(dialogLayout);
        builder.setTitle("Atenção!");
        builder.setMessage(content);
        builder.setIcon(R.drawable.icon_warning);
        builder.setPositiveButton("Entendi", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /* Função ao cancelar formulário */
    private void cancel() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Form1Activity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.alert_dialog_warning, null);
        builder.setView(dialogLayout);
        builder.setTitle("Atenção!");
        builder.setMessage("Ao continuar, seu e-brat será cancelado e seu processo será perdido.");
        builder.setIcon(R.drawable.icon_warning);
        builder.setPositiveButton("Continuar", (dialog, which) -> {
            dialog.dismiss();
            Intent intent = new Intent(Form1Activity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        builder.setNegativeButton("Voltar", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}