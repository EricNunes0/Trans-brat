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

import java.util.Calendar;

public class Form3Activity extends AppCompatActivity {
    /* Definindo inputs obrigatórios */
    // [0][] - EditText
    // [1][] - Spinner
    // [2][] - RadioGroup
    // [3][] - Checkbox
    // [][X] - Seção de perguntas
    private final int[][] required_questions = {
            // 1
            {R.id.section_1_question_2_input, 0, 1},
            {R.id.section_1_question_3_input, 0, 1},
            {R.id.section_1_question_4_input, 0, 1},
            {R.id.section_1_question_5_input, 0, 1},
            {R.id.section_1_question_6_input, 0, 1},
            {R.id.section_1_question_7_input, 0, 1},
            {R.id.section_1_question_8_input, 0, 1},
            {R.id.section_1_question_9_input, 0, 1},
            {R.id.section_1_question_10_input, 0, 1},
            {R.id.section_1_question_11_input, 0, 1},
            {R.id.section_1_question_12_radio_group, 2, 1},
            // 2
            {R.id.section_2_question_2_input, 0, 2},
            {R.id.section_2_question_3_input, 0, 2},
            // 3
            {R.id.section_3_question_2_input, 0, 3},
            {R.id.section_3_question_3_input, 0, 3},
            {R.id.section_3_question_4_input, 0, 3},
            {R.id.section_3_question_5_radio_group, 2, 3},
            // 4
            {R.id.section_4_question_2_input, 0, 4},
            {R.id.section_4_question_3_input, 0, 4},
            {R.id.section_4_question_4_input, 0, 4},
            {R.id.section_4_question_6_input, 0, 4},
            {R.id.section_4_question_7_input, 0, 4},
            {R.id.section_4_question_8_input, 0, 4},
            {R.id.section_4_question_9_radio_group, 2, 4},
            // 5
            {R.id.section_5_question_2_input, 0, 5},
            {R.id.section_5_question_3_input, 0, 5},
            {R.id.section_5_question_4_input, 0, 5},
            {R.id.section_5_question_5_input, 0, 5},
            {R.id.section_5_question_6_input, 0, 5},
            {R.id.section_5_question_7_input, 0, 5},
            {R.id.section_5_question_8_input, 0, 5},
            {R.id.section_5_question_9_input, 0, 5},
            {R.id.section_5_question_10_input, 0, 5},
            // 6
            {R.id.section_6_question_2_input, 0, 6},
            {R.id.section_6_question_3_input, 0, 6},
            {R.id.section_6_question_4_input, 0, 6},
            {R.id.section_6_question_5_input, 0, 6},
            {R.id.section_6_question_6_input, 0, 6},
            // 7
            {R.id.section_7_question_2_input, 0, 7},
            {R.id.section_7_question_3_input, 0, 7},
            {R.id.section_7_question_4_input, 0, 7},
            {R.id.section_7_question_6_input, 0, 7},
            {R.id.section_7_question_7_input, 0, 7},
            {R.id.section_7_question_8_input, 0, 7},
            // 8
            {0, 3, 8}
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

        /* Marcando os dois RadioGroups como "não" */
        markRadioButton(R.id.section_1_question_12_radio_group, R.id.section_1_question_12_radio_2);
        markRadioButton(R.id.section_3_question_5_radio_group, R.id.section_3_question_5_radio_2);
        /* Exibir 3, 6 e 7 | Esconder 2, 4 e 5 */
        toggleQuestions(new int[] {3, 6, 7}, new int[] {2, 4, 5});

        /* Evento para exibir/esconder partes do formulário ao marcar uma opção em um RadioGroup */
        editFormByRadioGroupEvent();

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
                    startActivity(intent);
                }
            }
        });
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
                // 2
                R.id.section_2_question_2,
                R.id.section_2_question_3,
                // 3
                R.id.section_3_question_2,
                R.id.section_3_question_3,
                R.id.section_3_question_4,
                R.id.section_3_question_5,
                // 4
                R.id.section_4_question_2,
                R.id.section_4_question_3,
                R.id.section_4_question_4,
                R.id.section_4_question_6,
                R.id.section_4_question_7,
                R.id.section_4_question_8,
                // 5
                R.id.section_5_question_2,
                R.id.section_5_question_3,
                R.id.section_5_question_4,
                R.id.section_5_question_6,
                R.id.section_5_question_7,
                R.id.section_5_question_8,
                R.id.section_5_question_9,
                R.id.section_5_question_10,
                // 6
                R.id.section_6_question_2,
                R.id.section_6_question_3,
                R.id.section_6_question_4,
                R.id.section_6_question_5,
                R.id.section_6_question_6,
                // 7
                R.id.section_7_question_2,
                R.id.section_7_question_3,
                R.id.section_7_question_4,
                R.id.section_7_question_6,
                R.id.section_7_question_7,
                R.id.section_7_question_8,
                // 8
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
                    String str = s.toString().replaceAll("[^\\d]", "");

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
        for(int h : hiddenQuestions) {
            totalHiddenQuestions += getSectionQuestionsCount(h);
        }
        return totalHiddenQuestions;
    }

    /* Função para obter total de perguntas que não estão escondidas */
    private int getNotHiddenQuestionsCount() {
        return required_questions.length - getHiddenQuestionsCount();
    }

    /* Função para obter total de perguntas em uma seção */
    private int getSectionQuestionsCount(int sectionNumber) {
        int totalQuestions = 0;
        for (int i = 0; i <= required_questions.length - 1; i++) {
            if(required_questions[i][2] == sectionNumber) {
                totalQuestions++;
            }
        }
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
        for (int i = 0; i <= required_questions.length - 1; i++) {
            /* Verificando se a seção de perguntas a pergunta está escondida */
            if(!arrayContains(hiddenQuestions, required_questions[i][2])) {
                if (required_questions[i][1] == 0) { // EditText
                    EditText editText = findViewById(required_questions[i][0]);
                    if (!editText.getText().toString().isEmpty()) {
                        answered++;
                        editText.setBackgroundResource(R.drawable.edit_text);
                    } else {
                        editText.setBackgroundResource(R.drawable.edit_text_error);
                    }
                } else if (required_questions[i][1] == 1) { // Spinner
                    Spinner spinner = findViewById(required_questions[i][0]);
                    if (!spinner.getSelectedItem().toString().equals("Selecione uma opção")) {
                        answered++;
                        spinner.setBackgroundResource(R.drawable.edit_text);
                    } else {
                        spinner.setBackgroundResource(R.drawable.edit_text_error);
                    }
                } else if (required_questions[i][1] == 2) { // RadioGroup
                    RadioGroup radioGroup = findViewById(required_questions[i][0]);
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    boolean groupSelected = selectedId != -1;
                    if (groupSelected) {
                        answered++;
                    }
                } else if (required_questions[i][1] == 3) { // Checkbox
                    if (checkboxIsSelected(required_questions[i][0])) {
                        answered++;
                    }
                }
            }
        }

        if(answered == getNotHiddenQuestionsCount()) {
            return true;
        } else {
            Toast.makeText(Form3Activity.this, answered + " perguntas respondidas de " + getNotHiddenQuestionsCount(), Toast.LENGTH_SHORT).show();
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