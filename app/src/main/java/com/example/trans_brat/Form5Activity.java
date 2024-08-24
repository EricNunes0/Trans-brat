package com.example.trans_brat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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