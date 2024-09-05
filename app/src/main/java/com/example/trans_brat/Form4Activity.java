package com.example.trans_brat;

import android.app.DatePickerDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import java.util.Arrays;
import java.util.Calendar;

public class Form4Activity extends AppCompatActivity {
    private final String logId = "Form4Activity_LOG";
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
            "F3_S8_Q6"
    };

    /* Respostas para enviar para o formulário seguinte */
    private final String[] toSendAnswersIds = {
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
            "F4_S4_M8_Q6"
    };

    private final int[][] all_questions = {
            {0, R.id.section_0_question_1_input, 1, 1},
            {0, R.id.section_1_main_group_1_question_3_input, 0, 1},
            {0, R.id.section_1_main_group_1_question_4_input, 0, 1},
            {0, R.id.section_1_main_group_1_question_5_input, 0, 1},
            {0, R.id.section_1_main_group_1_question_6_input, 0, 1},
            {0, R.id.section_1_main_group_1_question_7_input, 0, 1},
            {0, R.id.section_1_main_group_1_question_8_input, 0, 1},
            {0, R.id.section_1_main_group_1_question_9_input, 0, 1},
            {0, R.id.section_1_main_group_1_question_10_input, 0, 1},
            {0, R.id.section_1_main_group_1_question_11_input, 0, 1},
            {0, R.id.section_1_main_group_1_question_12_radio_group, 2, 1},
            {0, R.id.section_1_main_group_2_question_2_input, 0, 2},
            {0, R.id.section_1_main_group_2_question_3_input, 0, 2},
            {0, R.id.section_1_main_group_3_question_2_input, 0, 3},
            {0, R.id.section_1_main_group_3_question_3_input, 0, 3},
            {0, R.id.section_1_main_group_3_question_4_input, 0, 3},
            {0, R.id.section_1_main_group_3_question_5_radio_group, 2, 3},
            {0, R.id.section_1_main_group_4_question_2_input, 0, 4},
            {0, R.id.section_1_main_group_4_question_3_input, 0, 4},
            {0, R.id.section_1_main_group_4_question_4_input, 0, 4},
            {0, R.id.section_1_main_group_4_question_5_input, 0, 4},
            {0, R.id.section_1_main_group_4_question_6_input, 0, 4},
            {0, R.id.section_1_main_group_4_question_7_input, 0, 4},
            {0, R.id.section_1_main_group_4_question_8_input, 0, 4},
            {0, R.id.section_1_main_group_4_question_9_radio_group, 2, 4},
            {0, R.id.section_1_main_group_5_question_2_input, 0, 5},
            {0, R.id.section_1_main_group_5_question_3_input, 0, 5},
            {0, R.id.section_1_main_group_5_question_4_input, 0, 5},
            {0, R.id.section_1_main_group_5_question_5_input, 0, 5},
            {0, R.id.section_1_main_group_5_question_6_input, 0, 5},
            {0, R.id.section_1_main_group_5_question_7_input, 0, 5},
            {0, R.id.section_1_main_group_5_question_8_input, 0, 5},
            {0, R.id.section_1_main_group_5_question_9_input, 0, 5},
            {0, R.id.section_1_main_group_5_question_10_input, 0, 5},
            {0, R.id.section_1_main_group_6_question_2_input, 0, 6},
            {0, R.id.section_1_main_group_6_question_3_input, 0, 6},
            {0, R.id.section_1_main_group_6_question_4_input, 0, 6},
            {0, R.id.section_1_main_group_6_question_5_input, 0, 6},
            {0, R.id.section_1_main_group_6_question_6_input, 0, 6},
            {0, R.id.section_1_main_group_7_question_2_input, 0, 7},
            {0, R.id.section_1_main_group_7_question_3_input, 0, 7},
            {0, R.id.section_1_main_group_7_question_4_input, 0, 7},
            {0, R.id.section_1_main_group_7_question_5_input, 0, 7},
            {0, R.id.section_1_main_group_7_question_6_input, 0, 7},
            {0, R.id.section_1_main_group_7_question_7_input, 0, 7},
            {0, R.id.section_1_main_group_7_question_8_input, 0, 7},
            {0, 0, 3, 8}, // 46
            {0, R.id.section_2_main_group_1_question_3_input, 0, 1},
            {0, R.id.section_2_main_group_1_question_4_input, 0, 1},
            {0, R.id.section_2_main_group_1_question_5_input, 0, 1},
            {0, R.id.section_2_main_group_1_question_6_input, 0, 1},
            {0, R.id.section_2_main_group_1_question_7_input, 0, 1},
            {0, R.id.section_2_main_group_1_question_8_input, 0, 1},
            {0, R.id.section_2_main_group_1_question_9_input, 0, 1},
            {0, R.id.section_2_main_group_1_question_10_input, 0, 1},
            {0, R.id.section_2_main_group_1_question_11_input, 0, 1},
            {0, R.id.section_2_main_group_1_question_12_radio_group, 2, 1},
            {0, R.id.section_2_main_group_2_question_2_input, 0, 2},
            {0, R.id.section_2_main_group_2_question_3_input, 0, 2},
            {0, R.id.section_2_main_group_3_question_2_input, 0, 3},
            {0, R.id.section_2_main_group_3_question_3_input, 0, 3},
            {0, R.id.section_2_main_group_3_question_4_input, 0, 3},
            {0, R.id.section_2_main_group_3_question_5_radio_group, 2, 3},
            {0, R.id.section_2_main_group_4_question_2_input, 0, 4},
            {0, R.id.section_2_main_group_4_question_3_input, 0, 4},
            {0, R.id.section_2_main_group_4_question_4_input, 0, 4},
            {0, R.id.section_2_main_group_4_question_5_input, 0, 4},
            {0, R.id.section_2_main_group_4_question_6_input, 0, 4},
            {0, R.id.section_2_main_group_4_question_7_input, 0, 4},
            {0, R.id.section_2_main_group_4_question_8_input, 0, 4},
            {0, R.id.section_2_main_group_4_question_9_radio_group, 2, 4},
            {0, R.id.section_2_main_group_5_question_2_input, 0, 5},
            {0, R.id.section_2_main_group_5_question_3_input, 0, 5},
            {0, R.id.section_2_main_group_5_question_4_input, 0, 5},
            {0, R.id.section_2_main_group_5_question_5_input, 0, 5},
            {0, R.id.section_2_main_group_5_question_6_input, 0, 5},
            {0, R.id.section_2_main_group_5_question_7_input, 0, 5},
            {0, R.id.section_2_main_group_5_question_8_input, 0, 5},
            {0, R.id.section_2_main_group_5_question_9_input, 0, 5},
            {0, R.id.section_2_main_group_5_question_10_input, 0, 5},
            {0, R.id.section_2_main_group_6_question_2_input, 0, 6},
            {0, R.id.section_2_main_group_6_question_3_input, 0, 6},
            {0, R.id.section_2_main_group_6_question_4_input, 0, 6},
            {0, R.id.section_2_main_group_6_question_5_input, 0, 6},
            {0, R.id.section_2_main_group_6_question_6_input, 0, 6},
            {0, R.id.section_2_main_group_7_question_2_input, 0, 7},
            {0, R.id.section_2_main_group_7_question_3_input, 0, 7},
            {0, R.id.section_2_main_group_7_question_4_input, 0, 7},
            {0, R.id.section_2_main_group_7_question_5_input, 0, 7},
            {0, R.id.section_2_main_group_7_question_6_input, 0, 7},
            {0, R.id.section_2_main_group_7_question_7_input, 0, 7},
            {0, R.id.section_2_main_group_7_question_8_input, 0, 7},
            {0, 1, 3, 8},
            {0, R.id.section_3_main_group_1_question_3_input, 0, 1},
            {0, R.id.section_3_main_group_1_question_4_input, 0, 1},
            {0, R.id.section_3_main_group_1_question_5_input, 0, 1},
            {0, R.id.section_3_main_group_1_question_6_input, 0, 1},
            {0, R.id.section_3_main_group_1_question_7_input, 0, 1},
            {0, R.id.section_3_main_group_1_question_8_input, 0, 1},
            {0, R.id.section_3_main_group_1_question_9_input, 0, 1},
            {0, R.id.section_3_main_group_1_question_10_input, 0, 1},
            {0, R.id.section_3_main_group_1_question_11_input, 0, 1},
            {0, R.id.section_3_main_group_1_question_12_radio_group, 2, 1},
            {0, R.id.section_3_main_group_2_question_2_input, 0, 2},
            {0, R.id.section_3_main_group_2_question_3_input, 0, 2},
            {0, R.id.section_3_main_group_3_question_2_input, 0, 3},
            {0, R.id.section_3_main_group_3_question_3_input, 0, 3},
            {0, R.id.section_3_main_group_3_question_4_input, 0, 3},
            {0, R.id.section_3_main_group_3_question_5_radio_group, 2, 3},
            {0, R.id.section_3_main_group_4_question_2_input, 0, 4},
            {0, R.id.section_3_main_group_4_question_3_input, 0, 4},
            {0, R.id.section_3_main_group_4_question_4_input, 0, 4},
            {0, R.id.section_3_main_group_4_question_5_input, 0, 4},
            {0, R.id.section_3_main_group_4_question_6_input, 0, 4},
            {0, R.id.section_3_main_group_4_question_7_input, 0, 4},
            {0, R.id.section_3_main_group_4_question_8_input, 0, 4},
            {0, R.id.section_3_main_group_4_question_9_radio_group, 2, 4},
            {0, R.id.section_3_main_group_5_question_2_input, 0, 5},
            {0, R.id.section_3_main_group_5_question_3_input, 0, 5},
            {0, R.id.section_3_main_group_5_question_4_input, 0, 5},
            {0, R.id.section_3_main_group_5_question_5_input, 0, 5},
            {0, R.id.section_3_main_group_5_question_6_input, 0, 5},
            {0, R.id.section_3_main_group_5_question_7_input, 0, 5},
            {0, R.id.section_3_main_group_5_question_8_input, 0, 5},
            {0, R.id.section_3_main_group_5_question_9_input, 0, 5},
            {0, R.id.section_3_main_group_5_question_10_input, 0, 5},
            {0, R.id.section_3_main_group_6_question_2_input, 0, 6},
            {0, R.id.section_3_main_group_6_question_3_input, 0, 6},
            {0, R.id.section_3_main_group_6_question_4_input, 0, 6},
            {0, R.id.section_3_main_group_6_question_5_input, 0, 6},
            {0, R.id.section_3_main_group_6_question_6_input, 0, 6},
            {0, R.id.section_3_main_group_7_question_2_input, 0, 7},
            {0, R.id.section_3_main_group_7_question_3_input, 0, 7},
            {0, R.id.section_3_main_group_7_question_4_input, 0, 7},
            {0, R.id.section_3_main_group_7_question_5_input, 0, 7},
            {0, R.id.section_3_main_group_7_question_6_input, 0, 7},
            {0, R.id.section_3_main_group_7_question_7_input, 0, 7},
            {0, R.id.section_3_main_group_7_question_8_input, 0, 7},
            {0, 2, 3, 8},
            {0, R.id.section_4_main_group_1_question_3_input, 0, 1},
            {0, R.id.section_4_main_group_1_question_4_input, 0, 1},
            {0, R.id.section_4_main_group_1_question_5_input, 0, 1},
            {0, R.id.section_4_main_group_1_question_6_input, 0, 1},
            {0, R.id.section_4_main_group_1_question_7_input, 0, 1},
            {0, R.id.section_4_main_group_1_question_8_input, 0, 1},
            {0, R.id.section_4_main_group_1_question_9_input, 0, 1},
            {0, R.id.section_4_main_group_1_question_10_input, 0, 1},
            {0, R.id.section_4_main_group_1_question_11_input, 0, 1},
            {0, R.id.section_4_main_group_1_question_12_radio_group, 2, 1},
            {0, R.id.section_4_main_group_2_question_2_input, 0, 2},
            {0, R.id.section_4_main_group_2_question_3_input, 0, 2},
            {0, R.id.section_4_main_group_3_question_2_input, 0, 3},
            {0, R.id.section_4_main_group_3_question_3_input, 0, 3},
            {0, R.id.section_4_main_group_3_question_4_input, 0, 3},
            {0, R.id.section_4_main_group_3_question_5_radio_group, 2, 3},
            {0, R.id.section_4_main_group_4_question_2_input, 0, 4},
            {0, R.id.section_4_main_group_4_question_3_input, 0, 4},
            {0, R.id.section_4_main_group_4_question_4_input, 0, 4},
            {0, R.id.section_4_main_group_4_question_5_input, 0, 4},
            {0, R.id.section_4_main_group_4_question_6_input, 0, 4},
            {0, R.id.section_4_main_group_4_question_7_input, 0, 4},
            {0, R.id.section_4_main_group_4_question_8_input, 0, 4},
            {0, R.id.section_4_main_group_4_question_9_radio_group, 2, 4},
            {0, R.id.section_4_main_group_5_question_2_input, 0, 5},
            {0, R.id.section_4_main_group_5_question_3_input, 0, 5},
            {0, R.id.section_4_main_group_5_question_4_input, 0, 5},
            {0, R.id.section_4_main_group_5_question_5_input, 0, 5},
            {0, R.id.section_4_main_group_5_question_6_input, 0, 5},
            {0, R.id.section_4_main_group_5_question_7_input, 0, 5},
            {0, R.id.section_4_main_group_5_question_8_input, 0, 5},
            {0, R.id.section_4_main_group_5_question_9_input, 0, 5},
            {0, R.id.section_4_main_group_5_question_10_input, 0, 5},
            {0, R.id.section_4_main_group_6_question_2_input, 0, 6},
            {0, R.id.section_4_main_group_6_question_3_input, 0, 6},
            {0, R.id.section_4_main_group_6_question_4_input, 0, 6},
            {0, R.id.section_4_main_group_6_question_5_input, 0, 6},
            {0, R.id.section_4_main_group_6_question_6_input, 0, 6},
            {0, R.id.section_4_main_group_7_question_2_input, 0, 7},
            {0, R.id.section_4_main_group_7_question_3_input, 0, 7},
            {0, R.id.section_4_main_group_7_question_4_input, 0, 7},
            {0, R.id.section_4_main_group_7_question_5_input, 0, 7},
            {0, R.id.section_4_main_group_7_question_6_input, 0, 7},
            {0, R.id.section_4_main_group_7_question_7_input, 0, 7},
            {0, R.id.section_4_main_group_7_question_8_input, 0, 7},
            {0, 3, 3, 8}
    };

    /* Definindo inputs obrigatórios */
    // [0][] - EditText
    // [1][] - Spinner
    // [2][] - RadioGroup
    // [3][] - Checkbox
    // [][X] - Seção de perguntas

    /* Grupos de perguntas */
    // [X][][] Ids das seções
    // [][X][] Ids dos grupos
    // [][][X] Ids dos botões de exibir/esconder
    private int[][] sections = {
            {R.id.section_1, R.id.section_1_main, R.id.section_1_expansion},
            {R.id.section_2, R.id.section_2_main, R.id.section_2_expansion},
            {R.id.section_3, R.id.section_3_main, R.id.section_3_expansion},
            {R.id.section_4, R.id.section_4_main, R.id.section_4_expansion}
    };

    /* Ids das perguntas que podem ser exibidas/escondidas */
    private final int[] toggleableQuestionsSectionsIds = {
            R.id.section_1_main_group_2,
            R.id.section_1_main_group_3,
            R.id.section_1_main_group_4,
            R.id.section_1_main_group_5,
            R.id.section_1_main_group_6,
            R.id.section_1_main_group_7,
            R.id.section_2_main_group_2,
            R.id.section_2_main_group_3,
            R.id.section_2_main_group_4,
            R.id.section_2_main_group_5,
            R.id.section_2_main_group_6,
            R.id.section_2_main_group_7,
            R.id.section_3_main_group_2,
            R.id.section_3_main_group_3,
            R.id.section_3_main_group_4,
            R.id.section_3_main_group_5,
            R.id.section_3_main_group_6,
            R.id.section_3_main_group_7,
            R.id.section_4_main_group_2,
            R.id.section_4_main_group_3,
            R.id.section_4_main_group_4,
            R.id.section_4_main_group_5,
            R.id.section_4_main_group_6,
            R.id.section_4_main_group_7
    };

    private int[][] hiddenQuestions = {
            {2, 4},
            {2, 4},
            {2, 4},
            {2, 4}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_4);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.form_4), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /* Obtendo respostas dos formulários anteriores */
        getPreviousFormAnswers();

        /* Evento para permitir apenas textos com letras maiúsculas */
        eventTextAllCaps();

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

        /* Desativando todos os botões de exibir/esconder grupos de perguntas */
        disableAllSectionsAndGroupsAndButtons();

        /* Evento de exibir/esconder seções ao selecionar uma opção no dropdown */
        eventDropdownToggleSections();

        /* Evento de exibir/esconder grupos de perguntas ao clicar em um botão */
        eventExpandButtonsToggleGroups();

        /* Adicionando opções aos dropdowns */
        dropdownQuestions();

        /* Marcando os dois RadioGroups como "não" */
        markRadioButton(R.id.section_1_main_group_1_question_12_radio_group, R.id.section_1_main_group_1_question_12_radio_2);
        markRadioButton(R.id.section_1_main_group_3_question_5_radio_group, R.id.section_1_main_group_3_question_5_radio_2);
        markRadioButton(R.id.section_2_main_group_1_question_12_radio_group, R.id.section_2_main_group_1_question_12_radio_2);
        markRadioButton(R.id.section_2_main_group_3_question_5_radio_group, R.id.section_2_main_group_3_question_5_radio_2);
        markRadioButton(R.id.section_3_main_group_1_question_12_radio_group, R.id.section_3_main_group_1_question_12_radio_2);
        markRadioButton(R.id.section_3_main_group_3_question_5_radio_group, R.id.section_3_main_group_3_question_5_radio_2);
        markRadioButton(R.id.section_4_main_group_1_question_12_radio_group, R.id.section_4_main_group_1_question_12_radio_2);
        markRadioButton(R.id.section_4_main_group_3_question_5_radio_group, R.id.section_4_main_group_3_question_5_radio_2);
        /* Exibir 3, 6 e 7 | Esconder 2, 4 e 5 */
        toggleQuestions(0, new int[] {1, 4, 5}, new int[] {0, 2, 3});
        toggleQuestions(1, new int[] {1, 4, 5}, new int[] {0, 2, 3});
        toggleQuestions(2, new int[] {1, 4, 5}, new int[] {0, 2, 3});
        toggleQuestions(3, new int[] {1, 4, 5}, new int[] {0, 2, 3});

        /* Evento para exibir/esconder partes do formulário ao marcar uma opção em um RadioGroup */
        editFormByRadioGroupEvent();

        /* Botões inferiores */
        Button buttonBack = findViewById(R.id.back_button);
        Button buttonCancel = findViewById(R.id.cancel_button);
        Button buttonNext = findViewById(R.id.next_button);

        /* (Voltar) Voltando para o formulário 3 */
        buttonBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        /* (Cancelar) Voltando para o menu principal */
        buttonCancel.setOnClickListener(v -> cancel());

        /* (Avançar) Avançando para o formulário 5 */
        buttonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Form4Activity.this, Form5Activity.class);
                try {
                    sendAnswersToNextForm(intent);
                } catch (Exception e) {
                    Log.e(logId, "Erro ao enviar respostas:" + e);
                }
                startActivity(intent);
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
            checkBox1Text = String.valueOf(true);
        }
        assert checkBox2 != null;
        if (checkBox2.isChecked()) {
            checkBox2Text = String.valueOf(true);
        }
        assert checkBox3 != null;
        if (checkBox3.isChecked()) {
            checkBox3Text = String.valueOf(true);
        }
        assert checkBox4 != null;
        if (checkBox4.isChecked()) {
            checkBox4Text = String.valueOf(true);
        }
        assert checkBox5 != null;
        if (checkBox5.isChecked()) {
            checkBox5Text = String.valueOf(true);
        }
        assert checkBox6 != null;
        if (checkBox6.isChecked()) {
            checkBox6Text = String.valueOf(true);
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

    /* Função para perguntas obrigatórias */
    private void requiredQuestions() {
        /* Definindo perguntas obrigatórias */
        int[] questions_ids = {
                R.id.section_0_question_1
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
                R.id.section_1_main_group_3_question_4_input,
                R.id.section_1_main_group_5_question_10_input,
                R.id.section_1_main_group_6_question_4_input,
                R.id.section_1_main_group_6_question_6_input,
                R.id.section_2_main_group_3_question_4_input,
                R.id.section_2_main_group_5_question_10_input,
                R.id.section_2_main_group_6_question_4_input,
                R.id.section_2_main_group_6_question_6_input,
                R.id.section_3_main_group_3_question_4_input,
                R.id.section_3_main_group_5_question_10_input,
                R.id.section_3_main_group_6_question_4_input,
                R.id.section_3_main_group_6_question_6_input,
                R.id.section_4_main_group_3_question_4_input,
                R.id.section_4_main_group_5_question_10_input,
                R.id.section_4_main_group_6_question_4_input,
                R.id.section_4_main_group_6_question_6_input,
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
        DatePickerDialog datePickerDialog = new DatePickerDialog(Form4Activity.this, new DatePickerDialog.OnDateSetListener() {
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
                R.id.section_1_main_group_1_question_3_input,
                R.id.section_2_main_group_1_question_3_input,
                R.id.section_3_main_group_1_question_3_input,
                R.id.section_4_main_group_1_question_3_input
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
                R.id.section_1_main_group_4_question_2_input,
                R.id.section_1_main_group_5_question_2_input,
                R.id.section_1_main_group_7_question_2_input,
                R.id.section_2_main_group_4_question_2_input,
                R.id.section_2_main_group_5_question_2_input,
                R.id.section_2_main_group_7_question_2_input,
                R.id.section_3_main_group_4_question_2_input,
                R.id.section_3_main_group_5_question_2_input,
                R.id.section_3_main_group_7_question_2_input,
                R.id.section_4_main_group_4_question_2_input,
                R.id.section_4_main_group_5_question_2_input,
                R.id.section_4_main_group_7_question_2_input
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
                R.id.section_1_main_group_2_question_3_input,
                R.id.section_2_main_group_2_question_3_input,
                R.id.section_3_main_group_2_question_3_input,
                R.id.section_4_main_group_2_question_3_input
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
                R.id.section_1_main_group_3_question_3_input,
                R.id.section_1_main_group_6_question_3_input,
                R.id.section_2_main_group_3_question_3_input,
                R.id.section_2_main_group_6_question_3_input,
                R.id.section_3_main_group_3_question_3_input,
                R.id.section_3_main_group_6_question_3_input,
                R.id.section_4_main_group_3_question_3_input,
                R.id.section_4_main_group_6_question_3_input
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
        /* Seção 1 */
        /* RadioGroup 1 */
        RadioGroup radioSection1MainGroup1Question12RadioGroup = findViewById(R.id.section_1_main_group_1_question_12_radio_group);
        RadioButton radioSection1MainGroup1Question12Radio1 = findViewById(R.id.section_1_main_group_1_question_12_radio_1); // Sim
        RadioButton radioSection1MainGroup1Question12Radio2 = findViewById(R.id.section_1_main_group_1_question_12_radio_2); // Não
        RadioGroup radioSection2MainGroup1Question12RadioGroup = findViewById(R.id.section_2_main_group_1_question_12_radio_group);
        RadioButton radioSection2MainGroup1Question12Radio1 = findViewById(R.id.section_2_main_group_1_question_12_radio_1); // Sim
        RadioButton radioSection2MainGroup1Question12Radio2 = findViewById(R.id.section_2_main_group_1_question_12_radio_2); // Não
        RadioGroup radioSection3MainGroup1Question12RadioGroup = findViewById(R.id.section_3_main_group_1_question_12_radio_group);
        RadioButton radioSection3MainGroup1Question12Radio1 = findViewById(R.id.section_3_main_group_1_question_12_radio_1); // Sim
        RadioButton radioSection3MainGroup1Question12Radio2 = findViewById(R.id.section_3_main_group_1_question_12_radio_2); // Não
        RadioGroup radioSection4MainGroup1Question12RadioGroup = findViewById(R.id.section_4_main_group_1_question_12_radio_group);
        RadioButton radioSection4MainGroup1Question12Radio1 = findViewById(R.id.section_4_main_group_1_question_12_radio_1); // Sim
        RadioButton radioSection4MainGroup1Question12Radio2 = findViewById(R.id.section_4_main_group_1_question_12_radio_2); // Não

        /* RadioGroup 2 */
        RadioButton radioSection1MainGroup3Question5Radio1 = findViewById(R.id.section_1_main_group_3_question_5_radio_1); // Sim
        RadioButton radioSection1MainGroup3Question5Radio2 = findViewById(R.id.section_1_main_group_3_question_5_radio_2); // Não
        RadioButton radioSection2MainGroup3Question5Radio1 = findViewById(R.id.section_2_main_group_3_question_5_radio_1); // Sim
        RadioButton radioSection2MainGroup3Question5Radio2 = findViewById(R.id.section_2_main_group_3_question_5_radio_2); // Não
        RadioButton radioSection3MainGroup3Question5Radio1 = findViewById(R.id.section_3_main_group_3_question_5_radio_1); // Sim
        RadioButton radioSection3MainGroup3Question5Radio2 = findViewById(R.id.section_3_main_group_3_question_5_radio_2); // Não
        RadioButton radioSection4MainGroup3Question5Radio1 = findViewById(R.id.section_4_main_group_3_question_5_radio_1); // Sim
        RadioButton radioSection4MainGroup3Question5Radio2 = findViewById(R.id.section_4_main_group_3_question_5_radio_2); // Não

        /* RadioGroup 3 */
        RadioGroup radioSection1MainGroup4Question9RadioGroup = findViewById(R.id.section_1_main_group_4_question_9_radio_group);
        RadioGroup radioSection2MainGroup4Question9RadioGroup = findViewById(R.id.section_2_main_group_4_question_9_radio_group);
        RadioGroup radioSection3MainGroup4Question9RadioGroup = findViewById(R.id.section_3_main_group_4_question_9_radio_group);
        RadioGroup radioSection4MainGroup4Question9RadioGroup = findViewById(R.id.section_4_main_group_4_question_9_radio_group);

        radioSection1MainGroup1Question12Radio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Exibir 2, 4, 6 e 7 | Esconder 3 e 5 */
                toggleQuestions(0, new int[] {0, 2, 4, 5}, new int[] {1, 3});
                markRadioButton(R.id.section_1_main_group_3_question_5_radio_group, R.id.section_1_main_group_3_question_5_radio_1);
                markRadioButton(R.id.section_1_main_group_4_question_9_radio_group, R.id.section_1_main_group_4_question_9_radio_2);
            }
        });
        radioSection2MainGroup1Question12Radio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Exibir 2, 4, 6 e 7 | Esconder 3 e 5 */
                toggleQuestions(1, new int[] {0, 2, 4, 5}, new int[] {1, 3});
                markRadioButton(R.id.section_2_main_group_3_question_5_radio_group, R.id.section_2_main_group_3_question_5_radio_1);
                markRadioButton(R.id.section_2_main_group_4_question_9_radio_group, R.id.section_2_main_group_4_question_9_radio_2);
            }
        });
        radioSection3MainGroup1Question12Radio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Exibir 2, 4, 6 e 7 | Esconder 3 e 5 */
                toggleQuestions(2, new int[] {0, 2, 4, 5}, new int[] {1, 3});
                markRadioButton(R.id.section_3_main_group_3_question_5_radio_group, R.id.section_3_main_group_3_question_5_radio_1);
                markRadioButton(R.id.section_3_main_group_4_question_9_radio_group, R.id.section_3_main_group_4_question_9_radio_2);
            }
        });
        radioSection4MainGroup1Question12Radio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Exibir 2, 4, 6 e 7 | Esconder 3 e 5 */
                toggleQuestions(3, new int[] {0, 2, 4, 5}, new int[] {1, 3});
                markRadioButton(R.id.section_4_main_group_3_question_5_radio_group, R.id.section_4_main_group_3_question_5_radio_1);
                markRadioButton(R.id.section_4_main_group_4_question_9_radio_group, R.id.section_4_main_group_4_question_9_radio_2);
            }
        });

        radioSection1MainGroup1Question12Radio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Exibir 3 e 5 | Esconder 2, 4, 6 e 7 */
                toggleQuestions(0, new int[] {1, 3}, new int[] {0, 2, 4, 5});
            }
        });
        radioSection2MainGroup1Question12Radio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Exibir 3 e 5 | Esconder 2, 4, 6 e 7 */
                toggleQuestions(1, new int[] {1, 3}, new int[] {0, 2, 4, 5});
            }
        });
        radioSection3MainGroup1Question12Radio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Exibir 3 e 5 | Esconder 2, 4, 6 e 7 */
                toggleQuestions(2, new int[] {1, 3}, new int[] {0, 2, 4, 5});
            }
        });
        radioSection4MainGroup1Question12Radio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Exibir 3 e 5 | Esconder 2, 4, 6 e 7 */
                toggleQuestions(3, new int[] {1, 3}, new int[] {0, 2, 4, 5});
            }
        });

        radioSection1MainGroup3Question5Radio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Exibir 3 e 5 | Esconder 2, 4, 6 e 7 */
                toggleQuestions(0, new int[] {1, 3}, new int[] {0, 2, 4, 5});
            }
        });
        radioSection2MainGroup3Question5Radio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Exibir 3 e 5 | Esconder 2, 4, 6 e 7 */
                toggleQuestions(1, new int[] {1, 3}, new int[] {0, 2, 4, 5});
            }
        });
        radioSection3MainGroup3Question5Radio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Exibir 3 e 5 | Esconder 2, 4, 6 e 7 */
                toggleQuestions(2, new int[] {1, 3}, new int[] {0, 2, 4, 5});
            }
        });
        radioSection4MainGroup3Question5Radio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Exibir 3 e 5 | Esconder 2, 4, 6 e 7 */
                toggleQuestions(3, new int[] {1, 3}, new int[] {0, 2, 4, 5});
            }
        });

        radioSection1MainGroup3Question5Radio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Exibir 3, 6 e 7 | Esconder 2, 4 e 5 */
                toggleQuestions(0, new int[] {1, 4, 5}, new int[] {0, 2, 3});
            }
        });
        radioSection2MainGroup3Question5Radio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Exibir 3, 6 e 7 | Esconder 2, 4 e 5 */
                toggleQuestions(1, new int[] {1, 4, 5}, new int[] {0, 2, 3});
            }
        });
        radioSection3MainGroup3Question5Radio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Exibir 3, 6 e 7 | Esconder 2, 4 e 5 */
                toggleQuestions(2, new int[] {1, 4, 5}, new int[] {0, 2, 3});
            }
        });
        radioSection4MainGroup3Question5Radio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Exibir 3, 6 e 7 | Esconder 2, 4 e 5 */
                toggleQuestions(3, new int[] {1, 4, 5}, new int[] {0, 2, 3});
            }
        });


        /* Desativando radioGroup 2 ao selecionar "Sim" no RadioGroup 1 */
        radioSection1MainGroup1Question12RadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.section_1_main_group_1_question_12_radio_1) {
                    // Desativando o RadioGroup 3
                    radioSection1MainGroup4Question9RadioGroup.setEnabled(false);
                    setRadioGroupClickable(radioSection1MainGroup4Question9RadioGroup, false);
                } else {
                    // Reativando o RadioGroup 3
                    radioSection1MainGroup4Question9RadioGroup.setEnabled(true);
                    setRadioGroupClickable(radioSection1MainGroup4Question9RadioGroup, true);
                }
            }
        });
        radioSection2MainGroup1Question12RadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.section_2_main_group_1_question_12_radio_1) {
                    // Desativando o RadioGroup 3
                    radioSection2MainGroup4Question9RadioGroup.setEnabled(false);
                    setRadioGroupClickable(radioSection2MainGroup4Question9RadioGroup, false);
                } else {
                    // Reativando o RadioGroup 3
                    radioSection2MainGroup4Question9RadioGroup.setEnabled(true);
                    setRadioGroupClickable(radioSection2MainGroup4Question9RadioGroup, true);
                }
            }
        });
        radioSection3MainGroup1Question12RadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.section_3_main_group_1_question_12_radio_1) {
                    // Desativando o RadioGroup 3
                    radioSection3MainGroup4Question9RadioGroup.setEnabled(false);
                    setRadioGroupClickable(radioSection3MainGroup4Question9RadioGroup, false);
                } else {
                    // Reativando o RadioGroup 3
                    radioSection3MainGroup4Question9RadioGroup.setEnabled(true);
                    setRadioGroupClickable(radioSection3MainGroup4Question9RadioGroup, true);
                }
            }
        });
        radioSection4MainGroup1Question12RadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.section_4_main_group_1_question_12_radio_1) {
                    // Desativando o RadioGroup 3
                    radioSection4MainGroup4Question9RadioGroup.setEnabled(false);
                    setRadioGroupClickable(radioSection4MainGroup4Question9RadioGroup, false);
                } else {
                    // Reativando o RadioGroup 3
                    radioSection4MainGroup4Question9RadioGroup.setEnabled(true);
                    setRadioGroupClickable(radioSection4MainGroup4Question9RadioGroup, true);
                }
            }
        });
    }

    /* Função para desativar todos os botões de exibir/esconder grupos de perguntas */
    private void disableAllSectionsAndGroupsAndButtons() {
        for(int[] section : sections) {
            LinearLayout sectionLayout = findViewById(section[0]);
            LinearLayout linearLayout = findViewById(section[1]);
            Button button = findViewById(section[2]);

            sectionLayout.setVisibility(View.GONE);
            groupCollapse(linearLayout);
            button.setVisibility(View.GONE);
        }
    }

    /* Função para dropdowns */
    private void dropdownQuestions() {
        int[] spinnerIds = {
                R.id.section_0_question_1_input
        };
        String[][] spinnerOptions = {
                {"1", "2", "3", "4", "5"}
        };

        for (int i = 0; i < spinnerIds.length; i++) {
            Spinner spinner = findViewById(spinnerIds[i]);
            String[] options = spinnerOptions[i];
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, options);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }
    }

    /* Evento de exibir/esconder seções selecionar uma opção no dropdown */
    private void eventDropdownToggleSections() {
        Spinner dropdown = findViewById(R.id.section_0_question_1_input);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (selectedItem.equals("1")) {
                    sectionCollapseMultiple(new int[] {2, 3, 4, 5});
                } else if (selectedItem.equals("2")) {
                    sectionCollapseMultiple(new int[] {3, 4, 5});
                    sectionExpandMultiple(new int[] {2});
                } else if (selectedItem.equals("3")) {
                    sectionCollapseMultiple(new int[] {4, 5});
                    sectionExpandMultiple(new int[] {2, 3});
                } else if (selectedItem.equals("4")) {
                    sectionCollapseMultiple(new int[] {5});
                    sectionExpandMultiple(new int[] {2, 3, 4});
                } else if (selectedItem.equals("5")) {
                    sectionExpandMultiple(new int[] {2, 3, 4, 5});
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Caso nenhum item esteja selecionado
            }
        });
    }

    /* Evento de exibir/esconder grupos de perguntas ao clicar em um botão */
    private void eventExpandButtonsToggleGroups() {
        for(int[] group : sections) {
            LinearLayout hiddenLayout = findViewById(group[1]);
            Button showButton = findViewById(group[2]);
            showButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    groupToggle(hiddenLayout);
                }
            });
        }
    }

    /* Função para exibir/esconder grupos de perguntas */
    private void groupToggle(LinearLayout linearLayout) {
        if (linearLayout.getVisibility() == View.VISIBLE) {
            groupCollapse(linearLayout);
        } else if (linearLayout.getVisibility() == View.GONE) {
            groupExpand(linearLayout);
        }
    }

    /* Função para esconder grupos de perguntas */
    private void groupCollapse(LinearLayout linearLayout) {
        linearLayout.setVisibility(View.GONE);
    }

    /* Função para exibir uma array de grupos de perguntas */
    private void groupCollapseMultiple(LinearLayout[] linearLayouts) {
        for(LinearLayout linearLayout : linearLayouts) {
            groupCollapse(linearLayout);
        }
    }

    /* Função para exibir grupos de perguntas */
    private void groupExpand(LinearLayout linearLayout) {
        linearLayout.setVisibility(View.VISIBLE);
    }

    /* Função para exibir uma array de grupos de perguntas */
    private void groupExpandMultiple(LinearLayout[] linearLayouts) {
        for(LinearLayout linearLayout : linearLayouts) {
            groupExpand(linearLayout);
        }
    }

    /* Função para esconder seções */
    private void sectionCollapse(int sectionsIndex) {
        LinearLayout section = findViewById(sections[sectionsIndex][0]);
        section.setVisibility(View.GONE);
        Button button = findViewById(sections[sectionsIndex][2]);
        button.setVisibility(View.GONE);
    }

    /* Função para esconder uma array de seções */
    private void sectionCollapseMultiple(int[] sectionsIndexes) {
        for(int sectionIndex : sectionsIndexes) {
            sectionCollapse(sectionIndex - 2);
        }
    }

    /* Função para exibir seções */
    private void sectionExpand(int sectionsIndex) {
        LinearLayout section = findViewById(sections[sectionsIndex][0]);
        section.setVisibility(View.VISIBLE);
        Button button = findViewById(sections[sectionsIndex][2]);
        button.setVisibility(View.VISIBLE);
    }

    /* Função para exibir uma array de seções */
    private void sectionExpandMultiple(int[] sectionsIndexes) {
        for(int sectionIndex : sectionsIndexes) {
            sectionExpand(sectionIndex - 2);
        }
    }

    /* Função para marcar um RadioButton de um RadioGroup */
    private void markRadioButton(int radioGroupId, int radioButtonId) {
        RadioGroup radioGroup = findViewById(radioGroupId);
        radioGroup.check(radioButtonId);
    }

    /* Função ao cancelar formulário */
    private void cancel() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Form4Activity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.alert_dialog_warning, null);
        builder.setView(dialogLayout);
        builder.setTitle("Atenção!");
        builder.setMessage("Ao continuar, seu e-brat será cancelado e seu processo será perdido.");
        builder.setIcon(R.drawable.icon_warning);
        builder.setPositiveButton("Continuar", (dialog, which) -> {
            dialog.dismiss();
            Intent intent = new Intent(Form4Activity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        builder.setNegativeButton("Voltar", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /* Função para exibir/esconder múltiplas perguntas de uma seção */
    private void toggleQuestions(int section, int[] show, int[] hide) {
        /* Exibindo */
        for(int s : show) {
            showQuestions(section, s);
        }
        /* Escondendo */
        for(int h : hide) {
            hideQuestions(section, h);
        }
        /* Alterando a variável de seções de perguntas obrigatórias */
        setHiddenQuestionsVar(section, hide);
    }

    /* Função para esconder perguntas */
    private void hideQuestions(int section, int sectionIndex) {
        //Toast.makeText(Form4Activity.this, (section + 1) + " * " + (sectionIndex + 1) + " = " + ((section + 1) * (sectionIndex + 1)), Toast.LENGTH_SHORT).show();
        LinearLayout linearLayout = findViewById(toggleableQuestionsSectionsIds[(section * (toggleableQuestionsSectionsIds.length / 4)) + sectionIndex]);
        linearLayout.setVisibility(View.GONE);
    }

    /* Função para exibir perguntas */
    private void showQuestions(int section, int sectionIndex) {
        LinearLayout linearLayout = findViewById(toggleableQuestionsSectionsIds[(section * (toggleableQuestionsSectionsIds.length / 4)) + sectionIndex]);
        linearLayout.setVisibility(View.VISIBLE);
    }

    /* Função para alterar a variável de esconder seções de perguntas */
    private void setHiddenQuestionsVar(int section, int[] ids) {
        hiddenQuestions[section] = ids;
    }

    /* Função para definir que um RadioGroup seja clicável ou não */
    private void setRadioGroupClickable(RadioGroup radioGroup, boolean clickable) {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setEnabled(clickable);
        }
    }
}