package com.example.trans_brat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Form5Activity extends AppCompatActivity {
    private final String logId = "Form5Activity_LOG";
    /* Respostas do formulário anterior */
    private final String[] previousAnswersIds = {
            "F1_S1_Q1",
            "F1_S2_Q1",
            "F1_S3_Q1",
            "F1_S4_Q1",
            "F1_S5_Q1",
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
            "F2_S5_Q1",
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
            "F3_S8_Q6",
            "F4_S0_Q1",
            "F4_S1_M1_Q2",
            "F4_S1_M1_Q3",
            "F4_S1_M1_Q4",
            "F4_S1_M1_Q5",
            "F4_S1_M1_Q6",
            "F4_S1_M1_Q7",
            "F4_S1_M1_Q8",
            "F4_S1_M1_Q9",
            "F4_S1_M1_Q10",
            "F4_S1_M1_Q11",
            "F4_S1_M1_Q12",
            "F4_S1_M2_Q2",
            "F4_S1_M2_Q3",
            "F4_S1_M3_Q2",
            "F4_S1_M3_Q3",
            "F4_S1_M3_Q4",
            "F4_S1_M3_Q5",
            "F4_S1_M4_Q2",
            "F4_S1_M4_Q3",
            "F4_S1_M4_Q4",
            "F4_S1_M4_Q5",
            "F4_S1_M4_Q6",
            "F4_S1_M4_Q7",
            "F4_S1_M4_Q8",
            "F4_S1_M4_Q9",
            "F4_S1_M5_Q2",
            "F4_S1_M5_Q3",
            "F4_S1_M5_Q4",
            "F4_S1_M5_Q5",
            "F4_S1_M5_Q6",
            "F4_S1_M5_Q7",
            "F4_S1_M5_Q8",
            "F4_S1_M5_Q9",
            "F4_S1_M5_Q10",
            "F4_S1_M6_Q2",
            "F4_S1_M6_Q3",
            "F4_S1_M6_Q4",
            "F4_S1_M6_Q5",
            "F4_S1_M6_Q6",
            "F4_S1_M7_Q2",
            "F4_S1_M7_Q3",
            "F4_S1_M7_Q4",
            "F4_S1_M7_Q5",
            "F4_S1_M7_Q6",
            "F4_S1_M7_Q7",
            "F4_S1_M7_Q8",
            "F4_S1_M8_Q1",
            "F4_S1_M8_Q2",
            "F4_S1_M8_Q3",
            "F4_S1_M8_Q4",
            "F4_S1_M8_Q5",
            "F4_S1_M8_Q6",
            "F4_S2_M1_Q2",
            "F4_S2_M1_Q3",
            "F4_S2_M1_Q4",
            "F4_S2_M1_Q5",
            "F4_S2_M1_Q6",
            "F4_S2_M1_Q7",
            "F4_S2_M1_Q8",
            "F4_S2_M1_Q9",
            "F4_S2_M1_Q10",
            "F4_S2_M1_Q11",
            "F4_S2_M1_Q12",
            "F4_S2_M2_Q2",
            "F4_S2_M2_Q3",
            "F4_S2_M3_Q2",
            "F4_S2_M3_Q3",
            "F4_S2_M3_Q4",
            "F4_S2_M3_Q5",
            "F4_S2_M4_Q2",
            "F4_S2_M4_Q3",
            "F4_S2_M4_Q4",
            "F4_S2_M4_Q5",
            "F4_S2_M4_Q6",
            "F4_S2_M4_Q7",
            "F4_S2_M4_Q8",
            "F4_S2_M4_Q9",
            "F4_S2_M5_Q2",
            "F4_S2_M5_Q3",
            "F4_S2_M5_Q4",
            "F4_S2_M5_Q5",
            "F4_S2_M5_Q6",
            "F4_S2_M5_Q7",
            "F4_S2_M5_Q8",
            "F4_S2_M5_Q9",
            "F4_S2_M5_Q10",
            "F4_S2_M6_Q2",
            "F4_S2_M6_Q3",
            "F4_S2_M6_Q4",
            "F4_S2_M6_Q5",
            "F4_S2_M6_Q6",
            "F4_S2_M7_Q2",
            "F4_S2_M7_Q3",
            "F4_S2_M7_Q4",
            "F4_S2_M7_Q5",
            "F4_S2_M7_Q6",
            "F4_S2_M7_Q7",
            "F4_S2_M7_Q8",
            "F4_S2_M8_Q1",
            "F4_S2_M8_Q2",
            "F4_S2_M8_Q3",
            "F4_S2_M8_Q4",
            "F4_S2_M8_Q5",
            "F4_S2_M8_Q6",
            "F4_S3_M1_Q2",
            "F4_S3_M1_Q3",
            "F4_S3_M1_Q4",
            "F4_S3_M1_Q5",
            "F4_S3_M1_Q6",
            "F4_S3_M1_Q7",
            "F4_S3_M1_Q8",
            "F4_S3_M1_Q9",
            "F4_S3_M1_Q10",
            "F4_S3_M1_Q11",
            "F4_S3_M1_Q12",
            "F4_S3_M2_Q2",
            "F4_S3_M2_Q3",
            "F4_S3_M3_Q2",
            "F4_S3_M3_Q3",
            "F4_S3_M3_Q4",
            "F4_S3_M3_Q5",
            "F4_S3_M4_Q2",
            "F4_S3_M4_Q3",
            "F4_S3_M4_Q4",
            "F4_S3_M4_Q5",
            "F4_S3_M4_Q6",
            "F4_S3_M4_Q7",
            "F4_S3_M4_Q8",
            "F4_S3_M4_Q9",
            "F4_S3_M5_Q2",
            "F4_S3_M5_Q3",
            "F4_S3_M5_Q4",
            "F4_S3_M5_Q5",
            "F4_S3_M5_Q6",
            "F4_S3_M5_Q7",
            "F4_S3_M5_Q8",
            "F4_S3_M5_Q9",
            "F4_S3_M5_Q10",
            "F4_S3_M6_Q2",
            "F4_S3_M6_Q3",
            "F4_S3_M6_Q4",
            "F4_S3_M6_Q5",
            "F4_S3_M6_Q6",
            "F4_S3_M7_Q2",
            "F4_S3_M7_Q3",
            "F4_S3_M7_Q4",
            "F4_S3_M7_Q5",
            "F4_S3_M7_Q6",
            "F4_S3_M7_Q7",
            "F4_S3_M7_Q8",
            "F4_S3_M8_Q1",
            "F4_S3_M8_Q2",
            "F4_S3_M8_Q3",
            "F4_S3_M8_Q4",
            "F4_S3_M8_Q5",
            "F4_S3_M8_Q6",
            "F4_S4_M1_Q2",
            "F4_S4_M1_Q3",
            "F4_S4_M1_Q4",
            "F4_S4_M1_Q5",
            "F4_S4_M1_Q6",
            "F4_S4_M1_Q7",
            "F4_S4_M1_Q8",
            "F4_S4_M1_Q9",
            "F4_S4_M1_Q10",
            "F4_S4_M1_Q11",
            "F4_S4_M1_Q12",
            "F4_S4_M2_Q2",
            "F4_S4_M2_Q3",
            "F4_S4_M3_Q2",
            "F4_S4_M3_Q3",
            "F4_S4_M3_Q4",
            "F4_S4_M3_Q5",
            "F4_S4_M4_Q2",
            "F4_S4_M4_Q3",
            "F4_S4_M4_Q4",
            "F4_S4_M4_Q5",
            "F4_S4_M4_Q6",
            "F4_S4_M4_Q7",
            "F4_S4_M4_Q8",
            "F4_S4_M4_Q9",
            "F4_S4_M5_Q2",
            "F4_S4_M5_Q3",
            "F4_S4_M5_Q4",
            "F4_S4_M5_Q5",
            "F4_S4_M5_Q6",
            "F4_S4_M5_Q7",
            "F4_S4_M5_Q8",
            "F4_S4_M5_Q9",
            "F4_S4_M5_Q10",
            "F4_S4_M6_Q2",
            "F4_S4_M6_Q3",
            "F4_S4_M6_Q4",
            "F4_S4_M6_Q5",
            "F4_S4_M6_Q6",
            "F4_S4_M7_Q2",
            "F4_S4_M7_Q3",
            "F4_S4_M7_Q4",
            "F4_S4_M7_Q5",
            "F4_S4_M7_Q6",
            "F4_S4_M7_Q7",
            "F4_S4_M7_Q8",
            "F4_S4_M8_Q1",
            "F4_S4_M8_Q2",
            "F4_S4_M8_Q3",
            "F4_S4_M8_Q4",
            "F4_S4_M8_Q5",
            "F4_S4_M8_Q6"
    };

    /* Respostas para enviar para o formulário seguinte */
    private final String[] toSendAnswersIds = {
            "F5_S1_Q2",
            "F5_S1_Q3",
            "F5_S1_Q4",
            "F5_S2_Q2",
            "F5_S2_Q3",
            "F5_S2_Q4",
            "F5_S3_Q2",
            "F5_S3_Q3",
            "F5_S3_Q4",
            "F5_S4_Q2",
            "F5_S4_Q3",
            "F5_S4_Q4"
    };

    private final int[][] all_questions = {
            {0, R.id.section_1_question_2_input, 0, 1},
            {0, R.id.section_1_question_3_input, 0, 1},
            {0, R.id.section_1_question_4_input, 0, 1},
            {0, R.id.section_2_question_2_input, 0, 2},
            {0, R.id.section_2_question_3_input, 0, 2},
            {0, R.id.section_2_question_4_input, 0, 2},
            {0, R.id.section_3_question_2_input, 0, 3},
            {0, R.id.section_3_question_3_input, 0, 3},
            {0, R.id.section_3_question_4_input, 0, 3},
            {0, R.id.section_4_question_2_input, 0, 4},
            {0, R.id.section_4_question_3_input, 0, 4},
            {0, R.id.section_4_question_4_input, 0, 4}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_5);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.form_5), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /* Obtendo respostas dos formulários anteriores */
        getPreviousFormAnswers();

        //requiredQuestions();

        /* Formatando inputs de CPF */
        cpfQuestions();

        /* Formatando inputs de telefone */
        phoneQuestions();

        /* Botões inferiores */
        Button buttonBack = findViewById(R.id.back_button);
        Button buttonCancel = findViewById(R.id.cancel_button);
        Button buttonNext = findViewById(R.id.next_button);

        /* (Voltar) Voltando para o formulário 4 */
        buttonBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        /* (Cancelar) Voltando para o menu principal */
        buttonCancel.setOnClickListener(v -> cancel());

        /* (Avançar) Avançando para o formulário 6 */
        buttonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Form5Activity.this, Form6Activity.class);
                try {
                    sendAnswersToNextForm(intent);
                } catch (Exception e) {
                    Log.e(logId, "Erro ao enviar respostas:" + e);
                }
                startActivity(intent);
            }
        });
    }

    /* Função para obter respostas dos formulários anteriores */
    private void getPreviousFormAnswers() {
        for(String answerId : previousAnswersIds) {
            try {
                String answer = getIntent().getStringExtra(answerId);
                //assert answer != null;
                Log.d(logId, answerId + " - " + answer);
            } catch (Exception e) {
                Log.e(logId, "" + e);
            }
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
        int j = 0;
        for(int i = 0; i <= toSendAnswersIds.length - 1; i++) {
            if(i < all_questions.length) {
                //Log.e(logId, i + " Index do all_questions: " + toSendAnswersIds[i + j] + "\nTipo do Index: " + all_questions[i][2]);
                if (all_questions[i][2] == 3) {
                    getCheckboxAnswers(intent, i, i + j);
                    j += 5;
                } else {
                    String toSendAnswerId = toSendAnswersIds[i + j];
                    int requiredQuestionId = all_questions[i][1];
                    int requiredQuestionType = all_questions[i][2];
                    String answer = getInputAnswer(requiredQuestionId, requiredQuestionType);
                    intent.putExtra(toSendAnswerId, answer);
                    Log.i(logId, toSendAnswerId + " - " + answer);
                    j += 0;
                }
            } else {
                break;
            }
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
            CheckBox checkBox1 = null;
            CheckBox checkBox2 = null;
            CheckBox checkBox3 = null;
            CheckBox checkBox4 = null;
            CheckBox checkBox5 = null;
            CheckBox checkBox6 = null;
            if(questionId == 0) {
                checkBox1 = findViewById(R.id.section_1_damage_button_top_left);
                checkBox2 = findViewById(R.id.section_1_damage_button_top_center);
                checkBox3 = findViewById(R.id.section_1_damage_button_top_right);
                checkBox4 = findViewById(R.id.section_1_damage_button_bottom_left);
                checkBox5 = findViewById(R.id.section_1_damage_button_bottom_center);
                checkBox6 = findViewById(R.id.section_1_damage_button_bottom_right);
            } else if(questionId == 1) {
                checkBox1 = findViewById(R.id.section_2_damage_button_top_left);
                checkBox2 = findViewById(R.id.section_2_damage_button_top_center);
                checkBox3 = findViewById(R.id.section_2_damage_button_top_right);
                checkBox4 = findViewById(R.id.section_2_damage_button_bottom_left);
                checkBox5 = findViewById(R.id.section_2_damage_button_bottom_center);
                checkBox6 = findViewById(R.id.section_2_damage_button_bottom_right);
            } else if(questionId == 2) {
                checkBox1 = findViewById(R.id.section_3_damage_button_top_left);
                checkBox2 = findViewById(R.id.section_3_damage_button_top_center);
                checkBox3 = findViewById(R.id.section_3_damage_button_top_right);
                checkBox4 = findViewById(R.id.section_3_damage_button_bottom_left);
                checkBox5 = findViewById(R.id.section_3_damage_button_bottom_center);
                checkBox6 = findViewById(R.id.section_3_damage_button_bottom_right);
            } else if(questionId == 3) {
                checkBox1 = findViewById(R.id.section_4_damage_button_top_left);
                checkBox2 = findViewById(R.id.section_4_damage_button_top_center);
                checkBox3 = findViewById(R.id.section_4_damage_button_top_right);
                checkBox4 = findViewById(R.id.section_4_damage_button_bottom_left);
                checkBox5 = findViewById(R.id.section_4_damage_button_bottom_center);
                checkBox6 = findViewById(R.id.section_4_damage_button_bottom_right);
            }

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
    }

    /* Função para obter resposta de checkboxes */
    private void getCheckboxAnswers(Intent intent, int allQuestionsIndex, int j) {
        CheckBox checkBox1 = null;
        CheckBox checkBox2 = null;
        CheckBox checkBox3 = null;
        CheckBox checkBox4 = null;
        CheckBox checkBox5 = null;
        CheckBox checkBox6 = null;
        if(all_questions[allQuestionsIndex][1] == 0) {
            checkBox1 = findViewById(R.id.section_1_damage_button_top_left);
            checkBox2 = findViewById(R.id.section_1_damage_button_top_center);
            checkBox3 = findViewById(R.id.section_1_damage_button_top_right);
            checkBox4 = findViewById(R.id.section_1_damage_button_bottom_left);
            checkBox5 = findViewById(R.id.section_1_damage_button_bottom_center);
            checkBox6 = findViewById(R.id.section_1_damage_button_bottom_right);
        } else if(all_questions[allQuestionsIndex][1] == 1) {
            checkBox1 = findViewById(R.id.section_2_damage_button_top_left);
            checkBox2 = findViewById(R.id.section_2_damage_button_top_center);
            checkBox3 = findViewById(R.id.section_2_damage_button_top_right);
            checkBox4 = findViewById(R.id.section_2_damage_button_bottom_left);
            checkBox5 = findViewById(R.id.section_2_damage_button_bottom_center);
            checkBox6 = findViewById(R.id.section_2_damage_button_bottom_right);
        } else if(all_questions[allQuestionsIndex][1] == 2) {
            checkBox1 = findViewById(R.id.section_3_damage_button_top_left);
            checkBox2 = findViewById(R.id.section_3_damage_button_top_center);
            checkBox3 = findViewById(R.id.section_3_damage_button_top_right);
            checkBox4 = findViewById(R.id.section_3_damage_button_bottom_left);
            checkBox5 = findViewById(R.id.section_3_damage_button_bottom_center);
            checkBox6 = findViewById(R.id.section_3_damage_button_bottom_right);
        } else if(all_questions[allQuestionsIndex][1] == 3) {
            checkBox1 = findViewById(R.id.section_4_damage_button_top_left);
            checkBox2 = findViewById(R.id.section_4_damage_button_top_center);
            checkBox3 = findViewById(R.id.section_4_damage_button_top_right);
            checkBox4 = findViewById(R.id.section_4_damage_button_bottom_left);
            checkBox5 = findViewById(R.id.section_4_damage_button_bottom_center);
            checkBox6 = findViewById(R.id.section_4_damage_button_bottom_right);
        }

        String checkBox1Text = null;
        String checkBox2Text = null;
        String checkBox3Text = null;
        String checkBox4Text = null;
        String checkBox5Text = null;
        String checkBox6Text = null;

        assert checkBox1 != null;
        if (checkBox1.isChecked()) {
            checkBox1Text = checkBox1.getText().toString();
        }
        assert checkBox2 != null;
        if (checkBox2.isChecked()) {
            checkBox2Text = checkBox2.getText().toString();
        }
        assert checkBox3 != null;
        if (checkBox3.isChecked()) {
            checkBox3Text = checkBox3.getText().toString();
        }
        assert checkBox4 != null;
        if (checkBox4.isChecked()) {
            checkBox4Text = checkBox4.getText().toString();
        }
        assert checkBox5 != null;
        if (checkBox5.isChecked()) {
            checkBox5Text = checkBox5.getText().toString();
        }
        assert checkBox6 != null;
        if (checkBox6.isChecked()) {
            checkBox6Text = checkBox6.getText().toString();
        }

        String questionId1 = toSendAnswersIds[j];
        String questionId2 = toSendAnswersIds[j + 1];
        String questionId3 = toSendAnswersIds[j + 2];
        String questionId4 = toSendAnswersIds[j + 3];
        String questionId5 = toSendAnswersIds[j + 4];
        String questionId6 = toSendAnswersIds[j + 5];
        intent.putExtra(questionId1, checkBox1Text);
        intent.putExtra(questionId2, checkBox2Text);
        intent.putExtra(questionId3, checkBox3Text);
        intent.putExtra(questionId4, checkBox4Text);
        intent.putExtra(questionId5, checkBox5Text);
        intent.putExtra(questionId6, checkBox6Text);
        Log.w(logId, questionId1 + " - " + checkBox1Text);
        Log.w(logId, questionId2 + " - " + checkBox2Text);
        Log.w(logId, questionId3 + " - " + checkBox3Text);
        Log.w(logId, questionId4 + " - " + checkBox4Text);
        Log.w(logId, questionId5 + " - " + checkBox5Text);
        Log.w(logId, questionId6 + " - " + checkBox6Text);
    }

    /* Função para formatar CPF */
    private void cpfQuestions() {
        int[] cpfIds = {
                R.id.section_1_question_3_input,
                R.id.section_2_question_3_input,
                R.id.section_3_question_3_input,
                R.id.section_4_question_3_input
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

    /* Função para formatar telefone */
    private void phoneQuestions() {
        int[] phoneIds = {
                R.id.section_1_question_4_input,
                R.id.section_2_question_4_input,
                R.id.section_3_question_4_input,
                R.id.section_4_question_4_input
        };

        for (int i = 0; i < phoneIds.length; i++) {
            EditText phoneEditText = findViewById(phoneIds[i]);
            phoneEditText.addTextChangedListener(new TextWatcher() {
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
                        phoneEditText.setText(oldText);
                        phoneEditText.setSelection(oldText.length());
                        return;
                    }

                    // Formatação
                    if (str.length() > 2) {
                        str = "(" + str.substring(0, 2) + ") " + str.substring(2);
                    }
                    if (str.length() > 10) {
                        str = str.substring(0, 10) + "-" + str.substring(10);
                    }

                    isUpdating = true;
                    phoneEditText.setText(str);
                    phoneEditText.setSelection(str.length());
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
    }

    /* Função ao cancelar formulário */
    private void cancel() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Form5Activity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.alert_dialog_warning, null);
        builder.setView(dialogLayout);
        builder.setTitle("Atenção!");
        builder.setMessage("Ao continuar, seu e-brat será cancelado e seu processo será perdido.");
        builder.setIcon(R.drawable.icon_warning);
        builder.setPositiveButton("Continuar", (dialog, which) -> {
            dialog.dismiss();
            Intent intent = new Intent(Form5Activity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        builder.setNegativeButton("Voltar", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}