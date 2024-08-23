package com.example.trans_brat;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class Form2Activity extends AppCompatActivity {
    private EditText editTextDate;
    private EditText editTextTime;

    /* Definindo inputs obrigatórios */
    // 0 - EditText
    // 1 - Spinner
    // 2 - RadioGroup
    private final int[][] required_questions = {
            {R.id.section_1_question_2_input, 0},
            {R.id.section_1_question_3_input, 0},
            {R.id.section_2_question_2_input, 1},
            {R.id.section_2_question_3_input, 1},
            {R.id.section_3_question_2_input, 0},
            {R.id.section_3_question_3_input, 0},
            {R.id.section_3_question_4_input, 0},
            {R.id.section_3_question_6_input, 0},
            {R.id.section_3_question_7_input, 0},
            {R.id.section_3_question_8_input, 0},
            {R.id.section_4_question_2_radio_group, 2},
            {R.id.section_4_question_3_radio_group, 2},
            {R.id.section_4_question_4_radio_group, 2},
            {R.id.section_5_question_1_radio_group, 2}
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.form_2), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /* Obtendo respostas do formulário 1 */

        /* Definindo perguntas obrigatórias */
        requiredQuestions();

        /* Adicionando calendário aos inputs de data */
        editTextDate = findViewById(R.id.section_1_question_2_input);
        editTextDate.setOnClickListener(v -> showDatePickerDialog());

        /* Adicionando relógio aos inputs de horário */
        editTextTime = findViewById(R.id.section_1_question_3_input);
        editTextTime.setOnClickListener(v -> showTimePickerDialog());

        /* Adicionando opções aos dropdowns */
        dropdownQuestions();

        /* Formatando inputs de CEP */
        cepQuestions();

        /* Criando popups ao clicar nos botões de informações */
        infoButtons();

        /* Botões inferiores */
        Button buttonBack = findViewById(R.id.back_button);
        Button buttonCancel = findViewById(R.id.cancel_button);
        Button buttonNext = findViewById(R.id.next_button);

        /* (Voltar) Voltando para o formulário 1 */
        buttonBack.setOnClickListener(v -> finish());

        /* (Cancelar) Voltando para o menu principal */
        buttonCancel.setOnClickListener(v -> cancel());

        /* (Avançar) Avançando para o formulário 3 */
        buttonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(checkRequiredQuestions()) {
                    Intent intent = new Intent(Form2Activity.this, Form3Activity.class);
                    startActivity(intent);
                }
            }
        });
    }

    /* Função para obter respostas do formulário anterior */
    private void getPreviousFormAnswers() {}

    /* Função para perguntas obrigatórias */
    private void requiredQuestions() {
        /* Definindo perguntas obrigatórias */
        int[] questions_ids = {
                R.id.section_1_question_2,
                R.id.section_1_question_3,
                R.id.section_2_question_2,
                R.id.section_2_question_3,
                R.id.section_3_question_2,
                R.id.section_3_question_3,
                R.id.section_3_question_4,
                R.id.section_3_question_6,
                R.id.section_3_question_7,
                R.id.section_3_question_8,
                R.id.section_4_question_2,
                R.id.section_4_question_3,
                R.id.section_4_question_4,
                R.id.section_5_question_1
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
            SpannableString spannableString = new SpannableString(textWithAsterisk);

            // Aplicando a cor vermelha ao último caractere (asteriscos)
            spannableString.setSpan(new ForegroundColorSpan(Color.RED), textWithAsterisk.length() - 1, textWithAsterisk.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(spannableString);
        }
    }

    /* Evento para verificar se as perguntas obrigatórias foram respondidas */
    private boolean checkRequiredQuestions() {
        int answered = 0;
        for (int i = 0; i <= required_questions.length - 1; i++) {
            if(required_questions[i][1] == 0) { // EditText
                EditText editText = findViewById(required_questions[i][0]);
                if (!editText.getText().toString().isEmpty()) {
                    answered++;
                    editText.setBackgroundResource(R.drawable.edit_text);
                } else {
                    editText.setBackgroundResource(R.drawable.edit_text_error);
                }
            } else if(required_questions[i][1] == 1) { // Spinner
                Spinner spinner = findViewById(required_questions[i][0]);
                if (!spinner.getSelectedItem().toString().equals("Selecione uma opção")) {
                    answered++;
                    spinner.setBackgroundResource(R.drawable.edit_text);
                } else {
                    spinner.setBackgroundResource(R.drawable.edit_text_error);
                }
            } else if(required_questions[i][1] == 2) { // RadioGroup
                RadioGroup radioGroup = findViewById(required_questions[i][0]);
                int selectedId = radioGroup.getCheckedRadioButtonId();
                boolean groupSelected = selectedId != -1;
                if (groupSelected) {
                    answered++;
                }
            }
        }

        if(answered == required_questions.length) {
            return true;
        } else {
            Toast.makeText(Form2Activity.this, answered + " perguntas respondidas de " + required_questions.length, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /* Função para inputs de data */
    private void showDatePickerDialog() {
        // Obtendo a data atual
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Criando o DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(Form2Activity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                // O mês retornado é zero-based (Janeiro é 0)
                selectedMonth = selectedMonth + 1;
                String date = selectedDay + "/" + selectedMonth + "/" + selectedYear;
                editTextDate.setText(date);
            }
        }, year, month, day
        );

        // Exibindo o DatePickerDialog
        datePickerDialog.show();
    }

    /* Função para inputs de horário */
    private void showTimePickerDialog() {
        // Obtendo o horário atual
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Criando o TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                Form2Activity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                        // Formatando o horário como HH:mm
                        @SuppressLint("DefaultLocale") String time = String.format("%02d:%02d", selectedHour, selectedMinute);
                        editTextTime.setText(time);
                    }
                },
                hour, minute, true // O terceiro argumento define se é 24 horas ou não
        );

        // Exibindo o TimePickerDialog
        timePickerDialog.show();
    }

    /* Função para dropdowns */
    private void dropdownQuestions() {
        /* Definindo perguntas obrigatórias */
        int[] spinnerIds = {
                R.id.section_2_question_2_input,
                R.id.section_2_question_3_input
        };
        String[][] spinnerOptions = {
                {"Selecione uma opção", "Em uma via urbana", "Em uma rodovia", "Em uma estrada (não pavimentada)", "No estacionamento coletivo", "No estacionamento privado"},
                {"Selecione uma opção", "No cruzamento com semáforo", "No cruzamento sem semáforo", "No acesso a outra via ou estrada", "Em uma saída ou entrada de veículos", "Em uma rotatória (rótula)", "No trevo", "Em uma ponte", "No viaduto", "No túnel", "Em uma via de mão única", "Em uma via de mão dupla", "Outros"}
        };

        for (int i = 0; i < spinnerIds.length; i++) {
            Spinner spinner = findViewById(spinnerIds[i]);
            String[] options = spinnerOptions[i];
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, options);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }
    }

    /* Função para formatar CEP */
    private void cepQuestions() {
        /* Definindo perguntas obrigatórias */
        int[] cepIds = {
                R.id.section_3_question_2_input
        };

        for (int cepId : cepIds) {
            EditText cepEditText = findViewById(cepId);
            cepEditText.addTextChangedListener(new TextWatcher() {
                private boolean isUpdating = false;

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String str = s.toString().replaceAll("\\D", "");

                    if (isUpdating) {
                        isUpdating = false;
                        return;
                    }

                    String formatted = "";

                    // Formatar o CEP no formato 12345-678
                    if (str.length() > 5) {
                        formatted = str.substring(0, 5) + "-" + str.substring(5);
                    } else if (!str.isEmpty()) {
                        formatted = str;
                    }

                    isUpdating = true;
                    cepEditText.setText(formatted);
                    cepEditText.setSelection(formatted.length());
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
    }

    /* Função para botões de informações */
    private void infoButtons() {
        /* Definindo perguntas obrigatórias */
        int[] infoIds = {
                R.id.section_5_question_1_radio_1_info,
                R.id.section_5_question_1_radio_2_info,
                R.id.section_5_question_1_radio_3_info,
                R.id.section_5_question_1_radio_4_info,
                R.id.section_5_question_1_radio_5_info
        };
        String[][] infoTexts = {
                {"Abalroamento", "Acidente em os veículos colidem lateral ou transversalmente (pela parte frontal ou traseira do veículo)."},
                {"Capotagem", "Acidente em que o veículo gira sobre si mesmo, em qualquer sentido, chegando a ficar com as rodas para cima, imobilizando-se em qualquer posição."},
                {"Choque", "Acidente em que há impacto de um veículo contra qualquer fixo (muro, árvore, poste) ou móvel, mas sem movimento (veículo parado)"},
                {"Colisão", "Acidente em que o veículo em movimento sofre o impacto de outro veículo, também em movimento."},
                {"Tombamento", "Acidente em o veículo sai da sua posição normal, se imobilizando sobre uma de suas laterais, sua frente ou sua traseira."}
        };

        for (int i = 0; i < infoIds.length; i++) {
            Button infoButton = findViewById(infoIds[i]);
            int finalI = i;
            infoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopup(infoTexts[finalI][0], infoTexts[finalI][1]);
                }
            });
        }
    }

    /* Função para exibir popup */
    private void showPopup(String title, String content){
        AlertDialog.Builder builder = new AlertDialog.Builder(Form2Activity.this);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setPositiveButton("Entendi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /* Função ao cancelar formulário */
    private void cancel() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Form2Activity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.alert_dialog_warning, null);
        builder.setView(dialogLayout);
        builder.setTitle("Atenção!");
        builder.setMessage("Ao continuar, seu e-brat será cancelado e seu processo será perdido.");
        builder.setIcon(R.drawable.icon_warning);
        builder.setPositiveButton("Continuar", (dialog, which) -> {
            dialog.dismiss();
            Intent intent = new Intent(Form2Activity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        builder.setNegativeButton("Voltar", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}