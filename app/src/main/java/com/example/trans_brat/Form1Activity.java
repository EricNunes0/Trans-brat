package com.example.trans_brat;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import java.util.Calendar;
import java.util.List;

public class Form1Activity extends AppCompatActivity {
    /* Definindo RadioGroup's obrigatórios */
    private int[] questions_ids_inputs = {
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
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Form1Activity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        /* Avançando para o formulário 2 */
        buttonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Form1Activity.this, Form2Activity.class);
                startActivity(intent);
            }
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

            // Verificando se o texto não está vazio
            if (textWithAsterisk.length() > 0) {
                SpannableString spannableString = new SpannableString(textWithAsterisk);

                // Aplicando a cor vermelha ao último caractere (asteriscos)
                spannableString.setSpan(new ForegroundColorSpan(Color.RED), textWithAsterisk.length() - 1, textWithAsterisk.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView.setText(spannableString);
            }
        }
    }

    /* Evento para verificar se todas as perguntas obrigatórias foram respondidas */
    private void requiredQuestionsEvent() {
        for (int i = 0; i <= questions_ids_inputs.length - 1; i++) {
            /* Obtendo inputs pelos ids */
            int finalI = i;
            RadioGroup radioGroup = findViewById(questions_ids_inputs[finalI]);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // Executar uma função quando a seleção mudar
                    requiredQuestionsCheck();
                }
            });
        }
    }

    /* Evento para verificar se todas as perguntas obrigatórias foram respondidas */
    private void requiredQuestionsCheck() {
        int answered = 0;
        for (int i = 0; i <= questions_ids_inputs.length - 1; i++) {
            int finalI = i;
            RadioGroup radioGroup = findViewById(questions_ids_inputs[finalI]);
            int selectedId = radioGroup.getCheckedRadioButtonId();
            boolean groupSelected = selectedId != -1;
            if (groupSelected) {
                answered++;
            }
        }

        if(answered == questions_ids_inputs.length) {
            enableNextButton();
            Toast.makeText(this, "Você respondeu todas as perguntas :D", Toast.LENGTH_SHORT).show();
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
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
                    enableNextButton();
                }
            });
        }
        requiredQuestionsCheck();
    }

    /* Função para desativar botão de avançar */
    private void disableNextButton() {
        Button button = findViewById(R.id.next_button);
        button.setEnabled(false);
        button.setVisibility(View.GONE);
    }

    /* Função para ativar botão de avançar */
    private void enableNextButton() {
        Button button = findViewById(R.id.next_button);
        button.setEnabled(true);
        button.setVisibility(View.VISIBLE);
    }

    /* Função para exibir popup */
    private void showPopup(String content){
        AlertDialog.Builder builder = new AlertDialog.Builder(Form1Activity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.alert_dialog_warning, null);
        builder.setView(dialogLayout);
        builder.setTitle("Atenção!");
        builder.setMessage(content);
        builder.setIcon(R.drawable.icon_warning);
        builder.setPositiveButton("Entendi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}