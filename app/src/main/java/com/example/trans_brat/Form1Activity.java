package com.example.trans_brat;

import android.content.Intent;
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
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.List;

public class Form1Activity extends AppCompatActivity {
    private final String logId = "Form1Activity_LOG";
    private int notAllowedCount = 0;
    /* Definindo inputs obrigatórios */
    // [0] Id

    // [1] 0 - EditText
    // [1] 2 - RadioGroup

    // [2] X - Para enviar ou não
    private final int[][] all_questions = {
            {R.id.section_0_question_1_input, 0, 1},
            {R.id.section_0_question_2_input, 0, 1},
            {R.id.section_1_question_1_radio_group, 2, 0},
            {R.id.section_2_question_1_radio_group, 2, 0},
            {R.id.section_3_question_1_radio_group, 2, 0},
            {R.id.section_4_question_1_radio_group, 2, 0},
            {R.id.section_5_question_1_radio_group, 2, 0}
    };

    private final String[] all_questions_ids = {
            "F1_S0_Q1",
            "F1_S0_Q2"
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

        /* Evento para verificar se todos os inputs foram preenchidos corretamente */
        eventCheckInputs();

        /* Evento para permitir apenas textos com letras maiúsculas */
        eventTextAllCaps();

        /* Botões inferiores */
        Button buttonCancel = findViewById(R.id.cancel_button);
        Button buttonNext = findViewById(R.id.next_button);

        /* (Cancelar) Voltando para o menu principal */
        buttonCancel.setOnClickListener(v -> cancel());

        /* Avançando para o formulário 2 */
        buttonNext.setOnClickListener(v -> {
            if(checkAllQuestions(0)) {
                Intent intent = new Intent(Form1Activity.this, Form2Activity.class);
                sendAnswersToNextForm(intent);
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e(logId, "Erro: " + e);
                }
            }
        });
    }

    /* Função para perguntas obrigatórias */
    private void requiredQuestions() {
        /* Definindo perguntas obrigatórias */
        int[] questions_ids = {
                R.id.section_0_question_1,
                R.id.section_0_question_2,
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

    /* Função para verificar se as perguntas de texto foram respondidas */
    private boolean checkTextQuestions() {
        int textQuestions = 0;
        int answered = 0;
        for (int[] question : all_questions) {
            if (question[1] == 0) {
                textQuestions++;
                EditText editText = findViewById(question[0]);
                if (!editText.getText().toString().isEmpty()) {
                    answered++;
                    editText.setBackgroundResource(R.drawable.edit_text);
                } else {
                    editText.setBackgroundResource(R.drawable.edit_text_error);
                }
            }
        }

        if(answered == textQuestions) {
            Log.i(logId, answered + " perguntas de texto respondidas de " + textQuestions);
            return true;
        } else {
            Log.w(logId, answered + " perguntas de texto respondidas de " + textQuestions);
            return false;
        }
    }

    /* Função para verificar se as perguntas de radio foram respondidas */
    private boolean checkRadioQuestions(int radioSelectedId) {
        int[] radios_block_ids = {
                R.id.section_1_question_1_radio_2,
                R.id.section_2_question_1_radio_2,
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
                "Estrangeiros envolvidos em acidentes de trânsito sem vítimas devem fazer o registro da ocorrência no Batalhão de Polícia Militar mais próximo.",
                "Acidentes ocorridos fora do estado do Rio de Janeiro ou em rodovias federais não podem ser registrados pela Polícia Militar do Estado do Rio de Janeiro.",
                "Acidentes de trânsito com vítimas, feridos ou mortos, não podem ser registrados eletronicamente. Neste caso, a Polícia Militar deve ser acionada para fazer o registro no local do acidente.",
                "Acidentes de trânsito sem vítimas com mais de 5 veículos envolvidos devem ser registrados no Batalhão de Polícia Militar mais próximo."
        };

        if(radio_block_ids_list.contains(radioSelectedId)) {
            showPopup(radios_warnings[radio_block_ids_list.indexOf(radioSelectedId)]);
            return false;
        }

        int radioQuestions = 0;
        int answered = 0;
        for (int[] question : all_questions) {
            if (question[1] == 2) {
                radioQuestions++;
                RadioGroup radioGroup = findViewById(question[0]);
                int selectedId = radioGroup.getCheckedRadioButtonId();
                boolean groupSelected = selectedId != -1;
                if (groupSelected) {
                    answered++;
                }
            }
        }

        if(answered == radioQuestions) {
            Log.i(logId, answered + " perguntas de radio respondidas de " + radioQuestions);
            return true;
        } else {
            Log.w(logId, answered + " perguntas de radio respondidas de " + radioQuestions);
            return false;
        }
    }

    /* Função para verificar se as perguntas de radio foram respondidas */
    private boolean checkAllQuestions(int radioSelectedId) {
        boolean textCheck = checkTextQuestions();
        boolean radioCheck = checkRadioQuestions(radioSelectedId);
        if(textCheck && radioCheck) {
            enableNextButton();
            Log.i(logId, "Todas as perguntas obrigatórias foram respondidas!");
            return true;
        } else {
            disableNextButton();
            return false;
        }
    }

    /* Evento para verificar se algum RadioButton está selecionado */
    private void eventCheckInputs() {
        /* Verificando por inputs de texto */
        for(int[] question : all_questions) {
            if(question[1] == 0) {
                EditText editText = findViewById(question[0]);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        checkAllQuestions(0);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
            }
        }

        /* Verificando por inputs de radio */
        for(int[] question : all_questions) {
            if(question[1] == 2) {
                RadioGroup radioGroup = findViewById(question[0]);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        int selectedId = radioGroup.getCheckedRadioButtonId();
                        checkAllQuestions(selectedId);
                    }
                });
            }
        }
    }

    /* Evento para permitir apenas textos com letras maiúsculas */
    private void eventTextAllCaps() {
        for(int[] question : all_questions) {
            if(question[1] == 0) {
                EditText editText = findViewById(question[0]);
                editText.setFilters(new InputFilter[]{
                        new InputFilter.AllCaps()
                });
            }
        }
    }

    /* Função para obter resposta de diferentes inputs */
    private String getInputAnswer(int questionId, int questionType) {
        if(questionType == 0) {
            EditText editText = findViewById(questionId);
            return editText.getText().toString();
        } else {
            return null;
        }
    }

    /* Função para enviar respostas para o formulário seguinte */
    private void sendAnswersToNextForm(Intent intent) {
        /* Enviando respostas deste formulário */
        for(int i = 0; i <= all_questions.length - 1; i++) {
            int questionId = all_questions[i][0];
            int questionType = all_questions[i][1];
            int questionToSend = all_questions[i][2];
            if(questionToSend == 1) {
                String answer = getInputAnswer(questionId, questionType);
                intent.putExtra(all_questions_ids[i], answer);
                Log.i(logId, all_questions_ids[i] + " - " + answer);
            }
        }
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