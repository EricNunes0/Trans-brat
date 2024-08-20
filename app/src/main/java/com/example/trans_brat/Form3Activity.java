package com.example.trans_brat;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class Form3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.form_3), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        requiredQuestions();
        /* Adicionando calendário aos inputs de data */
        setDatePickers();

        /* Formatando placa de veículos */
        placaQuestions();

        /* Formatando inputs de CEP */
        cepQuestions();

        /* Formatando inputs de CNPJ */
        cnpjQuestions();

        /* Formatando inputs de CPF */
        cpfQuestions();

        /* Botões inferiores */
        Button buttonBack = findViewById(R.id.back_button);
        Button buttonCancel = findViewById(R.id.cancel_button);
        Button buttonNext = findViewById(R.id.next_button);

        /* (Voltar) Voltando para o formulário 2 */
        buttonBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        /* (Cancelar) Voltando para o menu principal */
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Form3Activity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        /* (Avançar) Avançando para o formulário 4 */
        buttonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Form3Activity.this, Form4Activity.class);
                startActivity(intent);
            }
        });
    }

    /* Função para perguntas obrigatórias */
    private void requiredQuestions() {
        /* Definindo perguntas obrigatórias */
        int[] questions_ids = {
                R.id.section_1_question_2,
                R.id.section_1_question_3,
                R.id.section_1_question_4,
                R.id.section_1_question_5,
                R.id.section_1_question_6,
                R.id.section_1_question_7,
                R.id.section_1_question_8,
                R.id.section_1_question_9,
                R.id.section_1_question_10,
                R.id.section_1_question_11
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

    /* Função para inputs de data */
    private void setDatePickers() {
        int[] dateIds = {
                R.id.section_3_question_4_input,
                R.id.section_5_question_10_input,
                R.id.section_6_question_4_input,
                R.id.section_6_question_6_input
        };

        for (int i = 0; i < dateIds.length; i++) {
            EditText editTextDate = findViewById(dateIds[i]);
            editTextDate.setOnClickListener(v -> showDatePickerDialog(editTextDate));
        }
    }

    /* Função para exibir calendários */
    private void showDatePickerDialog(EditText editTextDate) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Criando o DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(Form3Activity.this, new DatePickerDialog.OnDateSetListener() {
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

    /* Função para formatar placa de veículo */
    private void placaQuestions() {
        int[] placaIds = {
                R.id.section_1_question_2_input
        };

        for (int i = 0; i < placaIds.length; i++) {
            EditText cepEditText = findViewById(placaIds[i]);
            cepEditText.addTextChangedListener(new TextWatcher() {
                private boolean isUpdating = false;
                private String oldText = "";

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String str = s.toString().replaceAll("[^\\d]", "");

                    if (isUpdating) {
                        oldText = str;
                        isUpdating = false;
                        return;
                    }

                    String formatted = "";

                    // Formatar a placa no formato 123-4567
                    if (str.length() > 3) {
                        formatted = str.substring(0, 3) + "-" + str.substring(3);
                    } else if (str.length() > 0) {
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

    /* Função para formatar CEP */
    private void cepQuestions() {
        /* Definindo perguntas obrigatórias */
        int[] cepIds = {
                R.id.section_4_question_2_input,
                R.id.section_5_question_2_input,
                R.id.section_7_question_2_input
        };

        for (int i = 0; i < cepIds.length; i++) {
            EditText cepEditText = findViewById(cepIds[i]);
            cepEditText.addTextChangedListener(new TextWatcher() {
                private boolean isUpdating = false;
                private String oldText = "";

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String str = s.toString().replaceAll("[^\\d]", "");

                    if (isUpdating) {
                        oldText = str;
                        isUpdating = false;
                        return;
                    }

                    String formatted = "";

                    // Formatar o CEP no formato 12345-678
                    if (str.length() > 5) {
                        formatted = str.substring(0, 5) + "-" + str.substring(5);
                    } else if (str.length() > 0) {
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

    /* Função para formatar CNPJ */
    private void cnpjQuestions() {
        int[] cnpjIds = {
                R.id.section_2_question_3_input
        };

        for (int i = 0; i < cnpjIds.length; i++) {
            EditText cnpjEditText = findViewById(cnpjIds[i]);
            cnpjEditText.addTextChangedListener(new TextWatcher() {
                private boolean isUpdating = false;
                private String oldText = "";

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String str = s.toString().replaceAll("[^\\d]", "");

                    if (isUpdating) {
                        oldText = str;
                        isUpdating = false;
                        return;
                    }

                    if (str.length() < oldText.length()) {
                        oldText = str;
                        isUpdating = true;
                        cnpjEditText.setText(oldText);
                        cnpjEditText.setSelection(oldText.length());
                        return;
                    }

                    // Formatação
                    if (str.length() > 2) {
                        str = str.substring(0, 2) + "." + str.substring(2);
                    }
                    if (str.length() > 6) {
                        str = str.substring(0, 6) + "." + str.substring(6);
                    }
                    if (str.length() > 10) {
                        str = str.substring(0, 10) + "/" + str.substring(10);
                    }
                    if (str.length() > 15) {
                        str = str.substring(0, 15) + "-" + str.substring(15);
                    }

                    isUpdating = true;
                    cnpjEditText.setText(str);
                    cnpjEditText.setSelection(str.length());
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
    }

    /* Função para formatar CPF */
    private void cpfQuestions() {
        int[] cpfIds = {
                R.id.section_3_question_3_input,
                R.id.section_6_question_3_input
        };

        for (int i = 0; i < cpfIds.length; i++) {
            EditText cpfEditText = findViewById(cpfIds[i]);
            cpfEditText.addTextChangedListener(new TextWatcher() {
                private boolean isUpdating = false;
                private String oldText = "";

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String str = s.toString().replaceAll("[^\\d]", "");

                    if (isUpdating) {
                        oldText = str;
                        isUpdating = false;
                        return;
                    }

                    if (str.length() < oldText.length()) {
                        oldText = str;
                        isUpdating = true;
                        cpfEditText.setText(oldText);
                        cpfEditText.setSelection(oldText.length());
                        return;
                    }

                    // Formatação
                    if (str.length() > 3) {
                        str = str.substring(0, 3) + "." + str.substring(3);
                    }
                    if (str.length() > 7) {
                        str = str.substring(0, 7) + "." + str.substring(7);
                    }
                    if (str.length() > 11) {
                        str = str.substring(0, 11) + "-" + str.substring(11);
                    }

                    isUpdating = true;
                    cpfEditText.setText(str);
                    cpfEditText.setSelection(str.length());
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
    }
}