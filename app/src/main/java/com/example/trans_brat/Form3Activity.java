package com.example.trans_brat;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Form3Activity extends AppCompatActivity {
    private final String logId = "Form3Activity_LOG";
    /* Respostas do formulário anterior */
    private final String[] previousAnswersIds = {
            "F1_S0_Q1",
            "F1_S0_Q2",
            "F2_S1_Q2",
            "F2_S1_Q3",
            "F2_S2_Q2",
            "F2_S2_Q3",
            "F2_S3_Q2",
            "F2_S3_Q3",
            "F2_S3_Q4",
            "F2_S3_Q5",
            "F2_S3_Q6",
            "F2_S3_Q7",
            "F2_S3_Q8",
            "F2_S3_Q9",
            "F2_S4_Q2",
            "F2_S4_Q3",
            "F2_S4_Q4",
            "F2_S5_Q1"
    };
    /* Respostas para enviar para o formulário seguinte */
    private final String[] toSendAnswersIds = {
            "F3_S1_Q2",
            "F3_S1_Q3",
            "F3_S1_Q4",
            "F3_S1_Q5",
            "F3_S1_Q6",
            "F3_S1_Q7",
            "F3_S1_Q8",
            "F3_S1_Q9",
            "F3_S1_Q10",
            "F3_S1_Q11",
            "F3_S1_Q12",
            "F3_S2_Q2",
            "F3_S2_Q3",
            "F3_S3_Q2",
            "F3_S3_Q3",
            "F3_S3_Q4",
            "F3_S3_Q5",
            "F3_S4_Q2",
            "F3_S4_Q3",
            "F3_S4_Q4",
            "F3_S4_Q5",
            "F3_S4_Q6",
            "F3_S4_Q7",
            "F3_S4_Q8",
            "F3_S4_Q9",
            "F3_S5_Q2",
            "F3_S5_Q3",
            "F3_S5_Q4",
            "F3_S5_Q5",
            "F3_S5_Q6",
            "F3_S5_Q7",
            "F3_S5_Q8",
            "F3_S5_Q9",
            "F3_S5_Q10",
            "F3_S6_Q2",
            "F3_S6_Q3",
            "F3_S6_Q4",
            "F3_S6_Q5",
            "F3_S6_Q6",
            "F3_S7_Q2",
            "F3_S7_Q3",
            "F3_S7_Q4",
            "F3_S7_Q5",
            "F3_S7_Q6",
            "F3_S7_Q7",
            "F3_S7_Q8",
            "F3_S8_Q1",
            "F3_S8_Q2",
            "F3_S8_Q3",
            "F3_S8_Q4",
            "F3_S8_Q5",
            "F3_S8_Q6"
    };

    /* Definindo inputs obrigatórios */
    // [0] 0 - Opcional 0 | Obrigatório: 1
    // [0] 1 - Obrigatório

    // [1] Id

    // [2] 0 - EditText
    // [2] 1 - Spinner
    // [2] 2 - RadioGroup
    // [2] 3 - Checkbox

    // [3] X - Seção
    private final int[][] all_questions = {
            {1, R.id.section_1_question_2_input, 0, 1},
            {1, R.id.section_1_question_3_input, 0, 1},
            {1, R.id.section_1_question_4_input, 0, 1},
            {1, R.id.section_1_question_5_input, 0, 1},
            {1, R.id.section_1_question_6_input, 0, 1},
            {1, R.id.section_1_question_7_input, 0, 1},
            {1, R.id.section_1_question_8_input, 0, 1},
            {1, R.id.section_1_question_9_input, 0, 1},
            {1, R.id.section_1_question_10_input, 0, 1},
            {1, R.id.section_1_question_11_input, 0, 1},
            {1, R.id.section_1_question_12_radio_group, 2, 1},
            {1, R.id.section_2_question_2_input, 0, 2},
            {1, R.id.section_2_question_3_input, 0, 2},
            {1, R.id.section_3_question_2_input, 0, 3},
            {1, R.id.section_3_question_3_input, 0, 3},
            {1, R.id.section_3_question_4_input, 0, 3},
            {1, R.id.section_3_question_5_radio_group, 2, 3},
            {1, R.id.section_4_question_2_input, 0, 4},
            {1, R.id.section_4_question_3_input, 0, 4},
            {1, R.id.section_4_question_4_input, 0, 4},
            {0, R.id.section_4_question_5_input, 0, 4},
            {1, R.id.section_4_question_6_input, 0, 4},
            {1, R.id.section_4_question_7_input, 0, 4},
            {1, R.id.section_4_question_8_input, 0, 4},
            {1, R.id.section_4_question_9_radio_group, 2, 4},
            {1, R.id.section_5_question_2_input, 0, 5},
            {1, R.id.section_5_question_3_input, 0, 5},
            {1, R.id.section_5_question_4_input, 0, 5},
            {0, R.id.section_5_question_5_input, 0, 5},
            {1, R.id.section_5_question_6_input, 0, 5},
            {1, R.id.section_5_question_7_input, 0, 5},
            {1, R.id.section_5_question_8_input, 0, 5},
            {1, R.id.section_5_question_9_input, 0, 5},
            {1, R.id.section_5_question_10_input, 0, 5},
            {1, R.id.section_6_question_2_input, 0, 6},
            {1, R.id.section_6_question_3_input, 0, 6},
            {1, R.id.section_6_question_4_input, 0, 6},
            {1, R.id.section_6_question_5_input, 0, 6},
            {1, R.id.section_6_question_6_input, 0, 6},
            {1, R.id.section_7_question_2_input, 0, 7},
            {1, R.id.section_7_question_3_input, 0, 7},
            {1, R.id.section_7_question_4_input, 0, 7},
            {0, R.id.section_7_question_5_input, 0, 7},
            {1, R.id.section_7_question_6_input, 0, 7},
            {1, R.id.section_7_question_7_input, 0, 7},
            {1, R.id.section_7_question_8_input, 0, 7},
            {1, 0, 3, 8}
    };

    /* Ids das perguntas que podem ser exibidas/escondidas */
    private final int[] toggleableQuestionsSectionsIds = {
            R.id.section_2_group,
            R.id.section_3_group,
            R.id.section_4_group,
            R.id.section_5_group,
            R.id.section_6_group,
            R.id.section_7_group
    };

    private int[] hiddenQuestions = {2, 4};
    private Map<String, String[]> dataMap;

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

        /* Obtendo respostas dos formulários anteriores */
        //getPreviousFormAnswers();

        /* Evento para permitir apenas textos com letras maiúsculas */
        eventTextAllCaps();

        /* Função para preencher automaticamente campos de uma panilha */
        eventWorksheet();

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

        /* Formatando inputs de CNH */
        cnhQuestions();

        /* Formatando inputs de CHASSI */
        chassiQuestions();

        /* Formatando inputs de RENAVAM */
        renavamQuestions();

        /* Marcando os dois RadioGroups como "não" */
        markRadioButton(R.id.section_1_question_12_radio_group, R.id.section_1_question_12_radio_2);
        markRadioButton(R.id.section_3_question_5_radio_group, R.id.section_3_question_5_radio_2);
        /* Exibir 3, 6 e 7 | Esconder 2, 4 e 5 */
        toggleQuestions(new int[] {3, 6, 7}, new int[] {2, 4, 5});

        /* Evento para exibir/esconder partes do formulário ao marcar uma opção em um RadioGroup */
        editFormByRadioGroupEvent();

        /* Adicionando campos preenchidos automaticamente */
        EditText razaoSocialEditText = findViewById(R.id.section_2_question_2_input);
        EditText cnpjEditText = findViewById(R.id.section_2_question_3_input);
        EditText cepEditText = findViewById(R.id.section_4_question_2_input);
        EditText logradEditText = findViewById(R.id.section_4_question_3_input);
        EditText numeroEditText = findViewById(R.id.section_4_question_4_input);
        EditText bairroEditText = findViewById(R.id.section_4_question_6_input);
        EditText cidadeEditText = findViewById(R.id.section_4_question_7_input);
        EditText ufEditText = findViewById(R.id.section_4_question_8_input);
        razaoSocialEditText.setText("TRANSRIVER TRANSPORTE LTDA"); // RAZÃO SOCIAL
        cnpjEditText.setText("27.608.256/0001-32"); // CNPJ
        cepEditText.setText("23030-380"); // CEP
        logradEditText.setText("ESTRADA DA PEDRA"); // LOGRAD.
        numeroEditText.setText("3885"); // NÚMERO
        bairroEditText.setText("GUARATIBA"); // BAIRRO
        cidadeEditText.setText("RIO DE JANEIRO"); // CIDADE
        ufEditText.setText("RJ"); // UF

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
        buttonCancel.setOnClickListener(v -> cancel());

        /* (Avançar) Avançando para o formulário 4 */
        buttonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(checkRequiredQuestions()) {
                    Intent intent = new Intent(Form3Activity.this, Form4Activity.class);
                    sendAnswersToNextForm(intent);
                    try {
                        startActivity(intent);
                    } catch (Exception e) {
                        Log.e(logId, "Erro: " + e);
                    }
                }
            }
        });
    }

    /* Evento para permitir apenas textos com letras maiúsculas */
    private void eventTextAllCaps() {
        for(int[] question : all_questions) {
            if(question[2] == 0) {
                EditText editText = findViewById(question[1]);
                editText.setFilters(new InputFilter[]{
                        new InputFilter.AllCaps()
                });
            }
        }
    }

    /* Função para preencher automaticamente campos de uma panilha */
    private void eventWorksheet() {
        EditText ordemEditText = findViewById(R.id.section_1_question_2_input);

        ordemEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String ordemValue = s.toString();
                if (!ordemValue.isEmpty()) {
                    buscarDadosPorOrdem(ordemValue);
                }
            }
        });
    }

    private void buscarDadosPorOrdem(String ordem) {
        EditText placaEditText = findViewById(R.id.section_1_question_3_input);
        EditText marcaModeloEditText = findViewById(R.id.section_1_question_4_input);
        EditText tipoEditText = findViewById(R.id.section_1_question_5_input);
        EditText corEditText = findViewById(R.id.section_1_question_6_input);
        EditText ufEditText = findViewById(R.id.section_1_question_7_input);
        EditText anoFabricacaoEditText = findViewById(R.id.section_1_question_8_input);
        EditText anoModeloEditText = findViewById(R.id.section_1_question_9_input);
        EditText chassiEditText = findViewById(R.id.section_1_question_10_input);
        EditText renavamEditText = findViewById(R.id.section_1_question_11_input);

        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("frota.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(";");
                Log.i(logId, ordem + Arrays.toString(columns));

                try {
                    // Verifica se a "ORDEM" corresponde ao valor digitado
                    if (columns[0].equals(ordem)) {
                        // Preenche os EditTexts com os dados correspondentes
                        placaEditText.setText(columns[1]);        // PLACA
                        marcaModeloEditText.setText(columns[6]);        // MARCA/MODELO
                        tipoEditText.setText("PASSAGEIRO ÔNIBUS");        // TIPO DO VEÍCULO
                        corEditText.setText("FANTASIA");        // COR
                        ufEditText.setText("RJ");        // UF
                        anoFabricacaoEditText.setText(columns[7]);    // ANO DE FABRICAÇÃO
                        anoModeloEditText.setText(columns[8]);    // ANO DO MODELO
                        chassiEditText.setText(columns[3]);       // CHASSI
                        renavamEditText.setText(columns[2]);      // RENAVAM
                        break; // Encerra a leitura após encontrar a correspondência
                    }
                } catch (Exception e) {
                    Log.e(logId, e.toString());
                }
            }
            reader.close();
        } catch (IOException e) {
            Log.e(logId, e.toString());
            e.printStackTrace();
        }
    }

    /* Função para obter respostas dos formulários anteriores */
    private void getPreviousFormAnswers() {
        for(String answerId : previousAnswersIds) {
            String answer = getIntent().getStringExtra(answerId);
            assert answer != null;
            Log.d(logId, answerId + " - " + answer);
        }
    }

    /* Função para obter resposta de diferentes inputs */
    private String getInputAnswer(int questionId, int questionType) {
        if(questionType == 0) {
            EditText editText = findViewById(questionId);
            return editText.getText().toString();
        } else if(questionType == 1) {
            Spinner spinner = findViewById(questionId);
            return spinner.getSelectedItem().toString();
        } else if(questionType == 2) {
            RadioGroup radioGroup = findViewById(questionId);
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId != -1) {
                RadioButton selectedRadioButton = findViewById(selectedId);
                return selectedRadioButton.getText().toString();
            } else {
                return null;
            }
        } else if(questionType == 3) {
            if(questionId == 0) {
                CheckBox checkBox1 = findViewById(R.id.damage_button_top_left);
                CheckBox checkBox2 = findViewById(R.id.damage_button_top_center);
                CheckBox checkBox3 = findViewById(R.id.damage_button_top_right);
                CheckBox checkBox4 = findViewById(R.id.damage_button_bottom_left);
                CheckBox checkBox5 = findViewById(R.id.damage_button_bottom_center);
                CheckBox checkBox6 = findViewById(R.id.damage_button_bottom_right);

                StringBuilder selectedOptions = new StringBuilder("Selecionado:");
                if (checkBox1.isChecked()) {
                    selectedOptions.append("\n").append(checkBox1.getText().toString());
                }
                if (checkBox2.isChecked()) {
                    selectedOptions.append("\n").append(checkBox2.getText().toString());
                }
                if (checkBox3.isChecked()) {
                    selectedOptions.append("\n").append(checkBox3.getText().toString());
                }
                if (checkBox4.isChecked()) {
                    selectedOptions.append("\n").append(checkBox4.getText().toString());
                }
                if (checkBox5.isChecked()) {
                    selectedOptions.append("\n").append(checkBox5.getText().toString());
                }
                if (checkBox6.isChecked()) {
                    selectedOptions.append("\n").append(checkBox6.getText().toString());
                }

                if (selectedOptions.toString().equals("Selecionado:")) {
                    selectedOptions = new StringBuilder("Nenhuma opção selecionada");
                }
                return selectedOptions.toString();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /* Função para obter resposta de checkboxes */
    private void getCheckboxAnswers(Intent intent, int allQuestionsIndex) {
        if(all_questions[allQuestionsIndex][1] == 0) {
            CheckBox checkBox1 = findViewById(R.id.damage_button_top_left);
            CheckBox checkBox2 = findViewById(R.id.damage_button_top_center);
            CheckBox checkBox3 = findViewById(R.id.damage_button_top_right);
            CheckBox checkBox4 = findViewById(R.id.damage_button_bottom_left);
            CheckBox checkBox5 = findViewById(R.id.damage_button_bottom_center);
            CheckBox checkBox6 = findViewById(R.id.damage_button_bottom_right);

            String checkBox1Text = null;
            String checkBox2Text = null;
            String checkBox3Text = null;
            String checkBox4Text = null;
            String checkBox5Text = null;
            String checkBox6Text = null;

            if (checkBox1.isChecked()) {
                checkBox1Text = String.valueOf(true);
            }
            if (checkBox2.isChecked()) {
                checkBox2Text = String.valueOf(true);
            }
            if (checkBox3.isChecked()) {
                checkBox3Text = String.valueOf(true);
            }
            if (checkBox4.isChecked()) {
                checkBox4Text = String.valueOf(true);
            }
            if (checkBox5.isChecked()) {
                checkBox5Text = String.valueOf(true);
            }
            if (checkBox6.isChecked()) {
                checkBox6Text = String.valueOf(true);
            }

            String questionId1 = toSendAnswersIds[allQuestionsIndex];
            String questionId2 = toSendAnswersIds[allQuestionsIndex+1];
            String questionId3 = toSendAnswersIds[allQuestionsIndex+2];
            String questionId4 = toSendAnswersIds[allQuestionsIndex+3];
            String questionId5 = toSendAnswersIds[allQuestionsIndex+4];
            String questionId6 = toSendAnswersIds[allQuestionsIndex+5];
            intent.putExtra(questionId1, checkBox1Text);
            intent.putExtra(questionId2, checkBox2Text);
            intent.putExtra(questionId3, checkBox3Text);
            intent.putExtra(questionId4, checkBox4Text);
            intent.putExtra(questionId5, checkBox5Text);
            intent.putExtra(questionId6, checkBox6Text);
            Log.i(logId, questionId1 + " - " + checkBox1Text);
            Log.i(logId, questionId2 + " - " + checkBox2Text);
            Log.i(logId, questionId3 + " - " + checkBox3Text);
            Log.i(logId, questionId4 + " - " + checkBox4Text);
            Log.i(logId, questionId5 + " - " + checkBox5Text);
            Log.i(logId, questionId6 + " - " + checkBox6Text);
        }
    }

    /* Função para enviar respostas para o formulário seguinte */
    private void sendAnswersToNextForm(Intent intent) {
        /* Enviando respostas dos formulários anteriores */
        for(int i = 0; i <= previousAnswersIds.length - 1; i++) {
            String toSendAnswerId = previousAnswersIds[i];
            String answer = getIntent().getStringExtra(toSendAnswerId);
            intent.putExtra(toSendAnswerId, answer);
            Log.i(logId, toSendAnswerId + " - " + answer);
        }
        /* Enviando respostas deste formulário */
        for(int i = 0; i <= toSendAnswersIds.length - 1; i++) {
            if (all_questions[i][2] == 3) {
                getCheckboxAnswers(intent, i);
                i += 5;
            } else {
                String toSendAnswerId = toSendAnswersIds[i];
                int requiredQuestionId = all_questions[i][1];
                int requiredQuestionType = all_questions[i][2];
                String answer = getInputAnswer(requiredQuestionId, requiredQuestionType);
                intent.putExtra(toSendAnswerId, answer);
                Log.i(logId, toSendAnswerId + " - " + answer);
            }
        }
    }

    /* Função para verificar se um número existe em um array */
    private boolean arrayContains(int[] array, int number) {
        for (int j : array) {
            if (j == number) {
                return true;
            }
        }
        return false;
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
                R.id.section_1_question_11,
                R.id.section_1_question_12,
                R.id.section_2_question_2,
                R.id.section_2_question_3,
                R.id.section_3_question_2,
                R.id.section_3_question_3,
                R.id.section_3_question_4,
                R.id.section_3_question_5,
                R.id.section_4_question_2,
                R.id.section_4_question_3,
                R.id.section_4_question_4,
                R.id.section_4_question_6,
                R.id.section_4_question_7,
                R.id.section_4_question_8,
                R.id.section_5_question_2,
                R.id.section_5_question_3,
                R.id.section_5_question_4,
                R.id.section_5_question_6,
                R.id.section_5_question_7,
                R.id.section_5_question_8,
                R.id.section_5_question_9,
                R.id.section_5_question_10,
                R.id.section_6_question_2,
                R.id.section_6_question_3,
                R.id.section_6_question_4,
                R.id.section_6_question_5,
                R.id.section_6_question_6,
                R.id.section_7_question_2,
                R.id.section_7_question_3,
                R.id.section_7_question_4,
                R.id.section_7_question_6,
                R.id.section_7_question_7,
                R.id.section_7_question_8,
                R.id.section_8_question_1
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

    /* Função para inputs de data */
    private void setDatePickers() {
        int[] dateIds = {
                R.id.section_3_question_4_input,
                R.id.section_5_question_10_input,
                R.id.section_6_question_4_input,
                R.id.section_6_question_6_input
        };

        for (int dateId : dateIds) {
            EditText editTextDate = findViewById(dateId);
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
                R.id.section_1_question_3_input
        };

        for (int placaId : placaIds) {
            EditText cepEditText = findViewById(placaId);
            cepEditText.addTextChangedListener(new TextWatcher() {
                private boolean isUpdating = false;

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String str = s.toString().replaceAll("[^a-zA-Z0-9]", "");

                    if (isUpdating) {
                        isUpdating = false;
                        return;
                    }

                    String formatted = "";

                    // Formatar a placa no formato 123-4567
                    if (str.length() > 3) {
                        formatted = str.substring(0, 3) + "-" + str.substring(3);
                    } else if (!str.isEmpty()) {
                        formatted = str;
                    }
                    if (str.length() > 7) {
                        formatted = str.substring(0, 3) + "-" + str.substring(3, 7);
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

        for (int cepId : cepIds) {
            EditText cepEditText = findViewById(cepId);
            cepEditText.addTextChangedListener(new TextWatcher() {
                private boolean isUpdating = false;

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String str = s.toString().replaceAll("[^\\d]", "");

                    if (isUpdating) {
                        isUpdating = false;
                        return;
                    }

                    String formatted = "";

                    // Formatar o CEP no formato 12345-678
                    if (str.length() > 8) {
                        formatted = str.substring(0, 5) + "-" + str.substring(5, 8);
                    } else if (str.length() > 5) {
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

    /* Função para formatar CNPJ */
    private void cnpjQuestions() {
        int[] cnpjIds = {
                R.id.section_2_question_3_input
        };

        for (int cnpjId : cnpjIds) {
            EditText cnpjEditText = findViewById(cnpjId);
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
                    if (str.length() > 18) {
                        str = str.substring(0, 18);
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

        for (int cpfId : cpfIds) {
            EditText cpfEditText = findViewById(cpfId);
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
                    if (str.length() > 14) {
                        str = str.substring(0, 14);
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

    /* Função para formatar CNH */
    private void cnhQuestions() {
        int[] cnhIds = {
                R.id.section_5_question_9_input,
                R.id.section_6_question_5_input
        };

        for (int cnhId : cnhIds) {
            EditText cpfEditText = findViewById(cnhId);
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
                    if (str.length() > 9) {
                        str = str.substring(0, 9) + "-" + str.substring(9);
                    }
                    if (str.length() > 12) {
                        str = str.substring(0, 12);
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

    /* Função para formatar CHASSI */
    private void chassiQuestions() {
        int[] chassiIds = {
                R.id.section_1_question_10_input
        };

        for (int chassiId : chassiIds) {
            EditText cpfEditText = findViewById(chassiId);
            cpfEditText.addTextChangedListener(new TextWatcher() {
                private boolean isUpdating = false;
                private String oldText = "";

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String str = s.toString();

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
                    if (str.length() > 17) {
                        str = str.substring(0, 17);
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

    /* Função para formatar RENAVAM */
    private void renavamQuestions() {
        int[] renavamIds = {
                R.id.section_1_question_11_input
        };

        for (int renavamId : renavamIds) {
            EditText cpfEditText = findViewById(renavamId);
            cpfEditText.addTextChangedListener(new TextWatcher() {
                private boolean isUpdating = false;
                private String oldText = "";

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String str = s.toString();

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
                    if (str.length() > 11) {
                        str = str.substring(0, 11);
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

    /* Evento ao clicar em um RadioButton */
    private void editFormByRadioGroupEvent() {
        /* Sim / Não */
        /* RadioGroup 1 */
        RadioGroup radioSection1Question12RadioGroup = findViewById(R.id.section_1_question_12_radio_group);
        RadioButton radioSection1Question12Radio1 = findViewById(R.id.section_1_question_12_radio_1); // Sim
        RadioButton radioSection1Question12Radio2 = findViewById(R.id.section_1_question_12_radio_2); // Não

        /* RadioGroup 2 */
        RadioButton radioSection3Question5Radio1 = findViewById(R.id.section_3_question_5_radio_1); // Sim
        RadioButton radioSection3Question5Radio2 = findViewById(R.id.section_3_question_5_radio_2); // Não

        /* RadioGroup 3 */
        RadioGroup radioSection4Question9RadioGroup = findViewById(R.id.section_4_question_9_radio_group);

        radioSection1Question12Radio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Exibir 2, 4, 6 e 7 | Esconder 3 e 5 */
                toggleQuestions(new int[] {2, 4, 6, 7}, new int[] {3, 5});
                markRadioButton(R.id.section_3_question_5_radio_group, R.id.section_3_question_5_radio_1);
                markRadioButton(R.id.section_4_question_9_radio_group, R.id.section_4_question_9_radio_2);
            }
        });

        radioSection1Question12Radio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Exibir 3 e 5 | Esconder 2, 4, 6 e 7 */
                toggleQuestions(new int[] {3, 5}, new int[] {2, 4, 6, 7});
            }
        });

        radioSection3Question5Radio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Exibir 3 e 5 | Esconder 2, 4, 6 e 7 */
                toggleQuestions(new int[] {3, 5}, new int[] {2, 4, 6, 7});
            }
        });

        radioSection3Question5Radio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Exibir 3, 6 e 7 | Esconder 2, 4 e 5 */
                toggleQuestions(new int[] {3, 6, 7}, new int[] {2, 4, 5});
            }
        });


        /* Desativando radioGroup 2 ao selecionar "Sim" no RadioGroup 1 */
        radioSection1Question12RadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.section_1_question_12_radio_1) {
                    // Desativando o RadioGroup 3
                    radioSection4Question9RadioGroup.setEnabled(false);
                    setRadioGroupClickable(radioSection4Question9RadioGroup, false);
                } else {
                    // Reativando o RadioGroup 3
                    radioSection4Question9RadioGroup.setEnabled(true);
                    setRadioGroupClickable(radioSection4Question9RadioGroup, true);
                }
            }
        });
    }

   /* Função para verificar se algum Checkbox está selecionado */
   private boolean checkboxIsSelected(int checkboxNumber) {
       if(checkboxNumber == 0) {
           int[] checkboxes = {
                   R.id.damage_button_top_left,
                   R.id.damage_button_top_center,
                   R.id.damage_button_top_right,
                   R.id.damage_button_bottom_left,
                   R.id.damage_button_bottom_center,
                   R.id.damage_button_bottom_right
           };
           for(int c : checkboxes) {
               CheckBox checkBox = findViewById(c);
               if(checkBox.isChecked()) {
                   return true;
               }
           }
       }
       return false;
   }

    /* Função para obter total de perguntas que estão escondidas */
    private int getHiddenQuestionsCount() {
        int totalHiddenQuestions = 0;
        for(int i = 0; i <= all_questions.length - 1; i++) {
            if(arrayContains(hiddenQuestions, all_questions[i][3])) {
                totalHiddenQuestions++;
            }
        }
        return totalHiddenQuestions;
    }

    /* Função para obter total de perguntas que não estão escondidas */
    private int getNotHiddenQuestionsCount() {
        int totalNotHiddenQuestions = 0;
        for(int i = 0; i <= all_questions.length - 1; i++) {
            if(!arrayContains(hiddenQuestions, all_questions[i][3])) {
                totalNotHiddenQuestions++;
            }
        }
        return totalNotHiddenQuestions;
    }

    /* Função para obter o total de perguntas não obrigatórias que estão escondidas */
    private int getNotRequiredAndHiddenQuestionsCount() {
        int totalNotRequiredAndHiddenQuestions = 0;
        for(int i = 0; i <= all_questions.length - 1; i++) {
            if(arrayContains(hiddenQuestions, all_questions[i][3])) {
                if(all_questions[i][0] == 0) {
                    totalNotRequiredAndHiddenQuestions++;
                }
            }
        }
        return totalNotRequiredAndHiddenQuestions;
    }

    /* Função para obter o total de perguntas não obrigatórias que não estão escondidas */
    private int getNotRequiredAndNotHiddenQuestionsCount() {
        int totalNotRequiredAndHiddenQuestions = 0;
        for(int i = 0; i <= all_questions.length - 1; i++) {
            if(!arrayContains(hiddenQuestions, all_questions[i][3])) {
                if(all_questions[i][0] == 0) {
                    totalNotRequiredAndHiddenQuestions++;
                }
            }
        }
        return totalNotRequiredAndHiddenQuestions;
    }

    /* Função para obter o total de perguntas obrigatórias que não estão escondidas */
    private int getRequiredAndNotHiddenQuestionsCount() {
        return getNotHiddenQuestionsCount() - getNotRequiredAndNotHiddenQuestionsCount();
    }

    /* Função para obter total de perguntas em uma seção */
    private int getSectionQuestionsCount(int sectionNumber) {
        int totalQuestions = 0;
        for (int i = 0; i <= all_questions.length - 1; i++) {
            if (all_questions[i][3] == sectionNumber) {
                totalQuestions++;
            }
        }

        Log.w(logId, "Perguntas da seção " + sectionNumber + ": " + totalQuestions);
        return totalQuestions;
    }

    /* Função para exibir/esconder múltiplas perguntas */
    private void toggleQuestions(int[] show, int[] hide) {
        /* Exibindo */
        for(int s : show) {
            showQuestions(s - 2);
        }
        /* Escondendo */
        for(int h : hide) {
            hideQuestions(h - 2);
        }
        /* Alterando a variável de seções de perguntas obrigatórias */
        setHiddenQuestionsVar(hide);
    }

    /* Função para esconder perguntas */
    private void hideQuestions(int sectionIndex) {
        LinearLayout linearLayout = findViewById(toggleableQuestionsSectionsIds[sectionIndex]);
        linearLayout.setVisibility(View.GONE);
    }

    /* Função para exibir perguntas */
    private void showQuestions(int sectionIndex) {
        LinearLayout linearLayout = findViewById(toggleableQuestionsSectionsIds[sectionIndex]);
        linearLayout.setVisibility(View.VISIBLE);
    }

    /* Função para alterar a variável de esconder seções de perguntas */
    private void setHiddenQuestionsVar(int[] ids) {
        hiddenQuestions = ids;
    }

    /* Função para definir que um RadioGroup seja clicável ou não */
    private void setRadioGroupClickable(RadioGroup radioGroup, boolean clickable) {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setEnabled(clickable);
        }
    }

    /* Função para marcar um RadioButton de um RadioGroup */
    private void markRadioButton(int radioGroupId, int radioButtonId) {
        RadioGroup radioGroup = findViewById(radioGroupId);
        radioGroup.check(radioButtonId);
    }

    /* Evento para verificar se as perguntas obrigatórias foram respondidas */
    private boolean checkRequiredQuestions() {
        int answered = 0;
        for (int i = 0; i <= all_questions.length - 1; i++) {
            if (all_questions[i][0] == 1) { // Verificando se a pergunta é obrigatória
                /* Verificando se a seção de perguntas a pergunta está escondida */
                if (!arrayContains(hiddenQuestions, all_questions[i][3])) {
                    if (all_questions[i][2] == 0) { // EditText
                        EditText editText = findViewById(all_questions[i][1]);
                        if (!editText.getText().toString().isEmpty()) {
                            answered++;
                            editText.setBackgroundResource(R.drawable.edit_text);
                        } else {
                            editText.setBackgroundResource(R.drawable.edit_text_error);
                        }
                    } else if (all_questions[i][2] == 1) { // Spinner
                        Spinner spinner = findViewById(all_questions[i][1]);
                        if (!spinner.getSelectedItem().toString().equals("Selecione uma opção")) {
                            answered++;
                            spinner.setBackgroundResource(R.drawable.edit_text);
                        } else {
                            spinner.setBackgroundResource(R.drawable.edit_text_error);
                        }
                    } else if (all_questions[i][2] == 2) { // RadioGroup
                        RadioGroup radioGroup = findViewById(all_questions[i][1]);
                        int selectedId = radioGroup.getCheckedRadioButtonId();
                        boolean groupSelected = selectedId != -1;
                        if (groupSelected) {
                            answered++;
                        }
                    } else if (all_questions[i][2] == 3) { // Checkbox
                        if (checkboxIsSelected(all_questions[i][1])) {
                            answered++;
                        }
                    }
                }
            }
        }

        if(answered == getRequiredAndNotHiddenQuestionsCount()) { // JÁ FOI INVERTIDO
            return true;
        } else {
            /*Log.d(logId, "all_questions.length: Total de perguntas: " + all_questions.length);
            Log.d(logId, "getHiddenQuestionsCount(): Perguntas que estão escondidas: " + getHiddenQuestionsCount());
            Log.d(logId, "getNotHiddenQuestionsCount(): Perguntas que não estão escondidas: " + getNotHiddenQuestionsCount());
            Log.d(logId, "Seções escondidas: " + Arrays.toString(hiddenQuestions));
            Log.d(logId, "getNotRequiredAndHiddenQuestionsCount(): Perguntas não obrigatórias que estão escondidas: " + getNotRequiredAndHiddenQuestionsCount());
            Log.d(logId, "getNotRequiredAndNotHiddenQuestionsCount(): Perguntas não obrigatórias que não estão escondidas: " + getNotRequiredAndNotHiddenQuestionsCount());
            Log.i(logId, "getRequiredAndNotHiddenQuestionsCount(): Perguntas obrigatórias que não estão escondidas: " + getRequiredAndNotHiddenQuestionsCount());*/
            Log.w(logId, answered + " perguntas obrigatórias respondidas de " + getRequiredAndNotHiddenQuestionsCount());
            return false;
        }
    }

    /* Função ao cancelar formulário */
    private void cancel() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Form3Activity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.alert_dialog_warning, null);
        builder.setView(dialogLayout);
        builder.setTitle("Atenção!");
        builder.setMessage("Ao continuar, seu e-brat será cancelado e seu processo será perdido.");
        builder.setIcon(R.drawable.icon_warning);
        builder.setPositiveButton("Continuar", (dialog, which) -> {
            dialog.dismiss();
            Intent intent = new Intent(Form3Activity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        builder.setNegativeButton("Voltar", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}