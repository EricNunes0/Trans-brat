package com.example.trans_brat;

import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.text.AllCapsTransformationMethod;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Form6Activity extends AppCompatActivity {
    private final String logId = "Form6Activity_LOG";
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
            "F4_S4_M8_Q6",
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


    /* Respostas para enviar para o formulário seguinte */
    private final String[] toSendAnswersIds = {
            "F6_S1_Q2",
            "F6_S2_Q1"
    };

    private final int[][] all_questions = {
            {1, R.id.section_1_question_2_input, 0, 1},
            {1, 0, 4, 2}
    };

    private ActivityResultLauncher<Intent> galleryLauncher;
    private ImageAdapter imageAdapter;
    private List<Uri> selectedImages;
    private RecyclerView mainRecyclerView;
    private static final int IMAGES_MAX = 8;
    private String currentPhotoPath;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private static final int REQUEST_STORAGE_PERMISSION = 201;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_6);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.form_6), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
         mainRecyclerView = findViewById(R.id.section_2_question_1_picture);

        /* Obtendo respostas dos formulários anteriores */
        //getPreviousFormAnswers();

        /* Evento para permitir apenas textos com letras maiúsculas */
        eventTextAllCaps();

        requiredQuestions();

        /* Evento para iniciar o launcher que abre a galeria do celular */
        eventGalleryInitLauncher();

        /* Botões inferiores */
        Button buttonBack = findViewById(R.id.back_button);
        Button buttonCancel = findViewById(R.id.cancel_button);
        Button buttonNext = findViewById(R.id.next_button);

        /* (Voltar) Voltando para o formulário 5 */
        buttonBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        /* (Cancelar) Voltando para o menu principal */
        buttonCancel.setOnClickListener(v -> cancel());

        /* (Avançar) Avançando para o formulário ? */
        buttonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(checkRequiredQuestions()) {
                    Intent intent = new Intent(Form6Activity.this, Form7Activity.class);
                    try {
                        sendAnswersToNextForm(intent);
                    } catch (Exception e) {
                        Log.e(logId, "Erro ao enviar respostas:" + e);
                    }
                    startActivity(intent);
                }
            }
        });
    }

    /* Evento para permitir apenas textos com letras maiúsculas */
    private void eventTextAllCaps() {
        EditText editText = findViewById(R.id.section_1_question_2_input);
        editText.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(500)});
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
                } if (all_questions[i][2] == 4) {
                    getRecyclerViewAnswers(intent, i/*, i + j*/);
                    j += 9;
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

    /* Função para obter imagens de um RecyclerView */
    private void getRecyclerViewAnswers(Intent intent, int allQuestionsIndex) {
        ArrayList<String> selectedImageUris = new ArrayList<>();
        for(int i = 0; i <= 9; i++) {
            if(checkIfRecyclerViewContainsIndex(selectedImages, i)) {
                selectedImageUris.add(selectedImages.get(i).toString());
            } else {
                selectedImageUris.add(null);
            }
        }
        intent.putStringArrayListExtra(toSendAnswersIds[allQuestionsIndex], selectedImageUris);
        Log.i(logId, toSendAnswersIds[allQuestionsIndex] + " - " + selectedImageUris);
    }

    private boolean checkIfRecyclerViewContainsIndex(List<Uri> uriList, int index) {
        // Verifica se o índice está dentro dos limites da lista
        if (index >= 0 && index < uriList.size()) {
            Uri uriAtIndex = uriList.get(index);

            if (uriAtIndex != null) {
                Log.v(logId, "O índice " + index + " possui um Uri válido: " + uriAtIndex);
                return true;
            } else {
                Log.v(logId, "O índice " + index + " não possui um Uri válido (null).");
                return true;
            }
        } else {
            Log.v(logId, "O índice " + index + " está fora dos limites da lista.");
            return false;
        }
    }

    /* Função para perguntas obrigatórias */
    private void requiredQuestions() {
        /* Definindo perguntas obrigatórias */
        int[] questions_ids = {
                R.id.section_1_question_1,
                R.id.section_2_question_1
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
            if (!textWithAsterisk.isEmpty()) {
                SpannableString spannableString = new SpannableString(textWithAsterisk);

                // Aplicando a cor vermelha ao último caractere (asteriscos)
                spannableString.setSpan(new ForegroundColorSpan(Color.RED), textWithAsterisk.length() - 1, textWithAsterisk.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView.setText(spannableString);
            }
        }
    }

    /* Função ao cancelar formulário */
    private void cancel() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Form6Activity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.alert_dialog_warning, null);
        builder.setView(dialogLayout);
        builder.setTitle("Atenção!");
        builder.setMessage("Ao continuar, seu e-brat será cancelado e seu processo será perdido.");
        builder.setIcon(R.drawable.icon_warning);
        builder.setPositiveButton("Continuar", (dialog, which) -> {
            dialog.dismiss();
            Intent intent = new Intent(Form6Activity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        builder.setNegativeButton("Voltar", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /* Evento para iniciar o launcher que abre a galeria do celular */
    private void eventGalleryInitLauncher() {
        selectedImages = new ArrayList<>();
        imageAdapter = new ImageAdapter(this, selectedImages);

        RecyclerView recyclerView = findViewById(R.id.section_2_question_1_picture);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3)); // Grade de 3 colunas
        recyclerView.setAdapter(imageAdapter);

        Button buttonOpenCamera = findViewById(R.id.section_2_question_1_input_1);
        buttonOpenCamera.setOnClickListener(view -> openCamera());
    }

    /* Câmera */

    /* Função para abrir a câmera do celular ao clicar no botão */
    private void openCamera() {
        try {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            } else {
                dispatchTakePictureIntent();
            }
        } catch (Exception e) {
            Log.e(logId, "openCamera(): " + e.toString());
        }
    }


    /* Função para ativar câmera do dispositivo */
    private void dispatchTakePictureIntent() {
        try {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Erro ao criar o arquivo
                }
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this, "com.example.trans_brat.fileprovider", photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
            }
        } catch (Exception e) {
            Log.e(logId, "dispatchTakePictureIntent(): " + e.toString());
        }
    }

    /* Função para criar arquivo temporário de imagem */
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            File file = new File(currentPhotoPath);
            Uri photoURI = Uri.fromFile(file);
            if (selectedImages.size() < IMAGES_MAX) {
                selectedImages.add(photoURI);
                imageAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Você só pode selecionar até 8 imagens", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "É necessária permissão da câmera para tirar fotos", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /* Evento para verificar se as perguntas obrigatórias foram respondidas */
    private boolean checkRequiredQuestions() {
        int answered = 0;
        for (int i = 0; i <= all_questions.length - 1; i++) {
            if (all_questions[i][0] == 1) { // Verificando se a pergunta é obrigatória
                if (all_questions[i][2] == 0) { // EditText
                    EditText editText = findViewById(all_questions[i][1]);
                    if (!editText.getText().toString().isEmpty()) {
                        answered++;
                        editText.setBackgroundResource(R.drawable.edit_text);
                    } else {
                        editText.setBackgroundResource(R.drawable.edit_text_error);
                    }
                } else if (all_questions[i][2] == 4) { // RecyclerView
                    RecyclerView recyclerView = findViewById(all_questions[i][1]);
                    ImageAdapter adapter = imageAdapter;
                    if (adapter != null && adapter.getItemCount() > 0) {
                        // O RecyclerView possui pelo menos um item
                        answered++;
                    } else {
                        // O RecyclerView está vazio
                    }
                }
            }
        }

        if(answered == all_questions.length) {
            return true;
        } else {
            Log.w(logId, answered + " perguntas obrigatórias respondidas de " + all_questions.length);
            return false;
        }
    }
}